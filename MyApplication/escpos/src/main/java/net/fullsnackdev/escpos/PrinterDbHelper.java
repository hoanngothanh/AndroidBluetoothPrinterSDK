package net.fullsnackdev.escpos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class PrinterDbHelper extends SQLiteOpenHelper {
    //Db info
    private static final String DATABASE_NAME = "printerDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_PRINTER = "printer";

    //printer table columns
    private static final String KEY_PRINTER_ID = "id";
    private static final String KEY_PRINTER_NAME = "name";
    private static final String KEY_PRINTER_MAC = "mac";
    private static final String KEY_PRINTER_DEFAULT = "isDefault";

    private static PrinterDbHelper sInstance;

    public static synchronized PrinterDbHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new PrinterDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public PrinterDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public PrinterDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public PrinterDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRINTER_TABLE = "CREATE TABLE " + TABLE_PRINTER +
                "(" +
                KEY_PRINTER_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_PRINTER_NAME + " TEXT, " +
                KEY_PRINTER_MAC + " TEXT, " +
                KEY_PRINTER_DEFAULT + " INTEGER " +
                ")";
        db.execSQL(CREATE_PRINTER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRINTER);

            onCreate(db);
        }
    }

    public void addPrinter(IpayPrinter ipayPrinter) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            // The user might already exist in the database (i.e. the same user created multiple posts).
            long userId = addOrUpdatePrinter(ipayPrinter);

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("HOAN", "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    public long addOrUpdatePrinter(IpayPrinter user) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_PRINTER_NAME, user.name);
            values.put(KEY_PRINTER_MAC, user.macAddress);
            values.put(KEY_PRINTER_DEFAULT, user.isDefault);
            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                    KEY_PRINTER_ID, TABLE_PRINTER, KEY_PRINTER_MAC);
            Cursor cursorSe = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(user.macAddress)});
            if (cursorSe.moveToFirst()) {
                db.setTransactionSuccessful();
                return 0;

            } else {
                // user with this userName did not already exist, so insert new user
                userId = db.insertOrThrow(TABLE_PRINTER, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d("HOAN", "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return userId;
    }

    public void updateActivePrinter(IpayPrinter ipayPrinter) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        String getActivePrinter = String.format("SELECT %s FROM %s WHERE %s = ?",
                KEY_PRINTER_ID, TABLE_PRINTER, KEY_PRINTER_DEFAULT);


        Cursor cursorActive = db.rawQuery(getActivePrinter, new String[]{String.valueOf(1)});
        try {
            if (cursorActive != null && cursorActive.getCount() > 0) {
                if (cursorActive.moveToFirst()) {
                    IpayPrinter printer = new IpayPrinter();
                    printer.id = cursorActive.getInt(1);
                    printer.name = cursorActive.getString(2);
                    printer.isDefault = cursorActive.getInt(3);
                    addOrUpdatePrinter(printer);

                    db.setTransactionSuccessful();
                }
            }
        } finally {
            if (cursorActive != null && !cursorActive.isClosed()) {
                cursorActive.close();
            }
        }
        addOrUpdatePrinter(ipayPrinter);
    }

    public List<IpayPrinter> getAllPPrinter() {
        List<IpayPrinter> posts = new ArrayList<>();

        // SELECT * FROM POSTS
        // LEFT OUTER JOIN USERS
        // ON POSTS.KEY_POST_USER_ID_FK = USERS.KEY_USER_ID
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s",
                        TABLE_PRINTER
                );

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {

                    IpayPrinter newPost = new IpayPrinter();
                    newPost.name = cursor.getString(cursor.getColumnIndex(KEY_PRINTER_NAME));
                    newPost.macAddress = cursor.getString(cursor.getColumnIndex(KEY_PRINTER_MAC));
                    newPost.isDefault = cursor.getInt(cursor.getColumnIndex(KEY_PRINTER_DEFAULT));
                    posts.add(newPost);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("HOAN", "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return posts;
    }

    // Delete all posts and users in the database
    public void deletePrinter(IpayPrinter ipayPrinter) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(TABLE_PRINTER, KEY_PRINTER_MAC + "=?", new String[]{ipayPrinter.macAddress});
//            db.delete(TABLE_USERS, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("HOAN", "Error while trying to delete all posts and users");
        } finally {
            db.endTransaction();
        }
    }

    public IpayPrinter getCurrentActive() {
        SQLiteDatabase db = getWritableDatabase();


        db.beginTransaction();

        String getActivePrinter = String.format("SELECT %s FROM %s WHERE %s = ?",
                "*", TABLE_PRINTER, KEY_PRINTER_DEFAULT);

        IpayPrinter printer = new IpayPrinter();
        Cursor cursorActive = db.rawQuery(getActivePrinter, new String[]{String.valueOf(1)});
        try {
            if (cursorActive != null && cursorActive.getCount() > 0) {
                if (cursorActive.moveToFirst()) {

                    printer.id = cursorActive.getInt(0);
                    printer.name = cursorActive.getString(1);
                    printer.macAddress = cursorActive.getString(2);
                    printer.isDefault = cursorActive.getInt(3);
                    db.setTransactionSuccessful();
                }
            }
        } finally {
            if (cursorActive != null && !cursorActive.isClosed()) {
                cursorActive.close();
            }
            db.endTransaction();
        }
        return printer;
    }

    public void update(IpayPrinter ipayPrinter) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_PRINTER_NAME, ipayPrinter.name);
            values.put(KEY_PRINTER_MAC, ipayPrinter.macAddress);
            values.put(KEY_PRINTER_DEFAULT, ipayPrinter.isDefault);
            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            int rows = db.update(TABLE_PRINTER, values, KEY_PRINTER_MAC + "= ?", new String[]{ipayPrinter.macAddress});

            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                        KEY_PRINTER_ID, TABLE_PRINTER, KEY_PRINTER_MAC);
                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(ipayPrinter.macAddress)});
                try {
                    if (cursor.moveToFirst()) {
                        userId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                // user with this userName did not already exist, so insert new user
                userId = db.insertOrThrow(TABLE_PRINTER, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d("HOAN", "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return;
    }
}

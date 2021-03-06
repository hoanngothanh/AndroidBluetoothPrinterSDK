package net.fullsnackdev.escpos;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import net.fullsnackdev.escpos.models.HistoryDetailRes;
import net.fullsnackdev.escpos.models.HistoryOrderRes;
import net.fullsnackdev.escpos.print.GPrinterCommand;
import net.fullsnackdev.escpos.print.PrintMsgEvent;
import net.fullsnackdev.escpos.print.PrintPic;
import net.fullsnackdev.escpos.print.PrintQueue;
import net.fullsnackdev.escpos.print.PrintUtil;
import net.fullsnackdev.escpos.print.PrinterMsgType;
import net.fullsnackdev.escpos.printutil.PrintOrderDataMaker;
import net.fullsnackdev.escpos.printutil.PrinterWriter;
import net.fullsnackdev.escpos.printutil.PrinterWriter58mm;

import java.io.BufferedInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by liuguirong on 8/1/17.
 * <p/>
 * print ticket service
 */
public class BtService extends IntentService {
    public static final String KEY_DATA_TRANSACTION = "transaction";
    public static final String KEY_DATA_PRODUCT = "product";
    public static final String KEY_PRINT_MERCHANT_CPY = "print_merchant_cpy";

    private HistoryDetailRes mHistoryDetailRes;
    private ArrayList<HistoryOrderRes> mHistoryOrderRes;

    public BtService() {
        super("BtService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BtService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        if (intent.getAction().equals(PrintUtil.ACTION_PRINT_CUSTOMER)) {
            mHistoryDetailRes = intent.getParcelableExtra(KEY_DATA_TRANSACTION);
            mHistoryOrderRes = intent.getParcelableArrayListExtra(KEY_DATA_PRODUCT);
            int merchantPrint = intent.getIntExtra(KEY_PRINT_MERCHANT_CPY, 1);
            printTest(mHistoryDetailRes, mHistoryOrderRes, merchantPrint);
        } else if (intent.getAction().equals(PrintUtil.ACTION_PRINT_MERCHANT)) {
            mHistoryDetailRes = intent.getParcelableExtra(KEY_DATA_TRANSACTION);
            mHistoryOrderRes = intent.getParcelableArrayListExtra(KEY_DATA_PRODUCT);
            int merchantPrint = intent.getIntExtra(KEY_PRINT_MERCHANT_CPY, 1);

            printMerchant(mHistoryDetailRes, mHistoryOrderRes, 1);

        } else if (intent.getAction().equals(PrintUtil.ACTION_PRINT_TEST_TWO)) {
            printTesttwo(3);
        } else if (intent.getAction().equals(PrintUtil.ACTION_PRINT_BITMAP)) {
            printBitmapTest();
        }

    }

    private void printTest(HistoryDetailRes historyDetailRes, ArrayList<HistoryOrderRes> historyOrderRes, int merchantcopy) {
        PrintOrderDataMaker printOrderDataMaker = new PrintOrderDataMaker(this, "", PrinterWriter58mm.TYPE_58, PrinterWriter.HEIGHT_PARTING_DEFAULT, historyDetailRes, historyOrderRes);
        ArrayList<byte[]> printData = (ArrayList<byte[]>) printOrderDataMaker.getPrintData(PrinterWriter58mm.TYPE_58);

        if (merchantcopy == 2) {
            EventBus.getDefault().post(new PrintMsgEvent(PrinterMsgType.MESSAGE_MERCHANT_RECEIPT, "print"));
        } else if (merchantcopy == 0) {
            ArrayList<byte[]> printDataMerchant = (ArrayList<byte[]>) printOrderDataMaker.getPrintDataMerchant(PrinterWriter58mm.TYPE_58);
            printData.addAll(printDataMerchant);
        }
        PrintQueue.getQueue(getApplicationContext()).add(printData);
    }

    private void printMerchant(HistoryDetailRes historyDetailRes, ArrayList<HistoryOrderRes> historyOrderRes, int merchantcopy) {
        PrintOrderDataMaker printOrderDataMaker = new PrintOrderDataMaker(this, "", PrinterWriter58mm.TYPE_58, PrinterWriter.HEIGHT_PARTING_DEFAULT, historyDetailRes, historyOrderRes);
        ArrayList<byte[]> printDataMerchant = (ArrayList<byte[]>) printOrderDataMaker.getPrintDataMerchant(PrinterWriter58mm.TYPE_58);
        PrintQueue.getQueue(getApplicationContext()).add(printDataMerchant);

    }

    /**
     * 打印几遍
     *
     * @param num
     */
    private void printTesttwo(int num) {
        try {
            ArrayList<byte[]> bytes = new ArrayList<byte[]>();
            for (int i = 0; i < num; i++) {
                String message = "蓝牙打印测试\n蓝牙打印测试\n蓝牙打印测试\n\n";
                bytes.add(GPrinterCommand.reset);
                bytes.add(message.getBytes("gbk"));
                bytes.add(GPrinterCommand
                        .print);
                bytes.add(GPrinterCommand.print);
                bytes.add(GPrinterCommand.print);
            }
            PrintQueue.getQueue(getApplicationContext()).add(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void print(byte[] byteArrayExtra) {
        if (null == byteArrayExtra || byteArrayExtra.length <= 0) {
            return;
        }
        PrintQueue.getQueue(getApplicationContext()).add(byteArrayExtra);
    }

    private void printBitmapTest() {
        BufferedInputStream bis;
        try {
            bis = new BufferedInputStream(getAssets().open(
                    "icon_empty_bg.bmp"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeStream(bis);
        PrintPic printPic = PrintPic.getInstance();
        printPic.init(bitmap);
        if (null != bitmap) {
            if (bitmap.isRecycled()) {
                bitmap = null;
            } else {
                bitmap.recycle();
                bitmap = null;
            }
        }
        byte[] bytes = printPic.printDraw();
        ArrayList<byte[]> printBytes = new ArrayList<byte[]>();
        printBytes.add(GPrinterCommand.reset);
        printBytes.add(GPrinterCommand.print);
        printBytes.add(bytes);
        Log.e("BtService", "image bytes size is :" + bytes.length);
        printBytes.add(GPrinterCommand.print);
        PrintQueue.getQueue(getApplicationContext()).add(bytes);
    }

    private void printPainting() {
        byte[] bytes = PrintPic.getInstance().printDraw();
        ArrayList<byte[]> printBytes = new ArrayList<byte[]>();
        printBytes.add(GPrinterCommand.reset);
        printBytes.add(GPrinterCommand.print);
        printBytes.add(bytes);
        Log.e("BtService", "image bytes size is :" + bytes.length);
        printBytes.add(GPrinterCommand.print);
        PrintQueue.getQueue(getApplicationContext()).add(bytes);
    }
}
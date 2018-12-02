package net.fullsnackdev.escpos.printutil;

import android.content.Context;

import net.fullsnackdev.escpos.R;
import net.fullsnackdev.escpos.models.HistoryDetailRes;
import net.fullsnackdev.escpos.models.HistoryOrderRes;

import java.util.ArrayList;
import java.util.List;


/**
 * 测试数据生成器
 * Created by liuguirong on 8/1/17.
 */

public class PrintOrderDataMaker implements PrintDataMaker {


    private String qr;
    private int width;
    private int height;
    Context btService;
    private HistoryDetailRes mHistoryDetailRes;
    private ArrayList<HistoryOrderRes> mHistoryOrderRes;
    private String remark = "微点筷客推出了餐厅管理系统，可用手机快速接单（来自客户的预订订单），进行订单管理、后厨管理等管理餐厅。";


    public PrintOrderDataMaker(Context btService, String qr, int width, int height,
                               HistoryDetailRes historyDetailRes, ArrayList<HistoryOrderRes> historyOrderRes) {
        this.qr = qr;
        this.width = width;
        this.height = height;
        this.btService = btService;
        this.mHistoryDetailRes = historyDetailRes;
        this.mHistoryOrderRes = historyOrderRes;
    }


    @Override
    public List<byte[]> getPrintData(int type) {
        ArrayList<byte[]> data = new ArrayList<>();

        try {
            PrinterWriter printer;
            printer = type == PrinterWriter58mm.TYPE_58 ? new PrinterWriter58mm(height, width) : new PrinterWriter80mm(height, width);
            printer.setAlignCenter();
            data.add(printer.getDataAndReset());
            printer.getDataAndReset();
            printer.reset();
            ArrayList<byte[]> image1 = printer.getImageByte(btService.getResources(), R.drawable.trick);
            data.addAll(image1);
            printer.setAlignLeft();
            printer.printLineFeed();

            printer.printLineFeed();
            printer.setAlignCenter();
            printer.setEmphasizedOff();
            printer.setFontSize(0);
            printer.print("iPay88 (M) Sdn Bhd");
            printer.printLineFeed();
            printer.print("Suite 2B-20-1, 20th Floor");
            printer.printLineFeed();
            printer.print("Block 2B, Plaza Sentral");
            printer.printLineFeed();
            printer.print("50470 Kuala Lumpur, Malaysia");
            printer.printLineFeed();
            printer.print("N9NL10114660");
            printer.setEmphasizedOff();
            printer.printLineFeed();

            printer.printLineFeed();
            printer.setFontSize(0);
            printer.setAlignLeft();
            printer.printLineDouble();
            printer.print("Ref.No:" + mHistoryDetailRes.refNo);
            printer.printLineFeed();
            if (mHistoryDetailRes.paymentDate != null) {
                String
                        date = mHistoryDetailRes.paymentDate.substring(0, mHistoryDetailRes.paymentDate.lastIndexOf(":")).replace("T", " ");
                printer.print("Date/Time:" + date);
                printer.printLineFeed();
            }

            printer.setAlignLeft();
            printer.print("Payment method:" + mHistoryDetailRes.paymentType);
            printer.printLineFeed();




            if (mHistoryOrderRes!=null && mHistoryOrderRes.size()>0) {

                printer.printLine();
                printer.printLineFeed();
                printer.setAlignCenter();
//                 printer.print("菜品信息");
//                 printer.printLineFeed();
                printer.setAlignCenter();
                printer.printInOneLine("Quantity", "Price", "Amount", 0);
                printer.printLine();
//                 printer.printLineFeed();
                for (int i = 0; i < mHistoryOrderRes.size(); i++) {
//                     printer.printInOneLine(mHistoryOrderRes.get(i).getProductName().trim()
//                             .substring(0,Math.min(12,mHistoryOrderRes.get(i).getProductName().trim().length())), "X" + mHistoryOrderRes.get(i).ProductQuatity, mHistoryOrderRes.get(i).Currency +" " +
//                             mHistoryOrderRes.get(i).Amount, 0);
                    printer.setAlignLeft();
                    printer.print(mHistoryOrderRes.get(i).ProductId +" "+mHistoryOrderRes.get(i).getProductName().trim());
                    printer.printLineFeed();
                    printer.setAlignCenter();
                    printer.printInOneLine(mHistoryOrderRes.get(i).getProductQuatity(), mHistoryOrderRes.get(i).getPrice(), mHistoryOrderRes.get(i).Amount.toString(),0);
                    printer.printLineFeed();
                }
                printer.printLine();
                printer.printLineFeed();
            }


            printer.setAlignLeft();
            printer.printInOneLine("AMOUNT: " ,mHistoryDetailRes.currency + " " + mHistoryDetailRes.amount,0);
            printer.printLineFeed();

            printer.printLineDouble();
            printer.printLineFeed();
            printer.printLineFeed();
            printer.setAlignCenter();
            printer.print("*** Customer Copy ***");
            printer.printLineFeed();
            printer.print("*** 持卡人存根 ***");
            printer.printLineFeed();
            printer.print("THANK YOU AND COME AGAIN");
            printer.printLineFeed();
            printer.print("谢谢惠顾，请再光临");
            printer.printLineFeed();
            printer.printLineFeed();
            printer.printLineFeed();
            printer.feedPaperCutPartial();

            data.add(printer.getDataAndClose());
            return data;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }


     public List<byte[]> getPrintDataMerchant(int type)  {
         ArrayList<byte[]> data = new ArrayList<>();

         try {
             PrinterWriter printer;
             printer = type == PrinterWriter58mm.TYPE_58 ? new PrinterWriter58mm(height, width) : new PrinterWriter80mm(height, width);
             printer.setAlignCenter();
             data.add(printer.getDataAndReset());
             printer.getDataAndReset();
             printer.reset();
             ArrayList<byte[]> image1 = printer.getImageByte(btService.getResources(), R.drawable.trick);
             data.addAll(image1);
             printer.setAlignLeft();
             printer.printLineFeed();

             printer.printLineFeed();
             printer.setAlignCenter();
             printer.setEmphasizedOff();
             printer.setFontSize(0);
             printer.print("iPay88 (M) Sdn Bhd");
             printer.printLineFeed();
             printer.print("Suite 2B-20-1, 20th Floor");
             printer.printLineFeed();
             printer.print("Block 2B, Plaza Sentral");
             printer.printLineFeed();
             printer.print("50470 Kuala Lumpur, Malaysia");
             printer.printLineFeed();
             printer.print("N9NL10114660");
             printer.setEmphasizedOff();
             printer.printLineFeed();

             printer.printLineFeed();
             printer.setFontSize(0);
             printer.setAlignLeft();
             printer.printLineDouble();
             printer.print("Ref.No:" + mHistoryDetailRes.refNo);
             printer.printLineFeed();
             if (mHistoryDetailRes.paymentDate != null) {
                 String
                         date = mHistoryDetailRes.paymentDate.substring(0, mHistoryDetailRes.paymentDate.lastIndexOf(":")).replace("T", " ");
                 printer.print("Date/Time:" + date);
                 printer.printLineFeed();
             }

             printer.setAlignLeft();
             printer.print("Payment method:" + mHistoryDetailRes.paymentType);
             printer.printLineFeed();




             if (mHistoryOrderRes!=null && mHistoryOrderRes.size()>0) {

                 printer.printLine();
                 printer.printLineFeed();
                 printer.setAlignCenter();
//                 printer.print("菜品信息");
//                 printer.printLineFeed();
                 printer.setAlignCenter();
                 printer.printInOneLine("Quantity", "Price", "Amount", -1);
                 printer.printLine();
//                 printer.printLineFeed();
                 for (int i = 0; i < mHistoryOrderRes.size(); i++) {
//                     printer.printInOneLine(mHistoryOrderRes.get(i).getProductName().trim()
//                             .substring(0,Math.min(12,mHistoryOrderRes.get(i).getProductName().trim().length())), "X" + mHistoryOrderRes.get(i).ProductQuatity, mHistoryOrderRes.get(i).Currency +" " +
//                             mHistoryOrderRes.get(i).Amount, 0);
                     printer.setAlignLeft();
                     printer.print(mHistoryOrderRes.get(i).ProductId +" "+mHistoryOrderRes.get(i).getProductName().trim());
                     printer.printLineFeed();
                     printer.setAlignCenter();
                     printer.printInOneLine(mHistoryOrderRes.get(i).getProductQuatity(), mHistoryOrderRes.get(i).getPrice(), mHistoryOrderRes.get(i).Amount.toString(),0);
                     printer.printLineFeed();
                 }
                 printer.printLine();
                 printer.printLineFeed();
             }


             printer.setAlignLeft();
             printer.printInOneLine("AMOUNT: " ,mHistoryDetailRes.currency + " " + mHistoryDetailRes.amount,0);
             printer.printLineFeed();

             printer.printLineDouble();
             printer.printLineFeed();
             printer.printLineFeed();
             printer.setAlignCenter();
             printer.print("*** Merchant Copy ***");
             printer.printLineFeed();
             printer.print("*** 商户存根 ***");
             printer.printLineFeed();
             printer.print("THANK YOU AND COME AGAIN");
             printer.printLineFeed();
             printer.print("谢谢惠顾，请再光临");
             printer.printLineFeed();
             printer.printLineFeed();
             printer.printLineFeed();
             printer.feedPaperCutPartial();

             data.add(printer.getDataAndClose());
             return data;
         } catch (Exception e) {
             return new ArrayList<>();
         }
    }
}

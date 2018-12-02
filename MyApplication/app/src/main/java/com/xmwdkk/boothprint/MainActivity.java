package com.xmwdkk.boothprint;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.xmwdkk.boothprint.util.ToastUtil;

import net.fullsnackdev.escpos.BtService;
import net.fullsnackdev.escpos.base.AppInfo;
import net.fullsnackdev.escpos.bt.BluetoothActivity;
import net.fullsnackdev.escpos.models.HistoryDetailRes;
import net.fullsnackdev.escpos.models.HistoryOrderRes;
import net.fullsnackdev.escpos.print.PrintMsgEvent;
import net.fullsnackdev.escpos.print.PrintUtil;
import net.fullsnackdev.escpos.print.PrinterMsgType;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/***
 *  Created by liugruirong on 2017/8/3.
 */
public class MainActivity extends BluetoothActivity implements View.OnClickListener {

     TextView tv_bluename;
     TextView tv_blueadress;
      boolean mBtEnable = true;
    int PERMISSION_REQUEST_COARSE_LOCATION=2;
    /**
     * bluetooth adapter
     */
    BluetoothAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_bluename =findViewById(R.id.tv_bluename);
        tv_blueadress =findViewById(R.id.tv_blueadress);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
        //6.0以上的手机要地理位置权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
        }
        EventBus.getDefault().register(MainActivity.this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        BluetoothController.init(this);
    }




    @Override
    public void btStatusChanged(Intent intent) {
        super.btStatusChanged(intent);
        BluetoothController.init(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button4:
             startActivity(new Intent(MainActivity.this,SearchBluetoothActivity.class));
                break;
            case R.id.button5:
                if (TextUtils.isEmpty(AppInfo.btAddress)){
                    ToastUtil.showToast(MainActivity.this,"请连接蓝牙...");
                    startActivity(new Intent(MainActivity.this,SearchBluetoothActivity.class));
                }else {
                    if ( mAdapter.getState()==BluetoothAdapter.STATE_OFF ){//蓝牙被关闭时强制打开
                         mAdapter.enable();
                        ToastUtil.showToast(MainActivity.this,"蓝牙被关闭请打开...");
                    }else {
                        ToastUtil.showToast(MainActivity.this,"打印测试...");
                        Intent intent = new Intent(getApplicationContext(), BtService.class);
                        HistoryDetailRes historyDetailRes = new HistoryDetailRes();
                        historyDetailRes.amount = 8.00;
                        historyDetailRes.paymentType = "AliPay";
                        historyDetailRes.refNo = "D201811089675";
                        historyDetailRes.currency = "MYR";

                        HistoryOrderRes historyOrderRes1 = new HistoryOrderRes();
                        historyOrderRes1.setProductQuatity("2");
                        historyOrderRes1.setPrice("1.00");
                        historyOrderRes1.Amount = 2d;
                        historyOrderRes1.setProductId("12345");
                        historyOrderRes1.setProductName("Apple");
                        historyOrderRes1.setCurrency("MYR");
                        HistoryOrderRes historyOrderRes2 = new HistoryOrderRes();
                        historyOrderRes2.setProductQuatity("3");
                        historyOrderRes2.Amount =6d;
                        historyOrderRes2.setPrice("2.00");
                        historyOrderRes2.setProductId("222222");
                        historyOrderRes2.setProductName("Orange");
                        historyOrderRes2.setCurrency("MYR");
                        ArrayList<HistoryOrderRes> list = new ArrayList<>();
                        list.add(historyOrderRes1);
                        list.add(historyOrderRes2);
                        intent.putExtra(BtService.KEY_DATA_TRANSACTION, historyDetailRes);
                        intent.putParcelableArrayListExtra(BtService.KEY_DATA_PRODUCT,list);
                        intent.putExtra(BtService.KEY_PRINT_MERCHANT_CPY,true);
                        intent.setAction(PrintUtil.ACTION_PRINT_TEST);
                        startService(intent);
                    }

                }
                break;
            case R.id.button6:
                if (TextUtils.isEmpty(AppInfo.btAddress)){
                    ToastUtil.showToast(MainActivity.this,"请连接蓝牙...");
                    startActivity(new Intent(MainActivity.this,SearchBluetoothActivity.class));
                }else {
                    ToastUtil.showToast(MainActivity.this,"打印测试...");
                    Intent intent2 = new Intent(getApplicationContext(), BtService.class);
                    intent2.setAction(PrintUtil.ACTION_PRINT_TEST_TWO);
                    startService(intent2);

                }
                case R.id.button:
                if (TextUtils.isEmpty(AppInfo.btAddress)){
                    ToastUtil.showToast(MainActivity.this,"请连接蓝牙...");
                    startActivity(new Intent(MainActivity.this,SearchBluetoothActivity.class));
                }else {
                    ToastUtil.showToast(MainActivity.this,"打印图片...");
                    Intent intent2 = new Intent(getApplicationContext(), BtService.class);
                    intent2.setAction(PrintUtil.ACTION_PRINT_BITMAP);
                    startService(intent2);

                }
//                startActivity(new Intent(MainActivity.this,TextActivity.class));
                break;
        }

    }
    /**
     * handle printer message
     *
     * @param event print msg event
     */
    public void onEventMainThread(PrintMsgEvent event) {
        if (event.type == PrinterMsgType.MESSAGE_TOAST) {
            ToastUtil.showToast(MainActivity.this,event.msg);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        super.onPause();
//        EventBus.getDefault().register(MainActivity.this);
    }
}

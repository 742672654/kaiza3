package com.cz.activity;



import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;
import com.cz.bean.DataReceiveBean;
import com.cz.bean.OrderBean;
import com.cz.service.Led_in_service;
import com.cz.service.Led_out_service;
import com.cz.service.Sensor_in_service;
import com.cz.service.Sensor_out_service;
import com.cz.util.PingUtil;
import com.cz.util.TimeUtil;
import com.vz.tcpsdk;
import com.zld.bean.CarNumberOrder;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 日期:	2019年5月7日
 * 开发者:	KXD
 */
public class ZldNew2Activity extends ZldNew2Base {


    private static String TAG = "ZldNew2Activity";

    public final static int onDataReceive = 12;
    public final static int in_orders_photo = 103567;
    public final static int out_orders_photo = 203567;
    public final static int hide_orders = 507;
    public final static int onClick_orders = 5036;
    public final static int in_open_pole = 1111;    //入口抬杠
    public final static int out_open_pole = 2222;   //出口抬杠
    public final static int in_photo = 111;         //入口拍照
    public final static int out_photo = 222;        //出口拍照
    public final static int click_in_park = 905;   //模拟点击<在场车辆>
    public final static int click_out_park =9005;  //模拟点击<离场车辆>
    public final static int Refresh_nowIncome = 704; //刷新订单和收费
    public final static int clickOrder = 55; //模拟点击订单详情
    public final static int restart_in_sxt_handle = -100; //重启入场摄像头控制
    public final static int restart_out_sxt_handle = -200; //重启出场摄像头控制

    public final static int led_in_service = 1; //入口发送led消息
    public final static int led_out_service = 2; //出口发送led消息

    public static int sxtOK = 0;//0代表没掉线过，大于0代表掉线了。

    public static ZldNew2Activity zldNew2Activity;

    public ZldNew2Activity() {
        zldNew2Activity=this;
    }
    public static Handler getHandler() {
        return zldNew2Activity.handler;
    }

    private AlertDialog alertDialog;
    private long alertDialogLong = 0;


    private void congqi(){


        Log.w(TAG,"系统重启------------");

        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        //与正常页面跳转一样可传递序列化数据,在Launch页面内获得
        intent.putExtra("REBOOT","reboot");
        PendingIntent restartIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(TAG,"结束时调用");
        sensor_serviceTimer.cancel();
        order_quantityTimer.cancel();
        led_serviceTimer.cancel();

        tcpsdk tsdk = new tcpsdk();
        tsdk.cleanup();
        tsdk.setup();
    }

    private Timer sensor_serviceTimer = null;
    private Timer order_quantityTimer = null;
    private Timer led_serviceTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.zldNew2Activity = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {

        zldNew2Activity=this;
        super.onStart();



        //每3秒执行一次
        //获取当前停车场IP信息，启动定时器,定时判断地感线圈是否被占用
        sensor_serviceTimer = new Timer(true);
        sensor_serviceTimer.schedule(new TimerTask() {
            public void run() {


                if ( alertDialogLong < 10 ){
                    alertDialogLong++;
                    return;
                }

                Log.d(TAG,"启动检测地感线圈定时执行的服务");
                startService(new Intent(ZldNew2Activity.this, Sensor_in_service.class));
                startService(new Intent(ZldNew2Activity.this, Sensor_out_service.class));

            }
        }, 0, 3*1000);

        //清空led
        led_serviceTimer = new Timer(true);
        led_serviceTimer.schedule(new TimerTask() {
            public void run() {



                Log.d(TAG,"启动检测地感线圈定时执行的服务");
                Intent intent = new Intent(ZldNew2Activity.this, Led_in_service.class);
                intent.putExtra( "ip",getLed_in()!=null? getLed_in().getLedip():"");
                startService(intent);

                Intent intent2 = new Intent(ZldNew2Activity.this, Led_out_service.class);
                intent2.putExtra( "ip",getLed_out()!=null? getLed_out().getLedip():"");
                startService(intent2);

            }
        }, 0, 6*1000);

        //10分钟刷新空闲车位和收费情况,还有订单列表也刷新一下
        order_quantityTimer = new Timer(true);
        order_quantityTimer.schedule(new TimerTask() {
            public void run() {

                if ( alertDialogLong < 1 ){ return; }

                ZldNew2Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.i(TAG,"每10分钟刷新空闲车位和收费情况,还有订单列表也刷新一下");
                        getParkinfoFragment4().http_parameter();
                        getListFragment5().in_park_cars2.performClick();
                    }
                 });
            }
        }, 0, 60*1000*10);
    }



    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            Log.i(TAG, "收到Handler"+"joinType:" + msg.getData().getString("joinType")+";msg.what="+msg.what);

            switch (msg.what) {

                //TODO 照片回调
                case ZldNew2Activity.onDataReceive:

                    DataReceiveBean DataReceiveBean = (DataReceiveBean) msg.obj;
                    getEntranceFragment1().onDataReceive(DataReceiveBean);
                    getExitFragment2().onDataReceive(DataReceiveBean);
                    break;

                //TODO 入口拍照显示
                case ZldNew2Activity.in_orders_photo:

                    Log.i(TAG,";入口拍照显示CarNumberOrder=" + msg.obj!=null?msg.obj.toString():"空");

                    Toast.makeText(ZldNew2Activity.this, "成功生成订单", Toast.LENGTH_SHORT).show();
                    CarNumberOrder carNumberOrder = (CarNumberOrder) msg.obj;


                    //刷出主要图片
                    getParkrecordFragment3().transfer( carNumberOrder.getUuid() );

                    //订单列表<在场车辆>按钮模拟点击一下
                    getListFragment5().listFlag = 0;
                    getListFragment5().changeRadioBtnColor();
                    getListFragment5().refreshInList();

                    //刷新订单详情
                    getOrdetailsFragment6().transfer( carNumberOrder,carNumberOrder.getUuid() );

                    //显示新订单的车费
                    getCashFragment7().transfer(carNumberOrder );
                    break;

                //TODO 出口拍照显示
                case ZldNew2Activity.out_orders_photo:

                    Log.i(TAG,";出口拍照显示CarNumberOrder=" + msg.obj!=null?msg.obj.toString():"空");

                    //Toast.makeText(ZldNew2Activity.this, "成功生成订单", Toast.LENGTH_SHORT).show();
                    CarNumberOrder carNumberOrder2 = (CarNumberOrder) msg.obj;

                    Toast.makeText(ZldNew2Activity.this,"车辆到来",Toast.LENGTH_SHORT).show();
                    //显示新订单的车费
                    getCashFragment7().transfer(carNumberOrder2 );

                    //刷出主图照片
                    getParkrecordFragment3().transfer( carNumberOrder2.getUuid()+"3" );

                    //出口拍照后：设置列表为某一条
                    getListFragment5().setOrder(carNumberOrder2.getOrderid());

                    //刷新订单详情
                    getOrdetailsFragment6().transfer( carNumberOrder2,carNumberOrder2.getUuid()+"4" );
                    break;

                //TODO 隐藏车费按钮
                case ZldNew2Activity.hide_orders:

                    Log.i(TAG,";隐藏车费的所有按钮");
                    //清空订单信息
                    getOrdetailsFragment6().transfer(null,null );
                    //清空车费
                    getCashFragment7().hideButton();
                    break;

                //TODO 点击订单列表里面的详情
                case ZldNew2Activity.onClick_orders:

                    Log.i(TAG,";点击订单列表");
                    CarNumberOrder carNumberOrder3 = (CarNumberOrder) msg.obj;

                    String uuid = (getListFragment5().listFlag==0?msg.getData().getString("uuid"):msg.getData().getString("uuid")+3);

                    getParkrecordFragment3().transfer( uuid );
                    getOrdetailsFragment6().transfer( carNumberOrder3,uuid );
                    //显示新订单的车费
                    getCashFragment7().transfer( carNumberOrder3 );
                    break;


                //TODO 入口开闸
                case ZldNew2Activity.in_open_pole:

                    if(msg.getData().getString("text")!=null)Toast.makeText(ZldNew2Activity.this,  msg.getData().getString("text"), Toast.LENGTH_SHORT).show();

                    Log.i( TAG,"入口开闸" );
                    getEntranceFragment1().open_pole();
                    break;


                //TODO 出口开闸
                case ZldNew2Activity.out_open_pole:

                    if(msg.getData().getString("text")!=null)Toast.makeText(ZldNew2Activity.this,  msg.getData().getString("text"), Toast.LENGTH_SHORT).show();
                    getExitFragment2().open_pole();
                    break;

                //TODO 入口拍照
                case ZldNew2Activity.in_photo:

                    if(msg.getData().getString("text")!=null)Toast.makeText(ZldNew2Activity.this, msg.getData().getString("text"), Toast.LENGTH_SHORT).show();
                    getEntranceFragment1().open_phote();
                    break;

                //TODO 出口拍照
                case ZldNew2Activity.out_photo:

                    if(msg.getData().getString("text")!=null)Toast.makeText(ZldNew2Activity.this, msg.getData().getString("text"), Toast.LENGTH_SHORT).show();
                    getExitFragment2().out_photo();
                    break;

                //TODO 模拟点击在场车辆
                case ZldNew2Activity.click_in_park:

                    getListFragment5().in_park_cars2.performClick();
                    break;

                //TODO 模拟点击离场车辆
                case ZldNew2Activity.click_out_park:

                    getListFragment5().out_park_cars2.performClick();
                    break;

                //TODO 刷新订单量和收费
                case ZldNew2Activity.Refresh_nowIncome:

                    getParkinfoFragment4().http_parameter();
                    break;

                //TODO 发送led消息
                case ZldNew2Activity.led_in_service:

                    Intent intentLED = new Intent(ZldNew2Activity.this, Led_in_service.class);
                    if (msg.getData().getString("textData")!=null){
                        intentLED.putExtra("textData",msg.getData().getString("textData"));
                    }
                    if (msg.getData().getString("voiceData")!=null){
                        intentLED.putExtra("voiceData",msg.getData().getString("voiceData"));
                    }
                    intentLED.putExtra("ip",getLed_in().getLedip());
                    startService(intentLED);
                    break;

                //TODO 发送led消息
                case ZldNew2Activity.led_out_service:

                    Intent intentLED2 = new Intent(ZldNew2Activity.this, Led_out_service.class);
                    if (msg.getData().getString("textData")!=null){
                        intentLED2.putExtra("textData",msg.getData().getString("textData"));
                    }
                    if (msg.getData().getString("voiceData")!=null){
                        intentLED2.putExtra("voiceData",msg.getData().getString("voiceData"));
                    }
                    intentLED2.putExtra("ip",getLed_out().getLedip());
                    startService(intentLED2);
                    break;

                //TODO 重启fragment 1 的摄像头控制
                case ZldNew2Activity.restart_in_sxt_handle:
                   // int s = 1/0;

                    congqi();
//                    try {
//                        if (PingUtil.LAN_Ping(getCameraIn().getIp())){
//
//                          //  tcpsdk tsdk =  new tcpsdk();
////                            tsdk.cleanup();
////                            tsdk.setup();
//
//                            if(s++>5){
//                                s=0;
//                                getEntranceFragment1().openSXT();
//                                return;
//                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        int s = 1/0;
//                    }


//                    if (alertDialog!=null && alertDialog.isShowing()){ return; }
//
//                    //如果不到30秒就不提示
//                    if ( TimeUtil.getCurrentTime() <= alertDialogLong ){ return; }
//
//                    alertDialog = new AlertDialog.Builder(ZldNew2Activity.this).setTitle("摄像头连接错误，是否重启？")
//                            .setIcon(android.R.drawable.ic_dialog_info)
//                            .setPositiveButton("立即重启", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                    sensor_serviceTimer.cancel();
//                                    order_quantityTimer.cancel();
//                                    led_serviceTimer.cancel();
//
//                                    alertDialogLong = 0;
//                                    alertDialog.dismiss();
//                                    Intent intent2 = new Intent(ZldNew2Activity.this, ChooseWorkstation2Activity.class);
//                                    intent2.putExtra("joinType", 1);
//                                    intent2.putExtra("Activity", TAG);
//                                    startActivity(intent2);
//
//                             }}).setNegativeButton("等待30秒", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                    alertDialogLong = TimeUtil.getCurrentTime()+1000*30;
//                             }}).show();
                    break;


                case ZldNew2Activity.restart_out_sxt_handle:


                    congqi();
                   // int s2 = 1/0;
//                try{
//
//                    if (PingUtil.LAN_Ping(getCameraOut().getIp())){
//                        if(s++>5){
//                            s=0;
//                            getExitFragment2().openSXT();
//                            return;
//                        }
//                    }
//                }catch (Exception e){
//                   Log.w( TAG,e );
//                    int s = 1/0;
//                }
                    break;
                //TODO 模拟点击订单详情
                case ZldNew2Activity.clickOrder:

                    getListFragment5().clickOrder();
                    break;

                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

public  int s=0;

}

package com.cz.service;


import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import com.cz.activity.ZldNew2Activity;
import com.cz.fragment.ExitBase;
import com.vz.tcpsdk;


public class Sensor_out_service extends Service {


    private static final String TAG = "Sensor_out_service";
    private tcpsdk tsdk = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        if ( tsdk == null ){
            tsdk = new tcpsdk();
        }
    }




    private int pingmins = 0;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if ( tsdk == null ){
            tsdk = new tcpsdk();
        }
        int sxt_handle = ExitBase.sxt_handle;

        Log.d(TAG,tsdk.isConnected( sxt_handle ) +"");

        //TODO ping摄像头
        if ( sxt_handle == -1 || !tsdk.isConnected( sxt_handle ) || ZldNew2Activity.sxtOK > 0 ){

            Log.i(TAG,"摄像头断开连接"+";sxt_handle == -1 "+(sxt_handle == -1) +"!tsdk.isConnected( sxt_handle )="+(!tsdk.isConnected( sxt_handle )));


            try{


                    Bundle bundle = new Bundle();
                    bundle.putString( "joinType",TAG );
                    Message message = new Message();
                    message.what = ZldNew2Activity.restart_out_sxt_handle;
                    message.setData(bundle);
                    Handler handler = ZldNew2Activity.getHandler();
                    handler.sendMessage(message);

            }catch (Exception e){

                Log.w(TAG,e);
            }

            return super.onStartCommand(intent, flags, startId);
        }else{
            pingmins = 0;
        }





//TODO 检测地感是否有被压倒
        //因为1是开路，0是闭路
        int[] nOutput = { 3 };

        //检测是否有车辆压倒地感
        tsdk.getIoOutput( sxt_handle,0, nOutput);

        //如果i1[0]==0代表有压倒
        if ( nOutput[0]==0 ){
            Bundle bundle = new Bundle();
            bundle.putString( "joinType",TAG );
            Message message = new Message();
            message.what = ZldNew2Activity.out_photo;
            message.setData(bundle);
            Handler handler = ZldNew2Activity.getHandler();
            handler.sendMessage(message);
            Log.i(TAG,"出口触发循环拍照监测");
        }
       // Log.i(TAG,"出口循环拍照监测");
        return super.onStartCommand(intent, flags, startId);
    }

}

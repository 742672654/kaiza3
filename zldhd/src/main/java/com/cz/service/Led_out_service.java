package com.cz.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.cz.activity.ZldNew2Activity;
import com.cz.led.led.model2.Led_Thread;
import com.cz.util.TimeUtil;



public class Led_out_service extends Service {

    private static final String TAG = "Led_out_service";

    private long START_TIME = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

    try {
        Log.i(TAG,"启动Led_out_service"+ZldNew2Activity.zldNew2Activity.getLed_out().getLedip());
    }catch (Exception e){
        e.printStackTrace();
    }

    }


    private String voiceData0 = "";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        try {

            String ledIP = intent.getStringExtra("ip");

            String textData = intent.getStringExtra("textData");
            String voiceData = intent.getStringExtra("voiceData");


            Log.i( TAG,"ledIP="+ledIP+";textData="+textData+";voiceData="+voiceData );


            if (voiceData != null && !voiceData.equals( "" )) {


                if ( voiceData.equals( voiceData0 )) {
                    voiceData = null;

                } else {
                    voiceData0 = voiceData;
                }
            }
            if ( textData==null && START_TIME+1000*3 < TimeUtil.getCurrentTime() ){

                if ( START_TIME == 0 ){
                    return super.onStartCommand(intent, flags, startId);
                }

                Led_Thread.led_voice_Satrt(ledIP, "尚宝智慧停车系统",null);
                START_TIME = 0;
                Log.i(TAG,"清空文字  "+ledIP);
                return super.onStartCommand(intent, flags, startId);
            }
            START_TIME = TimeUtil.getCurrentTime();

            if ( ledIP==null || "".equals(ledIP)){  return super.onStartCommand(intent, flags, startId);}

            Led_Thread.led_voice_Satrt(ledIP, textData,voiceData);
            Log.i(TAG,"成功发送内码文字=  "+textData+";播报语音="+voiceData);
        } catch (Exception e) {
            Log.w(TAG,e);
        }

        return super.onStartCommand(intent, flags, startId);
    }

}

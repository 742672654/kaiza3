package com.cz.led.led.model2;

import android.util.Log;
import com.cz.util.TimeUtil;
import com.cz.led.led2.SendBuffer2;

public class Led_Thread extends Thread {


    private static final String TAG = "Led_Thread";

    public static long START_TIME = 0;

    private String ledIP ;
    private String cashOrderData;
    private String voiceData;

    public Led_Thread(String ledIP, String cashOrderData, String voiceData) {

        START_TIME = TimeUtil.getCurrentTime();

        this.ledIP = ledIP;
        this.cashOrderData=cashOrderData;
        this.voiceData = voiceData;
    }

    public void run() {

        try {

            if ( ledIP==null || ledIP.equals("")){ return;}

            if (voiceData!=null && !voiceData.equals("")){

                SendBuffer2.PlayVoice_Net(  voiceData, 	//播放内容
                    ledIP,	//控制卡IP地址
                    "00", 				//RS485地址,转发485口必填。默认00为广播地址
                    1, 					//网络通讯协议，1:UDP协议，2:TCP协议
                    2);					//转发端口,1:RS232的1口；2:RS232的2口；3:RS485口
            }

            if (cashOrderData!=null && !cashOrderData.equals("")){

                SendBuffer2.SendInternalText_Net(cashOrderData, ledIP, 1, 64, 32, 1, 0,
                    9, 1, 1, 1, 1, 1, 3, false);
            }

            Log.e(TAG,"成功发送内码文字=  "+cashOrderData+";播报语音="+voiceData);
        } catch (Exception e) {
        //    e.printStackTrace();
            Log.w(TAG,e);
        }
    }



    public static void led_voice_Satrt( String ledIP,String ledData, String voiceData ){

        new Led_Thread( ledIP,ledData,voiceData ).start();
    }



}

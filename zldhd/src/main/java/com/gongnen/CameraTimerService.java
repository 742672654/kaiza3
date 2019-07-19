package com.gongnen;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.vz.tcpsdk;
import com.zld.bean.InformationBean;
import com.zld.bean.PARKING_ip_Bean;
import com.zld.photo.DecodeManager;



public class CameraTimerService extends Service {

    private static final String TAG = "CameraTimerService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    static  tcpsdk  tsdk = null;
    static  int  sxt_handle = 0;

    @Override
    public void onCreate() {
        super.onCreate();
    }




    int[] i1 = new int[]{3};
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        if (PARKING_ip_Bean.getEntrance(1)==null){
            return super.onStartCommand(intent, flags, startId);
        }

        try{

            if(tsdk == null){
                //开启出口摄像头控制
                tsdk = new tcpsdk();
                //tsdk.setup();
                InformationBean info = PARKING_ip_Bean.getEntrance(1);
                sxt_handle = tsdk.open( info.getIp().getBytes(),info.getIp().length() , 8131,
                        info.getCusername().getBytes(),info.getCusername().length(),
                        info.getCpassword().getBytes(), info.getCpassword().length());
                Log.i(TAG+"39999","初始化:"+sxt_handle);
            }
        }catch (Exception e){

            Log.w(TAG+"39999",e);
        }


        //因为1是开路，0是闭路
        i1[0]=3;

        //检测是否有车辆压倒地感
       int s2 = tsdk.getIoOutput(sxt_handle, 0, i1);
        Log.i(TAG+"39999",sxt_handle+"--"+s2+"---"+i1[0]);
        //如果i1[0]==0代表有压倒
        if (i1[0]==0){

            Log.i(TAG+"39999","自动拍照ip:"+PARKING_ip_Bean.typeIP(1));
            DecodeManager.getinstance().getOneImg(PARKING_ip_Bean.typeIP(1));
        }

        return super.onStartCommand(intent, flags, startId);
    }

}

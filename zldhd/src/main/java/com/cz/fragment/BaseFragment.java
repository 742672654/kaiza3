package com.cz.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.cz.activity.LoginActivity;
import com.cz.activity.ZldNew2Activity;
import com.cz.service.Led_out_service;
import com.zld.bean.AllOrder;

import java.util.ArrayList;

public class BaseFragment extends Fragment {

    ZldNew2Activity activity;

    private static final String TAG = "BaseFragment";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (ZldNew2Activity) activity;
    }

    public boolean isOnMainThread() {

        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    public void toast_makeText(final String text) {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(activity,text,Toast.LENGTH_SHORT).show();
            }
        });
    }


    //TODO 发送到入口LED
    public boolean led_In_StartService( String textData,String voiceData,String joinTAG ) {

        try{

            Bundle bundle = new Bundle();

            bundle.putString( "joinType",joinTAG+"发送入口LED" );
            bundle.putString("ip",activity.getLed_in().getLedip());
            bundle.putString("textData",textData);
            bundle.putString("voiceData",voiceData);

            Message message = new Message();
            message.what = ZldNew2Activity.led_in_service;
            message.setData(bundle);
            Handler handler = activity.getHandler();
            handler.sendMessage(message);
            return true;
        }catch (Exception e){

            e.printStackTrace();
            return false;
        }
    }

    //TODO 发送到出口LED
    public boolean led_Out_StartService( String textData,String voiceData,String joinTAG ) {


        try{

            Bundle bundle = new Bundle();

            bundle.putString( "joinType",joinTAG+"发送出口LED" );
            bundle.putString("ip",activity.getLed_out().getLedip());
            bundle.putString("textData",textData);
            bundle.putString("voiceData",voiceData);

            Message message = new Message();
            message.what = ZldNew2Activity.led_out_service;
            message.setData(bundle);
            Handler handler = activity.getHandler();
            handler.sendMessage(message);
            return true;
        }catch (Exception e){

            e.printStackTrace();
            return false;
        }
    }

    //TODO 返回车牌号在场订单
    public AllOrder isExistence_In_Order(String carNumber ){


        try{

            ArrayList<AllOrder> orders = activity.getListFragment5().orders_in.getInfo();
            for (int i=0;i<orders.size();i++){
                if (carNumber.equals(orders.get(i).getCarnumber())){

                    return orders.get(i);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    //TODO 返回车牌号出场订单
    public AllOrder isExistence_Out_Order(String carNumber ){


        try{

            ArrayList<AllOrder> orders = activity.getListFragment5().orders_out.getInfo();
            for (int i=0;i<orders.size();i++){
                if (carNumber.equals(orders.get(i).getCarnumber())){

                    return orders.get(i);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    //TODO 根据nConfidence判断车辆类型
    protected String toCarType( int nConfidence ){

        switch (nConfidence){

            case 1:return "1";
            case 2:return "1";
            case 3:return "2";
            case 4:return "2";
            case 5:return "1";
            case 6:return "1";
            case 7:return "1";
            case 8:return "1";
            case 9:return "2";
            case 10:return "1";
            case 11:return "1";
            case 12:return "1";
            case 13:return "1";
            case 14:return "1";
            case 15:return "2";
            case 16:return "1";
            case 17:return "2";
            case 18:return "1";
            case 19:return "1";
            default:
                return "1";
        }

    }


}

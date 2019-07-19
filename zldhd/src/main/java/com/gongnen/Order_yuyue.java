package com.gongnen;

import android.util.Log;

import com.zld.bean.AppInfo;

import org.json.JSONObject;


public class Order_yuyue {


    public int is_yuyue(String plateText){

        //▼▼▼▼判断是否有预约，如果有预约就开闸，没有预约的向下
        try{

            StringBuffer buffer2 = new StringBuffer();
            buffer2.append("comid=").append(AppInfo.getInstance().getComid());
            buffer2.append("&plateText=").append(plateText);

            String  s2 = "";
            JSONObject jsonObject2 = new JSONObject(s2);

            if (jsonObject2.getInt("reserve") > 0) {
                Log.i("拍照","结束");
                return 1; //开闸，不产生订单
            }
        }catch (Exception e) {
            Log.w("拍照",e);
            return -1;
        }


        //▼▼▼▼判断停车场是否满了，如果满了，就返回0。
        StringBuffer buffer = new StringBuffer();
        buffer.append("comid=").append(AppInfo.getInstance().getComid());
        int number = -1;

        try {

            String  s1 = "";
            JSONObject jsonObject = new JSONObject(s1);
            number = jsonObject.getInt("number");

            if (number<=0){

                return 2; //不开闸，不同意进场，语音播报车位满了
            }
        }catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        //▲▲▲▲判断停车场是否满了，如果满了，就判断是否是预约订单。


        return 0;
    }


    public JSONObject reserve(String plateText){

        //▼▼▼▼出场判断是否有预约，如果有预约就开闸，没有预约的向下
        StringBuffer buffer2 = new StringBuffer();
            buffer2.append("comid=").append(AppInfo.getInstance().getComid());
            buffer2.append("&plateText=").append(plateText);

        String  s2 = "";
        try{

            Log.i("拍照","地址"+s2+"；发送值"+buffer2.toString());

              s2 = "";

            Log.i("拍照","返回值"+s2);
            JSONObject jsonObject2 = new JSONObject(s2);


            return jsonObject2;
        }catch (Exception e) {
            e.printStackTrace();
            Log.i("拍照","报错"+s2);

        }

        return null;
    }


}

package com.cz.fragment;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import com.Static_bean;
import com.cz.activity.ZldNew2Activity;
import com.cz.bean.AddOrderBean;
import com.cz.bean.DataReceiveBean;
import com.cz.http.HttpManager2;
import com.cz.util.StringUtil;
import com.google.gson.Gson;
import com.vz.PlateResult;
import com.zld.R;
import com.zld.bean.CarNumberOrder;
import com.zld.lib.util.FileUtil;
import com.zld.lib.util.ImageUitls;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/*
 * 功能说明: 入口Fragment
 * 日期:	2019年5月13日
 * 开发者:	KXD
 */
public class EntranceFragment1 extends EntranceBase{


    public String inCarNumber = "";
    public Long inTime = 0L;


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_photo2: open_phote();         //tsdk.setIoOutputAuto(sxt_handle, Short.valueOf("1"), 1000);
                break;

            case R.id.btn_entrance_open_pole2:open_pole();
                break;

            default:
                break;
        }
    }

    //TODO 入口拍照:触发1输出口
    public void open_phote(){

        if ( activity.getWorksiteBean().getNet_type().equals("1") ){

            tsdk.setIoOutputAuto(sxt_handle, Short.valueOf("1"), 1000);
        }else{
            tsdk.forceTrigger(sxt_handle);
        }
    }

    //TODO 入口抬杠
    public void open_pole(){

        tsdk.setIoOutputAuto(sxt_handle, Short.valueOf("0"), 1000);
    }

    //TODO 保存到sqlite、上传图片到云端
    protected void imgSeva(String uuid, String carNumber, int nConfidence, byte[] pImgFull, byte[] pImgPlateClip) {


        InputStream inputStream = null;
        InputStream inputStream2 = null;
        try {


            //保存车牌特写
            inputStream2 = new ByteArrayInputStream(pImgPlateClip);
            ImageUitls.saveFrameToPath(BitmapFactory.decodeStream(inputStream2), FileUtil.getSDCardPath() + "/tcb/" + uuid + 2 + ".jpg");

            //全景
            inputStream = new ByteArrayInputStream(pImgFull);
            ImageUitls.saveFrameToPath(BitmapFactory.decodeStream(inputStream), FileUtil.getSDCardPath() + "/tcb/" + uuid + ".jpg");

            //上传照片到文件管理器
            Map<String, String> param = new HashMap<String, String>();
            param.put("uid", uuid);
            param.put("pictype", "1");
            HttpManager2.onResponseFile(Static_bean.carpicsup_upPicToOss, param, "file",uuid+".jpg",pImgFull,this, "addPhoto");

        } catch (Exception e) {

            Log.w(TAG,e);
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (inputStream2 != null) inputStream2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO 上传订单:发送http请求
    private void preaddorder( String uuid, String carNumber, int nConfidence ) {


        // TODO 本地重复拍照检测
        //如果某车辆入场后10分钟内再次入场，则不操作
         Long nowTime = com.cz.util.TimeUtil.getCurrentTime();
         if ( carNumber.equals(inCarNumber) && nowTime<inTime+1000*60*2 ){ return; }

        inCarNumber = carNumber;
        inTime = nowTime;

        Map<String, String> param = new HashMap<String, String>(9);
        param.put("uuid", uuid);
        param.put("comid", worksite.getComid());
        param.put("uid", userBean.getAccount());
        param.put("carnumber", carNumber);
        param.put("through", String.valueOf(3));
        param.put("from", "0");     // 0:通道扫牌自动生成订单，1：补录车牌生成订单
        param.put("car_type",toCarType( nConfidence ));//String.valueOf(nConfidence));  // 车辆类型
        param.put("passid", info.getPassid());

        param.put("type", "in");
        HttpManager2.requestPost(Static_bean.cobp_preaddorder, param, this, "cobp_preaddorder");
    }

    //TODO >>>>接收创建订单的http请求，发送用于刷新6的http请求
    private void preaddorder2( Map<String, String> param, String object ) {


        AddOrderBean addOrderBean = new Gson().fromJson(object, AddOrderBean.class);

        //刷新6
        Map<String, String> map = new HashMap<String, String>();
        map.put("comid", worksite.getComid());
        map.put("orderid", addOrderBean.getOrderid());
        map.put("ptype", String.valueOf(1));
        map.put("out", "json");
        HttpManager2.requestPost(Static_bean.cobp_catorder, map, this, "clickOrder");

    }

    //TODO 上传订单:接收刷新6的http请求
    private void preaddorder3( Map<String, String> param,String object ) {

        CarNumberOrder carNumberOrder = new Gson().fromJson(object, CarNumberOrder.class);

        Bundle bundle = new Bundle();
        bundle.putString( "joinType",TAG );

        Message message = new Message();
        message.what = ZldNew2Activity.in_orders_photo;
        message.obj = carNumberOrder;
        message.setData(bundle);
        Handler handler = activity.getHandler();
        handler.sendMessage(message);
    }



    //TODO 判断是否是预约订单,不是就创建订单，否则开闸
    private void whitelistApi_isyuyue(Map<String, String> param, String object) {

        try {

            object = object.replace("\\", "");
            Map<String, Object> map = new Gson().fromJson(object, Map.class);

            //如果是预约订单就开闸放进去,否则创建订单
            if ("1".equals(map.get("code"))) {

                if ("1".equals(map.get("reserve"))) {

                    Bundle bundle = new Bundle();
                    bundle.putString("joinType", TAG);
                    bundle.putString("text", "预约订单，请进");
                    Message message2 = new Message();
                    message2.what = ZldNew2Activity.in_open_pole;
                    Handler handler2 = activity.getHandler();
                    handler2.sendMessage(message2);

                    Message message3 = new Message();
                    message3.what = ZldNew2Activity.click_in_park;
                    Handler handler3 = activity.getHandler();
                    handler3.sendMessage(message3);
                    return;
                }
            }

            Bundle bundle = new Bundle();
            bundle.putString("joinType", TAG);
            Message message2 = new Message();
            message2.what = ZldNew2Activity.in_open_pole;
            Handler handler2 = activity.getHandler();
            handler2.sendMessage(message2);

            //上传订单
            preaddorder(param.get("uuid"), param.get("carNumber"), Integer.parseInt(param.get("nConfidence")));
        } catch (Exception e) {
            Log.w(TAG, e);
        }
    }

    @Override
    public void onResponsePOST(String url, Map<String, String> param, String sign, String object) {

        Log.i(TAG, url + "----；sign="+sign+";object=" + object+"****"+"---param="+param);

        try{


        switch (sign) {
            case "cobp_preaddorder": preaddorder2(param, object);
                break;
            case "clickOrder": preaddorder3(param, object);
                break;
            case "whitelistApi_isyuyue": whitelistApi_isyuyue(param, object);
                break;
            default:
                break;
        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResponseFile(String url, Map<String, String> param, String sign, String object) {

        Log.i(TAG,"224接收图片上传到oos-----------"+object);
    }

    //TODO  拍照接收
    public void onDataReceive( DataReceiveBean dataReceiveBean ){


        String carNumber = PlateResult.readImageData(dataReceiveBean.getPlateResult().license);

        //如果不是本摄像头,或没有车票的return
        if (dataReceiveBean.getHandle()!=sxt_handle ){
            Log.i(TAG,"不是入口的sxt_handle；dataReceiveBean.getHandle()="+dataReceiveBean.getHandle()+";sxt_handle="+sxt_handle);
            return;
        }else{
            Log.i(TAG,"入口接收到="+carNumber);
        }

        Long nowTime = com.cz.util.TimeUtil.getCurrentTime();
        if ( carNumber.contains("无") ){
            if (nowTime-inTime<4000){
                carNumber = inCarNumber;
            }else{
                toast_makeText( "入口：没有车牌，无法生成订单");
                return;
            }
        }

//     //TODO 本地重复拍照检测
        //如果某车辆入场后10分钟内再次入场，则不操作
      // Long nowTime = com.cz.util.TimeUtil.getCurrentTime();
//        if (  inTime!=null && inCarNumber!=null ){
//
//            if ( carNumber.equals(inCarNumber) && nowTime<inTime+1000*60*10 ){
//
//                 return;
//            }
//        }


        //TODO 判断是否是刚出场的车辆
        //如果某车辆2分钟内出场，再次入场则不执行入场
        Long outTime = activity.getExitFragment2().outTime;
        String outCarNumber = activity.getExitFragment2().outCarNumber;
        if ( outTime !=null && outCarNumber!=null ){
            if ( nowTime < outTime+1000*60*2 && carNumber.equals(outCarNumber)  ){

                Log.i(TAG,"入口重复拍照");
                return;
            }
        }

        Log.i(TAG,activity.getLed_in().getLedip()+"入口接收到车牌号"+carNumber);

        led_In_StartService(carNumber+"欢迎光临",carNumber+"欢迎光临",TAG);

        //     0未知车牌，1蓝牌小汽车，2黑牌小汽车，3单排黄牌，4双排黄牌（大车尾牌，农用车），5警车车牌，6武警车牌，7个性化车牌，8单排军车牌，9双排军车牌，
        //     10使馆车牌，11香港进出中国大陆车牌，12农用车牌，13教练车牌，4澳门进出中国大陆车牌，15双层武警车牌，16武警总队车牌，17双层武警总队车牌
        int nConfidence = dataReceiveBean.getPlateResult().nConfidence;
        String uuid = StringUtil.getUuid();

        try {

            //保存和上传照片
            imgSeva( uuid, carNumber, nConfidence, dataReceiveBean.getpImgFull(), dataReceiveBean.getpImgPlateClip());

            //白名单预约
            Map<String, String> map3 = new HashMap<String, String>(5);
            map3.put("comid", activity.getWorksiteBean().getComid());
            map3.put("plateText", carNumber);
            map3.put("uuid", uuid);
            map3.put("carNumber", carNumber);
            map3.put("nConfidence", String.valueOf(nConfidence));
            HttpManager2.requestPost(Static_bean.whitelistApi_isyuyue, map3, this, "whitelistApi_isyuyue");


        } catch (Exception e) {

            Log.w(TAG,e);
        }

    }

}

package com.cz.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.Static_bean;
import com.cz.activity.ZldNew2Activity;
import com.cz.bean.DataReceiveBean;
import com.cz.bean.OrderTb;
import com.cz.http.HttpManager2;
import com.cz.util.StringUtil;
import com.cz.util.TimeUtil;
import com.google.gson.Gson;
import com.vz.PlateResult;
import com.zld.R;
import com.zld.bean.AllOrder;
import com.zld.bean.CarNumberOrder;
import java.util.HashMap;
import java.util.Map;


/**
 * 功能说明: 出口Fragment
 * 日期:	2019年5月13日
 * 开发者:	KXD
 */
public class ExitFragment2 extends ExitBase {



    private static final String TAG = "ExitFragment2";

    public String outCarNumber = "";
    public Long outTime = 0L;


    //播报用的车牌号
    private static String voice_CarNumber = "";


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_photo22:out_photo();
                break;

            case R.id.tv_exit_open_pole2:open_pole();
                break;

            default:
                break;
        }
    }

    //TODO 出口拍照
    public void out_photo(){

        if ( activity.getWorksiteBean().getNet_type().equals("1") ){

            tsdk.setIoOutputAuto(sxt_handle, Short.valueOf("1"), 1000);
        }else{
            tsdk.forceTrigger(sxt_handle);
        }
    }

    //TODO 出口开闸
    public void open_pole(){
        tsdk.setIoOutputAuto(sxt_handle, Short.valueOf("0"), 1000);
    }

    //TODO 接收查询订单
    private void refreshInList( Map<String, String> param, String object){


        CarNumberOrder carNumberOrder = new Gson().fromJson(object, CarNumberOrder.class);


        //如果不要钱就直接 订单结算并开闸
        if ( "0".equals(carNumberOrder.getCollect()) || "0.0".equals(carNumberOrder.getCollect())|| "".equals(carNumberOrder.getCollect())){

            led_Out_StartService(carNumberOrder.getCarnumber()+"订单0元",carNumberOrder.getCarnumber()+"订单0元，一路顺风",TAG);
            activity.getCashFragment7().preaddorder2(carNumberOrder.getOrderid());
            return;
        }

        //如果redis结算了，就开闸
        if ( "PAY".equals(carNumberOrder.getRedis())){

            Bundle bundle = new Bundle();
            bundle.putString( "joinType",TAG );
            bundle.putString("text","订单结算完成");
            Message message = new Message();
            message.what = ZldNew2Activity.out_open_pole;
            message.setData(bundle);
            Handler handler = activity.getHandler();
            handler.sendMessage(message);


            Bundle bundle2 = new Bundle();
            bundle2.putString( "joinType",TAG );
            bundle2.putString("text","订单结算完成");
            Message message2 = new Message();
            message2.what = ZldNew2Activity.click_in_park;
            message2.setData(bundle2);
            Handler handler2 = activity.getHandler();
            handler2.sendMessage(message2);

            return;
        }

        //如果在白名单就开闸
        if ( "deleteWhite".equals(carNumberOrder.getRedis())){

            Bundle bundle = new Bundle();
            bundle.putString( "joinType",TAG );
            Message message = new Message();
            message.what = ZldNew2Activity.out_open_pole;
            message.setData(bundle);
            Handler handler = activity.getHandler();
            handler.sendMessage(message);


            Bundle bundle2 = new Bundle();
            bundle2.putString( "joinType",TAG );
            Message message2 = new Message();
            message2.what = ZldNew2Activity.click_in_park;
            message2.setData(bundle2);
            Handler handler2 = activity.getHandler();
            handler2.sendMessage(message2);

            return;
        }


        led_Out_StartService(carNumberOrder.getCarnumber()+"订单"+carNumberOrder.getCollect()+"元",
                    carNumberOrder.getCarnumber()+"请缴费"+carNumberOrder.getCollect()+"元",TAG);



        Bundle bundle = new Bundle();
        bundle.putString( "joinType",TAG );

        Message message = new Message();
        message.what = ZldNew2Activity.out_orders_photo;
        message.obj = carNumberOrder;
        message.setData(bundle);
        Handler handler = activity.getHandler();
        handler.sendMessage(message);
    }

//    //TODO 接收上传订单
//    private void wxpay_savetemporder( String object ){
//
//            if (object==null){return;}
//
//
//            Map<String,Object> map = new Gson().fromJson(object, Map.class );
//
//            switch (map.get( "data" ).toString()){
//                case "OK":
//                    break;
//                case "PAY":
//
//            Log.i( TAG,"redis上传订单决定出口开闸" );
//                    led_Out_StartService("订单完成一路顺风",
//                            "订单完成一路顺风",TAG);
//
//                    Bundle bundle = new Bundle();
//                    bundle.putString( "joinType",TAG );
//                    bundle.putString("text","订单结算完成");
//                    Message message = new Message();
//                    message.what = ZldNew2Activity.out_open_pole;
//                    message.setData(bundle);
//                    Handler handler = activity.getHandler();
//                    handler.sendMessage(message);
//                    break;
//                case "Fail":
//                    break;
//                default:
//                    break;
//            }
//
//
//
//
//    }

//    //TODO 接收出场判断白名单
//    private void whitelistApi_plateText(Map<String, String> param, String object) {
//
//
////        String json = new StringBuffer("{\"result\":true,\"message\":\"\",\"code\":\"1\"")
////                .append(",\"reserve\":").append(1).append("}").toString();
//
//        Map map = new Gson().fromJson(object,Map.class);
//        if ( "true".equals(map.get("result"))){
//
//            switch ((String)map.get("reserve")){
//
//                case "1":
//
//
//                    led_Out_StartService("预约订单结算完成","预约订单已结算祝您一路顺风",TAG);
//
//                    Log.i( TAG,"白名单判断决定出口开闸" );
//
//
//                    Bundle bundle = new Bundle();
//                    bundle.putString( "joinType",TAG );
//
//                    Message message = new Message();
//                    message.what = ZldNew2Activity.out_open_pole;
//                    message.setData(bundle);
//                    Handler handler = activity.getHandler();
//                    handler.sendMessage(message);
//
//                    break;
//                case "2":
//
//                    led_Out_StartService("请重新结算订单","您还有欠费未结清请到小程序结算费用",TAG);
//                    break;
//            }
//        }
//
//
//    }

    //TODO 拍照接收
    public void onDataReceive(DataReceiveBean dataReceiveBean) {


    try{

        //如果不是本摄像头的return
        if ( dataReceiveBean.getHandle()!=sxt_handle ){
            Log.i(TAG,"不是出口的sxt_handle；dataReceiveBean.getHandle()="+dataReceiveBean.getHandle()+";sxt_handle="+sxt_handle);
            return;
        }

        long nowTime = com.cz.util.TimeUtil.getCurrentTime();

        //如果是一直压在地感线上，则判定是同一辆车
        String carNumber = PlateResult.readImageData(dataReceiveBean.getPlateResult().license);
        if ( carNumber.contains("无") ){
            if (nowTime-outTime<4000){
                carNumber = outCarNumber;
            }else{
                Toast.makeText(activity.getApplicationContext(), "出口：没有车牌，无法结算订单", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Log.i(TAG,"出口接收到车牌号"+carNumber);

        //TODO  重复拍照检测
        outCarNumber = carNumber;
        outTime = nowTime;

        Long inTime = activity.getEntranceFragment1().inTime;
        String inCarNumber = activity.getEntranceFragment1().inCarNumber;

        if ( inTime!=null && inCarNumber!=null ){

            if (carNumber.equals(inCarNumber)){
                if ( outTime <  inTime+1000*60){
                    activity.getEntranceFragment1().inTime = outTime;
                    Log.i(TAG,"出口重复拍照检测return");
                    return;
                }
            }else{
                activity.getEntranceFragment1().inCarNumber = "";
                activity.getEntranceFragment1().inTime = 0L;
            }

        }

        //去在场订单列表里面找订单,没有就重写请求后端
        AllOrder orderId = isExistence_In_Order(carNumber);
        if( orderId == null ) {

            Map<String, String> map2 = new HashMap<String, String>(3);
            map2.put("comid", activity.getParkingBean().getId());
            map2.put("car_number", carNumber);
            map2.put("nConfidence", String.valueOf(dataReceiveBean.getPlateResult().nConfidence));
            HttpManager2.requestPost(Static_bean.whitelistApi_queryorders, map2, this, "whitelistApi_queryorders");
            return;
        }

        //刷出订单详情
        clickOrder(orderId.getId());

//        //上传订单到redis
//        if (orderId != null) {
//            Map<String, String> map2 = new HashMap<String, String>();
//            map2.put("comid", activity.getCameraOut().getPassid());  //comid原本是停车场id，现在改成通道了
//            map2.put("orderid", (orderId == null || orderId.getId() == null) ? null : orderId.getId());
//            HttpManager2.requestPost(Static_bean.wxpay_savetemporder, map2, this, "wxpay_savetemporder");
//        }

//        //白名单出场判断
//        Map<String, String> map3 = new HashMap<String, String>(2);
//            map3.put("comid", activity.getWorksiteBean().getComid());
//            map3.put("plateText", carNumber);
//        HttpManager2.requestPost(Static_bean.whitelistApi_plateText, map3, this, "whitelistApi_plateText");


        //保存和上传出场照片
        imgSeva(  orderId.getUuid(),carNumber, dataReceiveBean.getpImgFull(), dataReceiveBean.getpImgPlateClip());

    }catch (Exception e){
        e.printStackTrace();
    }
    }

    //TODO 刷出订单详情
    public void clickOrder(String orderid){

        //刷出订单详情
        Map<String, String> map = new HashMap<String, String>(6);
        map.put("comid", activity.getWorksiteBean().getComid());
        map.put("orderid", orderid);
        map.put("ptype", String.valueOf(1));
        map.put("out", "json");


        map.put("channelid", activity.getCameraOut().getPassid());
        map.put("type", "out");
        HttpManager2.requestPost(Static_bean.cobp_catorder, map, this, "clickOrder");
    }

    //TODO >>>>接收查询订单id
    public void whitelistApi_queryorders(String object,Map<String, String> param0) {


        if (object==null || "".equals(object) || "null".equals(object)){

            //如果没有找到这个订单重新生成一个订单
            Map<String, String> param = new HashMap<String, String>(10);
            param.put("uuid", "");
            param.put("comid", activity.getWorksiteBean().getComid());
            param.put("uid", activity.getUserBean().getAccount());
            param.put("carnumber", param0.get("car_number"));
            param.put("through", String.valueOf(3));
            param.put("from", "0");     // 0:通道扫牌自动生成订单，1：补录车牌生成订单
            param.put("car_type",String.valueOf(super.toCarType(Integer.valueOf(param0.get("nConfidence"))))); // 车辆类型
            param.put("passid", info.getPassid());
            param.put("createTime", String.valueOf(TimeUtil.getCurrentTime()/1000-60*30));

            param.put("type", "out");

            HttpManager2.requestPost(Static_bean.cobp_preaddorder, param, this, "cobp_preaddorder");
            return;
        }


        try{

        OrderTb orderTb = new Gson().fromJson(object, OrderTb.class);
        //0未支付 1已支付 2:逃单
        if (orderTb.getState()==1){

            //如果30分钟内结算过就开闸
            if (TimeUtil.getCurrentTime()/1000 - orderTb.getEnd_time()<60*30){
        Log.i(TAG,"距离上次结算订单，小于30分钟,直接开闸"+TimeUtil.getCurrentTime()/1000 +"-"+orderTb.getEnd_time() +"="+(TimeUtil.getCurrentTime()/1000- orderTb.getEnd_time()));
                Bundle bundle = new Bundle();
                bundle.putString( "joinType",TAG );
                bundle.putString("text","订单结算完成");
                Message message = new Message();
                message.what = ZldNew2Activity.out_open_pole;
                message.setData(bundle);
                Handler handler = activity.getHandler();
                handler.sendMessage(message);
                return;
            }else{

             //否则重新生成一个订单
                Map<String, String> param = new HashMap<String, String>(9);
                param.put("uuid", orderTb.getUuid());
                param.put("comid", activity.getWorksiteBean().getComid());
                param.put("uid", activity.getUserBean().getAccount());
                param.put("carnumber", orderTb.getCar_number());
                param.put("through", String.valueOf(3));
                param.put("from", "0");     // 0:通道扫牌自动生成订单，1：补录车牌生成订单
                param.put("car_type",String.valueOf( orderTb.getCar_type() ));//String.valueOf(nConfidence));  // 车辆类型
                param.put("passid", info.getPassid());
                param.put("createTime", String.valueOf(TimeUtil.getCurrentTime()/1000-60*30));

                param.put("type", "out");

                HttpManager2.requestPost(Static_bean.cobp_preaddorder, param, this, "cobp_preaddorder");
            }
        }else{
            //如果未支付则去走正常流程
            clickOrder(String.valueOf(orderTb.getId()));
        }

        }catch (Exception e){
            Log.w(TAG,e);
        }
    }


    @Override
    public void onResponsePOST(String url, Map<String, String> param, String sign, String object) {

        try {


        Log.i(TAG, url + ";sign="+sign+"----object==" + object+";param="+param);

        switch (sign) {
            case "clickOrder": refreshInList(param, object);
                break;
//            case "wxpay_savetemporder": wxpay_savetemporder(object);
//                break;
            case "whitelistApi_queryorders": whitelistApi_queryorders(object,param);
                break;


//            case "whitelistApi_plateText":whitelistApi_plateText(param, object);
//                break;
            case "cobp_preaddorder":
                //如果查询订单id一般都要刷新一下《在场车辆》
                Message message3 = new Message();
                message3.what = ZldNew2Activity.click_in_park;
                Handler handler3 = activity.getHandler();
                handler3.sendMessage(message3);
                break;
            default:
                break;
        }
        }catch (Exception e){
           Log.w(TAG,e);
        }
    }

}
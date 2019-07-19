//package com.cz.service;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
//import android.util.Log;
//
//import com.Static_bean;
//import com.cz.Shared.Camera_in_Shared;
//import com.cz.Shared.Camera_out_Shared;
//import com.cz.Shared.CarType_Shared;
//import com.cz.Shared.FreeResons_Shared;
//import com.cz.Shared.Led_in_Shared;
//import com.cz.Shared.Led_out_Shared;
//import com.cz.Shared.OrderType_Shared;
//import com.cz.Shared.Parking_Shared;
//import com.cz.activity.ChooseWorkstation2Activity;
//import com.cz.bean.OrderTypeBean;
//import com.cz.bean.ParkingBean;
//import com.cz.http.HttpCallBack2;
//import com.cz.http.HttpManager2;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.zld.bean.WorkStationDevice;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class Configuration_Service extends Service implements HttpCallBack2 {
//
//    private final static String TAG = "Configuration_Service";
//
//    @Override
//    public IBinder onBind(Intent intent) {
//
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        //获取停车场信息
//        Map<String, String> param3 = new HashMap<String, String>();
//        param3.put("token", userBean.getToken());
//        param3.put("out", "json");
//        HttpManager2.requestGet(Static_bean.worksiteinfo_cominfo, param3, this, "worksiteinfo_cominfo");
//
//
//        //保存工作站的IP信息
//        Map<String, String> param2 = new HashMap<String, String>();
//        param2.put("worksite_id", worksite.getId());
//        param2.put("comId", worksite.getComid());
//        HttpManager2.requestGet(Static_bean.worksiteinfo_getpassinfo, param2, this, "worksiteinfo_getpassinfo");
//
//
//        //获取订单分类
//        Map<String, String> param = new HashMap<String, String>();
//        param.put("token", userBean.getToken());
//        HttpManager2.requestGet(Static_bean.OrderType_list, param, this, "OrderType_list");
//
//
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//
//
//
//
//
//    //TODO 保存订单类型
//    private void saveOrderType(String obj) {
//
//        try {
//
//            Log.i(TAG,"获取订单类型obj:  "+obj);
//
//            List<OrderTypeBean> orderTypeList = new Gson().fromJson(obj,new TypeToken<List<OrderTypeBean>>(){}.getType());
//            OrderType_Shared.saveALL(getApplicationContext(),orderTypeList);
//        } catch (Exception e) {
//
//            Log.i(TAG,e.toString());
//        }
//    }
//
//    //TODO 保存停车场信息
//    private void saveWorkStation(String obj) {
//
//        try {
//
//            Log.i(TAG,"获取停车场信息obj:  "+obj);
//
//            ParkingBean parking = new Gson().fromJson(obj, ParkingBean.class);
//            //保存车辆类型
//            CarType_Shared.saveALL(getApplicationContext(), parking.getAllCarTypes());
//            //保存免费类型
//            FreeResons_Shared.saveALL(getApplicationContext(), parking.getFreereasons());
//            //保存停车场信息
//            Parking_Shared.saveALL(getApplicationContext(), parking);
//
//        } catch (Exception e) {
//
//            Log.w(TAG,e.toString());
//        }
//    }
//
//    //TODO 保存工作站的IP信息
//    private void saveWorksiteAll(String obj) {
//
//
//        Log.i(TAG , obj + "***************");
//
//        List<WorkStationDevice> workStationDevice = new Gson().fromJson(obj, new TypeToken<List<WorkStationDevice>>(){}.getType());
//
//        for (int i = 0; i < workStationDevice.size(); i++) {
//
//            //0:入口车道下的摄像头; 1:出口车道下的摄像头
//            if ("0".equals(workStationDevice.get(i).getPasstype())) {
//
//                Camera_in_Shared.saveALL(getApplicationContext(), workStationDevice.get(i).getCameras()[0]);
//                Led_in_Shared.saveALL(getApplicationContext(), workStationDevice.get(i).getLeds()[0]);
//            } else if ("1".equals(workStationDevice.get(i).getPasstype())) {
//
//                Camera_out_Shared.saveALL(getApplicationContext(), workStationDevice.get(i).getCameras()[0]);
//                Led_out_Shared.saveALL(getApplicationContext(), workStationDevice.get(i).getLeds()[0]);
//            }
//        }
//
//    }
//
//
//    @Override
//    public void onResponseGET(String url, Map<String, String> param, String sign, final String object) {
//        Log.i(TAG, "url=" + url + ";param=" + param + ";sign=" + sign + ";object=" + object);
//
//        switch (sign) {
//
//            case "worksiteinfo_cominfo": saveWorkStation(object);
//                break;
//
//            case "worksiteinfo_getpassinfo": saveWorksiteAll(object);
//                break;
//            case "OrderType_list": saveOrderType(object);
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void onResponsePOST(String url, Map<String, String> param, String sign, String object) {
//
//    }
//
//    @Override
//    public void onResponseFile(String url, Map<String, String> param, String sign, String object) {
//
//    }
//}

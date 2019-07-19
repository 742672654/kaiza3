package com.cz.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.Static_bean;
import com.cz.Shared.Camera_in_Shared;
import com.cz.Shared.Camera_out_Shared;
import com.cz.Shared.CarType_Shared;
import com.cz.Shared.FreeResons_Shared;
import com.cz.Shared.Led_in_Shared;
import com.cz.Shared.Led_out_Shared;
import com.cz.Shared.OrderType_Shared;
import com.cz.Shared.Parking_Shared;
import com.cz.Shared.User_Shared;
import com.cz.bean.WorksiteBean;
import com.cz.Shared.Worksite_Shared;
import com.cz.bean.OrderTypeBean;
import com.cz.bean.ParkingBean;
import com.cz.bean.UserBean;
import com.cz.http.HttpCallBack2;
import com.cz.http.HttpManager2;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zld.R;
import com.zld.bean.WorkStationDevice;
import com.zld.lib.constant.Constant;
import com.zld.lib.util.SharedPreferencesUtils;
import com.zld.service.HomeExitPageService;
import com.zld.ui.BackgroundActivity;
import com.zld.view.WorkStationPicker;
import com.zld.view.WorkStationPicker.OnWorkStationChangedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 设置工作站
 */
@SuppressLint("LongLogTag")
public class ChooseWorkstation2Activity extends FragmentActivity implements HttpCallBack2 {

    private static final String TAG = "ChooseWorkstationActivity";

    private TextView tv_fuzy_search, tv_month_card;
    private CheckBox cb_fuzy_search;
    private WorkStationPicker np_work;
    private Button btn_into_background;
    private Button btn_yes;
    private int id; //工作站List的序号
    private String value; //工作站名
    private String intentStaname;
    private String workStation[] = null;
    private ArrayList<WorksiteBean> workStations;
    private ParkingBean parking;
    private UserBean userBean;

    private int s = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_set_workstation2);

        //加载部件
        initView();
        onClickEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();

        userBean = User_Shared.getALL(getApplicationContext());

        //请求后台参数
        getOrderTypeList();
        getWorkStation(userBean);
        getEnterclose(userBean);

    }

    //加载部件
    private void initView() {


        tv_fuzy_search = findViewById(R.id.tv_fuzy_search2);

        cb_fuzy_search = findViewById(R.id.cb_fuzy_search2);


        np_work = findViewById(R.id.np_work2);

        btn_into_background = findViewById(R.id.btn_into_background2);
        btn_yes = findViewById(R.id.btn_yes2);


        if (isShowFuzySearch()) {
            cb_fuzy_search.setChecked(true);
            tv_fuzy_search.setTextColor(getResources().getColor(R.color.black));
        } else {
            cb_fuzy_search.setChecked(false);
        }

    }

    //加载点击事件
    private void onClickEvent() {

        //工作站滚动监听
        np_work.setOnWorkStationChangedListener(new OnWorkStationChangedListener() {
            @Override
            public void onWorkStationChanged(String value, int id) {

                ChooseWorkstation2Activity.this.value = value;
                ChooseWorkstation2Activity.this.id = id;
            }
        });



        //确认按钮
        btn_yes.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                WorksiteBean worksite = workStations.get(0);

                //添加用户id
                worksite.setU_id(userBean.getAccount());

                //保存工作站信息
                saveWorksite(worksite);

                Map<String, String> param2 = new HashMap<String, String>();
                param2.put("worksite_id", worksite.getId());
                param2.put("comId", worksite.getComid());
                HttpManager2.requestGet(Static_bean.worksiteinfo_getpassinfo, param2, ChooseWorkstation2Activity.this, "worksiteinfo_getpassinfo");

            }
        });

    }

    //手动登录1,自动登录2
    public void join_ZldNew2Activity() {

Log.i( TAG,"s=====" +s);
        if( s<3 ){
            return;
        }



        WorksiteBean worksite = workStations.get(0);

        //添加用户id
        worksite.setU_id(userBean.getAccount());

        //保存工作站信息
        saveWorksite(worksite);

        Map<String, String> param2 = new HashMap<String, String>();
        param2.put("worksite_id", worksite.getId());
        param2.put("comId", worksite.getComid());
        HttpManager2.requestGet(Static_bean.worksiteinfo_getpassinfo, param2, ChooseWorkstation2Activity.this, "worksiteinfo_getpassinfo");
    }

    //TODO 获取订单类型
    private void getOrderTypeList( ) {

        Map<String, String> param = new HashMap<String, String>();
        param.put("token", userBean.getToken());

        HttpManager2.requestGet(Static_bean.OrderType_list, param, this, "OrderType_list");
    }

    //TODO 获取订单类型2
    private void getOrderTypeList2(String obj) {

        try {

            Log.i(TAG,"获取订单类型obj:  "+obj);

            List<OrderTypeBean> orderTypeList = new Gson().fromJson(obj,new TypeToken<List<OrderTypeBean>>(){}.getType());
            OrderType_Shared.saveALL(getApplicationContext(),orderTypeList);
            ++s;
            join_ZldNew2Activity();
        } catch (Exception e) {

            Log.w(TAG,e);
        }
    }

    //TODO 获取停车场信息
    private void getWorkStation(UserBean userBean) {

        Map<String, String> param = new HashMap<String, String>();
        param.put("token", userBean.getToken());
        param.put("out", "json");

        HttpManager2.requestGet(Static_bean.worksiteinfo_cominfo, param, this, "worksiteinfo_cominfo");
    }

    //TODO 获取停车场信息2
    private void getWorkStation2(String obj) {

        try {

            Log.i(TAG,"获取停车场信息obj:  "+obj);
            parking = new Gson().fromJson(obj, ParkingBean.class);

            ++s;
            join_ZldNew2Activity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO 获取工作站
    private void getEnterclose(UserBean userBean) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("comId", userBean.getComid());
        param.put("out", "json");

        HttpManager2.requestGet(Static_bean.worksiteinfo_queryworksite, param, this, "worksiteinfo_queryworksite");
    }

    //TODO 获取工作站2
    private void getEnterclose2(String object) {

        Log.i(TAG, "获取到工作站为" + object);

        int index = 0;
        if (object == null || "".equals(object)) {
            return;
        }
        workStations = new Gson().fromJson(object, new TypeToken<List<WorksiteBean>>() {
        }.getType());
        workStation = new String[workStations.size()];

        if (workStations != null || workStations.size() != 0) {

            for (int i = 0; i < workStations.size(); i++) {
                workStation[i] = workStations.get(i).getWorksite_name();
                if (intentStaname != null) {
                    if (workStation[i].equals(intentStaname)) {
                        index = i;
                    }
                }
            }
            np_work.setValue(index);
            np_work.setData(0, workStation.length - 1, workStation);

            //默认第一个
            value = workStation[index];
            id = index;
        }

        ++s;
        join_ZldNew2Activity();
    }

    //TODO 保存工作站的配置信息
    private void saveWorksite(WorksiteBean worksite) {

        //XML：保存工作站信息
        Worksite_Shared.saveALL(getApplicationContext(), worksite);

        CarType_Shared.saveALL(getApplicationContext(), parking.getAllCarTypes());
        FreeResons_Shared.saveALL(getApplicationContext(), parking.getFreereasons());
        //LiftReason_Shared.saveALL(getApplicationContext(), parking.getLiftreason());
        Parking_Shared.saveALL(getApplicationContext(), parking);
    }

    //TODO 保存工作站的IP信息
    private void saveWorksiteAll(String obj) {



        List<WorkStationDevice> workStationDevice = new Gson().fromJson(obj, new TypeToken<List<WorkStationDevice>>(){}.getType());

        for (int i = 0; i < workStationDevice.size(); i++) {

            //0:入口车道下的摄像头; 1:出口车道下的摄像头
            if ("0".equals(workStationDevice.get(i).getPasstype())) {

                Camera_in_Shared.saveALL(getApplicationContext(), workStationDevice.get(i).getCameras()[0]);
                Led_in_Shared.saveALL(getApplicationContext(), workStationDevice.get(i).getLeds()[0]);
            } else if ("1".equals(workStationDevice.get(i).getPasstype())) {

                Camera_out_Shared.saveALL(getApplicationContext(), workStationDevice.get(i).getCameras()[0]);
                Led_out_Shared.saveALL(getApplicationContext(), workStationDevice.get(i).getLeds()[0]);
            }
        }


            ChooseWorkstation2Activity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    Intent intent = new Intent(ChooseWorkstation2Activity.this, ZldNew2Activity.class);
                    intent.putExtra("joinType", TAG);
                    ChooseWorkstation2Activity.this.startActivity(intent);
                }
            });
    }

    private boolean isShowFuzySearch() {

        return getApplicationContext().getSharedPreferences("worksite", Context.MODE_PRIVATE).getBoolean("yessir", false);
    }



    @Override
    public void onResponseGET(String url, Map<String, String> param, String sign, final String object) {

        Log.i(TAG, "url=" + url + ";param=" + param + ";sign=" + sign + ";object=" + object);

        try {

        switch (sign) {
            case "worksiteinfo_queryworksite":

                ChooseWorkstation2Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getEnterclose2(object);
                    }
                });
                break;
            case "worksiteinfo_cominfo":
                ChooseWorkstation2Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getWorkStation2(object);
                    }
                });
                break;
            case "worksiteinfo_getpassinfo":
                saveWorksiteAll(object);

                break;
            case "OrderType_list":
                getOrderTypeList2(object);
                break;
            default:
                break;
        }
        }catch (Exception e){
            Log.w( TAG,e );
        }
    }

    @Override
    public void onResponsePOST(String url, Map<String, String> param, String sign, String object){}

    @Override
    public void onResponseFile(String url, Map<String, String> param, String sign, String object){}

    public boolean isOnMainThread(){return Thread.currentThread() == Looper.getMainLooper().getThread();}

}


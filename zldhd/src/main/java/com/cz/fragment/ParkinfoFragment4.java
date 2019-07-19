package com.cz.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.Static_bean;
import com.cz.Shared.User_Shared;
import com.cz.bean.WorksiteBean;
import com.cz.Shared.Worksite_Shared;
import com.cz.bean.UserBean;
import com.cz.http.HttpCallBack2;
import com.cz.http.HttpManager2;
import com.cz.view.SelectParkrNumber;
import com.google.gson.Gson;
import com.zld.R;
import com.zld.lib.util.TimeTypeUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 功能说明:4
 * 日期:	2019年5月7日
 * 开发者:	KXD
 */
public class ParkinfoFragment4 extends BaseFragment implements  HttpCallBack2 {


    private static final String TAG = "ParkinfoFragment4";
    public TextView tv_idle_carport_num;
    UserBean userBean;
    WorksiteBean worksiteBean;
    private TextView tv_work_time;
    private TextView btn_charge_people;
    private TextView tv_tcb_pay_money;
    private TextView tv_cash_pay_money;
    private SelectParkrNumber selectParkNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.cz4_park_info, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        userBean = User_Shared.getALL(getActivity());
        worksiteBean = Worksite_Shared.getALL(this.getActivity());

        local_parameter();
        http_parameter();
    }

    private void initView(View rootView) {

        tv_idle_carport_num = rootView.findViewById(R.id.tv_idle_carport_num2);
        tv_work_time = rootView.findViewById(R.id.tv_work_time2);
        tv_tcb_pay_money = rootView.findViewById(R.id.tv_tcb_pay_money2);
        tv_cash_pay_money = rootView.findViewById(R.id.tv_cash_pay_money2);
        btn_charge_people = rootView.findViewById(R.id.btn_charge_people2);

        Button btn_charge_number = rootView.findViewById(R.id.btn_charge_number2);

        //修改空闲车位的viev
        selectParkNumber = new SelectParkrNumber(btn_charge_number, this);
        btn_charge_number.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                selectParkNumber.showView();
            }
        });

        //显示和隐藏金额
        final TextView tv_show_money = rootView.findViewById(R.id.tv_show_money2);
        tv_show_money.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (tv_show_money.getText().equals("隐藏")) {
                    tv_show_money.setText("显示");
                    tv_tcb_pay_money.setVisibility(View.INVISIBLE);
                    tv_cash_pay_money.setVisibility(View.INVISIBLE);
                } else if (tv_show_money.getText().equals("显示")) {
                    tv_show_money.setText("隐藏");
                    tv_tcb_pay_money.setVisibility(View.VISIBLE);
                    tv_cash_pay_money.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void local_parameter() {

        btn_charge_people.setText(userBean.getName());
        tv_work_time.setText(userBean.getUptime());
    }


    //http请求获取空闲车位和支付金额
    public void http_parameter() {

        Map<String, String> param = new HashMap<String, String>();
        param.put("token", userBean.getToken());
        param.put("logontime", userBean.getLogontime());
        String etime = TimeTypeUtil.getTodayDate(new Date());
        param.put("btime", etime);
        param.put("etime", etime);
        param.put("worksite_id", worksiteBean.getId());
        param.put("comid", worksiteBean.getComid());
        param.put("out", "json");
        param.put("uid", userBean.getAccount());

        HttpManager2.requestGet(Static_bean.collectorrequest_order_quantity, param, this, "order_quantity");


        Map<String, String> param2 = new HashMap<String, String>();
        param2.put("comid", worksiteBean.getComid());
        param2.put("out", "json");
        HttpManager2.requestGet(Static_bean.whitelist_remaining, param2, this, "remaining");
    }




    //设置订单量和收费金额,要求主线程
    public void setMoneyTextView2(final Map<String, String> param, final String object) {


        if (!isOnMainThread()) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setMoneyTextView2( param, object );
                }
            });
            return;
        }

        Map<String,String> map = new Gson().fromJson(object, Map.class);

        tv_cash_pay_money.setText((map.get("total2")==null|| "null".equals(map.get("total2")) ?0:map.get("total2"))+"元");
        tv_tcb_pay_money.setText(map.get("sl")+"单");
    }



    //设置空闲车位数量，要求主线程
    public void setFreeParking(final Integer free, final String joinTAG) {

        if (!isOnMainThread()) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setFreeParking(free, joinTAG);
                }
            });
            return;
        }

        if (TAG.equals(joinTAG)) {

            tv_idle_carport_num.setText(free.toString());
        } else if ("SelectParkrNumber".equals(joinTAG)) {
            //已满
            if (free == -99999999) {
                tv_idle_carport_num.setText("0");
            } else {
                Integer in = Integer.valueOf(tv_idle_carport_num.getText().toString());
                tv_idle_carport_num.setText(((Integer) (in + free)).toString());
            }
        }
    }




    @Override
    public void onResponseGET(String url, Map<String, String> param, String sign, String object) {


        try {
                Log.i(TAG,"url="+url+";object="+object);
             if ("remaining".equals(sign)) {

                Map map = new Gson().fromJson(object, HashMap.class);
                setFreeParking(Integer.valueOf(map.get("free").toString()), TAG);
                }else if ("order_quantity".equals(sign)){

                setMoneyTextView2(param, object);
                }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResponsePOST(String url, Map<String, String> param, String sign, String object) {

    }

    @Override
    public void onResponseFile(String url, Map<String, String> param, String sign, String object) {

    }

}

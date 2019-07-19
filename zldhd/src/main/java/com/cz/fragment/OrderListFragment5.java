package com.cz.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.Static_bean;
import com.cz.Shared.User_Shared;
import com.cz.bean.WorksiteBean;
import com.cz.Shared.Worksite_Shared;
import com.cz.activity.ZldNew2Activity;
import com.cz.bean.UserBean;
import com.cz.bean.OrderBean;
import com.cz.http.HttpCallBack2;
import com.cz.http.HttpManager2;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zld.R;
import com.zld.adapter.CurrentOrderAdapter;
import com.zld.bean.AllOrder;
import com.zld.bean.CarNumberOrder;
import com.zld.bean.CurrentOrder;
import com.zld.lib.state.OrderListState;

import java.util.HashMap;
import java.util.Map;


/**
 * 功能说明: 订单列表
 * 日期:	2019年5月8日
 */
public class OrderListFragment5 extends Fragment implements OnClickListener, HttpCallBack2 {


    private static final String TAG = "OrderListFragment5";
    public AllOrder itemOrder;
    public OrderListState orderListState;
    WorksiteBean worksite;
    UserBean user;
    ZldNew2Activity activity;
    private View mainView;
    private int page = 1;
    private int size = 2000;
    public CurrentOrder orders_in;
    public CurrentOrder orders_out;

    public CurrentOrderAdapter adapter;
    private PullToRefreshListView lv_current_order;
    public int listFlag = 0;//在场还是离场的标记：在场0，离场1
    public RadioButton in_park_cars2;
    public RadioButton out_park_cars2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.cz5_pulltorefreshlistview, container, false);
        initView();
        return mainView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (ZldNew2Activity) activity;
    }

    @Override
    public void onStart() {
        super.onStart();

        worksite = Worksite_Shared.getALL(getActivity().getApplicationContext());
        user = User_Shared.getALL(getActivity().getApplicationContext());
        in_park_cars2.performClick();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.in_park_cars2:
                itemOrder = null;
                listFlag = 0;
                size = 20;
                changeRadioBtnColor(); //改变RadioBtn颜色
                refreshInList();    //请求数据
                hideButton();       //隐藏按钮
                break;
            case R.id.out_park_cars2:
                itemOrder = null;
                listFlag = 1;
                size = 20;
                changeRadioBtnColor(); //改变RadioBtn颜色
                refreshOutList();   //请求数据
                hideButton();       //隐藏按钮
                break;
            default:
                break;
        }
    }

    private void initView() {

        in_park_cars2 = mainView.findViewById(R.id.in_park_cars2);
        out_park_cars2 = mainView.findViewById(R.id.out_park_cars2);
        in_park_cars2.setOnClickListener(this);
        out_park_cars2.setOnClickListener(this);

        orderListState = OrderListState.getInstance();
        adapter = new CurrentOrderAdapter(getActivity(), null);
        lv_current_order = mainView.findViewById(R.id.pulltorefreshListView2);
        lv_current_order.setMode(PullToRefreshBase.Mode.BOTH);
        lv_current_order.setAdapter(adapter);

        //监听列表被刷新时事件.
        lv_current_order.setOnRefreshListener(
                new PullToRefreshBase.OnRefreshListener2<ListView>() {
                    @Override
                    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                        if (listFlag == 0) {
                            in_park_cars2.performClick();
                        } else if (listFlag == 1) {
                            out_park_cars2.performClick();
                        }
                        lv_current_order.onRefreshComplete();
                    }

                    @Override
                    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                        size = size * 2;
                        if (listFlag == 0) {
                            refreshInList();
                        } else if (listFlag == 1) {
                            refreshOutList();
                        }
                        lv_current_order.onRefreshComplete();
                    }
                });

        lv_current_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                itemOrder = adapter.getAllOrders(position);
                adapter.highLightSelectedItem(position);
                Log.i(TAG, "订单点击事件,获取订单" + itemOrder);

                clickOrder();
            }
        });
    }

    //订单点击事件
    public void clickOrder() {

        Map<String, String> map = new HashMap<String, String>(4);
        map.put("comid", worksite.getComid());
        map.put("orderid", itemOrder.getId());
        map.put("ptype", String.valueOf(1));
        map.put("out", "json");

        HttpManager2.requestGet(Static_bean.cobp_catorder, map, this, "clickOrder");
    }

    //订单点击事件
    public void clickOrder2(String object) {

         CarNumberOrder carNumberOrder = new Gson().fromJson(object, CarNumberOrder.class);

                Bundle bundle = new Bundle();
                    bundle.putString( "joinType",TAG );
                    bundle.putString("uuid",carNumberOrder.getUuid());

                Message message = new Message();
                message.what = ZldNew2Activity.onClick_orders;
                message.obj = carNumberOrder;

                message.setData(bundle);
                Handler handler = activity.getHandler();
                handler.sendMessage(message);
    }

    //点击入场列表
    public void refreshInList() {

        Map<String, String> map = new HashMap<String, String>(4);
        map.put("comid", worksite.getComid());
        map.put("page", String.valueOf(page));
        map.put("size", String.valueOf(size));
        map.put("through", String.valueOf(3));
        HttpManager2.requestGet(Static_bean.cobp_getcurrorder, map, this, "refreshInList");
    }

    //点击入场列表2
    public void refreshInList2(final String object) {

        OrderListFragment5.this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if ("{\"info\":\"没有记录\"}".equals(object)) {
                        orders_in = null;
                        adapter.setOrders(null);
                    } else {
                        orders_in = new Gson().fromJson(object, CurrentOrder.class);
                        adapter.setOrders(orders_in.getInfo());
                    }
                } catch (Exception e) {
                    Log.w(TAG, e);
                }
            }
        });
    }

    //点击出场列表
    public void refreshOutList() {

        Map<String, String> map = new HashMap<String, String>(8);
        map.put("comid", worksite.getComid());
        map.put("token", user.getToken());
        map.put("page", String.valueOf(page));
        map.put("size", String.valueOf(size));
        map.put("uid", user.getAccount());
        map.put("day", "today");
        map.put("ptype", String.valueOf(1));
        map.put("out", "json");

        HttpManager2.requestGet(Static_bean.collectorrequest_orderhistory, map, this, "refreshOutList");
    }

    //点击出场列表2
    public void refreshOutList2(final String object) {

        OrderListFragment5.this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if ("{\"info\":\"没有记录\"}".equals(object)) {
                        orders_out = null;
                        adapter.setOrders(null);
                    } else {
                        orders_out = new Gson().fromJson(object, CurrentOrder.class);
                        adapter.setOrders(orders_out.getInfo());
                    }
                } catch (Exception e) {
                    Log.w(TAG, e);
                }
            }
        });
    }

    // TODO  改变RadioBtn颜色
    public void changeRadioBtnColor() {

        if (listFlag == 0) {

            in_park_cars2.setBackgroundColor(this.getResources().getColor(R.color.blue_239DFD));
            in_park_cars2.setTextColor(this.getResources().getColor(R.color.plate_text));
            out_park_cars2.setBackgroundColor(this.getResources().getColor(R.color.plate_text));
            out_park_cars2.setTextColor(this.getResources().getColor(R.color.blue_239DFD));
        } else {

            out_park_cars2.setBackgroundColor(this.getResources().getColor(R.color.blue_239DFD));
            out_park_cars2.setTextColor(this.getResources().getColor(R.color.plate_text));
            in_park_cars2.setBackgroundColor(this.getResources().getColor(R.color.plate_text));
            in_park_cars2.setTextColor(this.getResources().getColor(R.color.blue_239DFD));
        }
    }

    //TODO 出口拍照后：设置列表为某一条
    public void setOrder(String ordersID){

        listFlag = 0;
        changeRadioBtnColor();

        for(int i=0;i<orders_in.getInfo().size();i++){

            if (orders_in.getInfo().get(i).getId().equals(ordersID)){
                adapter.setOrder( orders_in.getInfo().get(i) );
                return;
            }
        }
    }

    //TODO 清空订单信息、隐藏车费按钮
    public void hideButton() {


        Bundle bundle = new Bundle();
        bundle.putString( "joinType",TAG );

        Message message = new Message();
        message.what = ZldNew2Activity.hide_orders;

        message.setData(bundle);
        Handler handler = activity.getHandler();
        handler.sendMessage(message);
    }

    @Override
    public void onResponseGET(String url, Map<String, String> param, final String sign, final String object) {

        Log.i(TAG,"param="+param+",sign="+sign+";object="+object);
        try {
            switch (sign) {
                case "refreshInList":
                    refreshInList2(object);
                    break;
                case "refreshOutList":
                    refreshOutList2(object);
                    break;
                case "clickOrder":
                    clickOrder2(object);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponsePOST(String url, Map<String, String> param, String sign, String object) { }

    @Override
    public void onResponseFile(String url, Map<String, String> param, String sign, String object) { }

}
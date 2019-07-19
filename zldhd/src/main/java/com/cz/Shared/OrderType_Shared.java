package com.cz.Shared;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.cz.bean.OrderTypeBean;
import com.cz.util.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.util.List;

//订单类型
public class OrderType_Shared {



    private static final String TAG = "OrderType_Shared";


    //判断某个xml是否存在
    public static boolean isExistence(Context context) {


        return BaseShared.isExistence(context, "OrderType");
    }


    //返回xml所有参数
    public static List<OrderTypeBean> getALL(Context context) {

        try {

            String orderTypeString = BaseShared.getALL(context, "OrderType", String.class);
            OrderType_SharedBean orderType_SharedBean = new Gson().fromJson(orderTypeString, new TypeToken<OrderType_SharedBean>(){}.getType());

            List<OrderTypeBean> orderTypeList = new Gson().fromJson(orderType_SharedBean.getOrderType(), new TypeToken<List<OrderTypeBean>>(){}.getType());
            return orderTypeList;
        } catch (Exception e) {
            Log.w(TAG,e);
        }
        return null;
    }

    //对象保存参数到xml
    public static boolean saveALL(Context context, List<OrderTypeBean> orderTypeList) {


        try {

            Log.i( TAG+"保存",new Gson().toJson(orderTypeList));

            SharedPreferences spf2 = context.getSharedPreferences("OrderType", Context.MODE_PRIVATE);
            SharedPreferences.Editor spfEdit2 = spf2.edit();
            spfEdit2.clear();
            spfEdit2.commit();

            SharedPreferences spf = context.getSharedPreferences("OrderType", Context.MODE_PRIVATE);
            SharedPreferences.Editor spfEdit = spf.edit();
            spfEdit.putBoolean("existence", true);
            spfEdit.putString("orderType", new Gson().toJson(orderTypeList));
            spfEdit.putString("existence_time", TimeUtil.getDateTime());
            spfEdit.commit();
            return true;
        } catch (Exception e) {
            Log.w(TAG,e);
        }
        return false;
    }

}

class OrderType_SharedBean{

    private boolean existence;
    private String existence_time;
    private String orderType;

    public boolean isExistence() {
        return existence;
    }
    public void setExistence(boolean existence) {
        this.existence = existence;
    }
    public String getExistence_time() {
        return existence_time;
    }
    public void setExistence_time(String existence_time) {
        this.existence_time = existence_time;
    }
    public String getOrderType() {
        return orderType;
    }
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        return "OrderType_SharedBean{" +
                "existence=" + existence +
                ", existence_time='" + existence_time + '\'' +
                ", orderType='" + orderType + '\'' +
                '}';
    }

}
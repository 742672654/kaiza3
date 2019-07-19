package com.cz.Shared;


import android.content.Context;

import com.cz.bean.UserBean;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;


//TODO 当前登录用户信息
public class User_Shared {

    /**
     * 判断某个xml是否存在
     *
     * @param context
     */
    public static boolean isExistence(Context context) {


        return BaseShared.isExistence(context, "User");
    }


    /**
     * 返回xml所有参数
     *
     * @param context
     */
    public static UserBean getALL(Context context) {


        return BaseShared.getALL(context, "User", UserBean.class);
    }

    /**
     * 对象保存参数到xml
     *
     * @param context
     */
    public static boolean saveALL(Context context, UserBean userBean) {

        Map<String, Object> map = new Gson().fromJson(new Gson().toJson(userBean), HashMap.class);

        map.remove("LOGIN_USER");

        BaseShared.saveALLMap(context, "User", map);
        return true;
    }

}

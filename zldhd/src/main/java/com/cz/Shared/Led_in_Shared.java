package com.cz.Shared;

import android.content.Context;

import com.zld.bean.MyLedInfo;

public class Led_in_Shared {


    /**
     * 判断某个xml是否存在
     *
     * @param context
     */
    public static boolean isExistence(Context context) {


        return BaseShared.isExistence(context, "Led_in");
    }

    /**
     * 返回xml所有参数
     *
     * @param context
     */
    public static MyLedInfo getALL(Context context) {


        return BaseShared.getALL(context, "Led_in", MyLedInfo.class);
    }

    /**
     * 对象保存参数到xml
     *
     * @param context
     */
    public static boolean saveALL(Context context, MyLedInfo myLedInfo) {


        return BaseShared.saveALL(context, "Led_in", myLedInfo);
    }

}

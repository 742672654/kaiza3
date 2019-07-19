package com.cz.Shared;

import android.content.Context;

import com.zld.bean.MyCameraInfo;

public class Camera_out_Shared {


    /**
     * 判断某个xml是否存在
     *
     * @param context
     */
    public static boolean isExistence(Context context) {


        return BaseShared.isExistence(context, "Camera_out");
    }

    /**
     * 返回xml所有参数
     *
     * @param context
     */
    public static MyCameraInfo getALL(Context context) {


        return BaseShared.getALL(context, "Camera_out", MyCameraInfo.class);
    }

    /**
     * 对象保存参数到xml
     *
     * @param context
     */
    public static boolean saveALL(Context context, MyCameraInfo myCameraInfo) {


        return BaseShared.saveALL(context, "Camera_out", myCameraInfo);
    }


}

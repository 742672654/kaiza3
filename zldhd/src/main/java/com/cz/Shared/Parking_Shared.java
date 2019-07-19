package com.cz.Shared;

import android.content.Context;

import com.cz.bean.ParkingBean;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Parking_Shared {


    /**
     * 判断某个xml是否存在
     *
     * @param context
     */
    public static boolean isExistence(Context context) {


        return BaseShared.isExistence(context, "Parking");
    }


    /**
     * 返回xml所有参数
     *
     * @param context
     */
    public static ParkingBean getALL(Context context) {


        return BaseShared.getALL(context, "Parking", ParkingBean.class);
    }

    /**
     * 对象保存参数到xml
     *
     * @param context
     */
    public static boolean saveALL(Context context, ParkingBean parkingBean) {


        Map<String, Object> map = new Gson().fromJson(new Gson().toJson(parkingBean), HashMap.class);

        map.remove("allCarTypes");
        map.remove("freereasons");
        map.remove("liftreason");
        map.remove("parking");

        BaseShared.saveALLMap(context, "Parking", map);
            return true;
    }


}

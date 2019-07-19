package com.cz.Shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.cz.util.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zld.bean.CarType;
import com.zld.bean.FreeResons;

import java.util.List;


// 车辆免费类型
public class FreeResons_Shared {


    /**
     * 判断某个xml是否存在
     *
     * @param context
     */
    public static boolean isExistence(Context context) {


        return BaseShared.isExistence(context, "FreeResons");
    }


    /**
     * 返回xml所有参数
     *
     * @param context
     */
    public static List<FreeResons> getALL(Context context) {


        try {



            String freeResonsString = BaseShared.getALL(context, "FreeResons", String.class);
            FreeResons_SharedBean freeResons_SharedBean = new Gson().fromJson(freeResonsString, new TypeToken<FreeResons_SharedBean>(){}.getType());
            List<FreeResons> carTypeList = new Gson().fromJson(freeResons_SharedBean.getFreeResons(), new TypeToken<List<FreeResons>>() {}.getType());

            return carTypeList;
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    /**
     * 对象保存参数到xml
     *
     * @param context
     */
    public static boolean saveALL(Context context, List<FreeResons> freeResons) {

        try {


            SharedPreferences spf2 = context.getSharedPreferences("FreeResons", Context.MODE_PRIVATE);
            SharedPreferences.Editor spfEdit2 = spf2.edit();
            spfEdit2.clear();
            spfEdit2.commit();


            SharedPreferences spf = context.getSharedPreferences("FreeResons", Context.MODE_PRIVATE);
            SharedPreferences.Editor spfEdit = spf.edit();
            spfEdit.putBoolean("existence", true);

            spfEdit.putString("freeResons", new Gson().toJson(freeResons));

            spfEdit.putString("existence_time", TimeUtil.getDateTime());
            spfEdit.commit();
            return true;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }
}

class FreeResons_SharedBean{

    private boolean existence;
    private String existence_time;
    private String freeResons;


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
    public String getFreeResons() {
        return freeResons;
    }
    public void setFreeResons(String freeResons) {
        this.freeResons = freeResons;
    }

}
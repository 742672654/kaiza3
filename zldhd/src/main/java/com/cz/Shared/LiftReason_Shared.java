package com.cz.Shared;


import android.content.Context;
import android.content.SharedPreferences;

import com.cz.util.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zld.bean.LiftReason;

import java.util.List;


//手动抬杆原因
public class LiftReason_Shared {
    /**
     * 判断某个xml是否存在
     *
     * @param context
     */
    public static boolean isExistence(Context context) {


        return BaseShared.isExistence(context, "LiftReason");
    }


    /**
     * 返回xml所有参数
     *
     * @param context
     */
    public static List<LiftReason> getALL(Context context) {


        try {

            String liftReasonString = BaseShared.getALL(context, "LiftReason", String.class);
            List<LiftReason> liftReasonList = new Gson().fromJson(liftReasonString, new TypeToken<List<LiftReason>>() {
            }.getType());
            return liftReasonList;
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
    public static boolean saveALL(Context context, List<LiftReason> liftReason) {

        try {


            SharedPreferences spf2 = context.getSharedPreferences("LiftReason", Context.MODE_PRIVATE);
            SharedPreferences.Editor spfEdit2 = spf2.edit();
            spfEdit2.clear();
            spfEdit2.commit();


            SharedPreferences spf = context.getSharedPreferences("LiftReason", Context.MODE_PRIVATE);
            SharedPreferences.Editor spfEdit = spf.edit();
            spfEdit.putBoolean("existence", true);

            spfEdit.putString("liftReason", new Gson().toJson(liftReason));

            spfEdit.putString("existence_time", TimeUtil.getDateTime());
            spfEdit.commit();
            return true;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }
}

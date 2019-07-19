package com.cz.Shared;


import android.content.Context;
import android.content.SharedPreferences;

import com.cz.bean.WorksiteBean;
import com.cz.util.TimeUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;


public class Worksite_Shared {


    /**
     * 判断某个xml是否存在
     *
     * @param context
     */
    public static boolean isExistence(Context context) {

        //MODE_WORLD_WRITEABLE：指定此SharedPreferences能被其他程序读写。
        SharedPreferences sp = context.getSharedPreferences("Worksite", Context.MODE_PRIVATE);

        return sp.getBoolean("existence", false);
    }


    /**
     * 返回xml所有参数
     *
     * @param context
     */
    public static WorksiteBean getALL(Context context) {

        try {

            return new Gson().fromJson(new Gson().toJson(context.getSharedPreferences("Worksite", Context.MODE_PRIVATE).getAll()), WorksiteBean.class);
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
    public static boolean saveALL(Context context, WorksiteBean worksiteBean) {

        try {


            SharedPreferences spf2 = context.getSharedPreferences("Worksite", Context.MODE_PRIVATE);
            SharedPreferences.Editor spfEdit2 = spf2.edit();
            spfEdit2.clear();
            spfEdit2.commit();


            SharedPreferences spf = context.getSharedPreferences("Worksite", Context.MODE_PRIVATE);
            SharedPreferences.Editor spfEdit = spf.edit();
            spfEdit.putBoolean("existence", true);

            Map<String, Object> map = new Gson().fromJson(new Gson().toJson(worksiteBean), HashMap.class);

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                switch (entry.getValue().getClass().toString()) {
                    case "class java.lang.Byte":
                        spfEdit.putInt(entry.getKey(), (int) entry.getValue());
                        break;
                    case "class java.lang.Short":
                        spfEdit.putInt(entry.getKey(), (int) entry.getValue());
                        break;
                    case "class java.lang.Integer":
                        spfEdit.putInt(entry.getKey(), (int) entry.getValue());
                        break;
                    case "class java.lang.Long":
                        spfEdit.putLong(entry.getKey(), (long) entry.getValue());
                        break;
                    case "class java.lang.Float":
                        spfEdit.putFloat(entry.getKey(), (float) entry.getValue());
                        break;
                    case "class java.lang.Double":
                        spfEdit.putFloat(entry.getKey(), Float.valueOf(entry.getValue().toString()));
                        break;
                    case "class java.lang.Boolean":
                        spfEdit.putBoolean(entry.getKey(), (boolean) entry.getValue());
                        break;
                    case "class java.lang.Character":
                        spfEdit.putString(entry.getKey(), entry.getValue().toString());
                        break;
                    case "class java.lang.String":
                        spfEdit.putString(entry.getKey(), entry.getValue().toString());
                        break;
                }
            }

            spfEdit.putString("existence_time", TimeUtil.getDateTime());
            spfEdit.commit();
            return true;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }
}

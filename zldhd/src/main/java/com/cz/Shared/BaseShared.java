package com.cz.Shared;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.cz.util.TimeUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;


public class BaseShared {

    private static final String TGA = "BaseShared";

    /**
     * 判断某个xml是否存在
     *
     * @param context
     * @param xmlName xml名，不含(xml文件后缀)
     */
    public static boolean isExistence(Context context, String xmlName) {

        //MODE_WORLD_WRITEABLE：指定此SharedPreferences能被其他程序读写。
        SharedPreferences sp = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);

        return sp.getBoolean("existence", false);
    }

    /**
     * 返回xml所有参数
     *
     * @param context
     * @param xmlName xml名，不含(xml文件后缀)
     */
    public static Map<String, ?> getALL(Context context, String xmlName) {

        try {

            return context.getSharedPreferences(xmlName, Context.MODE_PRIVATE).getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 返回xml所有参数
     *
     * @param context
     * @param xmlName xml名，不含(xml文件后缀)
     */
    public static <T> T getALL(Context context, String xmlName, Class<T> clazz) {

        try {

            String json = new Gson().toJson(context.getSharedPreferences(xmlName, Context.MODE_PRIVATE).getAll());

            Log.i(TGA, "getALL" + json);


            if (clazz == String.class){

                return (T)json;
            }
            return new Gson().fromJson(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象保存参数到xml
     *
     * @param context
     * @param xmlName xml名，不含(xml文件后缀)
     */
    public static boolean saveALL(Context context, String xmlName, Object clazz) {

        String json = new Gson().toJson(clazz);
        Log.i(TGA, "saveALL" + json);

        Map<String, Object> map = new Gson().fromJson(json, HashMap.class);

        return saveALLMap(context, xmlName, map);
    }

    /**
     * 对象保存参数到xml
     *
     * @param context
     * @param xmlName xml名，不含(xml文件后缀)
     */
    public static boolean saveALLMap(Context context, String xmlName, Map<String, Object> map) {

        try {
            Log.i(TGA, "saveALLMap" + map);
            SharedPreferences spf2 = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
            SharedPreferences.Editor spfEdit2 = spf2.edit();
            spfEdit2.clear();
            spfEdit2.commit();


            SharedPreferences spf = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
            SharedPreferences.Editor spfEdit = spf.edit();
            spfEdit.putBoolean("existence", true);

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

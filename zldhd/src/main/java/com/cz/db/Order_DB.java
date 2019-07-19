package com.cz.db;


import android.database.Cursor;
import android.util.Log;

import com.cz.bean.OrderBean;


public class Order_DB {

    private static final String TAG = "Order_DB";

    public static OrderBean query_Order(BaseSQL_DB baseSQL_DB, String id) {

        Cursor cursor = null;
        try {

            String buf = new StringBuffer(" select * from localorder where id = '").append(id).append("'; ").toString();
            cursor = baseSQL_DB.getWritableDatabase().rawQuery(buf, null);

            if (cursor.getCount()==0){
                return null;
            }

            cursor.moveToFirst();

            OrderBean orderBean = new OrderBean();
            if (cursor.getColumnIndex("id")!=0)orderBean.setId(cursor.getString(cursor.getColumnIndex("id")));
            if (cursor.getColumnIndex("uuid")!=0)   orderBean.setUuid(cursor.getString(cursor.getColumnIndex("uuid")));
            if (cursor.getColumnIndex("account")!=0)   orderBean.setAccount(cursor.getString(cursor.getColumnIndex("account")));
            if (cursor.getColumnIndex("in_time")!=0)   orderBean.setIn_time(cursor.getString(cursor.getColumnIndex("in_time")));
            if (cursor.getColumnIndex("out_time")!=0)   orderBean.setOut_time(cursor.getString(cursor.getColumnIndex("out_time")));
            if (cursor.getColumnIndex("carnumber")!=0)   orderBean.setCarnumber(cursor.getString(cursor.getColumnIndex("carnumber")));

            return orderBean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
            }
        }
    }

    public static void insert_in_Order(BaseSQL_DB baseSQL_DB, OrderBean orderBean) {


        StringBuffer buf = new StringBuffer("INSERT INTO localorder(id, uuid, account, in_time, carnumber) VALUES ");
        buf.append("('").append(orderBean.getId()).append("',");
        buf.append("'").append(orderBean.getUuid()).append("',");
        buf.append("'").append(orderBean.getAccount()).append("',");
        buf.append("'").append(orderBean.getIn_time()).append("',");
        buf.append("'").append(orderBean.getCarnumber()).append("')");

        baseSQL_DB.getWritableDatabase().execSQL(buf.toString());
    }

    public static void up_Order_id( BaseSQL_DB baseSQL_DB,String id,String uuid ) {


        StringBuffer buf = new StringBuffer("update localorder ");
            buf.append(" set id = '").append(id).append("'");
            buf.append(" where uuid = '").append(uuid).append("'");

        Log.i( TAG, buf.toString());

        baseSQL_DB.getWritableDatabase().execSQL(buf.toString());
    }

    public static void up_out_Order(BaseSQL_DB baseSQL_DB, OrderBean orderBean) {


        StringBuffer buf = new StringBuffer("update localorder ");
        buf.append(" out_time = '").append(orderBean.getOut_time()).append("'");
        buf.append(" where id = '").append(orderBean.getId()).append("'");

        baseSQL_DB.getWritableDatabase().execSQL(buf.toString());
    }


}

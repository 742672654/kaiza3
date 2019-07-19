package com.cz.db;

import android.database.Cursor;

import com.cz.bean.UserBean;

public class User_DB {

    private final static String TAG = "User_DB";

    public static void insert_User(BaseSQL_DB baseSQL_DB, UserBean userBean) {

        deleteUser(baseSQL_DB, userBean);
        StringBuffer buf = new StringBuffer("insert into user( account,password,logontime,qr,role,iscancel,notemsg ,mobile ,")
                .append("cname ,nfc ,token ,authflag , etc ,swipe ,name ,isshowepay ,comid ,state ,info,uptime)");
            buf.append("values('").append(userBean.getAccount()).append("',");
            buf.append("'").append(userBean.getPassword()).append("',");
            buf.append("'").append(userBean.getLogontime()).append("',");
            buf.append("'").append(userBean.getQr()).append("',");
            buf.append("'").append(userBean.getRole()).append("',");
            buf.append("'").append(userBean.getIscancel()).append("',");
            buf.append("'").append(userBean.getNotemsg()).append("',");
            buf.append("'").append(userBean.getMobile()).append("',");
            buf.append("'").append(userBean.getCname()).append("',");
            buf.append("'").append(userBean.getNfc()).append("',");
            buf.append("'").append(userBean.getToken()).append("',");
            buf.append("'").append(userBean.getAuthflag()).append("',");
            buf.append("'").append(userBean.getEtc()).append("',");
            buf.append("'").append(userBean.getSwipe()).append("',");
            buf.append("'").append(userBean.getName()).append("',");
            buf.append("'").append(userBean.getIsshowepay()).append("',");
            buf.append("'").append(userBean.getComid()).append("',");
            buf.append("'").append(userBean.getState()).append("',");
            buf.append("'").append(userBean.getInfo()).append("',");
            buf.append("'").append(userBean.getUptime()).append("');");
        baseSQL_DB.getWritableDatabase().execSQL(buf.toString());
    }

    public static void deleteUser(BaseSQL_DB baseSQL_DB, UserBean userBean) {

        try{

            StringBuffer buf = new StringBuffer(" delete from user where account = '").append(userBean.getAccount()).append("'; ");
            baseSQL_DB.getWritableDatabase().execSQL(buf.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void deleteAll_User(BaseSQL_DB baseSQL_DB) {

        baseSQL_DB.getWritableDatabase().execSQL("delete from user where 1=1;");
    }

    public static void update_User(BaseSQL_DB baseSQL_DB, UserBean userBean) {

        deleteUser(baseSQL_DB, userBean);
        insert_User(baseSQL_DB, userBean);
    }

    public static UserBean query_User(BaseSQL_DB baseSQL_DB, String account) {

        Cursor cursor = null;
        try {

            String buf = new StringBuffer(" select * from user where account = '").append(account).append("'; ").toString();
            cursor = baseSQL_DB.getWritableDatabase().rawQuery(buf, null);
            cursor.moveToFirst();

            UserBean userBean = new UserBean();
                userBean.setAccount(cursor.getString(cursor.getColumnIndex("account")));
                userBean.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                userBean.setRole(cursor.getString(cursor.getColumnIndex("role")));
                userBean.setCname(cursor.getString(cursor.getColumnIndex("cname")));
                userBean.setToken(cursor.getString(cursor.getColumnIndex("token")));
                userBean.setName(cursor.getString(cursor.getColumnIndex("name")));
                userBean.setComid(cursor.getString(cursor.getColumnIndex("comid")));
                userBean.setState(cursor.getString(cursor.getColumnIndex("state")));
                userBean.setUptime(cursor.getString(cursor.getColumnIndex("uptime")));

            return userBean;
        }catch ( Exception e){
            e.printStackTrace();
            return null;
        }finally {
            try { if (cursor!=null){ cursor.close(); } }catch ( Exception e){ }
        }
    }


}

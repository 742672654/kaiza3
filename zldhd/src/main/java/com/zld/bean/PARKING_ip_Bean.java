package com.zld.bean;

import android.util.Log;

import java.util.List;

public class PARKING_ip_Bean {

    //停车场信息
    public static List<InformationBean> PARKING_ip = null;


    /**
     * @return 根据type返回对象
     * @type 1出口，0入口
     */
    public static InformationBean getEntrance(int type){

        for (int i =0;PARKING_ip!=null && i<PARKING_ip.size();i++){
            if (PARKING_ip.get(i).getPasstype().equals(type+"")){
                return PARKING_ip.get(i);
            }
        }
        return null;
    }


    /**
     * @return 根据type性质返回ip
     * @type 1出口，2入口，3出口led，4入口led
     */
    public static String typeIP(int type){

        Log.i("39999","自动拍照---:"+PARKING_ip.toString());

        for (int i =0;PARKING_ip!=null && i<PARKING_ip.size();i++){
            Log.i("39999","自动拍照i---p:"+PARKING_ip.get(i).getIp());
            switch (type){
                case 1: if(PARKING_ip.get(i).getPasstype().equals("1")){return PARKING_ip.get(i).getIp();}break;
                case 2: if(PARKING_ip.get(i).getPasstype().equals("0")){return PARKING_ip.get(i).getIp();};break;
                case 3: if(PARKING_ip.get(i).getPasstype().equals("1")){return PARKING_ip.get(i).getLedip();}break;
                case 4: if(PARKING_ip.get(i).getPasstype().equals("0")){return PARKING_ip.get(i).getLedip();}break;
                default:break;
            }
        }
        return null;
    }


    /**
     * @return 判断ip性质
     * @type 1出口，2入口，3出口led，4入口led
     */
    public static int isType(String ip){

        Log.i("39999",ip+"自动拍照---:"+PARKING_ip.toString());

        for (int i =0;PARKING_ip!=null && i<PARKING_ip.size();i++){

            Log.i("自动拍照---:",PARKING_ip.get(i).getPasstype().equals("0") +"--"+ ip.equals(PARKING_ip.get(i).getIp()));

            if(PARKING_ip.get(i).getPasstype().equals("1") && ip.equals(PARKING_ip.get(i).getIp())){



                return 1;}
            if (PARKING_ip.get(i).getPasstype().equals("0") && ip.equals(PARKING_ip.get(i).getIp())){return 2;}
            if (PARKING_ip.get(i).getPasstype().equals("1") && ip.equals(PARKING_ip.get(i).getLedip())){return 3;}
            if (PARKING_ip.get(i).getPasstype().equals("0") && ip.equals(PARKING_ip.get(i).getLedip())){return 4;}
        }

        return 0;
    }

}

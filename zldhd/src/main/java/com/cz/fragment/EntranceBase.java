package com.cz.fragment;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.cz.Shared.Camera_in_Shared;
import com.cz.Shared.User_Shared;
import com.cz.bean.WorksiteBean;
import com.cz.Shared.Worksite_Shared;
import com.cz.bean.UserBean;
import com.cz.http.HttpCallBack2;
import com.cz.util.PingUtil;
import com.vz.tcpsdk;
import com.vzvison.device.DeviceInfo;
import com.vzvison.device.DeviceSet;
import com.vzvison.device.VedioSetVeiw;
import com.zld.R;
import com.zld.bean.MyCameraInfo;
import java.util.Map;


/**
 * 功能说明: 入口Fragment
 * 日期:	2019年5月7日
 * 开发者:	KXD
 */
public class EntranceBase extends BaseFragment implements  View.OnClickListener,  HttpCallBack2{


    protected static final String TAG = "EntranceBase";

    protected Intent intent;
    protected Button btn_photo;
    protected Button btn_entrance_open_pole;
    protected tcpsdk tsdk = null;
    protected MyCameraInfo info;
    protected UserBean userBean;
    protected WorksiteBean worksite;

    public static Integer sxt_handle = -1;
    public RelativeLayout lnentrance;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        info = Camera_in_Shared.getALL(getActivity());
        userBean = User_Shared.getALL(getActivity());
        worksite = Worksite_Shared.getALL(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cz1_entrance_page, container, false);

        initView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        openSXT();
        initFrame();
    }

    //TODO 初始化控件
    private void initView(View rootView) {

        btn_photo = rootView.findViewById(R.id.btn_photo2);
        btn_photo.setOnClickListener(this);
        btn_entrance_open_pole = rootView.findViewById(R.id.btn_entrance_open_pole2);
        btn_entrance_open_pole.setOnClickListener(this);

        lnentrance = rootView.findViewById(R.id.ln_entrance1);
    }

    //TODO 打开摄像头
    public void openSXT() {


        try {
            //开启出口摄像头控制
            if (tsdk == null) tsdk = new tcpsdk();

            //tsdk.close(  sxt_handle );

            sxt_handle = tsdk.open( info.getIp().getBytes(), info.getIp().length(), 8131, info.getCusername().getBytes(), info.getCusername().length(),
                    info.getCpassword().getBytes(), info.getCpassword().length() );

            Log.i( TAG + "39999", "初始化:" + sxt_handle );
            //添加摄像头拍照回调
            tsdk.setPlateInfoCallBack( sxt_handle, PhotoCallback.getPhotoCallback(), 1 );
        } catch (Exception e) {
            Log.w( TAG, e );
        }
    }

    //TODO 打开网络摄像头（等待第一个先打开）
    public void initFrame() {

        new Thread( new Runnable(){
            @Override
            public void run() {

                //等待2秒再开启摄像头
                try {  Thread.sleep(2000);  } catch (InterruptedException e) { e.printStackTrace(); }

                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                initFrame2();
                            };
                        }, 1000);
                    };

                });
            }
        }).start();
    }

    public void initFrame2() {

        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                DeviceInfo di = new DeviceInfo(100 );

                Log.i( TAG,"开启出口摄像头"+activity.getCameraIn().getIp() );

                di.setIp(activity.getCameraIn().getIp());
                VedioSetVeiw vsv = new VedioSetVeiw(activity);
                vsv.setId(-1);
                DeviceSet ds = new DeviceSet(di, vsv);
                ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
                vsv.setLayoutParams(lp);
                lnentrance.addView(vsv);
                ds.select();
                ds.playVideo();
            }
        });
    }

//    //TODO 开启第一个摄像头视频流
//    private void initFrame() {
//
//        new Thread( new Runnable(){
//            @Override
//            public void run() {
//
//                //等待1秒再开启摄像头
//                try {  Thread.sleep(1000);  } catch (InterruptedException e) { e.printStackTrace(); }
//
//                activity.runOnUiThread(new Runnable(){
//                    @Override
//                    public void run(){
//                        new Handler().postDelayed(new Runnable(){
//                            @Override
//                            public void run() {
//                                activity.runOnUiThread(new Runnable(){
//                                    @Override
//                                    public void run(){
//                                        Log.i( TAG,"开启入口摄像头"+activity.getCameraIn().getIp() );
//                                        DeviceInfo di = new DeviceInfo(10 );
//                                        di.setIp(activity.getCameraIn().getIp());
//                                        VedioSetVeiw vsv = new VedioSetVeiw(activity);
//                                        vsv.setId(0);
//                                        DeviceSet ds = new DeviceSet(di, vsv);
//                                        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
//                                        vsv.setLayoutParams(lp);
//                                        lnentrance.addView(vsv);
//                                        ds.select();
//                                        ds.playVideo();
//                                    }
//                                });
//                            };
//                        },1000);
//                    };
//                });
//            }
//        }).start();
//    }

    @Override
    public void onResponseGET(String url, Map<String, String> param, String sign, String object) { }

    @Override
    public void onResponsePOST(String url, Map<String, String> param, String sign, String object) { }

    @Override
    public void onResponseFile(String url, Map<String, String> param, String sign, String object) { }

    @Override
    public void onClick(View v){}

// TODO ◆◆◆◆◆◆◆↓↓↓↓↓↓↓↓  以前打开摄像头的方法
//        new Handler().postDelayed(runnable, 3000);
//        Handler handlerNumber = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                initFrame();
//            }
//        };
// TODO  ▲▲▲▲▲▲ ↑↑↑↑↑↑↑↑以前打开摄像头的方法

}


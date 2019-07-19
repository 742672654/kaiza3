package com.cz.fragment;


import android.app.ActionBar;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.Static_bean;
import com.cz.Shared.Camera_out_Shared;
import com.cz.http.HttpCallBack2;
import com.cz.http.HttpManager2;
import com.cz.util.TimeUtil;
import com.vz.tcpsdk;
import com.vzvison.device.DeviceInfo;
import com.vzvison.device.DeviceSet;
import com.vzvison.device.VedioSetVeiw;
import com.zld.R;
import com.zld.bean.MyCameraInfo;
import com.zld.lib.util.FileUtil;
import com.zld.lib.util.ImageUitls;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * 功能说明: 出口Fragment
 * 日期:	2019年5月13日
 * 开发者:	KXD
 */
public class ExitBase extends BaseFragment implements  OnClickListener, HttpCallBack2 {


    private static final String TAG = "ExitBase";

    protected Button btn_photo2;
    private Button tv_exit_open_pole;
    protected MyCameraInfo info;
    protected tcpsdk tsdk = null;
    public static Integer sxt_handle = -1;

    public RelativeLayout lnentrance2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cz2_exit_page, container, false);

        initView(rootView);

        info = Camera_out_Shared.getALL(getContext());

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return rootView;
    }

    @Override
    public void onClick(View v){}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        openSXT();
        initFrame();
    }


    // 初始化控件
    private void initView(View rootView) {

        btn_photo2 = rootView.findViewById(R.id.btn_photo22);
        btn_photo2.setOnClickListener(this);

        tv_exit_open_pole = rootView.findViewById(R.id.tv_exit_open_pole2);
        tv_exit_open_pole.setOnClickListener(this);

        lnentrance2 = rootView.findViewById(R.id.ln_entrance2);
    }

    // 打开摄像头
    public void openSXT() {

        Log.i(TAG + "39999", "初始化:" + info.toString());

        //开启出口摄像头控制
       if (tsdk==null)tsdk = new tcpsdk();
        sxt_handle = tsdk.open(info.getIp().getBytes(), info.getIp().length(), 8131, info.getCusername().getBytes(), info.getCusername().length(),
                info.getCpassword().getBytes(), info.getCpassword().length());

        Log.i(TAG + "39999", "初始化:" + sxt_handle);
        //添加摄像头拍照回调
        tsdk.setPlateInfoCallBack(sxt_handle,PhotoCallback.getPhotoCallback(), 1);
    }

    //TODO 打开网络摄像头（等待第一个先打开）
    public void initFrame() {

        new Thread( new Runnable(){
            @Override
            public void run() {

                //等待1秒再开启摄像头
                try {  Thread.sleep(5000);  } catch (InterruptedException e) { e.printStackTrace(); }

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

                DeviceInfo di = new DeviceInfo(10 );

                Log.i( TAG,"开启出口摄像头"+activity.getCameraOut().getIp() );

                di.setIp(activity.getCameraOut().getIp());
                VedioSetVeiw vsv = new VedioSetVeiw(activity);
                vsv.setId(0);
                DeviceSet ds = new DeviceSet(di, vsv);
                ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
                vsv.setLayoutParams(lp);
                lnentrance2.addView(vsv);
                ds.select();
                ds.playVideo();
            }
        });
    }

    Handler mHandler = new Handler();
    Runnable rnnable = new Runnable() {
        @Override
        public void run() {

            if (TimeUtil.getCurrentTime()-up_time>1000*60){
                up_uuid  = "";
                up_time = 0;
            }
            //每隔3s循环执行run方法
            mHandler.postDelayed(this, 3000);
        }
    };


    private String up_uuid = "";
    private long up_time = 0;
    //TODO 保存到sqlite、上传到云端
    protected void imgSeva( String uuid, String carNumber,  byte[] pImgFull, byte[] pImgPlateClip ) {

        if (up_uuid.equals(uuid)){ return; }
        up_uuid = uuid;
        up_time = TimeUtil.getCurrentTime();

        Log.i(TAG,"上传到云端");
        InputStream inputStream = null;
        InputStream inputStream2 = null;
        try {

            //如果不含"无"，保存车牌特写
            if(!carNumber.contains("无")){

                inputStream2 = new ByteArrayInputStream(pImgPlateClip);
                ImageUitls.saveFrameToPath(BitmapFactory.decodeStream(inputStream2), FileUtil.getSDCardPath() + "/tcb/" + uuid  + "4.jpg");
            }

            //全景
            inputStream = new ByteArrayInputStream(pImgFull);
            ImageUitls.saveFrameToPath(BitmapFactory.decodeStream(inputStream), FileUtil.getSDCardPath() + "/tcb/" + uuid + "3.jpg");


            //上传照片到文件管理器
            Map<String, String> param = new HashMap<String, String>(2);
            param.put("uid", uuid);
            param.put("pictype", "2");
            HttpManager2.onResponseFile(Static_bean.carpicsup_upPicToOss, param, "file",uuid+".jpg",pImgFull,this, "addPhoto");
        } catch (Exception e) {

            Log.w(TAG,e);
        } finally {
            try { if (inputStream != null) inputStream.close(); } catch (IOException e) { Log.w(TAG,e); }
            try { if (inputStream2 != null) inputStream2.close(); } catch (IOException e) { Log.w(TAG,e); }
        }
    }


    @Override
    public void onResponseGET(String url, Map<String, String> param, String sign, String object) { }

    @Override
    public void onResponsePOST(String url, Map<String, String> param, String sign, String object) { }

    @Override
    public void onResponseFile(String url, Map<String, String> param, String sign, String object) {
        Log.i(TAG, url + ";sign="+sign+"----object==" + object+";param="+param);
    }

}

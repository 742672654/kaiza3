/*******************************************************************************
 * Copyright (c) 2015 by ehoo Corporation all right reserved.
 * 2015年4月13日 
 *
 *******************************************************************************/
package com.cz.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cz.activity.LoginActivity;
import com.cz.bean.EditionBean;
import com.cz.Shared.Edition_Shared;
import com.cz.bean.WorksiteBean;
import com.cz.Shared.Worksite_Shared;
import com.cz.activity.ChooseWorkstation2Activity;
import com.cz.http.HttpCallBack2;
import com.cz.util.UPTimeUtil;
import com.zld.R;
import com.zld.application;
import com.zld.bean.UpdataInfo;
import com.zld.lib.constant.Constant;
import com.zld.lib.util.AppInfoUtil;
import com.zld.lib.util.FileUtil;
import com.zld.lib.util.IsNetWork;
import com.zld.lib.util.OkHttpUtil;
import com.zld.lib.util.UpdateManager;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 功能说明: 主页：标题
 * 日期:	2019年5月6日19:50
 * 开发者:	KXD
 */
public class TitleFragment extends BaseFragment implements OnClickListener, HttpCallBack2 {

    private final static String TAG = "TitleFragment";


    ProgressDialog dialog;
    private TextView tv_tcb;//名字
    private TextView tv_tcb_version;//版本号
    private TextView tv_update_time;//时间
    private TextView tv_tcb_workstation;//工作站
    private Button btn_more;//设置
    private Button btn_Restart;//重启
    private Button btn_update;//手动升级

    private String versiontext;//版本号
    private UpdataInfo info;
    private UpdateManager manager; //安装新版本调用类


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cz_title, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EditionBean editionBean = Edition_Shared.getALL(getActivity().getApplicationContext());

        tv_tcb_version.setText("V." + editionBean.getEdition());

        new UPTimeUtil().updateData(tv_update_time);

        WorksiteBean worksiteBean = Worksite_Shared.getALL(this.getActivity());
        if (worksiteBean != null && worksiteBean.getWorksite_name() != null) {
            tv_tcb_workstation.setText(worksiteBean.getWorksite_name());
        }

    }

    /**
     * 初始化控件
     */
    private void initView(View rootView) {
        tv_tcb_version = rootView.findViewById(R.id.tv_tcb_version2);
        tv_update_time = rootView.findViewById(R.id.tv_update_time2);
        tv_tcb_workstation = rootView.findViewById(R.id.tv_tcb_workstation2);
        btn_more = rootView.findViewById(R.id.btn_more2);
        btn_more.setOnClickListener(this);

        btn_Restart = rootView.findViewById(R.id.btn_Restart2);
        btn_Restart.setOnClickListener(this);

        btn_update = rootView.findViewById(R.id.btn_update2);
        btn_update.setOnClickListener(this);

        manager = new UpdateManager(getActivity());
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_more2:    //设置
                //closeRemotService();
                Intent intent = new Intent(getActivity(), ChooseWorkstation2Activity.class);
                intent.putExtra("joinType", 1);
                intent.putExtra("Activity", TAG);
                startActivity(intent);
                break;

            case R.id.btn_update2: //升级
                //主动关闭掉HomeExitPageService，不关掉底层的算法也关不掉，重启后底层跑着多个进程了就。closeRemotService();
                versiontext = AppInfoUtil.getVersionCode(this.getActivity());
                long lasttime = 0;

                if (System.currentTimeMillis() - lasttime >= 2000) {
                    isNeedUpdate(versiontext);
                }
                lasttime = System.currentTimeMillis();
                break;

            case R.id.btn_Restart2:  //退出
                //主动关闭掉HomeExitPageService，不关掉底层的算法也关不掉，重启后底层跑着多个进程了就。
                //closeRemotService();
                //重启
               // restartApp(getActivity());

                Intent intent2 = new Intent(getActivity(), LoginActivity.class);
                intent2.putExtra("joinType", 1);
                intent2.putExtra("Activity", TAG);
                startActivity(intent2);
                break;

            default:
                break;
        }
    }

//    public void closeRemotService() {
//
//        Intent intent = new Intent(getActivity(), HomeExitPageService.class);
//        intent.putExtra("Activity", TAG);
//        Bundle bundle = new Bundle();
//        bundle.putString(Constant.INTENT_KEY, "closeService");
//        intent.putExtras(bundle);
//        getActivity().startService(intent);
//    }

    public void restartApp(Activity activity) {

//        if (activity != null) {
//            Intent intent = activity.getBaseContext().getPackageManager().getLaunchIntentForPackage(activity.getBaseContext().getPackageName());
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.putExtra("joinType", 1);
//            intent.putExtra("Activity", TAG);
//            startActivity(intent);
//            ((application) activity.getApplication()).closeActivity();
//        }
    }



    private void isNeedUpdate(final String versiontext) {
        String url = Constant.getUpdateUrlHand();
        System.out.println("访问更新信息的url--------->>>>>>" + url);
        if (IsNetWork.IsHaveInternet(getActivity())) {
            dialog = ProgressDialog.show(getActivity(), "加载中...", "获取检查更新数据...", true, true);

            Request request = new Request.Builder().url(url).build();
            OkHttpUtil.enqueue(request, new Callback() {

                @Override
                public void onResponse(Call arg0, Response arg1) throws IOException {
                    // TODO Auto-generated method stub
                    byte[] object = arg1.body().bytes();
                    Message m = new Message();
                    m.obj = object;
                }

                @Override
                public void onFailure(Call arg0, IOException arg1) {
                    // TODO Auto-generated method stub
                }
            });
        } else {
            Log.e(TAG, "没有网络, 进入主界面");

        }

    }

    // 需要更新时弹出升级对话框；
    private void showUpdataDialog(String message) {
        AlertDialog.Builder builder = new Builder(getActivity());
        builder.setIcon(R.drawable.app_icon_32);
        builder.setTitle("升级提醒");

        builder.setMessage(message);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e(TAG, "下载真来电apk文件" + info.getApkurl());
                if (FileUtil.getSDCardPath() == null) {
                    // activity.showToast("sd卡不可用或存储已满!");
                    return;
                }

                manager.new DownLoadApkAsyncTask().execute(info.getApkurl());

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e(TAG, "用户取消进入登陆界面");
            }
        });
        builder.setCancelable(false).create().show();
    }


    @Override
    public void onResponseGET(String url, Map<String, String> param, String sign, String object) {

    }

    @Override
    public void onResponsePOST(String url, Map<String, String> param, String sign, String object) {

    }

    @Override
    public void onResponseFile(String url, Map<String, String> param, String sign, String object) {

    }
}

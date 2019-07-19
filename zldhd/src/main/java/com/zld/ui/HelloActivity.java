package com.zld.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//import com.blueware.agent.android.BlueWare;
import com.networkbench.agent.impl.NBSAppAgent;
//import com.oneapm.agent.android.OneApmAgent;
import com.umeng.analytics.MobclickAgent;
import com.vzvison.MainActivity;
import com.zld.R;
import com.zld.application;
import com.zld.bean.AppInfo;
import com.zld.bean.UpdataInfo;
import com.zld.engine.UpdataInfoParser;
import com.zld.lib.constant.Constant;
import com.zld.lib.util.AppInfoUtil;
import com.zld.lib.util.FileUtil;
import com.zld.lib.util.SharedPreferencesUtils;
import com.zld.service.UpdateService;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("SdCardPath")
public class HelloActivity extends BaseActivity {

	private static final String TAG = "HelloActivity";
	private TextView tv_hello_version;
	private LinearLayout ll_hello_main;
	private UpdataInfo info;
	private String versiontext;
	private Handler handler;
	private final int UPDATE = 888;

	@SuppressLint("HandlerLeak")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		setContentView(R.layout.hello_activity);
		MobclickAgent.updateOnlineConfig(this);// 友盟发送策略；
//		BlueWare.withApplicationToken("5106067A94F46EB853A5042CF1F00C4E64").start(this.getApplication());
//		OneApmAgent.init(this.getApplicationContext()).setToken("5106067A94F46EB853A5042CF1F00C4E64").start();
		NBSAppAgent.setLicenseKey("a04ad42a66984f4391c6a23596a9dc9c")
		.withLocationServiceEnabled(true).start(this);
		((application) getApplication()).setHelloActivity(this);


		Log.e("isLocal","HelloActivity onCreate set isLocal false");
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case UPDATE:
					if(FileUtil.getSDCardPath() == null){
						showToast("sd卡不可用");

						return;
					}

					String version_num = SharedPreferencesUtils.getParam(
							getApplicationContext(), "version", "new_version", "111");

					System.out.println("客户端版本号:"+Integer.parseInt(versiontext)+
							"本地保存的新版本号:"+Integer.parseInt(version_num));

					Intent intentact = new Intent(HelloActivity.this, LoginActivity.class);
					if(Integer.parseInt(versiontext) < Integer.parseInt(version_num)){
						//客户端版本号小于新版本号,选择的下次安装
						File tempFile = new File(FileUtil.getSDCardPath(),"/tingchebaohd_hd.apk");
						if(tempFile.exists()){
							Uri fromFile = Uri.fromFile(tempFile);
						    Bundle bundle = new Bundle();
							bundle.putParcelable("installUri", fromFile);
							intentact.putExtras(bundle);
						}else{
							Log.i(TAG, "下载真来电apk文件" + info.getApkurl());
							Intent intent = new Intent(HelloActivity.this,UpdateService.class);
							intent.putExtra("urlpath", info.getApkurl());
							intent.putExtra("version", info.getVersion());
							startService(intent);
						}
					}else if(Integer.parseInt(versiontext) == Integer.parseInt(version_num)){

					}else{
						//没有升级文件时，下载
						Log.i(TAG, "下载真来电apk文件" + info.getApkurl());
						Intent intent = new Intent(HelloActivity.this,UpdateService.class);
						intent.putExtra("urlpath", info.getApkurl());
						intent.putExtra("version", info.getVersion());
						startService(intent);
					}
					startActivity(intentact);
					break;
				}
			}
		};


	}




	
}

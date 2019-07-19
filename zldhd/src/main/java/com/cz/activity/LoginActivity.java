package com.cz.activity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Static_bean;
import com.cz.bean.EditionBean;
import com.cz.Shared.Edition_Shared;
import com.cz.Shared.User_Shared;
import com.cz.bean.UserBean;
import com.cz.db.BaseSQL_DB;
import com.cz.bean.LoginLog_Bean;
import com.cz.db.LoginLog_DB;
import com.cz.bean.LoginUser_Bean;
import com.cz.db.LoginUser_DB;
import com.cz.db.User_DB;
import com.cz.http.HttpCallBack2;
import com.cz.http.HttpManager2;
import com.cz.util.TimeUtil;
import com.google.gson.Gson;
import com.zld.R;

import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;

//		onCreate-创建时调用
//		onStart-不可见到可见时调用
//		onResume-可见并与用户进行交互时调用
//		onPause-打开新activity(老的activity)或关闭activity(本activity)
//		onStop-新的窗口打开后，或者本窗口关闭老窗口显示后
//		onDestroy-本窗口关闭后执行onStop后执行
//		onRestart-本窗口之执行onPause后调用

public class LoginActivity extends FragmentActivity implements HttpCallBack2, View.OnClickListener{

	private final static String TAG = "LoginActivity";

	private EditText login_account_EditText;
	private EditText login_password_EditText;
	private EditText login_ip_EditText;
	private EditText edition_EditText;

    private BaseSQL_DB baseSQL_DB;
	private String edition;


//    public static boolean upgradeRootPermission(String pkgCodePath) {
//        Process process = null;
//        DataOutputStream os = null;
//        try {
//            String cmd = "chmod 777 " + pkgCodePath;
//            process = Runtime.getRuntime().exec("su"); //切换到root帐号
//            os = new DataOutputStream(process.getOutputStream());
//            os.writeBytes(cmd + "\n");
//            os.writeBytes("exit\n");
//            os.flush();
//            process.waitFor();
//        } catch (Exception e) {
//            return false;
//        } finally {
//            try {
//                if (os != null) {
//                    os.close();
//                }
//                process.destroy();
//            } catch (Exception e) {
//            }
//        }
//        return true;
//    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String joinActivity = intent.getStringExtra("Activity");
        int joinType = intent.getIntExtra("joinType", -1);

        if (joinType == -1 || joinType == 2) {
            automatic_login();
        }

    }

	@Override
	public void onClick(View v) {

        	if(v.getId()!=R.id.cz_bt_longin_login){return; }

            String account = login_account_EditText.getText().toString();
            String password = login_password_EditText.getText().toString();
            String ip = login_ip_EditText.getText().toString();


		LoginUser_Bean loginUserBean = new LoginUser_Bean(account, password, ip, TimeUtil.getDateTime(), null);
        login(loginUserBean, 1);
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cz_login_activity);

        //upgradeRootPermission(getPackageCodePath());
        initView();
    }



	private void initView(){
		Button button2 = findViewById(R.id.cz_bt_longin_login);
		button2.setOnClickListener(this);

		login_account_EditText = findViewById(R.id.cz_login_account);
		login_password_EditText = findViewById(R.id.cz_login_password);
		login_ip_EditText = findViewById(R.id.cz_login_ip);
		edition_EditText = findViewById(R.id.cz_edition);
		edition = edition_EditText.getText().toString();

        baseSQL_DB = new BaseSQL_DB(this, "parking.db", null, 1);

        LoginUser_Bean loginUser = LoginUser_DB.query_LocalLoginUser(baseSQL_DB);

        if (loginUser!=null){
            login_account_EditText.setText(loginUser.getAccount());
            login_password_EditText.setText(loginUser.getPassword());
        }

	}



    //TODO 1是手动登录，2是自动登录
    private void login(LoginUser_Bean loginUserBean, Integer joinType) {


		Map<String,String> param = new HashMap<String,String>();
			param.put("username",loginUserBean.getAccount());
			param.put("password",loginUserBean.getPassword());
        param.put("edition", edition);
        param.put("joinType", joinType.toString());
			param.put("out","json");

		HttpManager2.requestPost(Static_bean.login,param, this, "login");

	}

    //TODO 获取imei编号
    private String initImei() {

        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
         return telephonyManager.getDeviceId();
    }

    //TODO 登录
    private void automatic_login() {

        LoginUser_Bean loginUser = LoginUser_DB.query_LocalLoginUser(baseSQL_DB);
        if (loginUser != null && loginUser.getAccount() != null) {

            login(loginUser, 1);
        }
    }

	@Override
	public void onResponsePOST(String url, final Map<String,String> param, String sign, String object) {

        Log.i(TAG,url+";param="+param+";object="+object);

		final UserBean userBean = new Gson().fromJson(object, UserBean.class);

		LoginActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

			if(userBean!=null && userBean.getInfo()!=null && userBean.getInfo().equals("success")){
				userBean.setAccount(param.get("username"));
				userBean.setPassword(param.get("password"));
                userBean.setUptime(TimeUtil.getDateTimeShort());

				//添加到现在登录的用户中
				LoginUser_Bean login = new LoginUser_Bean(userBean.getAccount(),userBean.getPassword(),login_ip_EditText.getText().toString(),userBean.getLogontime(),null);
                LoginUser_DB.insert_LocalLoginUser(baseSQL_DB, login);

				//添加到常驻用户
                User_DB.insert_User(baseSQL_DB, userBean);

				//添加到登录日志
                LoginLog_Bean loginLog = new LoginLog_Bean(userBean.getAccount(), TimeUtil.getDateTime(), Integer.parseInt(param.get("joinType")), new Gson().toJson(userBean));
                LoginLog_DB.insert_log(baseSQL_DB, loginLog);

				//版本号保存到xml
				EditionBean editionBean = new EditionBean(true, TimeUtil.getDateTime(), edition,initImei(), TimeUtil.getDateTime());
				Edition_Shared.saveALL(getApplicationContext(), editionBean);

                //XML：保存当前用户信息
                User_Shared.saveALL(getApplicationContext(), userBean);

				Intent intent = new Intent(LoginActivity.this,ChooseWorkstation2Activity.class);
                intent.putExtra("Activity", TAG);
                intent.putExtra("joinType", Integer.valueOf(param.get("joinType")));
                Log.i(TAG, "跳转到第二页：Activity=" + TAG + ";joinType=" + param.get("joinType"));
				LoginActivity.this.startActivity(intent);

			}else if (userBean!=null){

				Toast.makeText( LoginActivity.this,userBean.getInfo(),Toast.LENGTH_SHORT ).show();
			}else if (userBean==null){

                Toast.makeText( LoginActivity.this,"登录失败",Toast.LENGTH_SHORT ).show();
            }
			}
		});
	}

    @Override
    public void onResponseGET(String url, Map<String, String> param, String sign, String object) { }

    @Override
    public void onResponseFile(String url, Map<String, String> param, String sign, String object) { }

}
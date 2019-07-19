package com.zld;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.vzvison.database.SnapImageTable;
import com.vzvison.database.plateCallbackInfoTable;
import com.vzvison.device.DeviceSet;
import com.vzvison.vz.WlistVehicle;
import com.zld.db.SqliteManager;
import com.zld.decode.CrashHandler;
import com.zld.ui.BaseActivity;
import com.zld.ui.HelloActivity;
import com.zld.ui.LoginActivity;
import com.zld.ui.ZldNewActivity;


public class application extends Application {


	private static final  String TAG = "application";

	private BaseActivity baseActivity;
	private HelloActivity helloActivity;
	private LoginActivity loginActivity;
	private ZldNewActivity zldNewActivity;
	private SqliteManager mSqliteManager = null;
//	private LocalOrderDBManager localOrderDBManager = null;
	private ImageLoader mImageLoader;
	private static application sInstance;



	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		//错误日志 保存本地
//		initImageLoader();
//		CrashHandler crashHandler = CrashHandler.getInstance();
//		crashHandler.init(getApplicationContext());
//		sInstance = this;
	}

	public ZldNewActivity getZldNewActivity() {
		return zldNewActivity;
	}
	
	public void setZldNewActivity(ZldNewActivity zldNewActivity) {
		this.zldNewActivity = zldNewActivity;
	}

	public SqliteManager getSqliteManager(Context mContext){
		if(mSqliteManager == null){
			mSqliteManager = new SqliteManager(mContext);
		}
		return mSqliteManager;
	}
	
//	public LocalOrderDBManager getLocalOrderDBManager(Context mContext){
//		if(localOrderDBManager == null){
//			localOrderDBManager = new LocalOrderDBManager(mContext);
//		}
//		return localOrderDBManager;
//	}

	public ImageLoader getImageLoader(){
		mImageLoader = ImageLoader.getInstance(); 
		DisplayImageOptions options = new DisplayImageOptions.Builder()  
		.showImageOnLoading(R.drawable.home_car_icon) //设置图片在下载期间显示的图片  
		.showImageForEmptyUri(R.drawable.home_car_icon)//设置图片Uri为空或是错误的时候显示的图片  
		.showImageOnFail(R.drawable.home_car_icon)  //设置图片加载/解码过程中错误时候显示的图片
		.cacheInMemory(true)//设置下载的图片是否缓存在内存中  
		.build();//构建完成  
		////创建默认的ImageLoader配置参数  	线程池为5		打印log信息 
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
		.defaultDisplayImageOptions(options).threadPoolSize(5).writeDebugLogs().build();
		//初始化ImageLoader
		mImageLoader.init(config);
		return mImageLoader;
	}


	public HelloActivity getHelloActivity() {
		return helloActivity;
	}
	public void setHelloActivity(HelloActivity helloActivity) {
		this.helloActivity = helloActivity;
	}

	public LoginActivity getLoginActivity() {
		return loginActivity;
	}

	public void setLoginActivity(LoginActivity loginActivity) {
		this.loginActivity = loginActivity;
	}

	public BaseActivity getBaseActivity() {
		return baseActivity;
	}

	public void setBaseActivity(BaseActivity baseActivity) {
		this.baseActivity = baseActivity;
	}

	public void closeActivity(){
//		if(loginActivity != null){
//			loginActivity.finish();
//		}
//		if(zldNewActivity != null){
//			zldNewActivity.finish();
//		}
//		android.os.Process.killProcess(android.os.Process.myPid());
//		System.exit(0);

		Log.w(TAG,"系统重启------------");

		Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
		//与正常页面跳转一样可传递序列化数据,在Launch页面内获得
		intent.putExtra("REBOOT","reboot");
		PendingIntent restartIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
		AlarmManager mgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent);
		android.os.Process.killProcess(android.os.Process.myPid());



	}










	private plateCallbackInfoTable  plateTable =null;
	private DeviceSet               devSet = null;
	private SnapImageTable          snapImageTable = null;
	private WlistVehicle          wlistVechile = null;
	

	
	public void setplateCallbackInfoTable(plateCallbackInfoTable table)
	{
		plateTable = table;
	}
	
	public	SnapImageTable getSnapImageTable()
	{
		return snapImageTable;
	}
	
	public	void setSnapImageTable(SnapImageTable table)
	{
		snapImageTable = table;
	}
	
	public	plateCallbackInfoTable getplateCallbackInfoTable()
	{
		return plateTable;
	}
	
	public	void setDeviceSet(DeviceSet ds)
	{
		devSet = ds;
	}

	public	DeviceSet getDeviceSet()
	{
		return devSet;
	}

	public	void setWlistVehicle(WlistVehicle ds)
	{
		wlistVechile = ds;
	}

	public	WlistVehicle getWlistVehicle()
	{
		return wlistVechile;
	}

}

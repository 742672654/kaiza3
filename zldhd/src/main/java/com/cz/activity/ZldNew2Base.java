package com.cz.activity;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.cz.Shared.Camera_in_Shared;
import com.cz.Shared.Camera_out_Shared;
import com.cz.Shared.CarType_Shared;
import com.cz.bean.EditionBean;
import com.cz.Shared.Edition_Shared;
import com.cz.Shared.FreeResons_Shared;
import com.cz.Shared.Led_in_Shared;
import com.cz.Shared.Led_out_Shared;
import com.cz.Shared.OrderType_Shared;
import com.cz.Shared.Parking_Shared;
import com.cz.Shared.User_Shared;
import com.cz.bean.WorksiteBean;
import com.cz.Shared.Worksite_Shared;
import com.cz.bean.OrderTypeBean;
import com.cz.bean.ParkingBean;
import com.cz.bean.UserBean;
import com.cz.db.BaseSQL_DB;
import com.cz.fragment.CashFragment7;
import com.cz.fragment.EntranceFragment1;
import com.cz.fragment.ExitFragment2;
import com.cz.fragment.OrderDetailsFragment6;
import com.cz.fragment.OrderListFragment5;
import com.cz.fragment.ParkinfoFragment4;
import com.cz.fragment.ParkrecordFragment3;
import com.cz.fragment.TitleFragment;
import com.cz.http.HttpCallBack2;
import com.vz.tcpsdk;
import com.zld.R;
import com.zld.bean.CarType;
import com.zld.bean.FreeResons;
import com.zld.bean.LiftReason;
import com.zld.bean.MyCameraInfo;
import com.zld.bean.MyLedInfo;
import com.zld.lib.util.FileUtil;
import com.zld.ui.BaseActivity;
import java.util.List;
import java.util.Map;


public class ZldNew2Base extends BaseActivity implements HttpCallBack2 {

    private static String TAG = "ZldNew2Base";


    private List<CarType> carType_list; //车辆类型
    private WorksiteBean worksiteBean;//工作站
    private UserBean userBean;//用户信息
    private ParkingBean parkingBean;//停车场信息
    private List<FreeResons> freeResons_list;//免费类型
    private List<LiftReason> liftReason_list;//手动抬杠原因
    private List<OrderTypeBean> orderType_list;//订单类型

    private MyCameraInfo cameraIn;//获取入口Camera
    private MyCameraInfo cameraOut;//获取出口Camera
    private MyLedInfo led_in; //入口LED
    private MyLedInfo led_out; //出口LED
    private EditionBean editionBean; //版本号与imei

    private TitleFragment titleFragment;
    private EntranceFragment1 entranceFragment1;
    private ExitFragment2 exitFragment2;
    private ParkrecordFragment3 parkrecordFragment3;
    private ParkinfoFragment4 parkinfoFragment4;
    private OrderListFragment5 listFragment5;
    private OrderDetailsFragment6 ordetailsFragment6;
    private CashFragment7 cashFragment7;

    public BaseSQL_DB baseSQL_DB;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.cz_zld_new_layout);

        //初始化摄像头,再打开
        tcpsdk tsdk = new tcpsdk();
        tsdk.cleanup();
        tsdk.setup();

        //加载布局
        initView();

        FileUtil.writeSDFile("加载时间", "379-->" + System.currentTimeMillis());
    }

    @Override
    protected void onStart() {
        super.onStart();

        cameraIn = Camera_in_Shared.getALL(this);//获取入口Camera
        cameraOut = Camera_out_Shared.getALL(this);//获取出口Camera
        led_in = Led_in_Shared.getALL(this); //入口LED
        led_out = Led_out_Shared.getALL(this); //出口LED

        //加载本地数据
        loadingData();
    }

    private void initView() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        titleFragment = (TitleFragment) fragmentManager.findFragmentById(R.id.title_fragment2);
        entranceFragment1 = (EntranceFragment1) fragmentManager.findFragmentById(R.id.entrance_fragment2);
        parkrecordFragment3 = (ParkrecordFragment3) fragmentManager.findFragmentById(R.id.park_record_fragment2);
        ordetailsFragment6 = (OrderDetailsFragment6) fragmentManager.findFragmentById(R.id.order_details_fragment2);
        listFragment5 = (OrderListFragment5) fragmentManager.findFragmentById(R.id.order_list_fragment2);
        cashFragment7 = (CashFragment7) fragmentManager.findFragmentById(R.id.cash_fragment2);
        parkinfoFragment4 = (ParkinfoFragment4) fragmentManager.findFragmentById(R.id.park_info_fragment2);
        exitFragment2 = (ExitFragment2) fragmentManager.findFragmentById(R.id.exit_fragment2);
    }

    private void loadingData() {

        baseSQL_DB = new BaseSQL_DB(this, "parking.db", null, 1);
        carType_list = CarType_Shared.getALL(getApplicationContext()); //车辆类型
        worksiteBean = Worksite_Shared.getALL(getApplicationContext()); //工作站
        userBean = User_Shared.getALL(getApplicationContext());//用户信息
        parkingBean = Parking_Shared.getALL(getApplicationContext());//停车场信息
        freeResons_list = FreeResons_Shared.getALL(getApplicationContext());//免费类型
        cameraIn = Camera_in_Shared.getALL(getApplicationContext());//获取入口Camera
        cameraOut = Camera_out_Shared.getALL(getApplicationContext()) ;//获取出口Camera
        led_in = Led_in_Shared.getALL(getApplicationContext()); //入口LED
        led_out = Led_out_Shared.getALL(getApplicationContext()); //出口LED
        editionBean = Edition_Shared.getALL(getApplicationContext()); //版本号与imei
        orderType_list = OrderType_Shared.getALL(getApplicationContext());//订单类型

        Log.i(TAG+"加载静态数据","车辆类型"+carType_list);
        Log.i(TAG+"加载静态数据","工作站"+worksiteBean);
        Log.i(TAG+"加载静态数据","用户信息"+userBean);
        Log.i(TAG+"加载静态数据","停车场信息"+parkingBean);
        Log.i(TAG+"加载静态数据","免费类型"+freeResons_list);
        Log.i(TAG+"加载静态数据","获取入口摄像头"+cameraIn);
        Log.i(TAG+"加载静态数据","获取出口摄像头"+cameraOut);
        Log.i(TAG+"加载静态数据","入口LED："+led_in);
        Log.i(TAG+"加载静态数据","出口LED："+led_out);
        Log.i(TAG+"加载静态数据","版本号与imei："+editionBean);
        Log.i(TAG+"加载静态数据","订单类型"+orderType_list);

    }



    @Override
    public void onResponseGET(String url, Map<String, String> param, String sign, String object) { }

    @Override
    public void onResponsePOST(String url, Map<String, String> param, String sign, String object) { }

    @Override
    public void onResponseFile(String url, Map<String, String> param, String sign, String object) { }

    //TODO 下面是get和set方法
    public TitleFragment getTitleFragment() {
        return titleFragment;
    }
    public EntranceFragment1 getEntranceFragment1() {
        return entranceFragment1;
    }
    public ExitFragment2 getExitFragment2() {
        return exitFragment2;
    }
    public ParkrecordFragment3 getParkrecordFragment3() {
        return parkrecordFragment3;
    }
    public ParkinfoFragment4 getParkinfoFragment4() {
        return parkinfoFragment4;
    }
    public OrderListFragment5 getListFragment5() {
        return listFragment5;
    }
    public OrderDetailsFragment6 getOrdetailsFragment6() {
        return ordetailsFragment6;
    }
    public CashFragment7 getCashFragment7() {
        return cashFragment7;
    }

    public List<CarType> getCarType_list() {
        return carType_list;
    }
    public WorksiteBean getWorksiteBean() {
        return worksiteBean;
    }
    public UserBean getUserBean() {
        return userBean;
    }
    public ParkingBean getParkingBean() {
        return parkingBean;
    }
    public List<FreeResons> getFreeResons_list() {
        return freeResons_list;
    }
    public List<LiftReason> getLiftReason_list() {
        return liftReason_list;
    }
    public MyCameraInfo getCameraIn() {
        return cameraIn;
    }
    public MyCameraInfo getCameraOut() {
        return cameraOut;
    }
    public MyLedInfo getLed_in() {
        return led_in;
    }
    public MyLedInfo getLed_out() {
        return led_out;
    }
    public EditionBean getEditionBean() {
        return editionBean;
    }
    public List<OrderTypeBean> getOrderType_list() {
        return orderType_list;
    }
}
package com.cz.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.Static_bean;
import com.cz.activity.ZldNew2Activity;
import com.cz.bean.OrderTypeBean;
import com.cz.http.HttpCallBack2;
import com.cz.http.HttpManager2;
import com.zld.R;
import com.zld.bean.CarNumberOrder;
import com.zld.lib.util.FileUtil;
import com.zld.lib.util.InputUtil;
import com.zld.view.KeyboardViewPager;
import com.zld.view.SelectCarType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 功能说明:
 * 日期:	2019年5月15日
 * 开发者:	KXD
 */
public class OrderDetailsFragment6 extends BaseFragment implements OnClickListener, OnTouchListener, HttpCallBack2 {


    private static final String TAG = "OrderDetailsFragment6";

    private String orderid;
    private EditText et_car_num;//车牌号码
    private ImageView iv_car_image;//车牌小样
    private Button btn_car_type; //车辆类型
    private Button btn_charge_carNumber; //修改
    private TextView tv_account_type;//订单类型
    private TextView tv_entrance_time;//入场时间
    private TextView tv_park_duration;//停车时长
    private SelectCarType selectCarType;
    private KeyboardViewPager kvp;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cz6_order_details, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        iv_car_image = rootView.findViewById(R.id.iv_car_image2);
        et_car_num = rootView.findViewById(R.id.et_car_num2);
        btn_car_type = rootView.findViewById(R.id.btn_car_type2);
        btn_charge_carNumber = rootView.findViewById(R.id.btn_charge_carNumber);
        tv_account_type = rootView.findViewById(R.id.tv_account_type2);
        tv_entrance_time = rootView.findViewById(R.id.tv_entrance_time2);
        tv_park_duration = rootView.findViewById(R.id.tv_park_duration2);
        selectCarType = new SelectCarType(activity, btn_car_type, this);
        InputUtil.hideTypewriting(activity, et_car_num);

        btn_charge_carNumber.setOnClickListener(this);
        btn_car_type.setOnClickListener(this);
        et_car_num.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_car_type2:
                if (activity.getListFragment5().itemOrder != null) {
                    selectCarType.showSwitchAccountView(orderid);
                }
                break;

            case R.id.btn_charge_carNumber:
                if ("修改".equals(btn_charge_carNumber.getText())){

                    charge_carNumber();
                }else if ("确定".equals(btn_charge_carNumber.getText())){

                    charge_carNumber_ok();
                }
                break;

            default:
                break;
        }
    }

    //TODO  触摸显示软键盘
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    //TODO 展开软键盘
    private void charge_carNumber(){
        if ( orderid == null ){return;}

        btn_charge_carNumber.setText("确定");
        if(kvp == null){
            kvp = new KeyboardViewPager(activity, true);
        }
        kvp.setEt_carnumber(et_car_num);
        // 键盘显示在搜索按钮右边
        kvp.setDirection("left");
        kvp.showPopupWindow(et_car_num);
    }

    //TODO 确定修改
    private void charge_carNumber_ok(){

        btn_charge_carNumber.setText("修改");

        String carnumber = et_car_num.getText().toString();
        Map<String,String> params = new HashMap<String,String>() ;
            params.put("comid", activity.getWorksiteBean().getComid());
            params.put("orderid", orderid);
            params.put("carnumber", carnumber);
            params.put("through", String.valueOf(3));

        HttpManager2.requestPost(Static_bean.cobp_addcarnumber,params, this, "cobp_addcarnumber");
    }

    //TODO 修改完车牌号
    private void charge_carNumber_ok2( Map<String, String> param, String object ){

        toast_makeText("1".equals(object)?"修改成功":"修改失败");
//        //在场还是离场的标记：在场0，离场1
//        if ("1".equals(object)){
//
//            Bundle bundle = new Bundle();
//            bundle.putString( "joinType",TAG );
//            bundle.putString("text","车牌号修改成功");
//
//            Message message2 = new Message();
//            message2.what = activity.getListFragment5().listFlag==1?ZldNew2Activity.click_out_park:ZldNew2Activity.click_in_park;
//            message2.setData(bundle);
//            Handler handler2 = activity.getHandler();
//            handler2.sendMessage(message2);
//        }
    }

    //TODO 根类型ID设置车辆类型
    public void upCarType_forId(String carTypeID){

        for (int i=0; i< activity.getCarType_list().size(); i++){

            if (carTypeID.equals(activity.getCarType_list().get(i).getValue_no())){

                btn_car_type.setText(activity.getCarType_list().get(i).getValue_name());
            }
        }
    }

    //TODO 刷出数据
    public void transfer( CarNumberOrder carNumberOrder,String uuid) {



        if ( carNumberOrder==null ){

            orderid = null;
            et_car_num.setText("");
            btn_car_type.setText("");
            tv_account_type.setText("");
            tv_entrance_time.setText("");
            tv_park_duration.setText("");
            iv_car_image.setImageResource(R.drawable.plate_sample);
            return;
        }

        Log.i(TAG,"刷出数据"+carNumberOrder.toString()+";uuid="+uuid);

        orderid = carNumberOrder.getOrderid();
        et_car_num.setText(carNumberOrder.getCarnumber());
        upCarType_forId( carNumberOrder.getCar_type() );
        tv_entrance_time.setText(carNumberOrder.getBtime());
        tv_park_duration.setText(carNumberOrder.getDuration());


        upOrderType_forID(carNumberOrder.getType());

        //设置车牌照片
        if (uuid == null || "".equals(uuid)){
            iv_car_image.setImageResource(R.drawable.plate_sample);
            return;
        }

        Bitmap bm = BitmapFactory.decodeFile(FileUtil.getSDCardPath() + "/tcb/" + uuid + "2.jpg");
        iv_car_image.setImageBitmap(bm);
    }

    //根类ypeID设置订单类型
    public void upOrderType_forID(String typeId){

        if(typeId==null || "".equals(typeId) || activity.getOrderType_list()==null || activity.getOrderType_list().size()==0){
            return;
        }

        List<OrderTypeBean> orderType_list = activity.getOrderType_list();


        Log.i(TAG,orderType_list.toString());

        for (int i=0;i<orderType_list.size();i++){

            if ( typeId.equals( orderType_list.get(i).getValue_no() ) ){

                tv_account_type.setText(orderType_list.get(i).getValue_name());
                return;
            }
        }
        tv_account_type.setText("其他订单");
    }

    //根类型名设置车辆类型
    public void upCarType_forName(String carTypeName){

        btn_car_type.setText(carTypeName);
    }

    //修改车辆类型
    public void changeCarType(String carTypeID){


        Map<String, String> params = new HashMap<String,String>(2);

            params.put("orderid", orderid);
            params.put("car_type", carTypeID);
            params.put("comid", activity.getWorksiteBean().getComid());

        HttpManager2.requestGet(Static_bean.cobp_changecartype,params, this, "cobp_changecartype");
    }

    @Override
    public void onResponseGET(String url, Map<String, String> param, String sign, String object) {

        try {
                switch (sign){
                    case "cobp_changecartype":
                        toast_makeText("1".equals(object)?"修改成功":"修改失败");
                        break;
                    default:
                        break;
                    }
            }catch (Exception e){
                e.printStackTrace();
            }
    }

    @Override
    public void onResponsePOST(String url, Map<String, String> param, String sign, String object) {

        try {
            switch (sign){

                case "cobp_addcarnumber": charge_carNumber_ok2( param, object );
                    break;

                default:
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResponseFile(String url, Map<String, String> param, String sign, String object) {

    }

}

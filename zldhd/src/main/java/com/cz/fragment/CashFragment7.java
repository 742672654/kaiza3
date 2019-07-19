package com.cz.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.Static_bean;
import com.cz.activity.ZldNew2Activity;
import com.cz.http.HttpCallBack2;
import com.cz.http.HttpManager2;
import com.zld.R;
import com.zld.bean.CarNumberOrder;
import java.util.HashMap;
import java.util.Map;


/**
 * 功能说明: 收费操作Fragment
 * 日期:	2019年5月17日
 * 开发者:	KXD
 */
public class CashFragment7 extends BaseFragment implements OnClickListener, HttpCallBack2 {

    private static final String TAG = "CashFragment7";

    private Button btn_free;// 免费
    private Button btn_discount;// 减免
    public CarNumberOrder carNumberOrder;
    private TextView tv_park_cost;// 车费
    private Button btn_charge_finish;// 收费完成按钮



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cz7_cash_operation, container, false);
        initView(rootView);
        return rootView;
    }

    //初始化控件
    private void initView(View rootView) {

        tv_park_cost = rootView.findViewById(R.id.tv_park_cost2);
        btn_free = rootView.findViewById(R.id.btn_free2);
        btn_charge_finish = rootView.findViewById(R.id.btn_charge_finish2);
        btn_discount = rootView.findViewById(R.id.btn_discount2);
        btn_free.setOnClickListener(this);
        btn_charge_finish.setOnClickListener(this);
        btn_discount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            //免费
            case R.id.btn_free2:
                ShowFree();
                break;

            //减免
            case R.id.btn_discount2:
                ShowDiscount();
                break;

            //收费完成
            case R.id.btn_charge_finish2:
                chargingCompleted();
                break;

            default:
                break;
        }


    }

    //TODO 刷出数据
    public void transfer( CarNumberOrder carNumberOrder0 ) {

        if (activity.getListFragment5().listFlag==0){
            btn_free.setVisibility( View.VISIBLE );
            btn_charge_finish.setVisibility( View.VISIBLE );
            btn_discount.setVisibility( View.VISIBLE );
        }else{
            btn_free.setVisibility( View.INVISIBLE );
            btn_charge_finish.setVisibility( View.INVISIBLE );
            btn_discount.setVisibility( View.INVISIBLE );
        }


        if(carNumberOrder0 == null){

            this.carNumberOrder = null;
            tv_park_cost.setText("0");
            return;
        }
        carNumberOrder = carNumberOrder0;
        this.carNumberOrder = carNumberOrder0;
        tv_park_cost.setText(carNumberOrder0.getCollect()==null?"0":carNumberOrder0.getCollect());
    }

    //TODO 选择免费原因
    private void ShowFree() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Holo_Light_Dialog);

        builder.setTitle("选择免费原因");
        builder.setIcon(android.R.drawable.ic_dialog_info);

        //    指定下拉列表的显示数据
        final String[] cities = {"内部用车", "贵宾", "错误订单", "军车/警车", "政府用车"};
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getActivity(), "选择的原因为：" + cities[which], Toast.LENGTH_SHORT).show();

                switch (cities[which]){

                    case "内部用车":Free_Discount(4,1);
                        break;
                    case "贵宾":Free_Discount(4,2);
                        break;
                    case "错误订单":Free_Discount(4,3);
                        break;
                    case "军车/警车":Free_Discount(4,4);
                        break;
                    case "政府用车":Free_Discount(4,5);
                        break;
                    default:
                        break;
                }

            }
        });
        builder.show();
    }

    private void ShowDiscount() {

//        final EditText inputServer = new EditText(getContext());
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("输入最终车费").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                String text = inputServer.getText().toString();
//
//                Toast.makeText(getActivity(), "最终车费为：" + text + "元", Toast.LENGTH_SHORT).show();
//            }
//        });
//        builder.show();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Holo_Light_Dialog);

        builder.setTitle("选择减免时长");
        builder.setIcon(android.R.drawable.ic_dialog_info);

        //    指定下拉列表的显示数据
        final String[] cities = {"减免 1 小时", "减免 2 小时", "减免 3 小时", "减免 4 小时", "减免 6 小时", "减免 12 小时", "减免 24 小时"};
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (cities[which]){

                    case "减免 1 小时":Free_Discount(3,1);
                        Toast.makeText(getActivity(), "减少：1小时" , Toast.LENGTH_SHORT).show();
                        break;
                    case "减免 2 小时":Free_Discount(3,2);
                        Toast.makeText(getActivity(), "减少：2小时" , Toast.LENGTH_SHORT).show();
                        break;
                    case "减免 3 小时":Free_Discount(3,3);
                        Toast.makeText(getActivity(), "减少：3小时" , Toast.LENGTH_SHORT).show();
                        break;
                    case "减免 4 小时":Free_Discount(3,4);
                        Toast.makeText(getActivity(), "减少：4小时" , Toast.LENGTH_SHORT).show();
                        break;
                    case "减免 6 小时":Free_Discount(3,6);
                        Toast.makeText(getActivity(), "减少：6小时" , Toast.LENGTH_SHORT).show();
                        break;
                    case "减免 12 小时":Free_Discount(3,12);
                        Toast.makeText(getActivity(), "减少：12小时" , Toast.LENGTH_SHORT).show();
                        break;
                    case "减免 24 小时":Free_Discount(3,24);
                        Toast.makeText(getActivity(), "减少：24小时" , Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }

            }
        });
        builder.show();
    }

    //TODO type=3减免，ytpe=4免费
    //TODO 发送http请求修改金额，然后刷新
    private void Free_Discount(int type,int time){

        //发送http结算订单
        Map<String,String> params = new HashMap<String,String>();
            params.put("orderid", carNumberOrder.getOrderid());
            params.put("type", String.valueOf(type)); //结算订单的金额
            params.put("money", String.valueOf(time));

        HttpManager2.requestGet(Static_bean.nfchandle_hdderate, params, this, "Free_Discount1");
    }

    //TODO 接收减免订单，刷新最新价格
    private void Free_Discount2( Map<String, String> param, String object ){

        toast_makeText("修改成功");


        Bundle bundle = new Bundle();
        bundle.putString( "joinType",TAG );
        bundle.putString("text","修改成功");
        Message message = new Message();
        message.what = ZldNew2Activity.clickOrder;
        message.obj = carNumberOrder;

        message.setData(bundle);
        Handler handler = activity.getHandler();
        handler.sendMessage(message);

    }

    //TODO 确定缴费？？
    private void chargingCompleted() {

        new AlertDialog.Builder(getActivity()).setTitle("确认已缴费吗？")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“确认”后结算订单
                        preaddorder();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“返回”后的操作,这里不设置没有任何操作
                    }
                }).show();
    }

    //TODO 订单结算并开闸
    public void preaddorder(){
        Log.i(TAG,"jsdd=");

        //发送http结算订单并开闸
        Map<String,String> params = new HashMap<String,String>();
        params.put("orderid", carNumberOrder.getOrderid());
        params.put("collect", tv_park_cost.getText().toString());
        params.put("comid", activity.getParkingBean().getId());
        params.put("uid",activity.getUserBean().getAccount());
        params.put("imei", activity.getEditionBean().getImei());
        params.put("passid", activity.getCameraOut().getPassid());
        params.put("isclick",String.valueOf(true));

        HttpManager2.requestPost(Static_bean.nfchandle_completeorder, params, this, "nfchandle_preaddorder");
    }

    //TODO 订单结算并开闸
    public void preaddorder2(String orderid){
        Log.i(TAG,"jsdd=");

        //发送http结算订单并开闸
        Map<String,String> params = new HashMap<String,String>(7);
        params.put("orderid", orderid);
        params.put("collect", tv_park_cost.getText().toString());
        params.put("comid", activity.getParkingBean().getId());
        params.put("uid",activity.getUserBean().getAccount());
        params.put("imei", activity.getEditionBean().getImei());
        params.put("passid", activity.getCameraOut().getPassid());
        params.put("isclick",String.valueOf(true));

        HttpManager2.requestPost(Static_bean.nfchandle_completeorder, params, this, "nfchandle_preaddorder");
    }


    //TODO 订单结算完成，开闸，刷新出场
    private void preaddorder2( Map<String, String> param, String object){

                if ("1".equals(object)){

                    Log.i(TAG,"订单结算完成，开闸ZldNew2Activity.out_open_pole");


                    led_Out_StartService("订单完成一路顺风","订单完成一路顺风",TAG);



                    Bundle bundle = new Bundle();
                    bundle.putString( "joinType",TAG );
                    bundle.putString("text","订单结算完成");
                    Message message = new Message();
                    message.what = ZldNew2Activity.out_open_pole;
                    message.setData(bundle);
                    Handler handler = activity.getHandler();
                    handler.sendMessage(message);


                    Message message2 = new Message();
                    message2.what = ZldNew2Activity.click_out_park;
                    message2.setData(bundle);
                    Handler handler2 = activity.getHandler();
                    handler2.sendMessage(message2);

                    Message message3 = new Message();
                    message3.what = ZldNew2Activity.Refresh_nowIncome;
                    message3.setData(bundle);
                    Handler handler3 = activity.getHandler();
                    handler3.sendMessage(message3);
                }
    }

    //TODO 隐藏按钮，清空车费
    public void hideButton() {

        tv_park_cost.setText("0.0");
        btn_free.setVisibility( View.INVISIBLE );
        btn_charge_finish.setVisibility( View.INVISIBLE );
        btn_discount.setVisibility( View.INVISIBLE );
    }

    @Override
    public void onResponseGET(String url, Map<String, String> param, String sign, String object) {
        Log.i(TAG,"param="+param+",sign="+sign+";object="+object);

     try {
        switch (sign){
            case "Free_Discount1":Free_Discount2( param,object );
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

        Log.i(TAG,"param="+param+",sign="+sign+";object="+object);
        try {
            switch (sign){
                case "nfchandle_preaddorder":preaddorder2( param,object );
                    break;

                default:
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResponseFile(String url, Map<String, String> param, String sign, String object) { }

}

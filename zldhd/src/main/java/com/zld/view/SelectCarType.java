package com.zld.view;


import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.cz.activity.ZldNew2Activity;
import com.cz.fragment.OrderDetailsFragment6;
import com.zld.R;
import com.zld.adapter.AccountDropListAdapter;
import com.zld.bean.CarType;
import com.zld.lib.constant.Constant;
import com.zld.lib.http.RequestParams;

import java.util.ArrayList;


@SuppressLint({ "NewApi", "ValidFragment" })
public class SelectCarType extends Fragment {
	public static final int ENTRANCE = 0;
	public static final int EXIT = 1;
	public static final int HOMEEXIT = 2;
	private View parent;
	private Toast mToast;
	private String orderID;
	private ZldNew2Activity activity;
    private OrderDetailsFragment6 orderDetailsFragment;

	private ListView listview;
	private PopupWindow popupWindow;

	private static final String TAG = "SelectCarType";

	public SelectCarType() {
		super();
	}

    public SelectCarType(ZldNew2Activity activity, View parent, OrderDetailsFragment6 odf) {
		this.parent = parent;
		this.activity = activity;
		this.orderDetailsFragment = odf;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState); 
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
        this.activity = (ZldNew2Activity)activity;
	}

	@SuppressLint("NewApi")
	public void showSwitchAccountView(String orderID) {
		showPopupWindow(parent);
		this.orderID = orderID;
	}
	public void closePop() {
		if (popupWindow != null) {
			popupWindow.dismiss();	
		}
	}
	public void showToast(String text) {
		if (mToast == null) {
            mToast = Toast.makeText(activity.getApplicationContext(), text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	/**
     * 暂时车牌类型选择
	 * @param parent
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	private void showPopupWindow(View parent) {
		int screenHeight = 0; 
		// TODO Auto-generated method stub
		if(listview == null){
			listview = (ListView) LayoutInflater.from(activity).inflate(R.layout.account_droplist, null); 
		}

		final ArrayList<String> selectAllAccount = new ArrayList<String>();
		for (int i = 0; i < activity.getCarType_list().size(); i++) {

			selectAllAccount.add(activity.getCarType_list().get(i).getValue_name());
		}

		//目的是显示用户名列表底部的添加账号
		if(selectAllAccount.size() != 0){
			AccountDropListAdapter adapter = new AccountDropListAdapter(activity, selectAllAccount,true);
			listview.setAdapter(adapter);
		}
		if (popupWindow == null) {  
			popupWindow = new PopupWindow(listview,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			DisplayMetrics dm = new DisplayMetrics();
			//获取屏幕信息
			((Activity) activity).getWindowManager().getDefaultDisplay().getMetrics(dm);
			int screenWidth = dm.widthPixels;
			screenHeight = dm.heightPixels;
//			popupWindow.setWidth((int)(screenWidth/8));
            popupWindow.setWidth(parent.getWidth());
		}  
		popupWindow.setFocusable(true);  
		popupWindow.setOutsideTouchable(true);  
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景  
		popupWindow.setBackgroundDrawable(new BitmapDrawable());  
		final int[] location = new int[2];  
		parent.getLocationOnScreen(location);  
		popupWindow.showAsDropDown(parent);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String account = selectAllAccount.get(position);
				Log.e(TAG,"选择车场点击的是："+account);
				if(account == null){
					return;
				}


				orderDetailsFragment.upCarType_forName( account );

				for (int i=0; i< activity.getCarType_list().size(); i++){

					if (account.equals(activity.getCarType_list().get(i).getValue_name())){

						orderDetailsFragment.changeCarType(activity.getCarType_list().get(i).getValue_no());
						break;
					}
				}

                popupWindow.dismiss();
			}
		});
	}


}

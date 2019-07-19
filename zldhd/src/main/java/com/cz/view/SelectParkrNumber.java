package com.cz.view;


import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.cz.fragment.ParkinfoFragment4;
import com.zld.R;
import com.zld.adapter.AccountDropListAdapter;
import com.zld.fragment.BaseFragment;

import java.util.ArrayList;


@SuppressLint({"NewApi", "ValidFragment"})
public class SelectParkrNumber extends BaseFragment {


    private static final String TAG = "SelectParkrNumber";
    private View parent;
    private Toast mToast;
    private ParkinfoFragment4 parkInfoFragment;
    private ListView listview;
    private PopupWindow popupWindow;

    public SelectParkrNumber() {
        super();
    }

    public SelectParkrNumber(View parent, ParkinfoFragment4 parkInfoFragment) {
        this.parent = parent;
        this.parkInfoFragment = parkInfoFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @SuppressLint("NewApi")
    public void showView() {
        showPopupWindow(parent);
    }


    public void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(parkInfoFragment.getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 修改空闲车位列表
     *
     * @param parent
     */
    private void showPopupWindow(View parent) {

        if (listview == null) {
            listview = (ListView) LayoutInflater.from(parkInfoFragment.getActivity()).inflate(R.layout.account_droplist, null);
        }
        int count = 5;
        final ArrayList<String> selectAllAccount = new ArrayList<String>();
        for (int i = 1; i < count; i++) {
            selectAllAccount.add("加" + i * 2 + "");
        }
        selectAllAccount.add("默认");
        selectAllAccount.add("已满");
        for (int i = 1; i < count; i++) {
            selectAllAccount.add("减" + i * 2 + "");
        }

        if (selectAllAccount.size() != 0) {
            AccountDropListAdapter adapter = new AccountDropListAdapter(parkInfoFragment.getActivity(), selectAllAccount, true);
            listview.setAdapter(adapter);
        }
        if (popupWindow == null) {
            popupWindow = new PopupWindow(listview, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            DisplayMetrics dm = new DisplayMetrics();
            //获取屏幕信息
            parkInfoFragment.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screenWidth = dm.widthPixels;

            popupWindow.setWidth((screenWidth / 5));
        }
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        final int[] location = new int[2];
        parent.getLocationOnScreen(location);
        popupWindow.showAsDropDown(parent, 0, -parent.getHeight());


        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = selectAllAccount.get(position);
                if (name == null) {
                    return;
                }

                if (name.equals("已满")) {

                    parkInfoFragment.setFreeParking(-99999999, TAG);
                } else if (name.equals("默认")) {

                    parkInfoFragment.setFreeParking(0, TAG);
                } else {
                    if (position < 4) {

                        parkInfoFragment.setFreeParking(Integer.valueOf((position + 1) * 2), TAG);
                    } else if (position > 5) {

                        parkInfoFragment.setFreeParking(Integer.valueOf((position - 5) * 2), TAG);
                    }
                }
                popupWindow.dismiss();
            }
        });

    }
}

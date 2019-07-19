/*******************************************************************************
 * Copyright (c) 2015 by ehoo Corporation all right reserved.
 * 2015年4月13日 
 *
 *******************************************************************************/
package com.cz.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zld.R;
import com.zld.bean.AllOrder;
import com.zld.bean.CarNumberOrder;
import com.zld.lib.util.FileUtil;


/**
 * 功能说明:
 * 日期:	2019年5月7日
 * 开发者:	KXD
 */
public class ParkrecordFragment3 extends Fragment {

    private static final String TAG = "ParkrecordFragment3";

    private ImageView iv_park_record;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.cz3_park_record, container, false);
        initView(rootView);
        return rootView;
    }

    /**
     * 初始化控件
     */
    private void initView(View rootView) {

        iv_park_record = rootView.findViewById(R.id.iv_park_record2);

    }


    public void transfer( String uuid ) {

        if (uuid == null || "".equals(uuid)){
            iv_park_record.setImageResource(R.drawable.home_car_icon);
            return;
        }
        Bitmap bm = BitmapFactory.decodeFile(FileUtil.getSDCardPath() + "/tcb/" + uuid + ".jpg");
        iv_park_record.setImageBitmap(bm);
    }


}

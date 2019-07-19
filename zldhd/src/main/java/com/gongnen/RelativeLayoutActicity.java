package com.gongnen;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cz.http.HttpCallBack2;
import com.zld.R;
import com.zld.ui.BaseActivity;

import java.util.Map;


public class RelativeLayoutActicity extends BaseActivity implements View.OnClickListener, HttpCallBack2 {

    static final String TAG = "RelativeLayoutActicity";


    @Override
    public void onCreate(Bundle v) {
        super.onCreate(v);
        setContentView( R.layout.relative_layout);

        Button button2 = findViewById(R.id.relative_btn1);
        button2.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {


        //HttpManager2.requestPOSTsynchro("http://tm.banxie.net:30002/collectorApi/orderhistory","");
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
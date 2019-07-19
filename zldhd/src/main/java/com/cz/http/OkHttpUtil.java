package com.cz.http;


import android.util.Log;
import com.cz.util.TimeUtil;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OkHttpUtil {

    private final static String TAG = "OkHttpUtil";



    public void doGet(final String url, final Map<String, String> param, final HttpCallBack2 httpCallBack2, final String sign) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            FormBody.Builder buil = new FormBody.Builder();

            //添加参数
            StringBuffer buf = new StringBuffer(url);
            if (!url.contains("?")) {
                buf.append("?");
            }
            if (url.contains("=")) {
                buf.append("&");
            }
            buf.append("r=").append(TimeUtil.getDateTimeLong());

            for (Map.Entry<String, String> entry : param.entrySet()) {
                buf.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }

            Log.i(TAG + "GET"+sign, buf.toString());

            Request request = new Request.Builder().url(buf.toString()).post(buil.build()).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    try {
                        httpCallBack2.onResponseGET(url, param, sign, null);
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    try {
                        httpCallBack2.onResponseGET(url, param, sign, response.body().string());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            httpCallBack2.onResponseGET(url, param, sign, null);
        }

    }


    public void doPost(final String url, final Map<String, String> param, final HttpCallBack2 httpCallBack2, final String sign) {


        try {

        Log.i(TAG + "POST"+sign, url + "----" + param);
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder buil = new FormBody.Builder();

        //添加参数
        for (Map.Entry<String, String> entry : param.entrySet()) {
            buil.add(entry.getKey(), entry.getValue());
        }

        Request request = new Request.Builder().url(url).post(buil.build()).build();
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

                try {
                    httpCallBack2.onResponsePOST(url,param,sign,null);
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    httpCallBack2.onResponsePOST(url,param,sign,response.body().string());
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });

        } catch (Exception e) {

            Log.w(TAG,e);
            httpCallBack2.onResponsePOST(url, param, sign, null);
        }

    }


    public void doLoadFile(final String url, final Map<String, String> param, String name,String filename,byte[] pImgFull, final HttpCallBack2 httpCallBack2, final String sign) {

        try {
            //补全请求地址
            MultipartBody.Builder builder = new MultipartBody.Builder();
            //设置类型
            builder.setType(MultipartBody.FORM);
            //追加参数
            for (String key : param.keySet()) {
                Object object = param.get(key);
                builder.addFormDataPart(key, object.toString());
            }


            builder.addFormDataPart(name, filename, RequestBody.create(null, pImgFull));

            //创建Request
            final Request request = new Request.Builder().url(url).post(builder.build()).build();
            //单独设置参数 比如读取超时时间
            final Call call = new OkHttpClient().newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.w(TAG,e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    try {
                        httpCallBack2.onResponseFile(url,param,sign,response.body().string());
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            Log.w(TAG,e);
        }
    }

}

package com.cz.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.cz.activity.ZldNew2Activity;
import com.cz.bean.DataReceiveBean;
import com.vz.PlateResult;
import com.vz.tcpsdk;



public class PhotoCallback  implements tcpsdk.OnDataReceiver{

    protected static final String TAG = "PhotoCallback";

    private static PhotoCallback photoCallback;

    public static PhotoCallback getPhotoCallback() {
        if (photoCallback==null)photoCallback = new PhotoCallback();
        return photoCallback;
    }


    @Override
    public void onDataReceive(int handle, PlateResult plateResult, int uNumPlates, int eResultType, byte[] pImgFull, int nFullSize, byte[] pImgPlateClip, int nClipSize){


        Log.i(TAG, "所有拍照回调----handle:" + handle + "--uNumPlates:" + uNumPlates + "--eResultType:" + eResultType + "--pImgFull:" + pImgFull.length
                + "--nFullSize:" + nFullSize + "--pImgPlateClip:" + "--nClipSize:" + nClipSize + "--plateResult" + plateResult.toString());

        DataReceiveBean DataReceiveBean = new DataReceiveBean( handle,  plateResult,  uNumPlates,  eResultType,  pImgFull,  nFullSize,  pImgPlateClip,  nClipSize);

        Bundle bundle = new Bundle();
        bundle.putString( "joinType",TAG );

        Message message = new Message();
            message.what = ZldNew2Activity.onDataReceive;
            message.obj = DataReceiveBean;
            message.setData(bundle);
        Handler handler = ZldNew2Activity.getHandler();
            handler.sendMessage(message);

    }
}

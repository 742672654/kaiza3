package com.gongnen;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;



public class FileSave {

    private static String TAG="FileSave";


    /**
     * @param dataPath 根目录 /storage/emulated/0/shangbao 下面的文件名
     * @param dataString    //字符串内容
     */
    public void seve( String dataPath, String dataString ){


        BufferedWriter out = null;

        //获取SD卡状态
        String state = Environment.getExternalStorageState();

        //取得SD卡根目录
        File file = Environment.getExternalStorageDirectory();
        try {
            Log.e(TAG, "======SD卡根目录：" + file.getCanonicalPath());
            if(file.exists()){
                Log.e(TAG, "file.getCanonicalPath() == " + file.getCanonicalPath());
            }
      /*
      输出流的构造参数1：可以是File对象 也可以是文件路径
      输出流的构造参数2：默认为False=>覆盖内容； true=>追加内容
       */
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getCanonicalPath() + "/"+dataPath+"",true)));
            out.newLine();
            out.write(dataString);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }



}

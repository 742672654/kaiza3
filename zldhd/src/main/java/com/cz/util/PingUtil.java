package com.cz.util;

import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class PingUtil {

    private static final String TAG = "PingUtil";

    public static boolean LAN_Ping(String ip) throws Exception {


     //   try {
            InetAddress address =InetAddress.getByName(ip);
            Log.i(TAG,ip+":   Name:" + address.getHostName());
            Log.i(TAG,ip+":   Addr:" + address.getHostAddress());
            Log.i(TAG,ip+":   Reach:" + address.isReachable(3000)); //是否能通信 返回true或false
            Log.i(TAG,address.toString());

            return address.isReachable(3000);
//        } catch (Exception e) {
//           // Log.w(TAG,e);
//        }

      //  return false;
    }

}

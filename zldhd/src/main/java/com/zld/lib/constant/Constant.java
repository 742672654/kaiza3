package com.zld.lib.constant;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.List;

public class Constant {


    /**
     * 获取当前停车场IP信息
     */
    public static final String WhitelistInformation_URL = "whitelist.do?action=information";

    /**
     * 获取停车场剩余车位
     */
    public static final String WhitelistSurplus_URL = "whitelist.do?action=surplus";

    /**
     * 入场判断
     */
    public static final String Isyuyue_URL = "whitelist.do?action=isyuyue";

    /**
     * 出场判断
     */
    public static final String Reserve_URL = "whitelist.do?action=reserve";

    /**
     * 网络相关常量库
     */
	/*1、PollingService修改获取离场消息字段
	  2、LoginActivity里修改longinSuccess方法里的
	  Constant.requestUrl  Constant.serverUrl  Constant.pingUrl(ip);*/
    /**
     * 更新URL 本地线上一致
     */
    public static final String UPDATE_URL = "http://d.tingchebao.com/update/puserhd/update.xml";
    /**
     * 手动更新的URL
     */
//	public static final String UPDATE_URL_HAND = "http://d.tingchebao.com/update/puserhd/update_hand.xml";
    public static String getUpdateUrlHand(){
        String url="";
        if(requestUrl.contains("/zld/")){
            url = requestUrl.replace("/zld/", "");
        }
        url+="/update/puserhd/update_hand.xml";
        Log.e("----", url);
        return url;
    }
    //
//	/*在是否用本地服务器时,默认走线上服务器*/
    public static String requestUrl = "http://s.tingchebao.com/zld/";

    public static String serverUrl = "http://s.tingchebao.com/mserver/";

    /*登录时输入ip,则使用本地服务器,*/
    public static void requestUrl(String ip){
        requestUrl = "http://"+ip+":8081/zld/";
    }
    public static void serverUrl(String ip){
        serverUrl = "http://"+ip+":8081/mserver/";
    }

    public static void pingUrl(String ip){
        Log.e("shuyu", "pingUrl"+ip);
        PING_TEST_LOCAL = "http://"+ip+":8081/zld/worksiteinfo.do?comid=&action=queryworksite";
    }

    /*用于平板本地化,检测网络*/
    public static String PING_TEST_LOCAL = "http://s.tingchebao.com/zld/worksiteinfo.do?comid=&action=queryworksite";

//	/**区分链接是线上还是本地*/
    public static final String LINE_LOCAL = "s.tingchebao.com";

    public static final int DELETE_IMAGE = 500;
    public static long ONEDAYTAMP = 1*24*60*60*1000;// 10分 以内的毫秒

    /**进入后台*/
    public static final String INTO_BACK = "http://47.111.11.222:8081/zld/";

    /**查询订单*/
    public static final String QUERY_ORDER = "cobp.do?action=queryorder";
    /**当前订单*/
    public static final String GET_CURRORDER = "cobp.do?action=getcurrorder";
    /**离场订单*/
    public static final String ORDER_HISTORY = "collectorrequest.do?action=orderhistory";
    /**当前订单详情*/
    public static final String CAT_ORDER = "cobp.do?action=catorder";
    /**修改大小车计费策略*/
    public static final String CHANGE_CAR_TYPE = "cobp.do?action=changecartype";
    /**修改大小车计费策略*/
//	public static final String GET_CAR_TYPE = "cobp.do?action=getcartype";
    /**收费员信息*/
    public static final String COLLECTOR_INFO = "collectorrequest.do?action=getnewincome";
    /**结算订单*/
    public static final String COMPLETE_ORDER = "nfchandle.do?action=completeorder";
    /**修改订单*/
    public static final String MODIFY_ORDER = "cobp.do?action=addcarnumber";
    /**免费订单*/
    public static final String FREE_ORDER = "collectorrequest.do?action=freeorder";
    /**生成订单*/
    public static final String MADE_ORDER = "cobp.do?action=preaddorder";
    /**抬杆动作记录*/
    public static final String LIFT_ORDER = "collectorrequest.do?action=liftrodrecord";
    /**出场时减免小时**/
    public static final String HD_DERATE = "nfchandle.do?action=hdderate";
    /**更改垃圾订单数*/
    public static final String CHANG_INVALIDORDER = "collectorrequest.do?action=invalidorders";
    /**获取车场信息*/
    public static final String COMINFO = "collectorrequest.do?action=cominfo";
    /**获取工作站下所有摄像头和LED信息*/
    public static final String WORKINFO = "worksiteinfo.do?action=getpassinfo";
    /**强制生成订单*/
    public static final String ADD_CAR = "cobp.do?action=addorder";
    /**预支付*/
    public static final String PRE_PAY = "nfchandle.do?action=doprepayorder";
    /**下班*/
    public static final String AFTER_WORK = "collectorrequest.do?action=gooffwork";
    /**获取工作站信息*/
    public static final String QUERY_WORKSITE = "worksiteinfo.do?action=queryworksite";
    /**获取对应工作站通道信息*/
    public static final String QUERY_PASS_INFO = "worksiteinfo.do?action=querypass";
    /**获取登录信息*/
    public static final String LOGIN = "collectorlogin.do?";
    /**获取离场订单信息*/
    public static final String GET_LEAVE_MESG = "getmesg.do?";
    /**获取分享信息*/
    public static final String GET_SHARE = "getshare.do?";
    /**下载图片*/
    public static final String DOWNLOAD_IMAGE = "carpicsup.do?action=downloadpic";
    /**下载log*/
    public static final String DOWNLOAD_LOGO_IMAGE = "carpicsup.do?action=downloadlogpic";
    /**获取到线上支付后回调*/
    public static final String PAY_BACK = "cobp.do?action=line2local";
    /**图片类型入口*/
    public static final int HOME_PHOTOTYPE = 0;
    /**图片类型出口*/
    public static final int EXIT_PHOTOTYPE = 1;

    /**
     * 消息相关常量库
     */
    /**离场订单消息*/
    public static final int LEAVEORDER_MSG = 1;
    /**入场车辆消息*/
    public static final int PARKING_NUMS_MSG = 2;
    public static final int SHOWVIDEO_MSG = 60;
    public static final int OPENCAMERA_SUCCESS_MSG = 61;
    public static final int PICUPLOAD_FILE = 62;
    public static final int SHOWPIC_ONRIGHT_MSG = 63;
    public static final int OPENCAMERA_FAIL_MSG = 64;
    public static final int COMECAR_MSG = 65;

    public static final int KEEPALIVE = 66;
    public static final int KEEPALIVE_TIME = 67;
    public static final int HOME_DELAYED_TIME = 68;
    public static final int EXIT_DELAYED_TIME = 69;
    public static final int STOP = 70;
    public static final int NONETWORK_MSG = 80;

    public static final int DELAY_UPLOAD = 81;
    public static final int LIST_REFRESH = 82;
    public static final int CLEAR_ORDER = 83;
    public static final int UPPOLE_IMAGR_SUCCESS = 84;
    public static final int UPPOLE_IMAGR_ERROR = 85;
    public static final int REFRESH_NOMONTHCAR_IMAGE = 86;
    public static final int REFRESH_NOMONTHCAR2_IMAGE = 87;
    public static final int HOME_CAR_OUTDATE_ICON = 89;
    public static final int PLAY_PULL = 88;

    public static final int KEEP_TIME = 5;
    public static final int RESTART_YES = 6;

    /*同步订单间隔时间*/
    public static final long time = 1000*60*1;

    //车辆相关常量值
	/*#define LT_UNKNOWN  0   //未知车牌
	#define LT_BLUE     1   //蓝牌小汽车
	#define LT_BLACK    2   //黑牌小汽车
	#define LT_YELLOW   3   //单排黄牌
	#define LT_YELLOW2  4   //双排黄牌（大车尾牌，农用车）
	#define LT_POLICE   5   //警车车牌
	#define LT_ARMPOL   6   //武警车牌
	#define LT_INDIVI   7   //个性化车牌
	#define LT_ARMY     8   //单排军车牌
	#define LT_ARMY2    9   //双排军车牌
	#define LT_EMBASSY  10  //使馆车牌
	#define LT_HONGKONG 11  //香港进出中国大陆车牌
	#define LT_TRACTOR  12  //农用车牌
	#define LT_COACH	13  //教练车牌
	#define LT_MACAO	14  //澳门进出中国大陆车牌
	#define LT_ARMPOL2   15 //双层武警车牌
	#define LT_ARMPOL_ZONGDUI 16  // 武警总队车牌
	#define LT_ARMPOL2_ZONGDUI 17 // 双层武警总队车牌*/
    public static final int LT_POLICE = 5;
    public static final int LT_ARMPOL = 6;
    public static final int LT_ARMY = 8;
    public static final int LT_ARMY2 = 9;
    public static final int LT_ARMPOL2 = 15;
    public static final int LT_ARMPOL_ZONGDUI = 16;
    public static final int LT_ARMPOL2_ZONGDUI = 17;

    public static final String INTENT_KEY = "intentkey";
    //车牌号的正常长度
//	public static final int CAR_PLATE_LENTH = 7;

    //保存图片文件夹路径
    public static final String FRAME_DUMP_FOLDER_PATH = Environment
            .getExternalStorageDirectory() + File.separator + "tingchebao/";

//	/**摄像头连接状态:成功*/
//	public static final String CAMERA_STATE_SUCCESS = Constant.sOne;
//	/**摄像头连接状态:断开*/
//	public static final String CAMERA_STATE_FAILE = Constant.sZero;

    public static final int StopVedio = 0x20001;
    public static final int StartVedio = 0x20002;

    public static final int SelectVedio = 0x20009;
    public static final int ConfigDeivce = 0x20010;
    public static final int DClickVedio = 0x200011;
    public static final int PlateImage = 0x200012;

    public static final String sZero = "0";
    public static final String sOne = "1";
    public static final String sTwo = "2";
    public static final String sThree = "3";
    public static final String sNine = "9";

    public static final int BerthHandlerWhat = 1219;
}

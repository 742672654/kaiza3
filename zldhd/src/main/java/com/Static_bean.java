package com;

public class Static_bean {


    public static String IP = "https://parking.yilufa.net:10888/";


    /**
     * TODO 登录，返回个人信息
     * @param username 账号
     * @param password 密码
     * @param edition 版本号
     * @param type 1是手动登录，2是自动登录
     * @param out 固定值json
     */
    public static String login = IP + "collectorlogin/login";

    /**
     * TODO 获取当前用户对应的停车场信息
     * @param token 51f0dd0b8e1afdf1f6b7b366695652bc
     * @param out 固定值json
     */
    public  static String worksiteinfo_cominfo = IP + "collectorApi/cominfo";

    /**
     * TODO 获取当前用户对应的工作站
     * @param comId 停车场id 21733   <<<<<<<<<<<<<<<<<Id 的I是大写
     * @param out 固定值json
     */
    public  static String worksiteinfo_queryworksite = IP + "worksiteInfoApi/queryworksite";

    /**
     * TODO 获取工作站的LED信息
     *
     * @param comId 停车场id 21733
     * @param worksite_id 工作站id 830
     * @param out 固定值json
     */
    public  static String worksiteinfo_getpassinfo = IP + "worksiteInfoApi/getpassinfo";

    /**
     * TODO 获取所有订单类型
     */
    public  static String OrderType_list = IP + "OrderType/list";

    /**
     * TODO 收费信息
     *
     * @param comid 停车场id 21733
     * @param worksite_id 工作站id 830
     * @param token 51f0dd0b8e1afdf1f6b7b366695652bc
     * @param logontime 1555993832
     * @param btime 2019-05-07
     * @param etime 2019-05-07
     * @param worksite_id 工作站id 830
     * @param comid 停车场id 21733
     * @param out 固定值json
     */
    public static  String collectorrequest_getnewincome = IP + "collectorApi/getnewincome";

    /**
     * TODO 单日订单数量和金额
     *
     * @param comid 停车场id 21733
     * @param uid 用户id
     */
    public static  String collectorrequest_order_quantity = IP + "collectorApi/order_quantity";

    /**
     * TODO 获取空闲车位数量
     *
     * @param comid 停车场id 21733
     * @param out 固定值json
     */
    public  static String whitelist_remaining = IP + "whitelistApi/remaining";

    /**
     * TODO 获取在场车辆订单
     *
     * @param comid 停车场id 21733
     * @param page 第几页，min:1
     * @param size 每页几条，min:1.max:1000
     * @param through= IP +3  固定值，不知道什么卵用
     */
    public  static String cobp_getcurrorder = IP + "cobp/getcurrorder?action=getcurrorder";

    /**
     * TODO 获取离场车辆订单列表
     *
     * @param comid 停车场id 21733
     * @param page 第几页，min:1
     * @param size 每页几条，min:1.max:1000
     * @param token
     * @param uid 522619
     * @param day 固定值today
     * @param ptype 固定值1
     * @param out json
     */
    public  static String collectorrequest_orderhistory = IP + "collectorApi/orderhistory";

    /**
     * TODO 获取车辆订单详情
     *
     * @param comid 停车场id 21733
     * @param ptype 固定值:1
     * @param orderid 订单号351207
     * @param channelid 通道号
     */
    public  static String cobp_catorder = IP + "cobp/catorder";//"http://47.111.11.222:8081/zld/cobp.do?action= IP +catorder";

    /**
     * TODO 生成订单
     *
     * @param comid 停车场id 21733
     * @param uid  用户id
     * @param carnumber 车牌号
     * @param through 固定值3
     * @param from 0:通道扫牌自动生成订单，1：补录车牌生成订单
     * @param car_type //车牌类型
     * @param passid 工作站ID
     * @param uuid 照片的id
     */
    public  static String cobp_preaddorder = IP + "cobp/preaddorder";

    //结算订单
    public  static String nfchandle_completeorder = IP + "nfchandleApi/completeorder";//"http://47.111.11.222:8081/zld/nfchandle.do?action= IP +completeorder";

    /**
     * TODO 减免/免费
     *
     * @param uid  用户id
     * @param time 免费几小时
     * @param type 3是减免，4是免费
     * @param comid 停车场id 21733
     * @param orderid 订单id
     */
    public  static String nfchandle_hdderate = IP + "whitelistApi/addticket";

    //修改订单的车辆类型
    public  static String cobp_changecartype = IP + "cobp/changecartype";//"http://47.111.11.222:8081/zld/cobp.do?action= IP +changecartype";

    /**
     * TODO 修改车牌号
     *
     * @param comid 停车场id 21733
     * @param orderid 订单id
     * @param carnumber 车牌号
     * @param through 固定值3
     */
    public  static String cobp_addcarnumber = IP + "cobp/addcarnumber";

    //TODO 上传照片
    /**
     * @param uid 订单关联字符串
     * @param pictype： 1.入场 2.出场
     */
    public  static String carpicsup_upPicToOss = IP + "carpicsup/upPicToOss";


    //TODO 上传订单状态到redis
    /**
     * @param comid 停车场id
     * @param orderid 订单id
     */
    public  static String wxpay_savetemporder = IP + "wxpay/savetemporder";


    //TODO 上传订单状态到redis
    /**
     * @param comid 停车场id
     * @param plateText 车牌号
     */
    public  static String whitelistApi_isyuyue = IP + "whitelistApi/isyuyue";

    //TODO 出场时调用
    /**
     * @param comid 停车场id
     * @param plateText 车牌号
     */
    public  static String whitelistApi_plateText = IP + "whitelistApi/plateText";

    //获取当前车辆最新订单
    public  static String whitelistApi_queryorders = IP + "whitelistApi/queryorders";
}

package com.cz.bean;

import java.io.Serializable;
import java.math.BigDecimal;



public class OrderTb implements Serializable{
    

    private Long id;

    /**
     * 创建时间
     */
    private Long create_time;

    /**
     * 停车场ID
     */
    private Long comid;

    /**
     * 用户ID
     */
    private Long uin;

    /**
     * 最后支付的金额
     */
    private BigDecimal total;

    /**
     * 0未支付 1已支付 2:逃单
     */
    private Integer state;

    /**
     * 支付时间
     */
    private Long end_time;

    /**
     * 0:正常结算，1：进场异常结算的订单，2：更正过车牌的订单，3:补录来车生成的订单
     */
    private Integer auto_pay;

    /**
     * 0:帐户支付,1:现金支付,2:手机支付 3:包月 4:现金预支付 5：银联卡(中央预支付，后面废弃) 6：商家卡(中央预支付，后面废弃) 8：免费放行 9：刷卡
     */
    private Integer pay_type;

    private String nfc_uuid;

    /**
     * 0:NFC,1:IBeacon,2:照牌,3:通道扫牌 4直付 5月卡用户6:车位二维码 7：月卡用户第2..3辆车入场 8：分段月卡
     */
    private Integer c_type;

    /**
     * 收费员帐号
     */
    private Long uid;

    /**
     * 车牌
     */
    private String car_number;

    /**
     * 手机串号
     */
    private String imei;

    /**
     * 计费方式：0按时(0.5/15分钟)，1按次（12小时内10元,前1/30min，后每小时1元）
     */
    private Integer pid;

    /**
     * 0：通用，1：小车，2：大车
     */
    private Integer car_type;

    /**
     * 进口通道id
     */
    private Long in_passid;

    /**
     * 出口通道id
     */
    private Long out_passid;

    /**
     * 0:默认状态 1：预定车位 
     */
    private Integer pre_state;

    /**
     * 类型：0普通订单，1极速通，3本地化订单 4本地服务器订单 5本地生成线上结算订单，6小程序预约订单
     */
    private Integer type;

    /**
     * 预支付订单需要同步到线下  0:不需要  1:需要  2同步完成   3本地切换到线上线上生成的需要  4:线上结算的都需要同步下去
     */
    private Integer need_sync;

    /**
     * 0否 1是不显示
     */
    private Integer ishd;

    /**
     *  免费原因   默认-1 不免费
     */
    private Integer freereasons;

    /**
     * 0系统结算，1手动结算
     */
    private Integer isclick;

    /**
     * 预付金额
     */
    private BigDecimal prepaid;

    /**
     * 预支付时间
     */
    private Long prepaid_pay_time;

    /**
     * 泊位号
     */
    private Long berthnumber;

    /**
     * 泊位段编号
     */
    private Long berthsec_id;

    /**
     * 所属小程序用户ID
     */
    private String groupid;

    /**
     * 出场收费员
     */
    private Long out_uid;

    /**
     * 0不是 1是 
     */
    private Integer is_union_user;

    /**
     * 适配车场的各种车辆类型信息记录
     */
    private String car_type_zh;

    /**
     * 适配车场上传的车辆进场类型（月卡套餐或普通）
     */
    private String c_type_zh;

    /**
     * 记录车场上传订单的编号
     */
    private String order_id_local;

    /**
     * 适配车场车辆进场通道信息
     */
    private String in_channel_id;

    /**
     * 适配车场车辆出场通道信息
     */
    private String out_channel_id;

    /**
     * 适配车场记录车辆停车时长信息
     */
    private Long duration;

    /**
     * 适配车场车辆的缴费类型，进行记录
     */
    private String pay_type_en;

    /**
     * 适配车场车辆免费停车原因
     */
    private String freereasons_local;

    private BigDecimal amount_receivable;

    private BigDecimal cash_pay;

    private BigDecimal cash_prepay;

    private BigDecimal electronic_pay;

    private BigDecimal electronic_prepay;

    private BigDecimal reduce_amount;

    private Integer sync_state;

    private Integer line_id;

    /**
     * 拍照的时间
     */
    private String photo_time;
    
    /**
     * 订单picUUID
     */
    private String uuid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public Long getComid() {
        return comid;
    }

    public void setComid(Long comid) {
        this.comid = comid;
    }

    public Long getUin() {
        return uin;
    }

    public void setUin(Long uin) {
        this.uin = uin;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }

    public Integer getAuto_pay() {
        return auto_pay;
    }

    public void setAuto_pay(Integer auto_pay) {
        this.auto_pay = auto_pay;
    }

    public Integer getPay_type() {
        return pay_type;
    }

    public void setPay_type(Integer pay_type) {
        this.pay_type = pay_type;
    }

    public String getNfc_uuid() {
        return nfc_uuid;
    }

    public void setNfc_uuid(String nfc_uuid) {
        this.nfc_uuid = nfc_uuid;
    }

    public Integer getC_type() {
        return c_type;
    }

    public void setC_type(Integer c_type) {
        this.c_type = c_type;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCar_type() {
        return car_type;
    }

    public void setCar_type(Integer car_type) {
        this.car_type = car_type;
    }

    public Long getIn_passid() {
        return in_passid;
    }

    public void setIn_passid(Long in_passid) {
        this.in_passid = in_passid;
    }

    public Long getOut_passid() {
        return out_passid;
    }

    public void setOut_passid(Long out_passid) {
        this.out_passid = out_passid;
    }

    public Integer getPre_state() {
        return pre_state;
    }

    public void setPre_state(Integer pre_state) {
        this.pre_state = pre_state;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNeed_sync() {
        return need_sync;
    }

    public void setNeed_sync(Integer need_sync) {
        this.need_sync = need_sync;
    }

    public Integer getIshd() {
        return ishd;
    }

    public void setIshd(Integer ishd) {
        this.ishd = ishd;
    }

    public Integer getFreereasons() {
        return freereasons;
    }

    public void setFreereasons(Integer freereasons) {
        this.freereasons = freereasons;
    }

    public Integer getIsclick() {
        return isclick;
    }

    public void setIsclick(Integer isclick) {
        this.isclick = isclick;
    }

    public BigDecimal getPrepaid() {
        return prepaid;
    }

    public void setPrepaid(BigDecimal prepaid) {
        this.prepaid = prepaid;
    }

    public Long getPrepaid_pay_time() {
        return prepaid_pay_time;
    }

    public void setPrepaid_pay_time(Long prepaid_pay_time) {
        this.prepaid_pay_time = prepaid_pay_time;
    }

    public Long getBerthnumber() {
        return berthnumber;
    }

    public void setBerthnumber(Long berthnumber) {
        this.berthnumber = berthnumber;
    }

    public Long getBerthsec_id() {
        return berthsec_id;
    }

    public void setBerthsec_id(Long berthsec_id) {
        this.berthsec_id = berthsec_id;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public Long getOut_uid() {
        return out_uid;
    }

    public void setOut_uid(Long out_uid) {
        this.out_uid = out_uid;
    }

    public Integer getIs_union_user() {
        return is_union_user;
    }

    public void setIs_union_user(Integer is_union_user) {
        this.is_union_user = is_union_user;
    }

    public String getCar_type_zh() {
        return car_type_zh;
    }

    public void setCar_type_zh(String car_type_zh) {
        this.car_type_zh = car_type_zh;
    }

    public String getC_type_zh() {
        return c_type_zh;
    }

    public void setC_type_zh(String c_type_zh) {
        this.c_type_zh = c_type_zh;
    }

    public String getOrder_id_local() {
        return order_id_local;
    }

    public void setOrder_id_local(String order_id_local) {
        this.order_id_local = order_id_local;
    }

    public String getIn_channel_id() {
        return in_channel_id;
    }

    public void setIn_channel_id(String in_channel_id) {
        this.in_channel_id = in_channel_id;
    }

    public String getOut_channel_id() {
        return out_channel_id;
    }

    public void setOut_channel_id(String out_channel_id) {
        this.out_channel_id = out_channel_id;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getPay_type_en() {
        return pay_type_en;
    }

    public void setPay_type_en(String pay_type_en) {
        this.pay_type_en = pay_type_en;
    }

    public String getFreereasons_local() {
        return freereasons_local;
    }

    public void setFreereasons_local(String freereasons_local) {
        this.freereasons_local = freereasons_local;
    }

    public BigDecimal getAmount_receivable() {
        return amount_receivable;
    }

    public void setAmount_receivable(BigDecimal amount_receivable) {
        this.amount_receivable = amount_receivable;
    }

    public BigDecimal getCash_pay() {
        return cash_pay;
    }

    public void setCash_pay(BigDecimal cash_pay) {
        this.cash_pay = cash_pay;
    }

    public BigDecimal getCash_prepay() {
        return cash_prepay;
    }

    public void setCash_prepay(BigDecimal cash_prepay) {
        this.cash_prepay = cash_prepay;
    }

    public BigDecimal getElectronic_pay() {
        return electronic_pay;
    }

    public void setElectronic_pay(BigDecimal electronic_pay) {
        this.electronic_pay = electronic_pay;
    }

    public BigDecimal getElectronic_prepay() {
        return electronic_prepay;
    }

    public void setElectronic_prepay(BigDecimal electronic_prepay) {
        this.electronic_prepay = electronic_prepay;
    }

    public BigDecimal getReduce_amount() {
        return reduce_amount;
    }

    public void setReduce_amount(BigDecimal reduce_amount) {
        this.reduce_amount = reduce_amount;
    }

    public Integer getSync_state() {
        return sync_state;
    }

    public void setSync_state(Integer sync_state) {
        this.sync_state = sync_state;
    }

    public Integer getLine_id() {
        return line_id;
    }

    public void setLine_id(Integer line_id) {
        this.line_id = line_id;
    }

    public String getPhoto_time() {
        return photo_time;
    }

    public void setPhoto_time(String photo_time) {
        this.photo_time = photo_time;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "OrderTb{" +
                "id=" + id +
                ", create_time=" + create_time +
                ", comid=" + comid +
                ", uin=" + uin +
                ", total=" + total +
                ", state=" + state +
                ", end_time=" + end_time +
                ", auto_pay=" + auto_pay +
                ", pay_type=" + pay_type +
                ", nfc_uuid='" + nfc_uuid + '\'' +
                ", c_type=" + c_type +
                ", uid=" + uid +
                ", car_number='" + car_number + '\'' +
                ", imei='" + imei + '\'' +
                ", pid=" + pid +
                ", car_type=" + car_type +
                ", in_passid=" + in_passid +
                ", out_passid=" + out_passid +
                ", pre_state=" + pre_state +
                ", type=" + type +
                ", need_sync=" + need_sync +
                ", ishd=" + ishd +
                ", freereasons=" + freereasons +
                ", isclick=" + isclick +
                ", prepaid=" + prepaid +
                ", prepaid_pay_time=" + prepaid_pay_time +
                ", berthnumber=" + berthnumber +
                ", berthsec_id=" + berthsec_id +
                ", groupid='" + groupid + '\'' +
                ", out_uid=" + out_uid +
                ", is_union_user=" + is_union_user +
                ", car_type_zh='" + car_type_zh + '\'' +
                ", c_type_zh='" + c_type_zh + '\'' +
                ", order_id_local='" + order_id_local + '\'' +
                ", in_channel_id='" + in_channel_id + '\'' +
                ", out_channel_id='" + out_channel_id + '\'' +
                ", duration=" + duration +
                ", pay_type_en='" + pay_type_en + '\'' +
                ", freereasons_local='" + freereasons_local + '\'' +
                ", amount_receivable=" + amount_receivable +
                ", cash_pay=" + cash_pay +
                ", cash_prepay=" + cash_prepay +
                ", electronic_pay=" + electronic_pay +
                ", electronic_prepay=" + electronic_prepay +
                ", reduce_amount=" + reduce_amount +
                ", sync_state=" + sync_state +
                ", line_id=" + line_id +
                ", photo_time='" + photo_time + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
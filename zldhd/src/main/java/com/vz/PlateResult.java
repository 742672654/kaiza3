package com.vz;


import java.io.UnsupportedEncodingException;


public class PlateResult {

    /**
     * <车牌颜色
     */
    public int nColor;
    public byte[] license;
    /**
     * <车牌号码
     *///GBK
    public byte[] color;
    /**
     * <车牌颜色序号，详见车牌颜色定义LC_X 0未知颜色，1蓝色，2黄色，3白色，4黑色，5绿色
     */
    public int nType;
    /**
     * <车牌类型，详见车牌类型定义LT_X
     * 0未知车牌，1蓝牌小汽车，2黑牌小汽车，3单排黄牌，4双排黄牌（大车尾牌，农用车），5警车车牌，6武警车牌，7个性化车牌，8单排军车牌，9双排军车牌，
     * 10使馆车牌，11香港进出中国大陆车牌，12农用车牌，13教练车牌，4澳门进出中国大陆车牌，15双层武警车牌，16武警总队车牌，17双层武警总队车牌
     */
    public int nConfidence;
    /**
     * <车牌可信度
     */
    public int nBright;
    /**
     * <亮度评价
     */
    public int nDirection;
    /**
     * <运动方向，详见运动方向定义 DIRECTION_X
     */
    public THRECT rcLocation;
    /**
     * <车牌位置
     */
    public int nTime;
    /**
     * <识别所用时间
     */
    public VZ_TIMEVAL tvPTS;
    /**
     * <识别时间点
     */
    public int uBitsTrigType;
    /**
     * <强制触发结果的类型,见TH_TRIGGER_TYPE_BIT	0自动，1外部，2软件，3虚拟地感线圈
     */
    public char nCarBright;
    /**
     * <车的亮度
     */
    public char nCarColor;
    /**
     * <车的颜色，详见车辆颜色定义LCOLOUR_X
     */
    //	   char reserved0[2];			/**<为了对齐*/
    public int uId;
    /**
     * <记录的编号
     */
    public VzBDTime struBDTime;

    public PlateResult() {
    }



	  // 将byte数组bRefArr转为一个整数,字节数组的低位是整型的低字节位
	 public static int toInt(byte[] bRefArr,int offset,int length) {
	        int iOutcome = 0;
	        byte bLoop;

         for (int i = offset; i < length; i++) {
             bLoop = bRefArr[i];
             iOutcome += (bLoop & 0xFF) << (8 * (i - offset));
         }
         return iOutcome;
     }

    //读取照片数据中的返回结果；读到的结果转码后返回String类型结果；
    public static String readImageData(byte[] bytes) {
        int i;
        for (i = 0; i < bytes.length; i++) {
            if (bytes[i] == 0) {
                break;
            }
        }

        byte[] temp = new byte[i];
        for (int j = 0; j < temp.length; j++) {
            temp[j] = bytes[j];
        }
        String result = "FAIL";
        try {
            result = new String(temp, "gbk");// 这里写转换后的编码方式
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String toString() {

        return "PlateResult{" +
                "license=" + readImageData(license) +
                ", color=" + readImageData(color) +
                ", nColor=" + nColor +
                ", nType=" + nType +
                ", nConfidence=" + nConfidence +
                ", nBright=" + nBright +
                ", nDirection=" + nDirection +
                ", rcLocation=" + rcLocation +
                ", nTime=" + nTime +
				", tvPTS=" + tvPTS +
				", uBitsTrigType=" + uBitsTrigType +
				", nCarColor=" + nCarColor +
				", uId=" + uId +
				", struBDTime=" + struBDTime +
				'}';
	}
}


 

 
package com.cz.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;


public class StringUtil {


    /**
     * 随机返回32位UUID
     */
    public static String getUuid() {

        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 随机返回原始格式36位UUID
     */
    public static String getUuid_36() {

        return UUID.randomUUID().toString();
    }

    /**
     * 随机返回指定长度的UUID
     */
    public static String getUuid(int length) {

        String uuid = UUID.randomUUID().toString();
        for (int j = 0; j < (length / 32); j++) {

            uuid += UUID.randomUUID().toString();
        }

        return uuid.replace("-", "").substring(0, length);
    }

    /**
     * @功能 获取随机正整数（不包含0）
     * @接收 i：最大值
     */
    public static int getRandomNumber(int i) {

        Random ra = new Random();

        return ra.nextInt(i) + 1;
    }

    /**
     * @return 0:都为空或""，1：都不为空或!""，2：s1为不空或!""，3：s2为不空或!""
     * @功能 判断两个字符串不为空的四种情况
     * @author xiaoduo
     */
    public static int two_Notempty(String s1, String s2) {

        if (s1 != null && !s1.equals("")) {

            if (s2 != null && !s2.equals("")) {

                return 1;
            } else {

                return 2;
            }
        } else if (s2 != null && !s2.equals("")) {

            return 3;
        } else return 0;
    }

    /**
     * @return 字符串、null
     * @功能 去除字符串无用字符【空格、制表符、换页符、空白字符、...】
     * @author xiaoduo
     */
    public static String replaceUseless(String s0) {

        try {
            if (s0 == null) {

                return null;
            } else {

                s0 = s0.replaceAll("\\s*", "");

                if (s0.length() == 0) {
                    return null;
                }
            }
        } catch (Exception e) {
            return s0;
        }
        return s0;
    }

    /**
     * @return 32位密文
     * @throws NoSuchAlgorithmException
     * @功能 进行32位小写MD5哈希计算
     * @author xiaoduo
     */
    public static String md5_hash32(String plainText) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(plainText.getBytes());
        byte b[] = md.digest();

        int i;

        StringBuffer buf = new StringBuffer();
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }

        return buf.toString();
    }

}

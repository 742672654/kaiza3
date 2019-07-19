package com.cz.http;


import java.util.Map;

/**
 * 描述：通信回调类
 * @author  lulogfei
 */
public interface HttpCallBack2 {


	/**
	 * 描述：通信成功的回调
	 * @param url 请求地址
	 * @param param 发送的参数
	 * @param sign 标识
	 * @param object 回调数据
	 * @return
	 */
    void onResponseGET(String url, Map<String, String> param, String sign, String object);

	/**
	 * 描述：通信成功的回调
	 * @param url 请求地址
	 * @param param 发送的参数
	 * @param sign 标识
	 * @param object 回调数据
	 * @return
	 */
    void onResponsePOST(String url, Map<String, String> param, String sign, String object);

	/**
	 * 描述：通信成功的回调
	 * @param url 请求地址
	 * @param param 发送的参数
	 * @param sign 标识
	 * @param object 回调数据
	 * @return
	 */
    void onResponseFile(String url, Map<String, String> param, String sign, String object);
}
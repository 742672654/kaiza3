//package com.cz.http;
//
//
//import java.io.BufferedOutputStream;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Map;
//import java.util.Map.Entry;
//
//
///**
// *@功能    模拟HTTP请求
// *@参考  www.cnblogs.com/zhi-leaf/p/8508071.html
// */
//
//public class HttpURLConnectionUtil {
//
//
//
//	public static final String  TAG = "requestGet";
//
//	 /**
//	  *@功能      模拟发送GET请求
//	  *@接收   param:"code=1&id=ss"
//	  */
//	  public static String doGET ( String uri, String param ){
//
//		  	BufferedReader br = null;
//	        try {
//
//	            URL url = new URL(uri );
//	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//	            connection.setDoOutput(true); // 设置该连接是可以输出的
//	            connection.setRequestMethod("GET"); // 设置请求方式
//	            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//	            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
//	            String line = null;
//	            StringBuilder result = new StringBuilder();
//	            while ((line = br.readLine()) != null) { // 读取数据
//	                result.append(line + "\n");
//	            }
//	            connection.disconnect();
//
//				return result.toString();
//	        } catch (Exception e) {
//	            e.printStackTrace();
//				return null;
//	        }finally {
//	        	if( br != null )try{br.close();}catch(IOException e){e.printStackTrace();}
//			}
//	    }
//
//
//	  /**
//	   *@功能    模拟发送POST form表单
//	   *@接收  param:"code=001&name=测试"
//	   */
//	  public static String doPOST( String uri, String param ) {
//
//		  		return doPOST2( uri, param, null );
//	    }
//
//	  /**
//	   *@功能    模拟发送POST form表单2
//	   *@接收  param: "code=001&name=测试"
//	   *@接收  headers： Map类型 headers头文件
//	   */
//	  public static String doPOST2( String uri, String param, Map<String,String> headers ) {
//
//	    	 BufferedReader br = null;
//	        try {
//	            URL url = new URL(uri);
//	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//	            connection.setDoInput(true); // 设置可输入
//	            connection.setDoOutput(true); // 设置该连接是可以输出的
//	            connection.setRequestMethod("POST"); // 设置请求方式
//	            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//
//	            if( headers!=null && headers.size()>0 ){
//	            for(Entry<String, String> entry : headers.entrySet()) {
//
//	            	  connection.setRequestProperty(entry.getKey(), entry.getValue());
//	            }
//	            }
//	            PrintWriter pw = new PrintWriter(new BufferedOutputStream(connection.getOutputStream()));
//	            pw.write(param);
//	            pw.flush();
//	            pw.close();
//	            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
//	            String line = null;
//	            StringBuilder result = new StringBuilder();
//	            while ((line = br.readLine()) != null) { // 读取数据
//	                result.append(line + "\n");
//	            }
//	            connection.disconnect();
//
//	            return result.toString();
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            return null;
//	        }finally {
//	        	if( br != null )try {br.close();} catch(IOException e){e.printStackTrace();}
//			}
//	    }
//
//	  /**
//	   *@功能    模拟发送JSON数据
//	   *@接收  param格式:{"code":"0","name":"小张","id":"01"}
//	   */
//	  public  String doJSON( String uri, String param ){
//
//	    	BufferedReader br = null;
//	        try {
//	            URL url = new URL( uri );
//	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//	            connection.setDoInput(true); // 设置可输入
//	            connection.setDoOutput(true); // 设置该连接是可以输出的
//	            connection.setRequestMethod("POST"); // 设置请求方式
//	            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//	            PrintWriter pw = new PrintWriter(new BufferedOutputStream(connection.getOutputStream()));
//	            pw.write(param);
//	            pw.flush();
//	            pw.close();
//	            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
//	            String line = null;
//	            StringBuilder result = new StringBuilder();
//	            while ((line = br.readLine()) != null){ // 读取数据
//	                result.append(line + "\n");
//	            }
//	            connection.disconnect();
//	            return result.toString();
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            return null;
//	        }finally {
//				if( br!=null )try{br.close();}catch (IOException e){e.printStackTrace();}
//			}
//	  }
//
//
//
//}
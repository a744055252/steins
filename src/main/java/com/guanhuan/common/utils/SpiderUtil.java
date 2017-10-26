package com.guanhuan.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @Author: liguanhuan_a@163.com
 * @Description: 爬虫工具类
 * @Date: 2017/10/15/015 18:05
 **/
public class SpiderUtil {

	/**
	 * @Author: liguanhuan_a@163.com
	 * @param: [url]
	 * @Description: 通过url获取页面document
	 * @Date: 2017/10/15/015 18:04
	 **/
	public static Document getDocument(String url) throws Exception {
//		int timeOut = Integer.parseInt(getProperties("shop.properties","TIMEOUT"));
		int timeOut = 5000;
		Document doc = null;
		doc = Jsoup.connect(url)
				.userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
				.timeout(timeOut)
				.get();
		return doc;
	}

	/**
	 * @Author: liguanhuan_a@163.com
	 * @param: [url]
	 * @Description:
	 * @Date: 2017/10/15/015 18:14
	 **/
	public static String getWordString(String url){
		InputStream is;
		try {
			is = new URL(url).openStream();
		} catch (Exception e) {
			throw new NullPointerException("无法获取到页面的源代码");
		}
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		String str = null;
		try {
			while(null != (str = bf.readLine())){
			    sb.append(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				is.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		return sb.toString();
	}

	/**
	 * @Author: liguanhuan_a@163.com
	 * @param: [url]
	 * @Description: 通过url获取到源文件的字符串
	 * @Date: 2017/10/15/015 18:13
	 **/
	public static String getString(String url) throws Exception{
		InputStream is = new URL(url).openStream();
		StringBuilder sb = new StringBuilder();
		try{
			int cp = 0;
			while((cp = is.read()) != -1){
				sb.append((char)cp);
			}
		}
		finally{
			is.close();
		}
		return sb.toString();
	}

	/**
	 * @Author: liguanhuan_a@163.com
	 * @param: [data, path, retain]
	 * @Description: 将数据保持到path里，retain是否刷新
	 * @Date: 2017/10/15/015 18:09
	 **/
	public static boolean saveData(String data, String path, boolean retain){
		File file = new File(path);
		if(!file.exists())
			file.mkdirs();
		if(!file.isFile())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file, retain));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			bw.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			bw.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * @Author: liguanhuan_a@163.com
	 * @param: [data, path]
	 * @Description: 保存数据到path中，加在path后面
	 * @Date: 2017/10/15/015 18:10
	 **/
	public static boolean saveData(String data, String path){
		return saveData(data, path, false);
	}

	/**
	 * @Author: liguanhuan_a@163.com
	 * @param: [fileName, attrKey]
	 * @Description:
	 * @Date: 2017/10/15/015 18:07
	 **/
	public static String getProperties(String fileName, String attrKey) throws Exception{
		ResourceBundle resource = ResourceBundle.getBundle("shop");
		String attrValue = resource.getString(attrKey);
		if("".equals(attrValue))
			throw new Exception("没有找到配置文件或者配置属性");
		return attrValue;
	}

	public static Map<String, String> getProperties(String path) throws Exception{
		ResourceBundle resource = ResourceBundle.getBundle(path);
		String key ;
		String value;
		Map<String, String> head = new HashMap<String, String>();
		Enumeration<String> allKey = resource.getKeys();
		while (allKey.hasMoreElements()){
			key = allKey.nextElement();
			value = resource.getString(key);
			head.put(key,value);
		}
		return head;
	}

	public static void main(String[] args) throws Exception{
//		String s = "a s  d d    d";
//		saveData(s, "f:\\2.txt", true);
		System.out.println(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println(sdf.format(new Date()));
	}
}

package com.guanhuan.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * acfun登录
 * @author guanhuan_li@163.com
 * @create 2017-10-24 10:32
 **/
public class AcfunUtil {
    private static String userName;

    private static String password;

    private String authCodeUrl;

    public final static int LOGIN_SUCCESS = 1;

    public final static int LOGIN_FAIL = 0;

    public static String login(){
        HttpURLConnection conn = null;
        PrintWriter pw = null;
        try {
            URL url = new URL("http://www.acfun.cn//login.aspx");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //读取超时时间
            conn.setReadTimeout(8000);
            //连接超时时间
            conn.setConnectTimeout(8000);

            //设置消息头

            for(Map.Entry<String, String> entry : getProperties().entrySet()){
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }

//            conn.setRequestProperty();

            //模拟参数
            String pars = "username=744055252%40qq.com&password=";
            pw = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            pw.print(pars);
            // flush输出流的缓冲
            pw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }

        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        // 定义BufferedReader输入流来读取URL的响应
        try {
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static Map<String, String> getProperties() throws Exception{
        ResourceBundle resource = ResourceBundle.getBundle("acfunHead");
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


    public static void main(String[] args) throws Exception {
        System.out.println(login());
    }
}

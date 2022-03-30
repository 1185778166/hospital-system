package com.maxt.system.hospital.common.common.util.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author Maxt
 * @Date 2022/3/30 上午11:45
 * @Version 1.0
 * @Description HttpUtils
 */
@Slf4j
public class HttpUtils {
    static final String POST = "POST";
    static final String GET = "GET";
    //ms
    static final int CONN_TIMEOUT = 30000;
    static final int READ_TIMEOUT = 30000;

    /**
     * post方式发送
     * @param strUrl
     * @param reqData
     * @return
     */
    public static byte[] doPost(String strUrl, byte[] reqData){
        return send(strUrl, POST, reqData);
    }

    /**
     * get方式发送
     * @param strUrl
     * @return
     */
    public static byte[] doGet(String strUrl){
        return send(strUrl, GET, null);
    }

    /**
     * 发送方法
     * @param strUrl
     * @param reqMethod
     * @param reqData
     * @return
     */
    private static byte[] send(String strUrl, String reqMethod, byte[] reqData) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setUseCaches(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setConnectTimeout(CONN_TIMEOUT);
            httpConn.setReadTimeout(READ_TIMEOUT);
            httpConn.setRequestMethod(reqMethod);
            httpConn.connect();
            if (reqMethod.equalsIgnoreCase(POST)){
                OutputStream outputStream = httpConn.getOutputStream();
                outputStream.write(reqData);
                outputStream.flush();
                outputStream.close();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "utf-8"));
            String inputLine;
            StringBuffer bankXmlBuffer = new StringBuffer();
            while ((inputLine = bufferedReader.readLine()) != null){
                bankXmlBuffer.append(inputLine);
            }
            bufferedReader.close();
            httpConn.disconnect();
            return bankXmlBuffer.toString().getBytes();
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.toString(), e);
        }
        return null;
    }
}

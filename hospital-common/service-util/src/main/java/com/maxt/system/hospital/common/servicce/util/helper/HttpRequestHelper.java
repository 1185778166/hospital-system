package com.maxt.system.hospital.common.servicce.util.helper;

import com.maxt.system.hospital.common.common.util.util.MD5Utils;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author Maxt
 * @Date 2022/4/13 12:35
 * @Version 1.0
 * @Description
 */
@Slf4j
public class HttpRequestHelper {

    /**
     *
     * @param parameterMap
     * @return
     */
    public static Map<String, Object> switchMap(Map<String, String[]> parameterMap) {
        Map<String, Object> resultMap = new HashMap<>();
        for (Map.Entry<String, String[]> param : parameterMap.entrySet()) {
            resultMap.put(param.getKey(), param.getValue()[0]);
        }
        return resultMap;
    }

    /**
     * 请求数据获取签名
     * @param paramMap
     * @param signKey
     * @return
     */
    public static String getSign(Map<String, Object> paramMap, String signKey){
        if (paramMap.containsKey("sign")){
            paramMap.remove("sign");
        }
        TreeMap<String, Object> sorted = new TreeMap<>(paramMap);
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> param : sorted.entrySet()) {
            builder.append(param.getValue()).append("|");
        }
        builder.append(signKey);
        log.info("加密前："+builder.toString());
        String md5Builder = MD5Utils.encrypt(builder.toString());
        log.info("加密后："+md5Builder);
        return md5Builder;
    }

    /**
     * 签名校验
     * @param paramMap
     * @param signKey
     * @return
     */
    public static boolean isSignEquals(Map<String, Object> paramMap, String signKey){
        String sign = (String)paramMap.get("sign");
        String md5Sign = getSign(paramMap, signKey);
        if (!sign.equals(md5Sign)){
           return false;
        }
        return true;
    }

    public static long getTimeStamp(){
        //获取秒值
        //LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        //获取毫秒值
        return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public static void main(String[] args) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("d", "4");
        paramMap.put("b", "2");
        paramMap.put("c", "3");
        paramMap.put("a", "1");
        paramMap.put("timestamp", getTimeStamp());
        log.info(getSign(paramMap, "1223"));
    }
}

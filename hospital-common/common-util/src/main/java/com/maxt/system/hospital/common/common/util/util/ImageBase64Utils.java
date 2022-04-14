package com.maxt.system.hospital.common.common.util.util;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @Author Maxt
 * @Date 2022/4/14 19:04
 * @Version 1.0
 * @Description  将图片处理成Base64格式
 */
public class ImageBase64Utils {

    public static String getImageString(String imageFile){
        InputStream inputStream = null;
        try {
            byte[] data = null;
            inputStream = new FileInputStream(new File(imageFile));
            data = new byte[inputStream.available()];
            inputStream.read(data);
            return new String(Base64.encodeBase64(data));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (null != inputStream){
                try {
                    inputStream.close();
                    inputStream = null;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public static void main(String[] args) {
        String imageFile = "~/Document/";
        System.out.println(getImageString(imageFile));
    }
}

package org.leon.dev.util;

import java.io.UnsupportedEncodingException;

/**
 * Created by LeonWong on 2015/6/18.
 */
public class TestAES {
    /**
     * 此类提供AES算法的演示
     * @param args
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        String pub = "Hello World!";
        String[] pasiv = AESHelper.generatePassword();
        String password = pasiv[0];
        String iv = pasiv[1];
        try{
            String sec = AESHelper.encrypt2String(pub,password,iv);
            System.out.println("生成的密文为："+sec);
            String dec = AESHelper.decrypt2String(sec,password,iv);
            System.out.println("解密后的明文为：" + dec);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);

    }

}

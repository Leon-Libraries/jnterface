package org.leon.dev.util;

import java.io.IOException;

public class Base64Helper {

    public static String encode(byte[] byteArray) {
        sun.misc.BASE64Encoder base64Encoder = new sun.misc.BASE64Encoder();
        return base64Encoder.encode(byteArray);
    }

    public static byte[] decode(String base64EncodedString) {
        sun.misc.BASE64Decoder base64Decoder = new sun.misc.BASE64Decoder();
        try {
            return base64Decoder.decodeBuffer(base64EncodedString);
        } catch (IOException e) {
            return null;
        }
    }
    public static void main(String args[]) throws Exception {
        String ming = "南方舆情";
        String pass = "pYQsKXYRjRdcTF5J419aztyPrshboW623mAyUcr4b5A=";
        String iv ="1Df+uv2j0bCsUxBNp+c5QA==";
        String miwen = AESHelper.encryptAppStr(ming,pass,iv);
        System.out.println("密文："+miwen);
        String jiemi = AESHelper.decryptAppStr(miwen,pass,iv);
        System.out.println("解密："+jiemi);
    }
}
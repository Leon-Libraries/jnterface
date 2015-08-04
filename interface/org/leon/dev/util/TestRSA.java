package org.leon.dev.util;


import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;

/**
 * 此类提供RSA算法功能演示
 * Created by LeonWong on 2015/6/18.
 */
public class TestRSA {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        System.out.println("start");
        HashMap<String, Object> map = RSAHelper.getKeys();
        //生成公钥和私钥
        RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
        RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
        long fst = System.currentTimeMillis();
        System.out.println("生成公钥共耗时"+(fst-start)+"毫秒");
        //模
        String modulus = publicKey.getModulus().toString();
        //公钥指数
        String public_exponent = publicKey.getPublicExponent().toString();
        //私钥指数
        String private_exponent = privateKey.getPrivateExponent().toString();
        //明文
        String ming = "12345672222222222222sdsadzhogsda综合那个青蛙89dsadzhogsda综合那dsadzhogsda综合那";
        //使用模和指数生成公钥和私钥
        RSAPublicKey pubKey = RSAHelper.getPublicKey(modulus, public_exponent);
        RSAPrivateKey priKey = RSAHelper.getPrivateKey(modulus, private_exponent);
        long snd = System.currentTimeMillis();
        System.out.println("使用模和指数生成公钥共耗时："+(snd-fst)+"毫秒");
        //加密后的密文
        String mi = RSAHelper.encryptByPublicKey(ming, pubKey);
        long thd = System.currentTimeMillis();
        System.err.println(mi);
        System.out.println("加密耗时："+(thd-snd)+"毫秒");

        //解密后的明文
        ming = RSAHelper.decryptByPrivateKey(mi, priKey);
        long fth = System.currentTimeMillis();
        System.err.println(ming);
        System.out.println("解密耗时："+(fth-thd)+"毫秒");
    }
}

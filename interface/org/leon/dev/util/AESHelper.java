package org.leon.dev.util;

import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.UUID;

/**
 * AES对称加密解密类
 **/
public class AESHelper {
    /**
     * 算法/模式/填充
     **/
    private static final String CipherMode = "AES/CBC/PKCS5Padding";



    /**
     * 此方法用于自动推送接口的解密以及哈希校验
     *
     * @param base64
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decrypt2String(String base64, String password, String ivStr) throws UnsupportedEncodingException {
        //base64解码数据
        byte[] data = Base64Helper.decode(base64);
        //存放hash字节数组
        byte[] sha256 = new byte[32];
        //存放内容的字节数组
        byte[] cipherBytes = new byte[data.length - sha256.length];
        //完成数组的复制操作
        System.arraycopy(data, data.length - 32, sha256, 0, sha256.length);
        System.arraycopy(data, 0, cipherBytes, 0, cipherBytes.length);
        //解密出来的字节数组
//		byte[] Base64 = Base64Helper.decode(ivStr);
//		System.out.println(new String(Base64, "UTF-8"));
        System.out.println("password[" + password + "]大小为" + Base64Helper.decode(password).length);
        System.out.println("iv[" + ivStr + "]大小为" + Base64Helper.decode(ivStr).length);
        byte[] xml = decrypt(cipherBytes, Base64Helper.decode(password), new IvParameterSpec(Base64Helper.decode(ivStr)));
        //base64形式的hash(取得的hash)
        String sha1Base64 = Base64Helper.encode(sha256);
        //base64形式的hash(生成的hash)
        String sha1Base64Src = Base64Helper.encode(DigestUtils.sha256(cipherBytes));
        //hash校验不通过
        if (!sha1Base64.equals(sha1Base64Src)) {
            return "";
        }
        return new String(xml, "UTF-8").toString();
    }

    /**
     * 加密生成base64
     * @param data
     * @param password
     * @param ivStr
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encrypt2String(String data, String password, String ivStr) throws UnsupportedEncodingException {
        byte[] enc = AESHelper.encrypt(data.getBytes("UTF-8"), Base64Helper.decode(password), new IvParameterSpec(Base64Helper.decode(ivStr)));
        byte[] sha256 = DigestUtils.sha256(enc);
        byte[] secret = new byte[enc.length+sha256.length];
        System.arraycopy(enc, 0, secret, 0, enc.length);
        System.arraycopy(sha256, 0, secret, enc.length, sha256.length);
        return Base64Helper.encode(secret);
    }
    /**
     * 对于密码和iv一致的情形
     *
     * @param base64
     * @param password
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decrypt2String(String base64, String password) throws UnsupportedEncodingException {
        return decrypt2String(base64, password, password);
    }

    /**
     * 创建密钥
     **/
    private static SecretKeySpec createKey(String password) {
        byte[] data = null;
        if (password == null) {
            password = "";
        }
        StringBuffer sb = new StringBuffer(32);
        sb.append(password);
        while (sb.length() < 32) {
            sb.append("0");
        }
        if (sb.length() > 32) {
            sb.setLength(32);
        }

        try {
            data = sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new SecretKeySpec(data, "AES");
    }

//    /** 加密字节数据 **/
//    public static byte[] encrypt(byte[] content, String password, IvParameterSpec iv) {
//        try {
//            SecretKeySpec key = createKey(password);
//            Cipher cipher = Cipher.getInstance(CipherMode);
//            cipher.init(Cipher.ENCRYPT_MODE, key,iv);
//            byte[] result = cipher.doFinal(content);
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * 加密字节数据
     **/
    public static byte[] encrypt(byte[] content, byte[] password, IvParameterSpec iv) {
        try {
            SecretKeySpec key = new SecretKeySpec(password, "AES");
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 适用于移动端的加密
     * @param content
     * @param passwordB64
     * @return 返回base64密文
     * @throws Exception
     */
    public static String encryptAppStr(String content, String passwordB64,String ivB64) throws Exception {
        SecretKeySpec key = new SecretKeySpec(Base64Helper.decode(passwordB64), "AES");
        Cipher cipher = Cipher.getInstance(CipherMode);
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(Base64Helper.decode(ivB64)));
        byte[] result = cipher.doFinal(content.getBytes("UTF-8"));
        return Base64Helper.encode(result);
    }

    /**
     * 适用于移动端的解密
     * @param contentB64
     * @param password64
     * @return
     * @throws Exception
     */
    public static String decryptAppStr(String contentB64,String password64,String ivB64) throws Exception {
        SecretKeySpec key = new SecretKeySpec(Base64Helper.decode(password64), "AES");
        Cipher cipher = Cipher.getInstance(CipherMode);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(Base64Helper.decode(ivB64)));
        byte[] result = cipher.doFinal(Base64Helper.decode(contentB64));
        return new String(result, "UTF-8").toString();
    }

//    /** 加密(结果为16进制字符串) **/
//    public static String encrypt(String content, String password, IvParameterSpec iv) {
//        byte[] data = null;
//        try {
//            data = content.getBytes("UTF-8");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        data = encrypt(data, password,iv);
//        String result = byte2hex(data);
//        return result;
//    }

    /**
     * 解密字节数组
     **/
    public static byte[] decrypt(byte[] content, String password, IvParameterSpec iv) {
        try {
            SecretKeySpec key = createKey(password);
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密字节数组
     **/
    public static byte[] decrypt(byte[] content, byte[] password, IvParameterSpec iv) {
        try {
            //SecretKeySpec key = createKey(password);
            SecretKeySpec key = new SecretKeySpec(password, "AES");
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密16进制的字符串为字符串
     **/
    public static String decrypt(String content, String password, IvParameterSpec iv) {
        byte[] data = null;
        try {
            data = hex2byte(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = decrypt(data, password, iv);
        if (data == null)
            return null;
        String result = null;
        try {
            result = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 字节数组转成16进制字符串
     **/
    public static String byte2hex(byte[] b) { // 一个字节的数，
        StringBuffer sb = new StringBuffer(b.length * 2);
        String tmp = "";
        for (int n = 0; n < b.length; n++) {
            // 整数转成十六进制表示
            tmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }
        return sb.toString().toUpperCase(); // 转成大写
    }

    /**
     * 将hex字符串转换成字节数组
     **/
    private static byte[] hex2byte(String inputString) {
        if (inputString == null || inputString.length() < 2) {
            return new byte[0];
        }
        inputString = inputString.toLowerCase();
        int l = inputString.length() / 2;
        byte[] result = new byte[l];
        for (int i = 0; i < l; ++i) {
            String tmp = inputString.substring(2 * i, 2 * i + 2);
            result[i] = (byte) (Integer.parseInt(tmp, 16) & 0xFF);
        }
        return result;
    }

    /**
     * 此方法提供给客户端密钥自动随机生成，数组中第一个是password，第二个则是iv
     * @return
     */
    public static String[] generatePassword(){
        String[] pasiv = new String[2];
        UUID uuid = UUID.randomUUID();
        byte[] passBytes = DigestUtils.sha256(uuid.toString()+(new Random().nextInt())+"password");
        byte[] ivsha256 = DigestUtils.sha256(uuid.toString()+(new Random().nextInt())+"iv");
        byte[] ivBytes = new byte[16];
        System.arraycopy(ivsha256, 0, ivBytes, 0, ivBytes.length);//哈希后的字节数组取前16字节作为iv
        pasiv[0]=Base64Helper.encode(passBytes);
        pasiv[1]=Base64Helper.encode(ivBytes);
        return pasiv;
    }

    public static void main(String args[]){
        System.out.print("生成密钥"+generatePassword()[0]);
        System.out.print("生成密钥" + generatePassword()[1]);
    }
}

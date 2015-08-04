package org.leon.dev.webservice;

import org.leon.dev.controller.ApplicationController;
import org.leon.dev.controller.writer.ResponseWriter;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.leon.dev.pojo.GetSessionid_POJO;
import org.leon.dev.util.Base64Helper;
import org.leon.dev.util.RSAHelper;
import org.leon.dev.util.StringUtil;
import org.leon.dev.util.redis.RedisClient;
import org.leon.dev.util.redis.SerializeUtil;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by LeonWong on 2015/6/19.
 * 涉及到会话的持久化都打算使用redis为存储介质
 */
public class ApplicationService implements Serializable {

    public static Logger log = Logger.getLogger(ApplicationService.class);
    private static String defaultType = "json";
    /**
     * 处理请求的核心方法，参数需要加密
     * @param protocal 接口方法协议
     * @param sessionid 需要客户端存储的会话ID
     * @return
     */
    public String execute(String protocal,String sessionid,String dataType){
        String ipfrom = getClientIp();
        log.info("访问方法来IP地址来自" + ipfrom);
        if (!isIPaddrValid(sessionid,ipfrom)){
            return new ResponseWriter(dataType).writeStatus(ResponseWriter.IP_NOT_BIND);
        }
        return new ApplicationController().parseAndRun( protocal, sessionid,dataType);
    }
    /**
     * 客户端第一次请求需调用此方法
     * @param dataType
     * @return 这里返回一个公钥PK和一个sessionid，其中sessionid由服务器时间生成uuid的哈希串，公钥pk字符串实际上是RSAPublicKey对象序列化后取得的Base64码
     */
    public String getSessionid(String dataType){
        String sessionid = DigestUtils.sha256Hex(UUID.randomUUID().toString());
        GetSessionid_POJO gspo = new GetSessionid_POJO();
        String resp = "";
        try{
            HashMap<String, Object> map = RSAHelper.getKeys();
            //生成公钥和私钥Base64
            RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
            RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
            String pubkeyB64 = Base64Helper.encode(SerializeUtil.serialize(publicKey));
            String priKeyB64 = Base64Helper.encode(SerializeUtil.serialize(privateKey));
            //将公钥私钥和sessionid，存入redis，初步考虑存放hashmap
            Jedis jedis = RedisClient.getConnection();
            HashMap<String,String> user_session = new HashMap<String,String>();
            user_session.put("pk",pubkeyB64);
            user_session.put("sk",priKeyB64);
            user_session.put("crtime", String.valueOf(System.currentTimeMillis()));
            user_session.put("ipaddr",getClientIp());
            jedis.hmset(sessionid,user_session);
            RedisClient.close(jedis);
            //组装返回数据
            gspo.setP_key(pubkeyB64);
            gspo.setSession_id(sessionid);
            //返回数据
            resp = new ResponseWriter(dataType).writeObject(gspo,ResponseWriter.OK);
        }catch(NoSuchAlgorithmException e){
            log.error("找不到此类算法，具体详见日志");
            //返回数据
            resp = new ResponseWriter(dataType).writeObject(gspo,ResponseWriter.ERR);
            e.printStackTrace();
        }catch(Exception e){
            log.error("程序内部异常，详见日志");
            //返回数据
            resp = new ResponseWriter(dataType).writeObject(gspo,ResponseWriter.ERR);
            e.printStackTrace();
        }finally {
            return resp;
        }
    }

    /**
     * 将AES密钥通过公钥加密的形式发送过来，本地做存储，因为此后所有请求数据都是以AES加密的形式传输
     * @param codeStr
     * @return
     */
    public String sendPass(String codeStr,String sessionid,String dataType){

        //校验IP
        if (!isIPaddrValid(sessionid,getClientIp())){
            return new ResponseWriter(dataType).writeStatus(ResponseWriter.IP_NOT_BIND);
        }

        //根据sessionid找到对应的会话私钥
        Jedis jedis = RedisClient.getConnection();
        String sk = jedis.hmget(sessionid,"sk").get(0);
        if(sk == null){
            RedisClient.close(jedis);
            return new ResponseWriter(dataType).writeStatus(ResponseWriter.USER_NOT_FOUND);
        }
        /**
         * 利用私钥对密文解密，需要拿到两个数据，一个是AES的Password，另一个是IV，数据格式为XML，如下：
         * <root>
         *    <password>nIerxTbs+Mma2lzrlWtF5wnKlN6lJZeVVntODqA7NGI=<password/>
         *    <iv>H/mOek+44geSdeacTz7frA==<iv/>
         * <root/>
         * 此密钥需要客户端生成，生成密钥的方式参照AESHelper类
         */
        String xml_AESSec = "";
        try{
            xml_AESSec = RSAHelper.decryptByPrivateKey(codeStr,(RSAPrivateKey)SerializeUtil.unserialize(Base64Helper.decode(sk)));
        }catch(Exception e){
            log.error("已拿到私钥，但解密过程出错，详见日志。"+e.getMessage());
            e.printStackTrace();
            RedisClient.close(jedis);
            return new ResponseWriter(dataType).writeStatus(ResponseWriter.ERR);
        }

        String password = "";
        String iv = "";
        try{
            Document doc = DocumentHelper.parseText(xml_AESSec);
            Element root = doc.getRootElement();
            password = root.elementText("password");
            iv = root.elementText("iv");
        }catch(DocumentException e){
            log.error("传输密钥过程协议出错，详见日志。"+e.getMessage());
            e.printStackTrace();
            RedisClient.close(jedis);
            return new ResponseWriter(dataType).writeStatus(ResponseWriter.ERR);
        }

        //存入redis会话中
        try {
            if(StringUtil.isEmpty(password) || StringUtil.isEmpty(iv)){
                return new ResponseWriter(dataType).writeStatus(ResponseWriter.AES_NOT_FOUND);
            }
            HashMap<String, String> aes_hm = new HashMap<String, String>();
            aes_hm.put("aes_password", password);
            aes_hm.put("aes_iv", iv);
            jedis.hmset(sessionid, aes_hm);
        }catch(Exception e){
            log.error("存入redis出错。"+e.getMessage());
            e.printStackTrace();
            RedisClient.close(jedis);
            return new ResponseWriter(dataType).writeStatus(ResponseWriter.ERR);
        }
        RedisClient.close(jedis);
        return new ResponseWriter(dataType).writeStatus(ResponseWriter.OK);
    }
    /**
     * 返回默认数据交互类型
     * @return
     */
    public String getDefaultType() {
        return defaultType;
    }

    /**
     * 获取客户端IP地址
     * @return
     */
    private String getClientIp(){
        MessageContext mc  =  MessageContext.getCurrentContext();
        HttpServletRequest request  =  (HttpServletRequest)  mc.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
        return request.getRemoteAddr();
    }

    /**
     * 校验IP是否有效
     * @param sessionid
     * @param ipfrom
     * @return
     */
    private boolean isIPaddrValid(String sessionid , String ipfrom){
        //校验IP地址
        Jedis jedis = RedisClient.getConnection();
        String ipaddr = jedis.hmget(sessionid,"ipaddr").get(0);
        RedisClient.close(jedis);
        if (StringUtil.isEmpty(ipaddr) || StringUtil.isEmpty(ipfrom) || !ipfrom.equals(ipaddr)){
            return false;
        }
        return true;
    }

}

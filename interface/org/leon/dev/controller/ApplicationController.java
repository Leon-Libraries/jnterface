package org.leon.dev.controller;




import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.leon.dev.controller.writer.ResponseWriter;
import org.leon.dev.function.exceptions.FunctionNotFoundException;
import org.leon.dev.function.exceptions.ParamRequiredException;
import org.leon.dev.function.handler.MethodHandler;
import org.leon.dev.function.inter.AutowiredFromSpring;
import org.leon.dev.function.inter.Method;
import org.leon.dev.function.inter.Param;
import org.leon.dev.function.register.FuncRegistry;
import org.leon.dev.util.AESHelper;
import org.leon.dev.util.StringUtil;
import org.leon.dev.util.redis.RedisClient;
import org.leon.dev.util.springhelper.SpringContextHelper;
import redis.clients.jedis.Jedis;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;

/**
 * WebService方法主要的控制器
 * Created by LeonWong on 15/6/21.
 */

public class ApplicationController {

    static Logger log = Logger.getLogger(ApplicationController.class);

    private String sessionid;


    /**
     * 本类主体方法
     * @param protocal
     * @param sessionid
     * @return
     */
    public String parseAndRun(String protocal,String sessionid,String dataType){

        String resp = "";//存储返回内容


        if(isSessionValid(sessionid)!= ResponseWriter.OK){
            return new ResponseWriter(dataType).writeStatus(isSessionValid(sessionid));
        }

        try{
            String xml = this.decrpytProtocal(protocal);
            resp = this.getDefinationFunctionObjectAndRun(xml,dataType);
        }catch (UnsupportedEncodingException e){
            log.error("编码出现问题，不太科学");
            e.printStackTrace();
            return new ResponseWriter(dataType).writeStatus(ResponseWriter.ERR);
        }catch (DocumentException e){
            log.error("传入协议解析不正确或协议格式不规范，详见日志");
            e.printStackTrace();
            return new ResponseWriter(dataType).writeStatus(ResponseWriter.PROTOCAL_ERR);
        }catch (FunctionNotFoundException e){
            log.error("所请求的方法未注册");
            e.printStackTrace();
            return new ResponseWriter(dataType).writeStatus(ResponseWriter.FUNC_NOT_FOUND);
        }catch (NullPointerException e){
            log.error("协议期望的字段可能不存在，空指针异常，具体详见日志");
            e.printStackTrace();
            return new ResponseWriter(dataType).writeStatus(ResponseWriter.ERR);
        }catch (ParamRequiredException e){
            log.error("必填参数为空");
            e.printStackTrace();
            return new ResponseWriter(dataType).writeStatus(ResponseWriter.PARAM_REQ_LOST);
        }catch (InstantiationException e){
            log.error("方法功能类实例化出错，详见日志");
            e.printStackTrace();
            return new ResponseWriter(dataType).writeStatus(ResponseWriter.ERR);
        }catch (Exception e){
            log.error("oops,出错了！详见日志:"+e.getMessage());
            e.printStackTrace();
            return new ResponseWriter(dataType).writeStatus(ResponseWriter.ERR);
        }

        return resp;
    }

    /**
     * 会话检验，理论上应该是在解析协议的前面进行会话校验
     * @param sessionid
     * @return
     */
    private int isSessionValid(String sessionid){
        Jedis jedis = RedisClient.getConnection();
        if(jedis.hmget(sessionid,"sk").get(0)== null){
            RedisClient.close(jedis);
            return ResponseWriter.USER_NOT_FOUND;
        }
        if(jedis.hmget(sessionid,"aes_password").get(0)==null
                || jedis.hmget(sessionid,"aes_iv").get(0)==null ){
            RedisClient.close(jedis);
            return ResponseWriter.AES_NOT_FOUND;
        }
        RedisClient.close(jedis);
        this.setSessionid(sessionid);
        return ResponseWriter.OK;
    }

    /**
     * 由于sessionid先前已经校验过，因此可以正常使用，不用再做校验
     * @param codeStr
     * @return 返回协议明文
     */
    private String decrpytProtocal(String codeStr)throws UnsupportedEncodingException {
        Jedis jedis = RedisClient.getConnection();
        String password = jedis.hmget(getSessionid(),"aes_password").get(0);
        String iv = jedis.hmget(getSessionid(), "aes_iv").get(0);
        String xml = AESHelper.decrypt2String(codeStr, password, iv);
        RedisClient.close(jedis);
        return xml;
    }

    /**
     * 讲解洗出来的协议明文生成功能方法对象，并设置参数
     * @param xml_protocal
     * @return
     */
    private String getDefinationFunctionObjectAndRun(String xml_protocal,String dataType) throws InstantiationException,ParamRequiredException,IllegalAccessException,DocumentException,NullPointerException,FunctionNotFoundException{

        Document doc = DocumentHelper.parseText(xml_protocal);
        Element root = doc.getRootElement();
        String m_Name = root.element("head").element("method").getText();
        log.info("需要调用的方法为" + m_Name);
        Class methodClass = FuncRegistry.getMethodObject(m_Name);
        Object object = methodClass.newInstance();//创建新实例
        if(!(object instanceof Method)){
            log.error("注册对象不是指定的Method类型");
            throw new FunctionNotFoundException("该注册对象不是指定的Method类型");
        }
        //设置dataType
        ((Method) object).setDataType(dataType);
        //组装参数到功能方法类的声明变量中
        bindParams2Object(object, root.element("params"));
        //动态代理绑定子类执行方法
        MethodHandler proxy = new MethodHandler();//实例化动态代理类
        Method method = (Method) proxy.bind(object);//动态绑定
        //执行方法
        long start = System.currentTimeMillis();
        String resp = method.execute();
        long end = System.currentTimeMillis();
        log.info(m_Name+"方法共执行了" + (end - start) + "毫秒");
        log.info("返回的json:\n"+resp);
        return resp;


    }

    /**
     * 此方法作用于功能方法类组装参数以及注入数据库操作类
     * @param object
     * @param params
     *
     */
    private void bindParams2Object(Object object,Element params)throws ParamRequiredException,IllegalAccessException {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field:fields){
            handleFields(field,object,params);
        }
        log.info("参数组装完毕");
    }

    /**
     * 对现有字段注解进行处理
     * @param field
     * @param params
     * @throws ParamRequiredException
     */
    private void handleFields(Field field , Object object,Element params) throws ParamRequiredException,IllegalAccessException {
        //可访问必须开启
        field.setAccessible(true);
        String fieldName = field.getName();

        //注解校验，没有Param注解的不做后续处理，直接跳过
        Param paramAnno = field.getAnnotation(Param.class);
        AutowiredFromSpring autowired = field.getAnnotation(AutowiredFromSpring.class);

        if (paramAnno != null){

            String fieldValue = params.elementText(fieldName.toLowerCase())!=null?
                    params.elementText(fieldName.toLowerCase()):"";//从xml读取参数值
            if(StringUtil.isEmpty(fieldValue) && paramAnno.isRequired()){
                //校验必填参数，注解表示默认必填
                throw new ParamRequiredException("必填参数为空");
            }

            if(StringUtil.isEmpty(fieldValue)){
                //非必填参数默认值
                fieldValue = paramAnno.defaultValue();
            }

            bindFields(object,field,fieldValue);
        }else if (autowired != null){
            /**
             * 注意：要实现Spring的集成注入，必须将fieldName和spring在bean中的name保持一致
             */
            field.set(object, SpringContextHelper.getContext().getBean(fieldName));
        }



    }

    /**
     * 绑定参数，需要识别类型，如果类型不支持则设为空
     * @param object
     * @param field
     * @param fieldValue
     *
     */
    private void bindFields(Object object,Field field,String fieldValue)throws IllegalAccessException {
        /**
         * Field类 api文档中描述
         * public void set(Object obj,Object value)throws IllegalArgumentException,IllegalAccessException
         * obj - the object whose field should be modified
         * value - the new value for the field of obj being modified
         */
        try{
            System.out.print(field.getType().getSimpleName().toUpperCase());
            switch (field.getType().getSimpleName().toUpperCase()){
                case "LONG" :
                    field.set(object, Long.valueOf(fieldValue.equals("") ? "0" : fieldValue));break;
                case "INTEGER":
                    field.set(object, Integer.valueOf(fieldValue));break;
                case "STRING":
                    field.set(object,fieldValue);break;
                case "BOOLEAN":
                    field.set(object, Boolean.valueOf(fieldValue));break;
                default:
                    log.warn("配置的功能方法类中参数类型不支持");break;
            }
        }catch(Exception e){
            e.printStackTrace();
            log.error("配置的功能方法类中参数类型不支持");
        }

    }

    private String getSessionid() {
        return sessionid;
    }

    private void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }


}

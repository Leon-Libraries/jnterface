package org.leon.dev.controller.writer;


import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.leon.dev.util.xmljson.XmlJson;

import java.util.Iterator;
import java.util.List;

/**
 * 默认以json数据返回的形式组装，如果dataType为xml，则json转xml
 * Created by LeonWong on 15/6/21.
 */
public class ResponseWriter {

    public final static int OK=200;//成功
    public final static int USER_NOT_FOUND = 400;//会话不存在
    public final static int ERR=500;//程序内部故障
    public final static int FUNC_NOT_FOUND = 404;//方法不存在
    public final static int AES_NOT_FOUND = 401;//会话存在，AES密钥未完成交换
    public final static int PROTOCAL_ERR = 501;//协议异常
    public final static int PARAM_REQ_LOST = 502;//必填参数为空
    public final static int USER_PASS_OR_ACC_ERR = 402;//用户名或密码错误
    public final static int ACC_OVERDATE = 403;//账户过期
    public final static int ACC_INVALID = 405;//账户停用或者被删除
    public final static int DATA_NOT_FOUND = 406;//数据未被找到
    public final static int PARAMS_FORMAT_ERR =503;//参数格式有误
    public final static int OPER_REPEAT =504;//重复操作（重复收藏）
    public final static int IP_NOT_BIND =505;//IP未与会话绑定

    public static Logger log = Logger.getLogger(ResponseWriter.class);
    private String dataType = "json";//返回数据格式，json or xml，不允许有其他

    public ResponseWriter(String dataType){
        this.setDataType(dataType);
    }
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        if(dataType==null || !dataType.equalsIgnoreCase("xml")){
            //将默认为json
        }else {
            this.dataType = dataType;
        }
    }

    /**
     * 直接写返回状态
     * @param status
     * @return
     */
    public String writeStatus(int status){
        String resp = "";
        if(dataType.equalsIgnoreCase("xml")){
            Document document = DocumentHelper.createDocument(DocumentHelper.createElement("root"));
            Element stat = DocumentHelper.createElement("status");
            stat.setText(String.valueOf(status));
            document.getRootElement().add(stat);
            resp = document.asXML();
        }else{
            JSONObject object = new JSONObject();
            object.put("status",status);
            resp = object.toString();
        }
        return resp;
    }
    /**
     * com.trs.nfyq.appdev.pojo包中都是接口功能方法返回数据所需要的封装对象，此类将封装对象转换成对应的数据类型
     * @param obj
     * @return
     */
    public String writeObject(Object obj,int status){
        String resp = "";
        JSONObject object = new JSONObject();
        Document document = DocumentHelper.createDocument(DocumentHelper.createElement("root"));
        //首先放入状态参数
        object.put("status",status);
        Element stat = DocumentHelper.createElement("status");
        stat.setText(String.valueOf(status));
        document.getRootElement().add(stat);

        if(dataType.equalsIgnoreCase("xml")){
            //解析xml，并附上状态码
            resp = XmlJson.object2XML(obj);
            try{
                Document doc = DocumentHelper.parseText(resp);
                document.getRootElement().add(doc.getRootElement());
                resp = document.asXML();
            }catch (DocumentException e){
                log.error("XMLObject数据解析出错，详见日志");
                e.printStackTrace();
                document.getRootElement().element("status").setText(String.valueOf(ERR));
                resp = document.asXML();
            }
        }else{
            //解析JSON，附上状态码
            resp = XmlJson.object2JSON(obj);
            try{
                JSONObject body = JSONObject.fromObject(resp);//数据主体
                Iterator<String> iter = body.keys();
                while(iter.hasNext()){
                    String key = iter.next();
                    object.put(key,body.get(key));
                }
                resp = object.toString();
            }catch(JSONException e){
                log.error("JSONObject数据解析出错，详见日志");
                e.printStackTrace();
                object.put("status",ERR);
                resp = object.toString();
            }
        }
        return resp;
    }

    /**
     * com.trs.nfyq.appdev.pojo包中都是接口功能方法返回数据所需要的封装对象，此类将封装对象的列表转换成对应的数据类型
     * @param list
     * @return
     */
    public String writeList(List list,int status){
        String resp_list = "";
        JSONObject list_object = new JSONObject();
        Document list_document = DocumentHelper.createDocument(DocumentHelper.createElement("root"));
        //首先放入状态参数
        list_object.put("status",status);
        Element stat = DocumentHelper.createElement("status");
        stat.setText(String.valueOf(status));
        list_document.getRootElement().add(stat);
        if(dataType.equalsIgnoreCase("xml")){
            //解析xml列表，附上状态码
            resp_list = XmlJson.list2XML(list);
            try{
                Document doc = DocumentHelper.parseText(resp_list);
                list_document.getRootElement().add(doc.getRootElement());
                resp_list = list_document.asXML();
            }catch (DocumentException e){
                log.error("XMLObject数据解析出错，详见日志");
                e.printStackTrace();
                list_document.getRootElement().element("status").setText(String.valueOf(ResponseWriter.ERR));
                resp_list = list_document.asXML();
            }
        }else{
            //解析JSON列表，附上状态码
            resp_list = XmlJson.list2JSON(list);
            try{
                JSONObject body = JSONObject.fromObject(resp_list);
                Iterator<String> iter = body.keys();
                while(iter.hasNext()){
                    String key = iter.next();
                    list_object.put(key,body.get(key));
                }
                resp_list = list_object.toString();
            }catch(JSONException e){
                log.error("JSONArray数据解析出错，详见日志");
                e.printStackTrace();
                list_object.put("status",ResponseWriter.ERR);
                resp_list = list_object.toString();
            }
        }
        return resp_list;
    }
}

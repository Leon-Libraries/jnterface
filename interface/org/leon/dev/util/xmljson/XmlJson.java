package org.leon.dev.util.xmljson;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import java.util.List;

/**
 * Created by LeonWong on 15/6/21.
 */
public class XmlJson {

    /**
     * 调用 XMLSerializer将XML转成JSON
     * @param xml
     * @return
     */
    public static String xml2JSON(String xml){
        return new XMLSerializer().read(xml).toString();
    }

    /**
     * 调用 XMLSerializer将JSON转化为XML
     * @param json
     * @return
     */
    public static String json2XML(String json){
        JSONObject jobj = JSONObject.fromObject(json);
        String xml =  new XMLSerializer().write(jobj);
        return xml;
    }

    /**
     * 调用xstream组件，将对象转化为xml
     * @param object
     * @return
     */
    public static String object2XML(Object object){
        XStream xStream = new XStream(new Dom4JDriver());
        xStream.alias("result",object.getClass());
        return xStream.toXML(object);
    }

    /**
     * 调用xstream组件，将列表转化为xml
     * @param array
     * @return
     */
    public static String list2XML(List array){
        XStream xStream = new XStream(new Dom4JDriver());
//        if(array.size() <= 0){
//            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
//        }
        xStream.alias("result",List.class);
//        xStream.alias(array.get(0).getClass().getSimpleName().toLowerCase(),array.get(0).getClass());
        return xStream.toXML(array);
    }

    /**
     * 调用xstream组件，将对象转化为JSON
     * @param object
     * @return
     */
    public static String object2JSON(Object object){
        XStream xStream = new XStream(new JsonHierarchicalStreamDriver());
        xStream.alias("result",object.getClass());
        return xStream.toXML(object);
    }

    /**
     * 调用xstream工具将列表转化为JSON
     * @param array
     * @return
     */
    public static String list2JSON(List array){
        XStream xStream = new XStream(new JsonHierarchicalStreamDriver());
//        if(array.size()<=0){
//            return "{}";
//        }
        xStream.alias("result",List.class);
//        xStream.alias(array.get(0).getClass().getSimpleName().toLowerCase(),array.get(0).getClass());
        return xStream.toXML(array);
    }
}

package org.leon.dev.function.register;

import com.trs.nfyq.appdev.function.exceptions.FunctionNotFoundException;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by LeonWong on 15/6/24.
 * 此类用于xml的解析和功能方法的注册器
 */
public class FuncRegistry {

    private static HashMap<String,Class> methods = new HashMap<>();
    public static Logger log = Logger.getLogger(FuncRegistry.class);
    /**
     * 此方法用于func-servlet.xml解析
     * @param xml
     */
    public void parseDefinitionMethods(String xml) throws DocumentException,NullPointerException,ClassNotFoundException {
        try{
            Document doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();
            Iterator<Element> iter = root.elementIterator("func");
            if(!iter.hasNext()){log.warn("注册器未注册任何方法");}
            while(iter.hasNext()){
                Element func = iter.next();
                this.registerDefinitionMethods(func.attribute("name").getValue(),func.attribute("class").getValue());
            }
        }catch (DocumentException e){
            log.error("注册器解析配置文件失败，请检查func-servlet.xml文件");
            e.printStackTrace();
            throw e;
        }catch (NullPointerException e){
            log.error("解析配置文件，空指针异常");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 此方法用于把配置中的类在内存中生成实例化
     * @param name
     * @param className
     */
    public void registerDefinitionMethods(String name,String className)throws ClassNotFoundException {
        try{
            Class m_Class = Class.forName(className);
            methods.put(name,m_Class);
        }catch(ClassNotFoundException e){
            log.error("反射机制实例化失败，"+className+"类找不到");
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * 取出已经注册的方法类
     * @param m_name
     * @return
     */
    public static Class getMethodObject(String m_name) throws FunctionNotFoundException{
        if(!methods.containsKey(m_name)){
            throw new FunctionNotFoundException("方法未注册");
        }
        return methods.get(m_name);
    }
}

package org.leon.dev.function.listener;

import com.trs.nfyq.appdev.function.register.FuncRegistry;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by LeonWong on 15/6/24.
 * 此类用于加载func-servlet.xml中功能方法类
 */
public class FunctionClassLoader implements ServletContextListener {

    public static Logger logger = Logger.getLogger(FunctionClassLoader.class);
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("[WebService功能方法正在加载]"+FunctionClassLoader.class.getName());
        FuncRegistry fr = new FuncRegistry();
        try{
            FileInputStream fis = new FileInputStream(
                    new File(FunctionClassLoader.class.getClassLoader().getResource("/").getPath()+ "func-servlet.xml"));
            int temp;
            String xml = "";
            while(( temp = fis.read()) != -1){
                xml += (char)temp;
            }
            fr.parseDefinitionMethods(xml);
            logger.info("[WebService功能配置XML加载成功]");
        }catch(IOException e){
            logger.error("[WebService功能配置XML加载失败，请核对路径]");
            e.printStackTrace();
        }catch (Exception e){
            logger.error("[WebService初始化功能配置文件失败，详见日志]");
            e.printStackTrace();
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}

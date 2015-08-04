package org.leon.dev.util.springhelper;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

@Repository
public class SpringContextHelper implements ApplicationContextAware {

    protected static ApplicationContext context;
   // private Logger log = Logger.getLogger(this.getClass());
    
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    	//log.info("SPRING INJECT APPLICATIONCONTTEXT,IS IT NULL:");
    	//log.info(applicationContext == null);
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }

}

package org.leon.dev.function.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 引入动态代理类
 * Created by LeonWong on 15/6/25.
 */
public class MethodHandler implements InvocationHandler {

    private Object tar;

    public Object bind(Object object){
        this.tar = object;
        return Proxy.newProxyInstance(tar.getClass().getClassLoader(), tar.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;

        result = method.invoke(tar,args);

        return result;
    }
}

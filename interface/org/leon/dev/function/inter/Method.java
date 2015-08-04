package org.leon.dev.function.inter;

/**
 * 所有接口的功能方法必须实现该interface，否则无法被注册成功能
 *
 * Created by LeonWong on 15/6/23.
 *
 * warning：所有参数不能使用基本类型定义，例如int要替换成Integer
 */
public interface Method {
    public String execute();
    public void setDataType(String dataType);
    public String getDataType();
}

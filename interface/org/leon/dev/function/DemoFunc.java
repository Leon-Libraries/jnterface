package org.leon.dev.function;

import org.leon.dev.controller.writer.ResponseWriter;
import org.leon.dev.function.inter.Method;
import org.leon.dev.function.inter.Param;
import org.leon.dev.pojo.Demo_POJO;

/**
 * Created by LeonWong on 15/8/4.
 * 演示方法
 */
public class DemoFunc implements Method{

    private String dataType;
    @Param
    private String name;
    @Param
    private Integer age;
    @Param(isRequired = false,defaultValue = "MALE")
    private String gender;

    @Override
    public String execute() {
        Demo_POJO demo_pojo = new Demo_POJO();
        demo_pojo.setAge(age);
        demo_pojo.setGender(gender);
        demo_pojo.setName(name);
        return new ResponseWriter(dataType).writeObject(demo_pojo,ResponseWriter.OK);
    }

    @Override
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public String getDataType() {
        return dataType;
    }
}

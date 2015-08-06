package org.leon.dev.pojo;

import org.leon.dev.pojo.inter.Object_POJO;

/**
 * Created by LeonWong on 15/8/4.
 */
public class Demo_POJO implements Object_POJO{
    private String name;
    private int age;
    private String gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

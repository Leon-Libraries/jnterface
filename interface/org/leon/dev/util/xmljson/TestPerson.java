package org.leon.dev.util.xmljson;

import java.util.List;

/**
 * Created by zealer on 15/6/21.
 */
public class TestPerson{
    private String name;
    private int age;
    private String gender;
    private List talent;

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

    public List getTalent() {
        return talent;
    }

    public void setTalent(List talent) {
        this.talent = talent;
    }
}

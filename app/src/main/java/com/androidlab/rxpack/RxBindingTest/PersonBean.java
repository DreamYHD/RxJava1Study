package com.androidlab.rxpack.RxBindingTest;

/**
 * Created by Haodong on 2017/3/13.
 */

public class PersonBean {
    private  String name;
    private int age;

    public PersonBean(String name, int age) {
        this.name = name;
        this.age = age;
    }

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
}

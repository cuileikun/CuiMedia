package com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.sp;

/**
 * Created by mbcloud-cuilk on 2018/5/9.
 */

public class Userbean {
    private String name;
    private int age;

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

    @Override
    public String toString() {
        return "Userbean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

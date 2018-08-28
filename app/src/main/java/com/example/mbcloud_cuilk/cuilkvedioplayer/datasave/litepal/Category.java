package com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.litepal;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by mbcloud-cuilk on 2018/5/16.
 */

public class Category extends DataSupport implements Serializable{
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

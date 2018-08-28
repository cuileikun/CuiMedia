package com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.litepal;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by mbcloud-cuilk on 2018/5/16.
 */

public class Introduction extends DataSupport implements Serializable{
    private int id;

    private String guide;

    private String digest;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    @Override
    public String toString() {
        return "Introduction{" +
                "id=" + id +
                ", guide='" + guide + '\'' +
                ", digest='" + digest + '\'' +
                '}';
    }
}

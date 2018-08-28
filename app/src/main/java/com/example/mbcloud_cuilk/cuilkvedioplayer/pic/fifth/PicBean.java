package com.example.mbcloud_cuilk.cuilkvedioplayer.pic.fifth;

import java.io.Serializable;

/**
 * Created by mbcloud-cuilk on 2018/5/11.
 * 图片bean
 */

public class PicBean implements Serializable {
    private String name;//图片名字
    private long time;//图片拍摄时间
    private String path;//图片路径

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}

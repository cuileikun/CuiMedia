package com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.vediorecord.entity;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/7/13 0013.
 */

public class VideoInfo {

    private String filePath;
    private String mimeType;
    private Bitmap b;
    private String title;
    private String time;
    private String size;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Bitmap getB() {
        return b;
    }

    public void setB(Bitmap b) {
        this.b = b;
    }

}

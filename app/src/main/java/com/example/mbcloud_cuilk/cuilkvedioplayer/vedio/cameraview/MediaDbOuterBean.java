package com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.cameraview;

import com.example.mbcloud_cuilk.cuilkvedioplayer.pic.preview.MediaDetailBean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbcloud-cuilk on 2018/5/18.
 */

public class MediaDbOuterBean extends DataSupport implements Serializable{
    public String time = "";
    public String timestamp;
    public List<MediaDbBean> mediaDbBeanList = new ArrayList<>();

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<MediaDbBean> getMediaDbBeanList() {
        return mediaDbBeanList;
    }

    public void setMediaDbBeanList(List<MediaDbBean> mediaDbBeanList) {
        this.mediaDbBeanList = mediaDbBeanList;
    }
}

package com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.cameraview;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by mbcloud-cuilk on 2018/5/18.
 */

public class MediaDbBean extends DataSupport implements Serializable {
    public Long id;
    public String postion = "";
    public String time = "";
    public String type = "";
    public String name = "";
    public String fileUrl = "";
    public String userId = "";
    public String latitude = "";
    public String longitude = "";
    public String timestamp = "";
    public String vedioPath = "";
    public MediaDbOuterBean mediaDbOuterBean;

    public MediaDbOuterBean getMediaDbOuterBean() {
        return mediaDbOuterBean;
    }

    public void setMediaDbOuterBean(MediaDbOuterBean mediaDbOuterBean) {
        this.mediaDbOuterBean = mediaDbOuterBean;
    }

    public String getVedioPath() {
        return vedioPath;
    }

    public void setVedioPath(String vedioPath) {
        this.vedioPath = vedioPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostion() {
        return postion;
    }

    public void setPostion(String postion) {
        this.postion = postion;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "MediaDbBean{" +
                "id=" + id +
                ", postion='" + postion + '\'' +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", userId='" + userId + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", timestamp=" + timestamp +
                ", vedioPath='" + vedioPath + '\'' +
                ", mediaDbOuterBean=" + mediaDbOuterBean +
                '}';
    }
}

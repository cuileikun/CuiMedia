package com.example.mbcloud_cuilk.cuilkvedioplayer.pic.preview;

import java.io.Serializable;

/**
 * Created by mbcloud-cuilk on 2018/5/9.
 * 已上传条目详情bean类
 */

public class UpLoadedItemBean implements Serializable {

    /**
     * FLE_TYP : image
     * THUMB_URL : http://***
     * URL : http://***
     */

    private String FLE_TYP="";//类型：String  必有字段  备注：文件类型： 图片image 视频video 音频 audio
    private String THUMB_URL="";//类型：String  必有字段  备注：缩略图地址, 语音该字段为空
    private String URL=""; //类型：String  必有字段  备注：原图(视频)地址

    public String getFLE_TYP() {
        return FLE_TYP;
    }

    public void setFLE_TYP(String FLE_TYP) {
        this.FLE_TYP = FLE_TYP;
    }

    public String getTHUMB_URL() {
        return THUMB_URL;
    }

    public void setTHUMB_URL(String THUMB_URL) {
        this.THUMB_URL = THUMB_URL;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}

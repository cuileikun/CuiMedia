package com.example.mbcloud_cuilk.cuilkvedioplayer.pic.preview;

import java.io.Serializable;

/**
 * Created by mbcloud-cuilk on 2018/5/9.
 *
 */

public class MediaDetailBean implements Serializable {
    public String type = "";//pic,video,audio
    public String name = "";
    public long timestamp;
    public String fileUrl="";//图片的本地地址
    public String videoUrl;
    public String time="";
    public String latitude="";
    public String longitude="";
    public String postion="";
    public boolean isDelete=false;
    public long id;
}

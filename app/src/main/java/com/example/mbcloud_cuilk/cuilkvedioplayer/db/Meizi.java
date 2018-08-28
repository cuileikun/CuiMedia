package com.example.mbcloud_cuilk.cuilkvedioplayer.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Entity;

/**
 * Created by mbcloud-cuilk on 2018/5/15.
 */
@Entity
public class Meizi {
    @Id(autoincrement = true)
    private Long _id;
    private String source;
    @org.greenrobot.greendao.annotation.NotNull
    private String url;
    @Generated(hash = 1585791477)
    public Meizi(Long _id, String source, String url) {
        this._id = _id;
        this.source = source;
        this.url = url;
    }
    @Generated(hash = 507723578)
    public Meizi() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getSource() {
        return this.source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}

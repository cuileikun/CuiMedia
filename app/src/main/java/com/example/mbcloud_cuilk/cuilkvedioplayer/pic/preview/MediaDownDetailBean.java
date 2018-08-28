package com.example.mbcloud_cuilk.cuilkvedioplayer.pic.preview;

import java.io.Serializable;

/**
 * Created by wbxmz15 on 2017/12/5.
 * 已上传数据类型
 */

public class MediaDownDetailBean implements Serializable {
//    public int num = 3;
//    public String type = "";

    /**
     * BIZ_UID : 1111
     * BIZ_TYP : 1
     * CLT_NAM : 客户名称1
     * PRJ_NAM : 项目111
     * CNT_NBR : mock
     * IMG_CNT : 1
     * VID_CNT : 2
     * AUD_CNT : 0
     * CRT_DTE : 20170103
     */

    private String BIZ_UID="";//类型：String  必有字段  备注：关联的业务主键
    private String BIZ_TYP="";//类型：String  必有字段  备注：类型： 1尽职调查 2面签 3租后检查
    private String CLT_NAM=""; //类型：String  必有字段  备注：客户名称
    private String PRJ_NAM="";//类型：String  必有字段  备注：项目名称
    private String CNT_NBR=""; //类型：String  必有字段  备注：合同编号
    private String IMG_CNT=""; //类型：String  必有字段  备注：图片数量
    private String VID_CNT=""; //类型：String  必有字段  备注：视频数量
    private String AUD_CNT="";//类型：String  必有字段  备注：语音数量
    private String CRT_DTE="";//类型：String  必有字段  备注：创建日期
    private String IMG_URL="";//类型：String  必有字段  备注：第一张图片缩略图
    private String VID_URL=""; //类型：String  必有字段  备注：第一个视频缩略图

    public String getIMG_URL() {
        return IMG_URL;
    }

    public void setIMG_URL(String IMG_URL) {
        this.IMG_URL = IMG_URL;
    }

    public String getVID_URL() {
        return VID_URL;
    }

    public void setVID_URL(String VID_URL) {
        this.VID_URL = VID_URL;
    }

    public String getBIZ_UID() {
        return BIZ_UID;
    }

    public void setBIZ_UID(String BIZ_UID) {
        this.BIZ_UID = BIZ_UID;
    }

    public String getBIZ_TYP() {
        return BIZ_TYP;
    }

    public void setBIZ_TYP(String BIZ_TYP) {
        this.BIZ_TYP = BIZ_TYP;
    }

    public String getCLT_NAM() {
        return CLT_NAM;
    }

    public void setCLT_NAM(String CLT_NAM) {
        this.CLT_NAM = CLT_NAM;
    }

    public String getPRJ_NAM() {
        return PRJ_NAM;
    }

    public void setPRJ_NAM(String PRJ_NAM) {
        this.PRJ_NAM = PRJ_NAM;
    }

    public String getCNT_NBR() {
        return CNT_NBR;
    }

    public void setCNT_NBR(String CNT_NBR) {
        this.CNT_NBR = CNT_NBR;
    }

    public String getIMG_CNT() {
        return IMG_CNT;
    }

    public void setIMG_CNT(String IMG_CNT) {
        this.IMG_CNT = IMG_CNT;
    }

    public String getVID_CNT() {
        return VID_CNT;
    }

    public void setVID_CNT(String VID_CNT) {
        this.VID_CNT = VID_CNT;
    }

    public String getAUD_CNT() {
        return AUD_CNT;
    }

    public void setAUD_CNT(String AUD_CNT) {
        this.AUD_CNT = AUD_CNT;
    }

    public String getCRT_DTE() {
        return CRT_DTE;
    }

    public void setCRT_DTE(String CRT_DTE) {
        this.CRT_DTE = CRT_DTE;
    }
}

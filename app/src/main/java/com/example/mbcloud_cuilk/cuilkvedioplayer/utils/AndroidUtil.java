package com.example.mbcloud_cuilk.cuilkvedioplayer.utils;


import android.content.Context;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * Created by wch on 2017/9/25.
 */

public class AndroidUtil {
    /**
     * 获取屏幕的宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static String getTime() {
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dff.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String time = dff.format(new Date());
        if (time == null) {
            time = "";
        }
        return time;
    }

    /**
     * 将毫秒转化成当前时间
     * 时间格式: yyyy-MM-dd HH:mm:ss
     *
     * @param second
     * @return
     */
    public static String secondToNowTime(long second) {
        String newtime = String.valueOf(second);
        if (!TextUtils.isEmpty(newtime) && !newtime.equals("0")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowTime = formatter.format(new Date(second));
            return nowTime;
        }
        return "";
    }

    /**
     * 将时间转化成毫秒
     * 时间格式: yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static Long timeStrToSecond(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Long second = format.parse(time).getTime();
            return second;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1l;
    }
}

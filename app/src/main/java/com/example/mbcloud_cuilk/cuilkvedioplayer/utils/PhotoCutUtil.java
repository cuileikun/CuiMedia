package com.example.mbcloud_cuilk.cuilkvedioplayer.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by mbcloud-cuilk on 2018/5/9.
 * 图片处理工具类
 */

public class PhotoCutUtil {
    /**
     * @param res Resource
     * @param resId 资源id
     * @param targetWidth 目标图片的宽，单位px
     * @param targetHeight 目标图片的高，单位px
     * @return 返回压缩后的图片的Bitmap
     */
    public Bitmap compressBitmap(Resources res, int resId, int targetWidth, int targetHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//设为true，节约内存
        BitmapFactory.decodeResource(res, resId, options);//返回null
        int height = options.outHeight;//得到源图片height，单位px
        int width = options.outWidth;//得到源图片的width，单位px
        //计算inSampleSize
        options.inSampleSize = calculateInSampleSize(width,height,targetWidth,targetHeight);
        options.inJustDecodeBounds = false;//设为false，可以返回Bitmap
        return BitmapFactory.decodeResource(res,resId,options);
    }

    /**
     * 计算压缩比例
     * @param width  源图片的宽
     * @param height 源图片的高
     * @param targetWidth  目标图片的宽
     * @param targetHeight 目标图片的高
     * @return inSampleSize 压缩比例
     */
    public int calculateInSampleSize(int width,int height, int targetWidth, int targetHeight) {
        int inSampleSize = 1;
        if (height > targetHeight || width > targetWidth) {
            //计算图片实际的宽高和目标图片宽高的比率
            final int heightRate = Math.round((float) height / (float) targetHeight);
            final int widthRate = Math.round((float) width / (float) targetWidth);
            //选取最小的比率作为inSampleSize
            inSampleSize = heightRate < widthRate ? heightRate : widthRate;
        }
        return inSampleSize;
    }
}

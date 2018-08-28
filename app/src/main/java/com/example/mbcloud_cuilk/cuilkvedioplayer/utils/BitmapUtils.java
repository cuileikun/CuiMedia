package com.example.mbcloud_cuilk.cuilkvedioplayer.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.example.mbcloud_cuilk.cuilkvedioplayer.utils.encoder.BASE64Decoder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by wbxmz19 on 2017/10/26.
 */
public class BitmapUtils {

    /**
     * 调整图片的大小 (先将图片缩小或者放大，然后对图片进行裁剪，让不同的图片都能占满不同的手机尺寸的屏幕)
     *
     * @param boxWidth
     * @param boxHeight
     * @param bitmap    原图
     * @return
     */
    public static Bitmap resizeBitmap(int boxWidth, int boxHeight, Bitmap bitmap) {

        float scaleX = ((float) boxWidth) / ((float) bitmap.getWidth());
        float scaleY = ((float) boxHeight) / ((float) bitmap.getHeight());
        float scale = 1.0f;

        if ((scaleX >= scaleY && scaleY >= 1.0f) || (scaleX > scaleY && scaleX < 1.0f) || (scaleX >= 1.0f && scaleY < 1.0f)) {
            scale = scaleX;
        }
        if ((scaleY > scaleX && scaleX >= 1.0f) || (scaleY > scaleX && scaleY < 1.0f) || (scaleX < 1.0f && scaleY >= 1.0f)) {
            scale = scaleY;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Bitmap alterBitmap = Bitmap.createBitmap(newBitmap, 0, 0, boxWidth, boxHeight);
        newBitmap = null;
        return alterBitmap;
    }

    /**
     * add by clk
     * 给定图片维持宽高比缩放后，截取正中间的正方形部分
     *
     * @param bitmap     原图
     * @param edgeLength 希望得到的正方形部分的边长
     * @return 缩放截取正中部分后的位图。
     */
    public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength) {
        if (null == bitmap || edgeLength <= 0) {
            return null;
        }

        Bitmap result = bitmap;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();

        if (widthOrg > edgeLength && heightOrg > edgeLength) {
            //压缩到一个最小长度是edgeLength的bitmap
            int longerEdge = (int) (edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
            int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
            int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
            Bitmap scaledBitmap;

            try {
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
            } catch (Exception e) {
                return null;
            }

            //从图中截取正中间的正方形部分。
            int xTopLeft = (scaledWidth - edgeLength) / 2;
            int yTopLeft = (scaledHeight - edgeLength) / 2;

            try {
                result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
                scaledBitmap.recycle();
            } catch (Exception e) {
                return null;
            }
        }

        return result;
    }


    /**
     * 得到本地或者网络上的bitmap url - 网络或者本地图片的绝对路径,比如:
     * <p>
     * A.网络路径: url="http://blog.foreverlove.us/girl2.png" ;
     * <p>
     * B.本地路径:url="file://mnt/sdcard/photo/image.png";
     * <p>
     * C.支持的图片格式 ,png, jpg,bmp,gif等等
     *
     * @param url
     * @return
     */
    public static Bitmap GetLocalOrNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), 2 * 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 2 * 1024);
//            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * base64解码转换成bitmap
     *add by clk
     * @param s
     * @return
     */
    private static byte[] temp;

    public static Bitmap getBitmapFromByte(String s) {

        if (s == null) return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            temp = decoder.decodeBuffer(s);
        } catch (Exception e) {
            String s1 = e.toString();
        }

        if (temp != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        } else {
            return null;
        }
    }


}

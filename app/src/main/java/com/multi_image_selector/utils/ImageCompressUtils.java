package com.multi_image_selector.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageCompressUtils {

    private static final String TAG = "ImageCompressUtils";

    /**
     * 通过降低图片的质量来压缩图片
     *
     * @param bitmap  要压缩的图片位图对象
     * @param maxSize 压缩后图片大小的最大值,单位KB
     * @return 压缩后的图片位图对象
     */
    public static Bitmap compressByQuality(Bitmap bitmap, int maxSize) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
        Log.i(TAG, "图片压缩前大小：" + byteArrayOutputStream.toByteArray().length + "byte");

        boolean isCompressed = false;
        while (byteArrayOutputStream.toByteArray().length / 1024 > maxSize) {
            quality -= 10;
            byteArrayOutputStream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
            Log.i(TAG, "质量压缩到原来的" + quality + "%时大小为：" + byteArrayOutputStream.toByteArray().length + "byte");
            isCompressed = true;
        }

        Log.i(TAG, "图片压缩后大小：" + byteArrayOutputStream.toByteArray().length + "byte");

        if (isCompressed) {
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            int byteArrayLength = byteArray.length;
            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArrayLength);
            recycleBitmap(bitmap);
            return compressedBitmap;
        } else {
            return bitmap;
        }
    }

    /**
     * 通过压缩图片的尺寸来压缩图片大小，仅仅做了缩小，如果图片本身小于目标大小，不做放大操作
     *
     * @param pathName     图片的完整路径
     * @param targetWidth  缩放的目标宽度
     * @param targetHeight 缩放的目标高度
     * @return 缩放后的图片
     */
    public static Bitmap compressBySize(String pathName, int targetWidth, int targetHeight) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等；
        Bitmap bitmap;

        // 得到图片的宽度、高度；
        int imgWidth = opts.outWidth;
        int imgHeight = opts.outHeight;

        // 分别计算图片宽度、高度与目标宽度、高度的比例；取大于等于该比例的最小整数；
        int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
        int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);

        if (widthRatio > 1 || heightRatio > 1) {
            if (widthRatio > heightRatio) {
                opts.inSampleSize = widthRatio;
            } else {
                opts.inSampleSize = heightRatio;
            }
        }

        // 设置好缩放比例后，加载图片进内容；
        opts.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(pathName, opts);
        return bitmap;
    }

    /**
     * 通过压缩图片的尺寸来压缩图片大小
     *
     * @param bitmap       要压缩图片
     * @param targetWidth  缩放的目标宽度
     * @param targetHeight 缩放的目标高度
     * @return 缩放后的图片
     */
    public static Bitmap compressBySize(Bitmap bitmap, int targetWidth, int targetHeight) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        int byteArrayLength = byteArray.length;
        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArrayLength, opts);

        // 得到图片的宽度、高度；
        int imgWidth = opts.outWidth;
        int imgHeight = opts.outHeight;

        // 分别计算图片宽度、高度与目标宽度、高度的比例；取大于该比例的最小整数；
        int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
        int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);

        if (widthRatio > 1 || heightRatio > 1) {
            if (widthRatio > heightRatio) {
                opts.inSampleSize = widthRatio;
            } else {
                opts.inSampleSize = heightRatio;
            }
        }

        // 设置好缩放比例后，加载图片进内存；
        opts.inJustDecodeBounds = false;
        byte[] newByteArray = byteArrayOutputStream.toByteArray();
        int newByteArrayLength = newByteArray.length;
        Bitmap compressedBitmap = BitmapFactory.decodeByteArray(newByteArray, 0, newByteArrayLength, opts);
        recycleBitmap(bitmap);
        return compressedBitmap;
    }

    /**
     * 通过压缩图片的尺寸来压缩图片大小，通过读入流的方式，可以有效防止网络图片数据流形成位图对象时内存过大的问题；
     *
     * @param inputStream  要压缩图片，以流的形式传入
     * @param targetWidth  缩放的目标宽度
     * @param targetHeight 缩放的目标高度
     * @return 缩放后的图片
     * @throws IOException 读输入流的时候发生异常
     */
    public static Bitmap compressBySize(InputStream inputStream, int targetWidth, int targetHeight) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len;

        while ((len = inputStream.read(buff)) != -1) {
            byteArrayOutputStream.write(buff, 0, len);
        }

        byte[] data = byteArrayOutputStream.toByteArray();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        Bitmap bitmap;

        // 得到图片的宽度、高度；
        int imgWidth = opts.outWidth;
        int imgHeight = opts.outHeight;

        // 分别计算图片宽度、高度与目标宽度、高度的比例；取大于该比例的最小整数；
        int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
        int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);

        if (widthRatio > 1 || heightRatio > 1) {
            if (widthRatio > heightRatio) {
                opts.inSampleSize = widthRatio;
            } else {
                opts.inSampleSize = heightRatio;
            }
        }

        // 设置好缩放比例后，加载图片进内存；
        opts.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
        return bitmap;
    }

    /**
     * 旋转图片摆正显示
     *
     * @param srcPath 要旋转的图片路径
     * @param bitmap Bitmap
     * @return 校正后的Bitmap
     */
    public static Bitmap rotateBitmapByExif(String srcPath, Bitmap bitmap) {
        ExifInterface exif;
        Bitmap newBitmap;
        try {
            exif = new ExifInterface(srcPath);
            // 读取图片中相机方向信息
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int angle = 0;
            switch (ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    angle = 90;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    angle = 180;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    angle = 270;
                    break;
            }

            if (angle != 0) {
                Matrix m = new Matrix();
                m.postRotate(angle);
                newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                recycleBitmap(bitmap);
                return newBitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
//            CrashReport.postCatchedException(e);
        }
        return bitmap;
    }

    public static File saveBitmapToFile(Bitmap bitmap,String savePath){
            File file = new File(savePath);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG,90,bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
//            CrashReport.postCatchedException(e);
        }

        return file;
    }

    public static int[] getImageParams(String fileCompletePath){
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等；
        BitmapFactory.decodeFile(fileCompletePath, opts);
        return new int[] {opts.outWidth,opts.outHeight};
    }

    /**
     * 回收位图对象
     *
     * @param bitmap 要回收的Bitmap
     */
    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            System.gc();
            bitmap = null;
        }
    }

}
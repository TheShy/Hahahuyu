package com.touyuanren.hahahuyu.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Jing on 2016/10/31.
 * 图片处理
 */
public  class FoImage {






    /**
     * 计算缩放设置
     *
     * @param imageWidth  图像宽度
     * @param imageHeight 图像高度
     * @return 缩放设置实例
     */
    public static BitmapFactory.Options zoom(int imageWidth, int imageHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        int baseHeight = 720, baseWidth = 560;
        /*if (number<=2){
            baseHeight = 1440;
            baseWidth = 1024;}*/
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        if (imageWidth > baseWidth * 15 || imageHeight > baseHeight * 15) {
            options.inSampleSize = 15;
        } else if (imageWidth > baseWidth * 14 || imageHeight > baseHeight * 14) {
            options.inSampleSize = 14;
        } else if (imageWidth > baseWidth * 13 || imageHeight > baseHeight * 13) {
            options.inSampleSize = 13;
        } else if (imageWidth > baseWidth * 12 || imageHeight > baseHeight * 12) {
            options.inSampleSize = 12;
        } else if (imageWidth > baseWidth * 11 || imageHeight > baseHeight * 11) {
            options.inSampleSize = 11;
        } else if (imageWidth > baseWidth * 10 || imageHeight > baseHeight * 10) {
            options.inSampleSize = 10;
        } else if (imageWidth > baseWidth * 9 || imageHeight > baseHeight * 9) {
            options.inSampleSize = 9;
        } else if (imageWidth > baseWidth * 8 || imageHeight > baseHeight * 8) {
            options.inSampleSize = 8;
        } else if (imageWidth > baseWidth * 7 || imageHeight > baseHeight * 7) {
            options.inSampleSize = 7;
        } else if (imageWidth > baseWidth * 6 || imageHeight > baseHeight * 6) {
            options.inSampleSize = 6;
        } else if (imageWidth > baseWidth * 5 || imageHeight > baseHeight * 5) {
            options.inSampleSize = 5;
        } else if (imageWidth > baseWidth * 4 || imageHeight > baseHeight * 4) {
            options.inSampleSize = 4;
        } else if (imageWidth > baseWidth * 3 || imageHeight > baseHeight * 3) {
            options.inSampleSize = 3;
        } else if (imageWidth < baseWidth * 2 || imageHeight < baseHeight * 2) {
            options.inSampleSize = 2;
        } else if (imageWidth < baseWidth || imageHeight < baseHeight) {
            options.inSampleSize = 1;
        } else {
            options.inSampleSize = 1;
        }

        return options;
    }
}

package com.multi_image_selector.bean;

import android.text.TextUtils;

/**
 * @author Robin
 * @since 2016-06-02
 */
public class Image {
    public String path;
    public String name;
    public long time;

    public Image(String path, String name, long time) {
        this.path = path;
        this.name = name;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Image other = (Image) o;
            return TextUtils.equals(this.path, other.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}

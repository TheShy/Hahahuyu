package com.touyuanren.hahahuyu.bean;

/**
 * Created by Jing on 2016/9/26.
 * 频道bean类
 */
public class ChannelBean {
    private String channel_id;
    private String channel_name;

    public ChannelBean() {
    }

    public ChannelBean(String channel_id, String channel_name) {
        this.channel_id = channel_id;
        this.channel_name = channel_name;
    }

    @Override
    public String toString() {
        return "ChannelBean{" +
                "channel_id='" + channel_id + '\'' +
                ", channel_name='" + channel_name + '\'' +
                '}';
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }
}

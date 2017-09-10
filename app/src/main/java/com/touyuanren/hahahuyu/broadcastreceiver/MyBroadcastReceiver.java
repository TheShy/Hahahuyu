package com.touyuanren.hahahuyu.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.touyuanren.hahahuyu.utils.FoNet;

/**
 * Created by Jing on 2016/1/5.
 * 广播监听网络状态
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    //是否连接
    private Boolean isConnect;

    @Override
    public void onReceive(Context context, Intent intent) {
        isConnect = FoNet.getNetState(context);
    }
}

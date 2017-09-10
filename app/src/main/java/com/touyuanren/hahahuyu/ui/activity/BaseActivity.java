package com.touyuanren.hahahuyu.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.broadcastreceiver.MyBroadcastReceiver;
import com.touyuanren.hahahuyu.widget.CustomProgressDialog;

/**
 * Created by liang on 2015/12/8.
 * BaseActivity,加入了网络判断
 */
public class BaseActivity extends AppCompatActivity {
    private MyBroadcastReceiver myBroadcastReceiver;
    private CustomProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myBroadcastReceiver = new MyBroadcastReceiver();
        progressDialog = new CustomProgressDialog(this);
        //注册网络监听
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myBroadcastReceiver, filter);
        MyApplication.getInstance().addActivity(this);
        netStateListener();
    }

    /**
     * dialog 开关控制器
     */
    protected void toggleShowLoading(boolean toggle) {
        if (toggle) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    public void showLoading() {
        toggleShowLoading(true);
    }

    public void hideLoading() {
        toggleShowLoading(false);
    }

    /**
     * 判断网络是否链接
     *
     * @param ctx
     * @return
     */
    public boolean getNetState(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    /**
     *   n监听网络状态修改时发送的本地广播 ,
     */
    public void netStateListener() {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);

        IntentFilter intentFilter = new IntentFilter("com.life.work.LOCAL_NET_CHANGE");

        localBroadcastManager.registerReceiver(new LocalReceiver(), intentFilter);
    }

    /**
     * 网络状态变化是发送的本地广播接收器
     */
    public class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String typeName = intent.getStringExtra("typename");
            Toast.makeText(context, typeName, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 标题左键
     */
    protected void setTitleLeftBtn() {
        ImageView iv = (ImageView) this.findViewById(R.id.iv_header_left);
        if (null == iv) {
            return;
        }
        iv.setImageResource(R.drawable.back);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 设置页面标题名称
     */
    protected void setTitleImage(int rId) {
        ImageView imageView = (ImageView) this.findViewById(R.id.iv_header_title);
        if (null == imageView) {
            return;
        }

        imageView.setBackgroundResource(rId);
    }

    /**
     * 设置页面左侧标题名称
     */
    protected void setLestTitle(String rId) {
        TextView mLeftTitle = (TextView) this.findViewById(R.id.tv_left_title);
        if (null == mLeftTitle) {
            return;
        }
        mLeftTitle.setText(rId);
    }

    /**
     * 设置页面标题名称
     */
    protected void setTitleName(int rId) {
        TextView textView = (TextView) this.findViewById(R.id.tv_header_title);
        if (null == textView) {
            return;
        }

        textView.setText(rId);
    }

    /**
     * 设置页面标题名称
     */
    protected void setTitleName(String titleName) {
        TextView textView = (TextView) this.findViewById(R.id.tv_header_title);
        if (null == textView) {
            return;
        }

        textView.setText(titleName);
    }

    /**
     * 标题右键(文字)
     */
    protected void setTitleRightBtn(int rId, View.OnClickListener listener) {
        TextView tv = (TextView) this.findViewById(R.id.rv_header_right);
        tv.setVisibility(View.VISIBLE);

        ImageView imageView = (ImageView) this.findViewById(R.id.iv_header_right);
        imageView.setVisibility(View.GONE);
        if (null == tv) {
            return;
        }

        tv.setText(rId);
        tv.setTextColor(getResources().getColor(R.color.white));
        if (null != listener) {
            tv.setOnClickListener(listener);
        }
    }

    /**
     * 标题右键(图片)
     */
    protected void setTitleRightImage(int rId, View.OnClickListener listener) {
        ImageView imageView = (ImageView) findViewById(R.id.iv_header_right);
        imageView.setVisibility(View.VISIBLE);

        TextView tv = (TextView) this.findViewById(R.id.rv_header_right);
        tv.setVisibility(View.GONE);
        if (null == imageView) {
            return;
        }

        imageView.setImageResource(rId);

        if (null != listener) {
            imageView.setOnClickListener(listener);
        }
    }

    /**
     * 隐藏标题
     */
    protected void hidTitle() {
        RelativeLayout rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        rl_title.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_left_in, R.anim.anim_right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver); //取消监听
        //结束Activity&从集合中移除
        MyApplication.getInstance().finishActivity(this);
    }
}

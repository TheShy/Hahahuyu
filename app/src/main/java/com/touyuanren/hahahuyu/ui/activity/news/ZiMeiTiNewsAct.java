package com.touyuanren.hahahuyu.ui.activity.news;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.touyuanren.hahahuyu.utils.ShowPopUp;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Jing on 2016/10/24.
 * 自媒体详情
 */
public class ZiMeiTiNewsAct extends BaseActivity {
    private WebView mWebView;
    private ProgressBar progressBar_zimeiti;

    private String userId;
    private String contentId;
    private static final String TAG = "ZiMeiTiNewsAct";
    private TextView tv_dashang_comment_zimeiti;
    View view_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_zimeiti_detail);

        view_layout = LayoutInflater.from(this).inflate(R.layout.act_zimeiti_detail, null);

        tv_dashang_comment_zimeiti = (TextView) findViewById(R.id.tv_dashang_comment_zimeiti);

        final String content = getIntent().getStringExtra("content");
        userId = getIntent().getStringExtra("user_id");
        contentId = getIntent().getStringExtra("content_id");
        mWebView = (WebView) findViewById(R.id.wv_zimeiti_detail);
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        progressBar_zimeiti = (ProgressBar) findViewById(R.id.progressBar_zimeiti);

        mWebView.loadUrl(content);

//        mWebView.setWebViewClient(new WebViewClient(){
//            //覆写shouldOverrideUrlLoading实现内部显示网页
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // TODO 自动生成的方法存根
//                view.loadUrl(content);
//                return true;
//            }
//        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    progressBar_zimeiti.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    progressBar_zimeiti.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar_zimeiti.setProgress(newProgress);//设置进度值
                }
            }

        });

        tv_dashang_comment_zimeiti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View views) {
                final ShowPopUp showPopUp = new ShowPopUp(ZiMeiTiNewsAct.this);
                showPopUp.et_click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (TextUtils.isEmpty(showPopUp.num$)) {
                            FoToast.toast(getBaseContext(), "请输入密码");
                        }
                        toreward(showPopUp.num$);
                        showPopUp.show(view_layout);
                    }
                });
                showPopUp.show(view_layout);
            }
        });

        findViewById(R.id.iv_header_left_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.iv_header_right_fenxiang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url_h5 = "www.hahahuyu.com/hd_h5/zi_media_article.php?act=detail&id=" + contentId;
                showShare(url_h5);
            }
        });

        findViewById(R.id.iv_header_right_pinglun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getBaseContext(), ZMTcommentsActivity.class);
                mIntent.putExtra("content", content);
                mIntent.putExtra("user_id", userId);
                mIntent.putExtra("content_id", contentId);
                startActivity(mIntent);
            }
        });
    }

    //彻底关闭webview
    public void setConfigCallback(WindowManager windowManager) {
        try {
            Field field = WebView.class.getDeclaredField("mWebViewCore");
            field = field.getType().getDeclaredField("mBrowserFrame");
            field = field.getType().getDeclaredField("sConfigCallback");
            field.setAccessible(true);
            Object configCallback = field.get(null);

            if (null == configCallback) {
                return;
            }

            field = field.getType().getDeclaredField("mWindowManager");
            field.setAccessible(true);
            field.set(configCallback, windowManager);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    //进行打赏
    public void toreward(String money) {
        showLoading();
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "a_reward");
        formMap.put("type ", "2");
        formMap.put("bds_id", userId);
        formMap.put("money", money);
        formMap.put("id_type", "3");
        formMap.put("id_value", contentId);
        FoHttp.reword(formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                Log.e(TAG, response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String msg = jsonObject.getString("msg");
                    if (status.equals("1")) {
                        FoToast.toast(MyApplication.getContext(), msg);
                    } else if (status.equals("113")) {
                        FoToast.toast(MyApplication.getContext(), msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideLoading();
            }
        });
    }

    private void showShare(String fx_url) {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
//        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(fx_url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(fx_url);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(fx_url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(fx_url);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(fx_url);

        // 启动分享GUI
        oks.show(this);
    }
}

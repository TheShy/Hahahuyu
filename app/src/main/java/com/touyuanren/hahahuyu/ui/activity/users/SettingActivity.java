package com.touyuanren.hahahuyu.ui.activity.users;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.AboutUsAct;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.activity.account.LoginActivity;
import com.touyuanren.hahahuyu.utils.FoContents;
import com.touyuanren.hahahuyu.utils.FoPreference;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.touyuanren.hahahuyu.utils.UrlHelper;
import com.touyuanren.hahahuyu.utils.newapp.CheckVersionInfoTask;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Jing on 2016/2/24.
 * app的设置界面
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 退出登录
     */
    private TextView loginOut;
    private static final String TAG = "SettingActivity";
    private TextView tv_version_name;
    private LinearLayout ll_about_us;
    /**
     * 检查版本
     */
    private LinearLayout ll_check_version;
    /**
     * 版本名：1.0.1
     */
    private String versionName;
    /**
     * 版本号：101
     */
    private int versionCode;
    private String versionNote;
    private String versionUrl;
private MyApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        setTitleLeftBtn();
        setTitleName(R.string.setting);
        app=MyApplication.getInstance();
        initView();
        getVersion();

    }

    private void getVersion() {
        PackageManager manager = SettingActivity.this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(SettingActivity.this.getPackageName(), 0);
            versionName = info.versionName; // 版本名
            versionCode = info.versionCode; // 版本号
            Log.e(TAG, versionName + "/" + versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch blockd
            e.printStackTrace();
        }
        tv_version_name.setText(versionName);
    }

    private void initView() {
        loginOut = (TextView) findViewById(R.id.tv_loginout);
        tv_version_name = (TextView) findViewById(R.id.tv_versionname_act);
        ll_about_us = (LinearLayout) findViewById(R.id.ll_about_us);
        ll_check_version = (LinearLayout) findViewById(R.id.ll_check_version_setting);
        //绑定监听
        loginOut.setOnClickListener(this);
        ll_about_us.setOnClickListener(this);
        ll_check_version.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent mIntent = null;
        switch (v.getId()) {
            case R.id.tv_loginout:
                loginOut();
                break;
            case R.id.ll_about_us:
                mIntent = new Intent(SettingActivity.this, AboutUsAct.class);
                startActivity(mIntent);
                break;
            case R.id.ll_check_version_setting:
                //检查版本更新
//                getVersionInfo();

                //检测新版本
                new CheckVersionInfoTask(SettingActivity.this, true).execute();
                break;
        }
    }

    //退出登录
    public void loginOut() {
        showLoading();
        Map<String, String> formMap = new HashMap<>();
        OkHttpUtils.post()
                .url(HttpApi.ROOT_PATH + HttpApi.LOGIN_OUT)
                .params(UrlHelper.getInstance().getBaseParams(formMap))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        hideLoading();
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e("msg", response);
                        hideLoading();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if ("1".equals(status)) {
                                Intent mIntent = new Intent(SettingActivity.this, LoginActivity.class);
                                startActivity(mIntent);
                                FoPreference.putString(FoContents.LOGIN_TOKEN, null);
                                finish();
                                FoToast.toast(MyApplication.getContext(), jsonObject.getString("msg"));
                            } else {
                                FoToast.toast(MyApplication.getContext(), jsonObject.getString("msg"));
                                Log.e("msg", jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

//    public void getVersionInfo() {
//        showLoading();
//        Map<String, String> formMap = new HashMap<>();
//        formMap.put("type", "1");
//        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.VERSION, formMap, new StringCallback() {
//            @Override
//            public void onError(Request request, Exception e) {
//                hideLoading();
//            }
//
//            @Override
//            public void onResponse(String response) {
//                Log.e(TAG, response);
//                if (response != null) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
//                        versionNote = jsonObject1.getString("v_note");
//                        int versionCode2 = Integer.parseInt(jsonObject1.getString("v_code"));
//                        versionName = jsonObject1.getString("v_name");
//                        versionUrl = jsonObject1.getString("download_url");
//                        hideLoading();
//                        if (versionCode < versionCode2) {
//                            //提示更新
//                            showUpdateDialog();
//                        } else {
//                            FoToast.toast(MyApplication.getContext(), "已是最新版本");
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                hideLoading();
//            }
//        });
//    }

//    private void showUpdateDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("检测到新版本");
//        builder.setMessage("是否下载更新?");
//        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // TODO Auto-generated method stub
//                Intent it = new Intent(SettingActivity.this, NotificationUpdateActivity.class);
//                startActivity(it);
////                app.isDownload = true;
//                app.setIsDownload(true);
//            }
//        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // TODO Auto-generated method stub
//
//            }
//        });
//        builder.show();
//    }

}

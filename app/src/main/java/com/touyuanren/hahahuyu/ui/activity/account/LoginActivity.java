package com.touyuanren.hahahuyu.ui.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.activity.MainActivity;
import com.touyuanren.hahahuyu.utils.FoContents;
import com.touyuanren.hahahuyu.utils.FoPreference;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.touyuanren.hahahuyu.utils.UrlHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 2016/2/26.
 * 登录
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 登录
     */
    private Button bt_Login;
    /**
     * 用户名
     */
    private EditText mAccountName;
    /**
     * 密码
     */
    private EditText mAccountPwd;
    /**
     * 忘记密码
     */
    private TextView find_pwd;
    //注册按钮
    private Button register;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        setTitleName(R.string.title_login);
        mIntent=getIntent();
        initView();
    }

    private void initView() {
        bt_Login = (Button) findViewById(R.id.bt_login);
        mAccountName = (EditText) findViewById(R.id.et_login_name);
        mAccountPwd = (EditText) findViewById(R.id.et_login_pwd);
        find_pwd = (TextView) findViewById(R.id.tv_login_find_pwd);
        register = (Button) findViewById(R.id.bt_register);
        //绑定监听
        bt_Login.setOnClickListener(this);
        mAccountName.setOnClickListener(this);
        mAccountPwd.setOnClickListener(this);
        find_pwd.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent mIntent = null;
        String userName;
        switch (v.getId()) {
            case R.id.et_login_pwd:
                userName = mAccountName.getText().toString();
                if (userName.length() < 0) {
                    FoToast.toast(this, "请输入用户名");
                }
                break;
            case R.id.bt_login:
                userName = mAccountName.getText().toString();
                if (userName.length() < 0) {
                    FoToast.toast(this, "请输入用户名");
//                    return;
                }
                String userPassword = mAccountPwd.getText().toString();
                if (TextUtils.isEmpty(userPassword)) {
                    FoToast.toast(this, "请输入密码");
                }
                userLogin(HttpApi.ROOT_PATH + HttpApi.API_LOGIN, userName, userPassword);
                break;
            case R.id.tv_login_find_pwd:
                mIntent = new Intent(this, ForgetPasswordActivity.class);
                startActivity(mIntent);
                break;
            //注册
            case R.id.bt_register:
                mIntent = new Intent(this, RegisterActivity.class);
                startActivity(mIntent);
                break;
        }
    }

    /**
     * @param name:用户名
     * @param pwd：密码
     */
    public void userLogin(String url, final String name, String pwd) {

        showLoading();
        Map<String, String> formMap = new HashMap<>();
        formMap.put("user_name", name);
        formMap.put("password", pwd);
        OkHttpUtils.post()
                .url(url)
                .params(UrlHelper.getInstance().getBaseParams(formMap))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(LoginActivity.this,R.string.intent_error,Toast.LENGTH_LONG).show();
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
//                                if (mIntent!=null){
//                                   setIntent(mIntent);
//                                    //发送广播
//
//                                }else{
                                    Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(mIntent);
//                                }
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                String token = jsonObject1.getString("token");
                                FoPreference.putString(FoContents.LOGIN_TOKEN, token);
                                finish();
                                FoToast.toast(MyApplication.getContext(), jsonObject.getString("msg"));
                            } else {
                                FoToast.toast(MyApplication.getContext(), jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}

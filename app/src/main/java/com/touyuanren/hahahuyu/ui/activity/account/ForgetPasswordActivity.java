package com.touyuanren.hahahuyu.ui.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.utils.FoContents;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.touyuanren.hahahuyu.utils.UrlHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 2016/7/22.
 * 忘记密码，进行密码修改
 */
public class ForgetPasswordActivity extends BaseActivity {
    /**
     * 新密码
     */
    private EditText et_new_pwd;
    /**
     * 新密码
     */
    private EditText et_repeat_pwd;
    /**
     * 电话号码
     */
    private EditText et_phone;
    /**
     * 短信验证码
     */
    private EditText et_authtoken;
    /**
     * 修改密码按钮
     */
    private Button bt_change;
    /**
     * 获取短信验证码按钮
     */
    private Button bt_get_note;
    /**
     * 密码操作类型
     * 忘记登录密码； 修改登录密码； 修改交易密码；
     */
    private String pwd_type;
    /**
     * 短信验证码间隔时间
     */
    private int second = FoContents.GET_SMS_CODE;
    //每隔一秒修改按钮状态
    private Handler hander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //这样写的目的是减小运算量,只需要一次设置就够了
            if (bt_get_note.isEnabled()) {
                bt_get_note.setEnabled(false);
                bt_get_note.setBackgroundResource(R.drawable.n_code_sel);
            }
            bt_get_note.setText(getString(R.string.get_code_again_left) + second-- + getString(R.string.get_code_again_right));

            if (0 > second) {
                second = FoContents.GET_SMS_CODE;

                bt_get_note.setBackgroundResource(R.drawable.n_code);
                bt_get_note.setText(getText(R.string.get_auth_code));
                bt_get_note.setEnabled(true);
            } else {
                hander.sendMessageDelayed(Message.obtain(), 1000);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_forget_password);
        setTitleName(R.string.back_pwd);
        setTitleLeftBtn();
        init();
    }

    private void init() {
        et_new_pwd = (EditText) findViewById(R.id.et_new_password);
        et_repeat_pwd = (EditText) findViewById(R.id.et_repeat_password);
        et_phone = (EditText) findViewById(R.id.et_forget_phone);
        et_authtoken = (EditText) findViewById(R.id.et_forget_authcode);

        //获取短信验证码
        bt_get_note = (Button) findViewById(R.id.bt_forget_get_authcode);
        //修改用户密码
        bt_change = (Button) findViewById(R.id.bt_forget_change);

        //从intent中获取操作的类型
        Intent intent = getIntent();
//        if (null != intent)
//        {
//            pwd_type = intent.getStringExtra(FoContents.PWD_CHANGE_TYPE);
//        }

        bt_get_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestAuthCode();
            }
        });

        bt_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestChangePwd();
            }
        });

    }

    /**
     * 先判断手机号不为空，获取短信验证码
     */
    public void requestAuthCode() {
        if (checkPhone()) {
            return;
        }
        //参数集合
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", et_phone.getText().toString());
        OkHttpUtils.post()
                .url(HttpApi.ROOT_PATH + HttpApi.USER_SEND_MESSAGE)
                .params(UrlHelper.getInstance().getBaseParams(map))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if ("1".equals(status)) {
                                FoToast.toast(MyApplication.getContext(), jsonObject.getString("msg"));
                                hander.sendEmptyMessage(0);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 密码修改请求；先做非空判断，然后提交
     */
    private void requestChangePwd() {
        if (checkNewPwd() || checkPhone() || checkToken() || checkNewRePwd()) {
            return;
        }
        if (et_repeat_pwd.getText().toString().equals(et_new_pwd.getText().toString())) {

        } else {
            FoToast.toast(MyApplication.getContext(), R.string.pwd_not_match);
            return;
        }
        //提交网络请求
        showLoading();
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "reset_pass");
        formMap.put("mobile", et_phone.getText().toString());
        formMap.put("verify", et_authtoken.getText().toString());
        formMap.put("password", et_new_pwd.getText().toString());
        formMap.put("confirm_password", et_repeat_pwd.getText().toString());
        OkHttpUtils.post()
                .url(HttpApi.ROOT_PATH + HttpApi.USER_INFO)
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
                                Intent mIntent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                                startActivity(mIntent);
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

    /**
     * 检测新号码为空
     */
    private boolean checkPhone() {
        if (TextUtils.isEmpty(et_phone.getText().toString())) {
            FoToast.toast(ForgetPasswordActivity.this, R.string.please_write_phone_number);
            return true;
        }
        return false;
    }

    /**
     * 检测新密码为空
     */
    private boolean checkNewPwd() {
        if (TextUtils.isEmpty(et_new_pwd.getText().toString())) {
            FoToast.toast(ForgetPasswordActivity.this, R.string.write_new_pwd);
            return true;
        }
        return false;
    }

    /**
     * 检测确认新密码为空
     */
    private boolean checkNewRePwd() {
        if (TextUtils.isEmpty(et_repeat_pwd.getText().toString())) {
            FoToast.toast(ForgetPasswordActivity.this, R.string.write_new_pwd);
            return true;
        }
        return false;
    }

    /**
     * 检测新验证码为空
     */
    private boolean checkToken() {
        if (TextUtils.isEmpty(et_authtoken.getText().toString())) {
            FoToast.toast(ForgetPasswordActivity.this, R.string.please_write_note_token);
            return true;
        }
        return false;
    }
}

package com.touyuanren.hahahuyu.ui.activity.account;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.utils.DeviceUtil;
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
 * Created by Jing on 2016/8/22.
 * 注册
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 电话号码
     */
    private EditText user_phone;
    /**
     * 注册密码
     */
    private EditText user_pwd;
    /**
     * 注册确认密码
     */
    private EditText user_pwd_confirm;
    /**
     * 验证码
     */
    private EditText user_authcode;
    /**
     * 获取验证码
     */
    private Button bt_code;
    /**
     * 同意协议
     */
    private CheckBox cb_agree;

    /**
     * 注册按钮
     */
    private Button bt_register;

    /**
     * 保存密码
     */
    private String userPwd;
    /**
     * 再次输入的密码
     */
    private String   userPwdConfirm;
    /**
     * 保存手机号
     */
    private String userPhone;
    /**
     * 验证码
     */
    private String authCode;
    private int second = FoContents.GET_SMS_CODE;
    //每隔一秒修改按钮状态
    private Handler hander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //这样写的目的是减小运算量,只需要一次设置就够了
            if (bt_code.isEnabled()) {
                bt_code.setEnabled(false);
                bt_code.setBackgroundResource(R.drawable.n_code_sel);
            }
            bt_code.setText(getString(R.string.get_code_again_left) + second-- + getString(R.string.get_code_again_right));

            //在second变为-1前每一秒重新发送一次message
            if (0 > second) {
                second = FoContents.GET_SMS_CODE;

                bt_code.setBackgroundResource(R.drawable.n_code);
                bt_code.setText(getText(R.string.get_auth_code));
                bt_code.setEnabled(true);
            } else {
                hander.sendMessageDelayed(Message.obtain(), 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);
        setTitleLeftBtn();
        setTitleName(R.string.register);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {

        user_pwd = (EditText) findViewById(R.id.et_register_pwd);
        user_phone = (EditText) findViewById(R.id.et_register_phone);
        user_pwd_confirm= (EditText) findViewById(R.id.et_register_pwd_confirm);
        user_authcode = (EditText) findViewById(R.id.et_register_authcode);

        bt_code = (Button) findViewById(R.id.bt_register_get_authcode);
        cb_agree = (CheckBox) findViewById(R.id.cb_register_agree);

//        et_imageCode = (EditText) findViewById(R.id.et_image_code);
        //用户注册
        bt_register = (Button) findViewById(R.id.bt_register);
        bt_code.setOnClickListener(this);
        bt_register.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        userPwd = user_pwd.getText().toString();
        userPhone = user_phone.getText().toString();
        authCode = user_authcode.getText().toString();
        userPwdConfirm=user_pwd_confirm.getText().toString();
        switch (v.getId()) {
            //发送验证码，先弹出对话框填写图形验证码
            case R.id.bt_register_get_authcode:
                if (TextUtils.isEmpty(userPhone)) {
                    FoToast.toast(MyApplication.getInstance(), R.string.please_input_phone);
                    return;
                }
                if (!isTurePhone(userPhone)) {
                    FoToast.toast(MyApplication.getInstance(), R.string.please_right_phone);
                    return;
                }
                if (TextUtils.isEmpty(userPwd)) {
                    FoToast.toast(MyApplication.getInstance(), R.string.please_input_psw);
                    return;
                }
                if (TextUtils.isEmpty(userPwdConfirm)&&userPwdConfirm.equals(userPwd)) {
                    FoToast.toast(MyApplication.getInstance(), R.string.please_input_psw);
                    return;
                }
                requestAuthCode(userPhone);
                break;
            //注册
            case R.id.bt_register:
                if (TextUtils.isEmpty(userPhone)) {
                    FoToast.toast(MyApplication.getInstance(), R.string.please_input_phone);
                    return;
                }
                if (!isTurePhone(userPhone)) {
                    FoToast.toast(MyApplication.getInstance(), R.string.please_right_phone);
                    return;
                }
                if (TextUtils.isEmpty(userPwd)) {
                    FoToast.toast(MyApplication.getInstance(), R.string.please_input_psw);
                    return;
                }
                if (TextUtils.isEmpty(authCode)) {
                    return;
                }
                if (!cb_agree.isChecked())
                {
                    FoToast.toast(RegisterActivity.this, R.string.please_read_agreement);
                    return ;
                }
                toRegister(userPhone, userPwd, authCode,userPwdConfirm);
                break;
        }
    }

    /**
     * 注册用户
     */
    private void toRegister(final String userPhone, String userPwd, String authCode,String userPwdConfirm) {
        showLoading();
        //参数集合
        Map<String, String> map = new HashMap<String, String>();
        map.put("password", userPwd);
        map.put("mobile", userPhone);
        map.put("confirm_password", userPwdConfirm);
        map.put("verify", authCode);
        map.put("type_origin","1");
        map.put("phone_model", DeviceUtil.getModel());
        OkHttpUtils.post()
                .url(HttpApi.ROOT_PATH + HttpApi.USER_REGISTER)
                .params(UrlHelper.getInstance().getBaseParams(map))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e("msg", response);
                        try {
                            String   status=new JSONObject(response).getString("status");
                            if (status.equals("1")){
                                FoToast.toast(MyApplication.getContext(),new JSONObject(response).getString("msg"));
                                FoPreference.putString(FoContents.REGISTER_PHONE, userPhone);
                                FoPreference.putString(FoContents.NICKNAME,userPhone);
                                finish();
                            }else {
                                FoToast.toast(MyApplication.getContext(),new JSONObject(response).getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        hideLoading();
                    }
                });

    }

    /**
     * 获取图形验证码
     */
    private void getImgNodeCode() {

    }

    /**
     * 获取短信验证码
     */
    public void requestAuthCode(String phoneNumber) {
        //参数集合
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", phoneNumber);
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
                        Log.e("response",response);
                        try {
                            JSONObject  jsonObject=new JSONObject(response);
                            String status =jsonObject.getString("status");
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

    //判断手机号格式
    public boolean isTurePhone(String phoneNum) {
        if (phoneNum.length() == 11 && phoneNum.startsWith("1")) {
            return true;
        } else {
            return false;
        }
    }
}

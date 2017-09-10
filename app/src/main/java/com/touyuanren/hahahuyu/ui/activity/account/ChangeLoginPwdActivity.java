package com.touyuanren.hahahuyu.ui.activity.account;

import android.content.Intent;
import android.os.Bundle;
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
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 2016/8/30.
 * 修改登录密码
 */
public class ChangeLoginPwdActivity extends BaseActivity {
    /**
     * 老登录密码
     */
    private EditText et_old_password;
    /**
     * 新密码
     */
    private EditText et_new_password;

    /**
     * 新密码密码重复
     */
    private EditText et_repeat_password;

    /**
     * 提交按钮
     */
    private Button bt_commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login_change_pwd);
        setTitleLeftBtn();
        setTitleName(R.string.change_login_pwd);
        init();
    }

    private void init() {
        et_old_password = (EditText) findViewById(R.id.et_old_login_password);
        et_new_password = (EditText) findViewById(R.id.et_new_login_password);
        et_repeat_password = (EditText) findViewById(R.id.et_repeat_login_password);
        bt_commit = (Button) findViewById(R.id.bt_commit);
        bt_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLoginPassword();
            }
        });
    }

    /**
     * 重新设置登录密码
     */
    private void changeLoginPassword() {
        if (!checkIsChangeParam()) {
            return;
        }
        //参数获取
        String old_password = et_old_password.getText().toString();
        String new_password = et_new_password.getText().toString();
        String repeat_password = et_repeat_password.getText().toString();
        //校验参数
        //提交网络请求
        showLoading();
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "edit_pass");
        formMap.put("old_password", old_password);
        formMap.put("password",new_password);
        formMap.put("confirm_password", repeat_password);
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.USER_INFO, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Log.e("msg", response);
                hideLoading();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if ("1".equals(status)) {
                        Intent mIntent = new Intent(ChangeLoginPwdActivity.this, LoginActivity.class);
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
     * 监测登录密码是否填写是否一致
     *
     * @return
     */
    private boolean checkIsChangeParam() {
        //参数获取
        String old_password = et_old_password.getText().toString();
        String new_password = et_new_password.getText().toString();
        String repeat_password = et_repeat_password.getText().toString();

        if (TextUtils.isEmpty(old_password)) {
            FoToast.toast(this, getString(R.string.write_old_login_pwd));
            return false;
        }
        if (TextUtils.isEmpty(new_password)) {
            FoToast.toast(this, getString(R.string.write_new_login_pwd));
            return false;
        }
        if (TextUtils.isEmpty(repeat_password)) {
            FoToast.toast(this, getString(R.string.repeat_new_login_pwd));
            return false;
        }
        if (new_password.equals(repeat_password)){

        }else{
            FoToast.toast(this, R.string.pwd_not_match);
            return false;
        }
        return true;
    }
}

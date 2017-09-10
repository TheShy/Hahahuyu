package com.touyuanren.hahahuyu.ui.activity.account;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.UserInfoBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.utils.FoContents;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.utils.FoPreference;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 2016/7/5.
 * 关于用户的基本信息
 */
public class AccountManagementAct extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "AccountManagementAct";
    /**
     * 用户名
     */
    private TextView tv_username;
    /**
     * 用户性别
     */
    private TextView tv_user_sex;
    /**
     * 用户生日
     */
    private TextView tv_user_birth;
    /**
     * 是否实名认证
     */
    private TextView tv_auth;
    /**
     * 实名认证
     */
    private LinearLayout ll_name_authen;
    /**
     * 用户信息
     */
    public UserInfoBean userInfoBean;
    /**
     * 修改登录密码
     */
    private LinearLayout ll_change_loginpwd;
    private String initStartDateTime = "1990年8月15日"; // 初始化开始时间
    /**
     * 用户性别
     */
    private LinearLayout ll_usersex;
    /**
     * 邀请码
     */
    private TextView tv_invite_code;
    /**
     * 日历
     */
    private Calendar cal = Calendar.getInstance();
    private Date date = new Date();// 修改后的生日 你
    /**
     * 保存用户性别
     */
    int userSex;
    /**
     * 设置昵称
     */
    private LinearLayout ll_setNickname;
    /**
     * 用户绑定手机号
     */
    private TextView tv_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_account_management);
        setTitleLeftBtn();
        setTitleName(getString(R.string.account_manage));
        initView();
        initData();
    }

    private void initData() {
        //从服务端获取用户账户信息；
        getUserInfo();
    }

    //获取用户信息
    private void getUserInfo() {
        //提交网络请求
        showLoading();
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "user_info");
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.USER_INFO, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                Log.e(TAG, response);
                Gson gson = new Gson();
                if (response != null) {
                    userInfoBean = gson.fromJson(response, UserInfoBean.class);
                    tv_username.setText(userInfoBean.getData().getInfo().getNick_name());
                    if ("1".equals(userInfoBean.getData().getInfo().getSex())) {
                        tv_user_sex.setText(R.string.man);
                    } else if ("2".equals(userInfoBean.getData().getInfo().getSex())) {
                        tv_user_sex.setText(R.string.woman);
                    } else {
                        tv_user_sex.setText(R.string.secrecy_user);
                    }
                    tv_invite_code.setText(userInfoBean.getData().getInfo().getRcode());
                    FoPreference.putString(FoContents.NICKNAME, userInfoBean.getData().getInfo().getNick_name());
                    FoPreference.putString(FoContents.REGISTER_PHONE, userInfoBean.getData().getInfo().getMobile_phone());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String sd = sdf.format(new Date(Long.parseLong(userInfoBean.getData().getInfo().getBirthday()) * 1000));
                    tv_user_birth.setText(sd);
                    tv_phone.setText(userInfoBean.getData().getInfo().getReal_phone());
                    changeRealNameInfo(userInfoBean.getData().getInfo().getAuth_status(), userInfoBean.getData().getInfo().getReal_name(), userInfoBean.getData().getInfo().getId_card());
                }
                hideLoading();
            }
        });
    }

    private void initView() {
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_user_sex = (TextView) findViewById(R.id.tv_user_sex);
        tv_auth = (TextView) findViewById(R.id.tv_is_auth);
        tv_user_birth = (TextView) findViewById(R.id.tv_user_birth);
        ll_name_authen = (LinearLayout) findViewById(R.id.ll_security_name_authen);
        ll_change_loginpwd = (LinearLayout) findViewById(R.id.ll_change_loginpwd);
        ll_usersex = (LinearLayout) findViewById(R.id.ll_usersex);
        tv_invite_code = (TextView) findViewById(R.id.tv_invite_code);
        ll_setNickname = (LinearLayout) findViewById(R.id.ll_show_nickname);
        tv_phone= (TextView) findViewById(R.id.tv_user_phone);
        //绑定监听
        ll_name_authen.setOnClickListener(this);
        ll_change_loginpwd.setOnClickListener(this);
        ll_usersex.setOnClickListener(this);
        ll_setNickname.setOnClickListener(this);
        tv_user_birth.setOnClickListener(this);
    }

    /**
     * 测试中,如果库中实名认证状态修改,那么安全中心显示修改,但是由于没有修改本地实名认证状态所以点击实名认证条目时进入显示实名认证信息
     */
    private void changeRealNameInfo(String authState, String realName, String idCard) {
        if ("0".equals(authState)) {
            tv_auth.setText(getString(R.string.user_not_shiming));
            FoPreference.putBoolean(FoContents.IS_REAL_NAME, false);
            FoPreference.putString(FoContents.REALNAME, null);
            //用户身份证号保存
            FoPreference.putString(FoContents.ID_CARD, null);
        }
        if ("1".equals(authState)) {
            tv_auth.setText(getString(R.string.user_already_shiming));
            FoPreference.putBoolean(FoContents.IS_REAL_NAME, true);
            FoPreference.putString(FoContents.REALNAME, realName);
            //用户身份证号保存
            FoPreference.putString(FoContents.ID_CARD, idCard);
        }
    }


    @Override
    public void onClick(View v) {
        Intent mIntent;
        switch (v.getId()) {
            //跳转到实名认证界面
            case R.id.ll_security_name_authen:
                mIntent = new Intent(AccountManagementAct.this, RealNameAuthenActivity.class);
                startActivityForResult(mIntent, FoContents.REQUSET_REALNAME);
                break;
            case R.id.ll_change_loginpwd:
                mIntent = new Intent(AccountManagementAct.this, ChangeLoginPwdActivity.class);
                startActivity(mIntent);
                break;
            case R.id.ll_usersex:

                View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.dialog_selcet_sex, null);
                RadioGroup rgSelectSex = (RadioGroup) view.findViewById(R.id.radiogroup_sex_user);
                rgSelectSex.setOnCheckedChangeListener(new MyOnclickListener());
                new AlertDialog.Builder(this).setTitle("选择性别").setView(view)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //上传
                        setUserInfo("sex", userSex + "");
                    }
                }).show();
                break;
            case R.id.ll_show_nickname:
                changeNickName();
                break;
            case R.id.tv_user_birth:
                DatePicker datePicker;
                AlertDialog ad;
                LinearLayout dateTimeLayout = (LinearLayout)
                        LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.select_date, null);
                datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
                int year = cal.get(Calendar.YEAR);
                int monthOfYear = cal.get(Calendar.MONTH);
                int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                datePicker.init(year, monthOfYear, dayOfMonth,
                        new DatePicker.OnDateChangedListener() {

                            public void onDateChanged(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                cal.set(Calendar.YEAR, year);
                                cal.set(Calendar.MONTH, monthOfYear);
                                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                date = new Date(cal.getTime().getTime());
                            }
                        });
                ad = new AlertDialog.Builder(this)
                        .setTitle("请选择生日")
                        .setView(dateTimeLayout)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Log.e("birth", date.getTime() + "");
                                //此时上传生日
                                setUserInfo("birthday", date.getTime() / 1000 + "");
                                String timeString = null;
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                timeString = sdf.format(date);//单位秒
                                tv_user_birth.setText(timeString);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }).show();
                break;
        }
    }

    public class MyOnclickListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.secrecy_sex_user:
                    userSex = 3;
                    break;
                case R.id.rb_man_user:
                    userSex = 1;
                    break;
                case R.id.rb_women_user:
                    userSex = 2;
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FoContents.REQUSET_REALNAME) {
            if (resultCode == FoContents.RESULT_REALNAME) {
                boolean idRealName = FoPreference.getBoolean(FoContents.IS_REAL_NAME);
                if (idRealName) {
                    tv_auth.setText(getString(R.string.user_already_shiming));
                } else {
                    tv_auth.setText(getString(R.string.user_not_shiming));
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 重新设置用户的各种信息
     */
    public void setUserInfo(final String changeType, final String value) {
        //提交网络请求
        showLoading();
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "edit_info");
        formMap.put("set_name", changeType);
        formMap.put("set_value", value);
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.USER_INFO, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                Log.e("userinfo", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ("1".equals(jsonObject.getString("status"))) {
                        if (changeType.equals("sex")) {
                            if ("1".equals(value)) {
                                tv_user_sex.setText(R.string.man);
                            } else if ("2".equals(value)) {
                                tv_user_sex.setText(R.string.woman);
                            } else {
                                tv_user_sex.setText(R.string.secrecy_user);
                            }
                        } else if (changeType.equals("nick_name")) {
                            //设置昵称
                            tv_username.setText(value);
                            FoPreference.putString(FoContents.NICKNAME, value);
                        } else if (changeType.equals("birthday")) {

                        }

                        FoToast.toast(MyApplication.getContext(), jsonObject.getString("msg"));
                    } else {
                        FoToast.toast(MyApplication.getContext(), jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideLoading();
            }
        });
    }


    /**
     * 个性签名;修改用户昵称
     */
    public void changeNickName() {
        final EditText ev_nickName;
        // 自定义的对话框布局
        LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.dialog_change_nickname, null);
        ev_nickName = (EditText) layout.findViewById(R.id.et_nickname);
        new AlertDialog.Builder(this).setTitle("设置昵称").setView(layout)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //上传
                if (TextUtils.isEmpty(ev_nickName.getText().toString())) {
                    FoToast.toast(MyApplication.getContext(), getString(R.string.input_nickname));
                } else {
                    setUserInfo("nick_name", ev_nickName.getText().toString());
                }
            }
        }).show();
    }

}

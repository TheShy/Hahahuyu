package com.touyuanren.hahahuyu.ui.activity.account;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.utils.FoContents;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.utils.FoPreference;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 2016/7/11.
 * 实名认证
 */
public class RealNameAuthenActivity extends BaseActivity {
    /**
     * 用户名字
     */
    private EditText et_name;

    /**
     * 用户身份证号
     */
    private EditText et_id_card;

    /**
     * 提交按钮
     */
    private Button bt_authen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_realname_acthen);
        setTitleLeftBtn();
        setTitleName(getString(R.string.real_name_auth));
        initView();
    }

    private void initView() {
        LinearLayout ll_auth = (LinearLayout) findViewById(R.id.ll_have_auth);
        LinearLayout ll_no_auth = (LinearLayout) findViewById(R.id.ll_no_auth);

        if (FoPreference.getBoolean(FoContents.IS_REAL_NAME)) {
            ll_auth.setVisibility(View.VISIBLE);
            ll_no_auth.setVisibility(View.GONE);

            showReanNameInfo();
        } else {
            ll_auth.setVisibility(View.GONE);
            ll_no_auth.setVisibility(View.VISIBLE);

            et_name = (EditText) findViewById(R.id.et_real_name);
            et_id_card = (EditText) findViewById(R.id.et_ID_Card);
            bt_authen = (Button) findViewById(R.id.bt_authen);

            bt_authen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    authen();
                }
            });
        }
    }

    private void showReanNameInfo() {
        String realName = FoPreference.getString(FoContents.REALNAME);
        String idCard = FoPreference.getString(FoContents.ID_CARD);

        TextView tv_name = (TextView) findViewById(R.id.tv_user_real_name);
        TextView tv_id_card = (TextView) findViewById(R.id.tv_id_card);

        tv_name.setText(realName);
        tv_id_card.setText(idCard);
    }

    /**
     * 输入真实姓名
     */
    private void authen() {
        //参数获取
        String name = et_name.getText().toString();
        final String id_card = et_id_card.getText().toString();

        //校验参数
        if (!checkIsPayParam()) {
            return;
        }
        showLoading();
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "name_auth");
        formMap.put("id_card", id_card);
        formMap.put("realname", name);
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.ISREALNAME, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                Log.e("userinfo", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if ("1".equals(status)) {
                        //保存实名认证状态
                        FoPreference.putBoolean(FoContents.IS_REAL_NAME, true);
                        FoPreference.putString(FoContents.REALNAME, et_name.getText().toString());
                        //用户身份证号保存
                        FoPreference.putString(FoContents.ID_CARD, id_card);
                        FoToast.toast(MyApplication.getContext(), jsonObject.getString("msg"));
                        setResult(FoContents.RESULT_REALNAME);
                        finish();
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
     * 判断真实姓名与省份证信息是否匹配
     *
     * @return
     */
    private boolean checkIsPayParam() {
        String name = et_name.getText().toString();
        String id_card = et_id_card.getText().toString();

        if (TextUtils.isEmpty(name)) {
            FoToast.toast(this, getString(R.string.write_real_name));
            return false;
        }

        if (TextUtils.isEmpty(id_card)) {
            FoToast.toast(this, getString(R.string.write_id_card));
            return false;
        }
        if (!personIdValidation(id_card)) {
            //判断身份证号格式
            FoToast.toast(this, "身份证号格式不正确");
            return false;
        }
        return true;
    }

    /**
     * 验证身份证号是否符合规则
     *
     * @param text 身份证号
     * @return
     */
    public boolean personIdValidation(String text) {
        String regx = "[0-9]{17}[xX]";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }

}

package com.touyuanren.hahahuyu.ui.activity.users;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.squareup.okhttp.Request;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
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
 * Created by Administrator on 2017/7/15/015.
 */

public class ChongzhiActivity extends BaseActivity implements View.OnClickListener {

    private RadioGroup rp;
    private RadioButton rbWechatPayorder;
    private RadioButton rbZhifubaoPayorder;
    private RadioButton rbYinlianPayorder;

    private  Button btn_topay;
    private EditText money;




    public static final String APP_ID = "wxf83448c54b8867cb";
    private IWXAPI api;
    private int method = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chongzhi_activity);
        setTitleLeftBtn();
        setTitleName("充值");

        initView();
        initData();
        initListener();
    }


    private void initView() {
        rp = (RadioGroup) findViewById(R.id.rg_pay_method);
        rbWechatPayorder = (RadioButton) findViewById(R.id.rb_wechat_payorder);
        rbZhifubaoPayorder = (RadioButton) findViewById(R.id.rb_zhifubao_payorder);
        rbYinlianPayorder = (RadioButton) findViewById(R.id.rb_yinlian_payorder);
        btn_topay = (Button) findViewById(R.id.btn_topay);
        money = (EditText) findViewById(R.id.chongzhi_et_money);


    }

    private void initListener() {
        btn_topay.setOnClickListener(this);
        rp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_wechat_payorder:
                        method = 0;
                        break;
                    case R.id.rb_zhifubao_payorder:
                        method = 1;
                        break;
                    case R.id.rb_yinlian_payorder:
                        method = 2;
                        break;
                }
            }
        });
    }

    private void initData() {
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        api.registerApp(APP_ID);

    }

    @Override
    public void onClick(View v) {
        Intent mIntent = null;
        switch (v.getId()) {
            case R.id.btn_topay:
                switch (method) {
                    case 0:
                        topay();
                        break;
                    case 1:
                        FoToast.toast(this, "敬请期待");
                        zhifubo();
                        break;
                    case 2:
                        FoToast.toast(this, "敬请期待");
                        break;
                }
                break;
        }
    }

    private void zhifubo() {
        if (money.getText().toString().isEmpty()) {
            FoToast.showToast("充值金额不能小于0");
            return;
        }
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "weixin");
        formMap.put("money", money.getText().toString());
        formMap.put("pay_type","1");
    }

    private void topay() {
        if (money.getText().toString().isEmpty()) {
            FoToast.showToast("充值金额不能小于0");
            return;
        }
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "weixin");
        formMap.put("money", money.getText().toString());
        formMap.put("pay_type","0");
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.WECHTA_PAY, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                FoToast.showToast("请检查您的网络");
            }

            @Override
            public void onResponse(String response) {

                Log.e("data",response);
                if (response != null) {
                    try {
                        PayReq req = new PayReq();
                        JSONObject json1 = new JSONObject(response);
                        JSONObject json2 = json1.getJSONObject("data");
                        JSONObject json = json2.getJSONObject("weixin");
                        req.appId = json.getString("appid");
                        req.partnerId = json.getString("partnerid");
                        req.prepayId = json.getString("prepayid");
                        req.nonceStr = json.getString("noncestr");
                        req.timeStamp = json.getString("timestamp");
                        Log.e("ss",req.timeStamp);
                        req.packageValue = json.getString("package");
                        req.sign = json.getString("sign");
                        req.extData = "app data"; // optional
                        FoToast.toast(getApplicationContext(),"正在发起支付");
                        api.sendReq(req);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}

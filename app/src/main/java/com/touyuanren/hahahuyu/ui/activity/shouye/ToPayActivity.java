package com.touyuanren.hahahuyu.ui.activity.shouye;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
 * Created by Jing on 2016/9/22.
 * 进行支付;活动支付界面
 */
public class ToPayActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_confirm;
    private String hd_id;
    /**
     * 选择支付方式
     */
    private RadioGroup rg_pay;
    /**
     * 记录支付方式
     */
    private int payWays;
    /**
     * 订单号
     */
    private String orderNumber;
    /**
     * 图片地址
     */
    private String image_url;
    /**
     * 用户订单
     */
    private TextView tv_orderNumber;
    /**
     * 活动内容
     */
    private String huodong_count;
    /**
     * 活动票价
     */
    private String huodong_price;
    /**
     * 活动标题
     */
    private String title;

    /**
     * 活动名称
     */
    private TextView tv_title;

    /**
     * 活动内容
     */
    private TextView tv_huodong_count;

    /**
     * 活动票价
     */
    private TextView tv_huodong_price;

    private String is_upload;

    /**
     * 活动图片
     */
    private ImageView imageView_tp_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_topay);
        setTitleLeftBtn();
        setTitleName(R.string.topay);
        hd_id = getIntent().getStringExtra("huodongId");
        orderNumber = getIntent().getStringExtra("order_sn");
        is_upload = getIntent().getStringExtra("is_upload");
        title = getIntent().getStringExtra("huodong_title");
        huodong_price = getIntent().getStringExtra("huodong_price");
        image_url = getIntent().getStringExtra("huodong_img");
        huodong_count = getIntent().getStringExtra("huodong_count");
        initView();
        initData();
    }

    private void initData() {
        tv_orderNumber.setText(orderNumber);
        tv_title.setText(title);
        tv_huodong_count.setText(huodong_count);
        tv_huodong_price.setText(huodong_price);
        Glide.with(ToPayActivity.this).load(image_url).error(R.drawable.jiazaishibai).into(imageView_tp_bg);
    }

    private void initView() {
        tv_huodong_price = (TextView) findViewById(R.id.tv_order_price);
        tv_huodong_count = (TextView) findViewById(R.id.tv_huodong_count);
        imageView_tp_bg = (ImageView) findViewById(R.id.imageView_tp_bg);
        btn_confirm = (Button) findViewById(R.id.bt_forget_change);
        rg_pay = (RadioGroup) findViewById(R.id.radioGroup);
        tv_orderNumber = (TextView) findViewById(R.id.tv_order_number);
        tv_title = (TextView) findViewById(R.id.tv_hd_title_pay);
        RadioButton rb_yue = (RadioButton) findViewById(R.id.rb_yue);

        rb_yue.setChecked(true);
        btn_confirm.setOnClickListener(this);
        rg_pay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_yue:
                        payWays = 0;
                        break;
                    case R.id.rb_wechatpay:
                        payWays = 1;
                        break;
                    case R.id.rd_zhifubaopay:
                        payWays = 2;
                        break;
                }
            }
        });

    }

    //购票,新增订单
    public void topay() {
        //支付方式为1时，选择余额支付
        showLoading();
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "pay_order");
        //订单号
        formMap.put("order_sn", orderNumber);
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.TICKET_ORDER, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                Log.e("response+ticket", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if ("1".equals(status)) {
//                        FoToast.toast(MyApplication.getContext(), "报名成功");
//                        //判断是否需要作品上传
//                        if (is_upload != null & "1".equals(is_upload)) {
//                            Intent mIntent = new Intent(ToPayActivity.this, CommitWorksAct.class);
//                            mIntent.putExtra("gameId", hd_id);
//                            startActivity(mIntent);
//                        }
//                        finish();

                        Toast.makeText(ToPayActivity.this,"成功",Toast.LENGTH_LONG).show();

                        //订单生成成功，进入支付
                    } else {
                        //暂不处理
                        FoToast.toast(MyApplication.getContext(), "余额不足");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideLoading();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_forget_change:
                if (payWays == 0) {
                    topay();
                } else {
                }
                break;
        }
    }
}

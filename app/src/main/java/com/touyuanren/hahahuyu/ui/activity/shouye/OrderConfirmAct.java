package com.touyuanren.hahahuyu.ui.activity.shouye;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.TicketInfo;
import com.touyuanren.hahahuyu.bean.TicketInfoBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.adapter.TicketAdapter;
import com.touyuanren.hahahuyu.utils.FoContents;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.utils.FoPreference;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Jing on 2016/8/10.
 * 订单确认；活动购票订单
 */
public class OrderConfirmAct extends BaseActivity {
    /**
     * 票券列表
     */
    private ListView lv_ticket;
    /**
     * 活动title
     */
    private TextView tv_title;
    /**
     * 活动id
     */
    private String huodongId;
    /**
     * 票券集合
     */
    private ArrayList<TicketInfo> ticketList = new ArrayList();
    /**
     * 记录票券位置
     */
    private int position_ticket;
    /**
     * 总计费用
     */
    private TextView tv_count;
    /**
     * 前往支付
     */
    private TextView tv_topay;
    /**
     * 活动标题
     */
    private String title;
    /**
     * 活动内容
     */
    private String huodong_count;
    /**
     * 活动图片
     */
    private String huodong_img;
    /**
     * 用户名
     */
    private TextView tv_name;
    /**
     * 手机号
     */
    private TextView tv_phone;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_order_confirm);
        setTitleLeftBtn();
        setTitleName(R.string.order_confirm);
        huodongId = getIntent().getStringExtra("huodong_id");
        title = getIntent().getStringExtra("huodong_title");
        huodong_count = getIntent().getStringExtra("huodong_count");
        huodong_img = getIntent().getStringExtra("huodong_img");
        initView();
        initData();
    }

    private void initData() {
        getTicketInfo();
    }

    private void initView() {
        tv_name = (TextView) findViewById(R.id.tv_name_order_act);
        tv_phone = (TextView) findViewById(R.id.tv_phone_order_act);
        lv_ticket = (ListView) findViewById(R.id.lv_ticket_order);
        tv_count = (TextView) findViewById(R.id.tv_count_price);
        tv_topay = (TextView) findViewById(R.id.tv_topay_order);
        tv_title = (TextView) findViewById(R.id.tv_hd_title);
        tv_title.setText(title);
        lv_ticket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position_ticket = position;
                view.setSelected(true);
                tv_count.setText(ticketList.get(position).getPrice() + "元");
            }
        });
        tv_topay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交订单
                topay();
            }
        });
        tv_phone.setText(FoPreference.getString(FoContents.REGISTER_PHONE));
        tv_name.setText(FoPreference.getString(FoContents.NICKNAME==""?"未命名":FoContents.NICKNAME));
    }


    //购票,新增订单
    public void topay() {
        showLoading();
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "add_order");
        formMap.put("hd_id", huodongId);
        formMap.put("type", "2");
        formMap.put("is_mark", "0");
        formMap.put("id_value", ticketList.get(position_ticket).getId());
        formMap.put("price", ticketList.get(position_ticket).getPrice());

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
                        //订单生成成功，进入支付
                        String orderNumber = jsonObject.getJSONObject("data").getString("order_sn");
                        String huodong_price = jsonObject.getJSONObject("data").getString("price")+"￥";
                        Intent mIntent = new Intent(OrderConfirmAct.this, ToPayActivity.class);
                        mIntent.putExtra("huodongId", huodongId);
                        mIntent.putExtra("order_sn", orderNumber);
                        mIntent.putExtra("huodong_title", title);
                        mIntent.putExtra("huodong_count", huodong_count);
                        mIntent.putExtra("huodong_img", huodong_img);
                        mIntent.putExtra("huodong_price", huodong_price);
                        startActivity(mIntent);
//                        if ("0".equals(ticketList.get(position_ticket).getPrice())) {
//                            FoToast.toast(MyApplication.getContext(), "购票成功");
//                        }
                    } else {


                        //111,已经生成订单，未支付
                        FoToast.toast(MyApplication.getContext(), "您已购买票券，请去支付");
                        String orderNumber = jsonObject.getJSONObject("data").getString("order_sn");
                        String huodong_price = jsonObject.getJSONObject("data").getString("price")+"￥";
                        Intent mIntent = new Intent(OrderConfirmAct.this, ToPayActivity.class);
                        mIntent.putExtra("huodongId", huodongId);
                        mIntent.putExtra("order_sn", orderNumber);
                        mIntent.putExtra("huodong_title", title);
                        mIntent.putExtra("huodong_count", huodong_count);
                        mIntent.putExtra("huodong_img", huodong_img);
                        mIntent.putExtra("huodong_price", huodong_price);
                        startActivity(mIntent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideLoading();
            }
        });
    }

    public void getTicketInfo() {
        showLoading();
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "activity");

        formMap.put("hd_id", huodongId);
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.UPLOAD_INFO, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if ("1".equals(status)) {
                        TicketInfoBean mTicketInfoBean = new Gson().fromJson(response, TicketInfoBean.class);
                        ticketList = (ArrayList) mTicketInfoBean.getData().getList();
                        lv_ticket.setAdapter(new TicketAdapter(MyApplication.getContext(), ticketList));
                    } else {
                        //暂不处理
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideLoading();
            }
        });
    }
}

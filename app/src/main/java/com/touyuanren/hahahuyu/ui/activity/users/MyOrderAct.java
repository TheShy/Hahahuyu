package com.touyuanren.hahahuyu.ui.activity.users;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.OrderInfo;
import com.touyuanren.hahahuyu.bean.OrderInfoBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.adapter.OrderAdapter;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 2016/9/19.
 * 用户全部订单
 */
public class MyOrderAct extends BaseActivity {
    private ListView lv_order;
    private ArrayList<OrderInfo> orderList;
    private OrderAdapter adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_myorder);
        setTitleLeftBtn();
        setTitleName(R.string.my_order);
        lv_order = (ListView) findViewById(R.id.lv_userorder_act);
        initData();
    }

    public void initData() {
        orderList = new ArrayList<>();
        adapter = new OrderAdapter(orderList, MyApplication.getContext());
        lv_order.setAdapter(adapter);
        getOrderInfo();
    }

    //获取订单列表
    public void getOrderInfo() {
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "order_list");
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.TICKET_ORDER, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                Log.e("response+order", response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            OrderInfoBean mOrderInfoBean = new Gson().fromJson(response, OrderInfoBean.class);
                            ArrayList<OrderInfo> mList = (ArrayList<OrderInfo>) mOrderInfoBean.getData().getList();
                            adapter.setList(mList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                hideLoading();
            }
        });
    }
}

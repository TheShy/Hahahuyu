package com.touyuanren.hahahuyu.ui.activity.users;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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
import com.touyuanren.hahahuyu.ui.activity.shouye.ToPayActivity;
import com.touyuanren.hahahuyu.ui.adapter.TicketAdapter;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/31/031.
 */

public class GamePersonAct extends BaseActivity implements View.OnClickListener {

    private String gameTitle;
    private String gameId;

    private EditText et_name;
    private EditText et_phone;
    private TextView tv_topay;
    private ArrayList<TicketInfo> ticketList = new ArrayList();
    private int position_ticket;
    private TextView tv_count;
    private ListView lv_ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_person_activity);
        gameId = getIntent().getStringExtra("gameid");
        gameTitle = getIntent().getStringExtra("title");
        setTitleLeftBtn();
        setTitleName("观众报名");
        initView();
        initData();
    }

    private void initData() {
        getTicketInfo();
    }
    private void initView() {
        et_name = (EditText) findViewById(R.id.game_person_et_name);
        et_phone = (EditText) findViewById(R.id.game_person_et_user);
        tv_topay = (TextView) findViewById(R.id.game_person_tv_apaly);
        lv_ticket = (ListView) findViewById(R.id.game_person_lv);
        tv_count = (TextView) findViewById(R.id.game_person_tv_price);
        //绑定监听
        tv_topay.setOnClickListener(this);

        lv_ticket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position_ticket = position;
                view.setSelected(true);
                tv_count.setText(ticketList.get(position).getPrice() + "元");
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.game_person_tv_apaly:
                //对上面做非空判断
                if (TextUtils.isEmpty(et_name.getText().toString())) {
                    FoToast.toast(MyApplication.getContext(), getString(R.string.please_input_name));
                    return;
                }
                if (TextUtils.isEmpty(et_phone.getText().toString())) {
                    FoToast.toast(MyApplication.getContext(), getString(R.string.please_input_phone));
                    return;
                }
                uploadPlayerInfo();

                break;
        }
    }
    public void getTicketInfo() {
        showLoading();
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "activity");
        formMap.put("hd_id", gameId);
        formMap.put("type","2");
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.UPLOAD_INFO, formMap, new StringCallback() {
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
    //上传用户信息
    public void  uploadPlayerInfo() {
        //提交网络请求
        showLoading();
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "huodong");
        formMap.put("id", gameId);
        formMap.put("realname", et_name.getText().toString());
        formMap.put("mobile", et_phone.getText().toString());
        formMap.put("t_id", ticketList.get(position_ticket).getId());
        formMap.put("price", ticketList.get(position_ticket).getPrice());
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.UPLOAD_INFO, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                Log.e("response+upload", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if ("1".equals(status)) {
                        if ("0".equals(ticketList.get(position_ticket).getPrice())) {
                            FoToast.toast(MyApplication.getContext(), "报名成功");
                        } else {
                            //进行支付
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            String orderNumber = jsonObject1.getString("order_sn");
                            Intent mIntent = new Intent(GamePersonAct.this, ToPayActivity.class);
                            mIntent.putExtra("order_sn", orderNumber);
                            mIntent.putExtra("hd_name", gameTitle);
                            mIntent.putExtra("huodongId", jsonObject1.getString("hd_id"));
                            mIntent.putExtra("is_upload", jsonObject1.getString("is_upload"));
                            startActivity(mIntent);
                            finish();
                        }
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
}

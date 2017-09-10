package com.touyuanren.hahahuyu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.activity.shouye.OrderConfirmAct;
import com.touyuanren.hahahuyu.ui.adapter.TicketAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jing on 2016/3/4.
 * 活动购票界面；票券列表(暂时停止使用票券列表)，将订单和票券合并到一个界面
 */
public class BuyTicketActivity extends BaseActivity {
    private ListView lv_ticket;
    /**
     * 票券数据集合
     */
    private List mList;
    /**
     * 票券列表适配器
     */
    private TicketAdapter mTicketAdapter;
    /**
     * 活动id
     */
    private String huodongId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyticket_activity);
        setTitleLeftBtn();
        setTitleName(R.string.baoming);
        huodongId = getIntent().getStringExtra("huodong_id");
        lv_ticket = (ListView) findViewById(R.id.lv_buyticket_act);
        initData();
        lv_ticket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到订单页面
                Intent mIntent = new Intent(BuyTicketActivity.this, OrderConfirmAct.class);
                startActivity(mIntent);
            }
        });
    }

    private void initData() {

        mList = new ArrayList();
        for (int i = 0; i < 7; i++) {
            mList.add("");
        }
        mTicketAdapter = new TicketAdapter(MyApplication.getContext(), mList);
        lv_ticket.setAdapter(mTicketAdapter);

    }


}

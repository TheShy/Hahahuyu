package com.touyuanren.hahahuyu.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jing on 2016/3/21.
 * 相关活动的实时报道;详情完全从后台获取
 */
public class GameReportActivity extends BaseActivity {
    /**
     * 报道列表
     */
    private ListView listReport;
    /**
     * list数据集合
     */
    private List report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_report_activity);
        initView();
        initData();
        listReport.setAdapter(new ReportAdapter());
    }

    private void initData() {
        report = new ArrayList();
        for (int i = 0; i < 10; i++) {
            report.add("a" + i);
        }
    }

    private void initView() {
        listReport = (ListView) findViewById(R.id.list_game_report_act);

    }

    //适配器
    public class ReportAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return report.size();
        }

        @Override
        public Object getItem(int position) {
            return report.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.mb_list_report, null);
            return view;
        }
    }

}

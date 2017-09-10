package com.touyuanren.hahahuyu.ui.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.DateText;
import com.touyuanren.hahahuyu.ui.adapter.DateAdapter;
import com.touyuanren.hahahuyu.utils.DateComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jing on 2016/3/18.
 * 实时展示比赛进程；根据比赛性质，展示不同效果
 * 时间轴进行实现
 */
public class GameProcessActivity extends BaseActivity {
    /**
     * 展示时间轴的listview
     */
    private ListView tlList;
    // 数据list
    private List<DateText> list;
    // 列表适配器
    private DateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_process_activity);
        tlList = (ListView) findViewById(R.id.list_game_process_act);
        list = new ArrayList<DateText>();
        // 添加测试数据
        addData();
        // 将数据按照时间排序
        DateComparator comparator = new DateComparator();
        Collections.sort(list, comparator);
        // listview绑定适配器
        adapter = new DateAdapter(this, list);
        tlList.setAdapter(adapter);
    }

    private void addData() {
        DateText date1 = new DateText("20140710", "撒大声地");
        DateText date2 = new DateText("20140709", "撒萨达");
        DateText date3 = new DateText("20140726", "撒大ADS");
        DateText date4 = new DateText("20140710", "撒达到对萨达撒地");
        DateText date5 = new DateText("20140711", "撒大阿瑟的萨达地");
        DateText date6 = new DateText("20140713", "撒撒大大地");
        DateText date7 = new DateText("20140712", "撒大鼎折覆餗地");
        DateText date8 = new DateText("20140714", "撒大请问阿尔阿斯顿");
        DateText date9 = new DateText("20140709", "撒大亲爱额问问乔恩地");
        DateText date10 = new DateText("20140705", "撒 请问请问地");
        DateText date11 = new DateText("20140729", "撒请问额外确认声地");
        DateText date12 = new DateText("20140725", "撒大按时打算");
        DateText date13 = new DateText("20140716", "撒大爱上大声地");
        DateText date14 = new DateText("20140711", "撒其味无穷地");
        DateText date15 = new DateText("20140710", "撒大请问我期待地");
        DateText date16 = new DateText("20140711", "撒大声萨达");
        DateText date17 = new DateText("20140712", "阿斯达");
        DateText date18 = new DateText("20140711", "撒大声大声道");
        DateText date19 = new DateText("20140715", "阿斯顿撒打算23 ");
        DateText date20 = new DateText("20140723", "范德萨发生");
        DateText date21 = new DateText("20140718", "阿萨德飞洒");
        DateText date22 = new DateText("20140706", "撒打算打算");
        DateText date23 = new DateText("20140714", "撒打算");
        DateText date24 = new DateText("20140726", "轻微的城市的方式");
        DateText date25 = new DateText("20140725", "阿斯达阿斯达现在");
        DateText date26 = new DateText("20140723", "代购费多少自行车");
        DateText date27 = new DateText("20140721", "多撒阿萨德时打算");
        DateText date28 = new DateText("20140716", "爱上大声地啊地");
        DateText date29 = new DateText("20140712", "阿斯蒂芬当我师傅");
        DateText date30 = new DateText("20140710", "撒大声大声道");
        list.add(date1);
        list.add(date2);
        list.add(date3);
        list.add(date4);
        list.add(date5);
        list.add(date6);
        list.add(date7);
        list.add(date8);
        list.add(date9);
        list.add(date10);
        list.add(date11);
        list.add(date12);
        list.add(date13);
        list.add(date14);
        list.add(date15);
        list.add(date16);
        list.add(date17);
        list.add(date18);
        list.add(date19);
        list.add(date20);
        list.add(date21);
        list.add(date22);
        list.add(date23);
        list.add(date24);
        list.add(date25);
        list.add(date26);
        list.add(date27);
        list.add(date28);
        list.add(date29);
        list.add(date30);
    }


}

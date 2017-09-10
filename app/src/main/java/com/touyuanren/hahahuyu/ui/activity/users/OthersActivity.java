package com.touyuanren.hahahuyu.ui.activity.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.activity.LifoActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/2/27 0027.
 */
public class OthersActivity extends BaseActivity {
    public GridView gridview_other;

    //定义图标数组
    private int[] imageRes = {
            R.drawable.gv_shipin,
            R.drawable.gv_qunzu,
            R.drawable.gv_beibao};

    //定义图标下方的名称数组
    private String[] name = {
            "视频",
            "群组",
            "礼佛"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        setTitleLeftBtn();
        setTitleName(R.string.other_titlt);
        init();
    }

    public void init(){
        gridview_other = (GridView) findViewById(R.id.gridview_other);

        int length = imageRes.length;

        //生成动态数组，并且转入数据
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", imageRes[i]);//添加图像资源的ID
            map.put("ItemText", name[i]);//按序号做ItemText
            lstImageItem.add(map);
        }
        //生成适配器的ImageItem 与动态数组的元素相对应
        SimpleAdapter saImageItems = new SimpleAdapter(OthersActivity.this,
                lstImageItem,//数据来源
                R.layout.gv_btn_item,//item的XML实现

                //动态数组与ImageItem对应的子项
                new String[]{"ItemImage", "ItemText"},

                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[]{R.id.img_shoukuan, R.id.txt_shoukuan});
        //添加并且显示
        gridview_other.setAdapter(saImageItems);
        //添加消息处理
        gridview_other.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent;
                switch (position) {
                    case 0:
                        Toast.makeText(OthersActivity.this,R.string.jingqingqidai,Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        mIntent = new Intent(OthersActivity.this,UserGroupActivity.class);
                        startActivity(mIntent);
                        break;
                    case 2:
                        mIntent = new Intent(OthersActivity.this, LifoActivity.class);
                        startActivity(mIntent);
                        break;
                }
            }
        });
    }
}

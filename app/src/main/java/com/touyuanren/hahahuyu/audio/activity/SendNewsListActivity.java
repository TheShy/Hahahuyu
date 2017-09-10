package com.touyuanren.hahahuyu.audio.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.audio.adapter.SendNewsListAdatper;
import com.touyuanren.hahahuyu.audio.bean.SendNewsInfo;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.utils.DensityUtil;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.touyuanren.hahahuyu.widget.LoadListView;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/21/021.
 */

public class SendNewsListActivity extends BaseActivity implements LoadListView.ILoadListener{

    private LoadListView sendnews_lv;
    private ArrayList<SendNewsInfo> mList= new ArrayList<>();
    private SendNewsListAdatper mAdapter;
    /**
     * 控制页数
     */
    private int page = 1;
    /**
     * 刷新控件
     */
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_news_list_activity);
        setTitleLeftBtn();
        setTitleName("我发布的文章");

        initView();
    }

    private void initView() {
        sendnews_lv = (LoadListView) findViewById(R.id.send_news_lv);
        sendnews_lv.setAdapter(mAdapter);
        sendnews_lv.setInterface(this);
//        initSwipeLayout();
        initData();

    }
    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new SendNewsListAdatper(mList, this);
        sendnews_lv.setAdapter(mAdapter);
//        totalList = new ArrayList<>();
        getNewsInfo(page=1);
        sendnews_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                FoToast.showToast("点点点~~~");
//                NewsInfo.DataBean.InfoBean mInfoBean = (NewsInfo.DataBean.InfoBean) adapter.getItemAtPosition(position);
//                Intent mIntent = new Intent(SendNewsListActivity.this, NewsListDetali.class);
////                mIntent.putExtra("id", mList.get(position).getId());
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("info", (Serializable) mInfoBean);
//                mIntent.putExtras(bundle);
//                startActivity(mIntent);
            }
        });
    }



    private void initSwipeLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.send_news_list_swipe);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        //swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        int size = DensityUtil.dip2pxComm(this, 25);
        // 第一次进入页面的时候显示加载进度条
        swipeRefreshLayout.setProgressViewOffset(false, 0, size);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getNewsInfo(page);
            }
        });
    }

    private void getNewsInfo(final int page) {

//        swipeRefreshLayout.measure(0,0);
//        swipeRefreshLayout.setRefreshing(false);

        Map<String, String> formMap = new HashMap<>();
//        formMap.put("page", "" + page);
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.MY_SEND_NEWS, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getApplicationContext(), R.string.intent_error, Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(String response) {
               Log.e("data", response);
                if (response != null) {
                    mList.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonObject1 = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonObject1.length(); i++) {
                            JSONObject jsonObject2 = (JSONObject) jsonObject1.get(i);
                            SendNewsInfo mInfo = new Gson().fromJson(response,SendNewsInfo.class);
                            mList.add(mInfo);
                        }
                        sendnews_lv.setAdapter(new SendNewsListAdatper(mList,getApplicationContext()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                hideLoading();
            }
        });
    }




    @Override
    public void onLoad() {
        page++;
        getNewsInfo(page);
    }
}

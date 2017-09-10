package com.touyuanren.hahahuyu.ui.activity.users;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.DongTaiInfo;
import com.touyuanren.hahahuyu.bean.DongTaiInfoBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.activity.account.PublishDongTaiAct;
import com.touyuanren.hahahuyu.ui.adapter.DongTaiAdapter;
import com.touyuanren.hahahuyu.utils.FoContents;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.widget.LoadListView;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 2016/10/26.
 * 用户动态
 */
public class UserDongTaiAct extends BaseActivity implements LoadListView.ILoadListener {
    private static final String TAG = "UserDongTaiAct";
    private LoadListView lv_dongtai;
    private ArrayList<DongTaiInfo> dongtaiList = new ArrayList<>();
    /**
     * 控制页数
     */
    private int page = 1;
    private DongTaiAdapter adapter;
    private ArrayList<DongTaiInfo> totalList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_dongtai);
        lv_dongtai = (LoadListView) findViewById(R.id.llv_dongtai_act);
        lv_dongtai.setInterface(this);
        adapter = new DongTaiAdapter(dongtaiList, this);
        lv_dongtai.setAdapter(adapter);
        setTitleLeftBtn();
        setTitleName(R.string.my_dongtai);
        setTitleRightBtn(R.string.fabu, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入发布
                Intent mIntnet = new Intent(UserDongTaiAct.this, PublishDongTaiAct.class);
                startActivityForResult(mIntnet, FoContents.REQUEST_DONGTAI);
            }
        });
        getDongtaiList(page);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            getDongtaiList(1);
        }

    }

    public void getDongtaiList(final int page) {
        showLoading();
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "list_dt");
        formMap.put("page", page + "");
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.USER_DONGTAI, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                Log.e(TAG + page, response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            dongtaiList.clear();
                            DongTaiInfoBean dongTaiInfoBean = new Gson().fromJson(response, DongTaiInfoBean.class);
                            dongtaiList = (ArrayList<DongTaiInfo>) dongTaiInfoBean.getData().getList();

//                            if (dongtaiList.size() < 1) {
//                                FoToast.toast(MyApplication.getContext(),"没有更多了");
//                                adapter.setList(dongtaiList);
//                                lv_dongtai.loadComplete();
//                                return;
//                            }
                            if (page == 1) {
                                totalList.clear();
                                adapter.setList(dongtaiList);
                                totalList.addAll(dongtaiList);
                            } else {
                                totalList.addAll(dongtaiList);
                                adapter.setList(totalList);
                                lv_dongtai.loadComplete();
                            }
                        }
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
        getDongtaiList(page);
    }
}

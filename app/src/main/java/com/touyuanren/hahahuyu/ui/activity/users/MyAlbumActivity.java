package com.touyuanren.hahahuyu.ui.activity.users;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.UserAlbumInfo;
import com.touyuanren.hahahuyu.bean.UserAlbumInfoBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.activity.PleaseUploadPicAct;
import com.touyuanren.hahahuyu.ui.activity.account.MyPicListAct;
import com.touyuanren.hahahuyu.ui.activity.account.PublishAlbumActivity;
import com.touyuanren.hahahuyu.ui.adapter.MyAlbumAdapter;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 2016/10/19.
 * 我的相册
 */
public class MyAlbumActivity extends BaseActivity {

    private GridView gv_album;
    /**
     * 相册列表
     */
    private ArrayList<UserAlbumInfo> albumList;
    private ArrayList<Object> totalList;
    private MyAlbumAdapter adapter;
    private int page = 1;
    private static final String TAG = "MyAlbumActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_myalbum);
        setTitleLeftBtn();
        setTitleName("我的相册");
        setTitleRightBtn(R.string.publish_album, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建相册
                Intent mIntent = new Intent(MyAlbumActivity.this, PublishAlbumActivity.class);
                startActivityForResult(mIntent, 121);
            }
        });
        gv_album = (GridView) findViewById(R.id.gv_myalbum_act);
        gv_album.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0) {
//
//                } else
                if (((UserAlbumInfo) totalList.get(position)).getCount().equals("0")) {
                    //进入相册
                    Intent mIntent = new Intent(MyAlbumActivity.this, PleaseUploadPicAct.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("index", position);
                    bundle.putSerializable("albumlist", albumList);
                    mIntent.putExtras(bundle);
                    startActivity(mIntent);
                } else {
                    //相册张数不为0
                    Intent mIntent = new Intent(MyAlbumActivity.this, MyPicListAct.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("index", position);
                    bundle.putSerializable("albumlist", albumList);
                    mIntent.putExtras(bundle);
                    startActivity(mIntent);
                }
            }
        });
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAlbum(page);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            page=1;
            getAlbum(page);
        }

    }

    private void initData() {
        albumList = new ArrayList<>();
        totalList = new ArrayList<>();
//        totalList.add("add");
        adapter = new MyAlbumAdapter(MyApplication.getContext(), totalList);
        gv_album.setAdapter(adapter);
        getAlbum(page);
    }

    public void getAlbum(final int page) {
        showLoading();
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "u_album_list");
        formMap.put("type", "1");
        formMap.put("page", "" + page);
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.USER_ALBUM, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                Log.e(TAG, response);
                if (response != null) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            UserAlbumInfoBean userAlbumInfoBean = new Gson().fromJson(response, UserAlbumInfoBean.class);
                            ArrayList<UserAlbumInfo> mList = (ArrayList<UserAlbumInfo>) userAlbumInfoBean.getData().getList();
                            if (page == 1) {
                                albumList.clear();
                                albumList.addAll(mList);
                                totalList.clear();
//                                totalList.add("add");
                                totalList.addAll(mList);
                            } else {
                                albumList.addAll(mList);
                                totalList.addAll(mList);
                            }
                            adapter.setList(totalList);
                        } else {

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

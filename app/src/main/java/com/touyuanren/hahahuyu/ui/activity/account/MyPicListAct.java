package com.touyuanren.hahahuyu.ui.activity.account;

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
import com.touyuanren.hahahuyu.bean.UserPicInfo;
import com.touyuanren.hahahuyu.bean.UserPicInfoBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.activity.LargeImageActivity;
import com.touyuanren.hahahuyu.ui.adapter.UserAlbumAdapter;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 2016/11/1.
 * 展示我的相册
 */
public class MyPicListAct extends BaseActivity {
    private static final String TAG = "MyPicListAct";
    private static final String INDEX = "index";
    private static final String TOTAL = "total";
    private static final String IMGS = "imgs";
    /**
     * 相册列表
     */
    private GridView gv_album;
    /**
     * 相册id
     */
    private String albumId;

    private ArrayList<UserPicInfo> picList = new ArrayList<>();
    private UserAlbumAdapter adapter;
    private int index;
    private ArrayList<UserAlbumInfo> albumList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mypic_list);
        Bundle bundle = getIntent().getExtras();
        index = bundle.getInt("index");
        albumList = (ArrayList<UserAlbumInfo>) bundle.getSerializable("albumlist");
        albumId = albumList.get(index).getAlbum_id();
        gv_album = (GridView) findViewById(R.id.gv_album_user_act);
        adapter = new UserAlbumAdapter(picList, MyApplication.getContext());
        gv_album.setAdapter(adapter);
        setTitleLeftBtn();
        setTitleRightBtn(R.string.upload, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上传照片
                Intent mIntent = new Intent(MyPicListAct.this, UploadPicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("index", index);
                bundle.putString("act", "MyPicListAct");
                bundle.putSerializable("albumlist", albumList);
                mIntent.putExtras(bundle);
                startActivityForResult(mIntent, 111);
            }
        });
        getPicList();
        viewAction();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            if (resultCode == RESULT_OK) {
                getPicList();
            }
        }
    }

    public void viewAction() {
        gv_album.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //进入大图展示
                Intent intent = new Intent(MyPicListAct.this, LargeImageActivity.class);
                ArrayList<String> mList = new ArrayList<String>();
                for (int i = 0; i < picList.size(); i++) {
                    mList.add(HttpApi.ROOT_PATH + picList.get(i).getPath());
                }
                Bundle bundle = new Bundle();
                bundle.putInt(TOTAL, picList.size());
                bundle.putInt(INDEX, position);
                bundle.putStringArrayList(IMGS, mList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void getPicList() {
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "pic_list");
        formMap.put("album_id", albumId);
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
                            UserPicInfoBean userPicInfoBean = new Gson().fromJson(response, UserPicInfoBean.class);
                            picList = (ArrayList<UserPicInfo>) userPicInfoBean.getData().getList();
                            adapter.setList(picList);
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

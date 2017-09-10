package com.touyuanren.hahahuyu.ui.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.PublishAlbumBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 2016/10/25.
 * 创建相册
 */
public class PublishAlbumActivity extends BaseActivity {
    private EditText et_title;
    private EditText et_describe;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_publish_album);
        mIntent = getIntent();
        setTitleLeftBtn();
        setTitleName(R.string.publish_album);
        setTitleRightBtn(R.string.save, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建相册
                if (TextUtils.isEmpty(et_title.getText().toString())) {
                    FoToast.toast(MyApplication.getContext(), "请输入标题");
                    return;
                }
                createAlbum(et_title.getText().toString(), et_describe.getText().toString());

            }
        });
        et_title = (EditText) findViewById(R.id.et_title_album_act);
        et_describe = (EditText) findViewById(R.id.et_describe_album_act);
    }

    public void createAlbum(String title, String describe) {
        showLoading();
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "add");
        formMap.put("album_name", title);
        formMap.put("type", "1");
        formMap.put("album_desc", describe);
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.USER_ALBUM, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            PublishAlbumBean publishAlbumBean = new Gson().fromJson(response, PublishAlbumBean.class);
                            FoToast.toast(MyApplication.getContext(), publishAlbumBean.getMsg());
//                            Intent mIntent = new Intent(PublishAlbumActivity.this, PleaseUploadPicAct.class);
//                            mIntent.putExtra("album_id", publishAlbumBean.getData().getAlbum_id());
//                            startActivity(mIntent);
                            setResult(RESULT_OK, mIntent);
                            finish();
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

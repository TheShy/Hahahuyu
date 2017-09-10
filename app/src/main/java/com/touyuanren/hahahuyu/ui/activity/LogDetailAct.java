package com.touyuanren.hahahuyu.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.LogDetail;
import com.touyuanren.hahahuyu.bean.LogDetailBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Jing on 2016/11/15.
 * 日志详情，加日志评论
 */
public class LogDetailAct extends BaseActivity {
    private static final String TAG = "LogDetailAct";
    @BindView(R.id.tv_title_logdetail)
    TextView tvTitleLogdetail;
    @BindView(R.id.tv_nickname_logdetail)
    TextView tvNicknameLogdetail;
    @BindView(R.id.tv_date_logdetail)
    TextView tvDateLogdetail;
    @BindView(R.id.tv_msg_logdetail)
    TextView tvMsgLogdetail;
    private String id;
    private LogDetail logDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_log_detail);
        ButterKnife.bind(this);
        setTitleLeftBtn();
        setTitleName("详情");
        id = getIntent().getStringExtra("id");
        Log.e(TAG, id);
        getLogList();
    }

    public void getLogList() {
        showLoading();
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "xiangqing");
        formMap.put("id", id);
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.PUBLISH_LOG, formMap, new StringCallback() {
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
                            LogDetailBean logDetailBean = new Gson().fromJson(response, LogDetailBean.class);
                            logDetail = logDetailBean.getData().getBlog_list();

                            //开始赋值
                            tvDateLogdetail.setText(logDetail.getAdd_time());
                            tvMsgLogdetail.setText(logDetail.getContent());
                            tvNicknameLogdetail.setText(logDetail.getNick_name());
                            tvTitleLogdetail.setText(logDetail.getTitle());
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

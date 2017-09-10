package com.touyuanren.hahahuyu.ui.activity.news;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.TopicInfo;
import com.touyuanren.hahahuyu.bean.TopicInfoBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.adapter.TopicAdapter;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.touyuanren.hahahuyu.widget.LoadListViewForScrollView;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/6 0006.
 */
public class ZMTcommentsActivity extends BaseActivity implements LoadListViewForScrollView.ILoadListener {

    /**
     * 评论列表
     */
    private LoadListViewForScrollView lvComment;
    private ArrayList<TopicInfo> commentList;
    private ArrayList<TopicInfo> totalList;
    private TopicAdapter adapter;
    private int page = 1;
    private TextView tv_write;
    private TextView tv_pinglun_fabiao;
    private EditText et_comment;
    private PopupWindow popupWindow;

    InputMethodManager imm;

    private String contentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zmt_comments);

        setTitleLeftBtn();

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        lvComment = (LoadListViewForScrollView) findViewById(R.id.llv_topiclist_zimeiti);
        tv_write = (TextView) findViewById(R.id.tv_write_comment_zimeiti);
        lvComment.setInterface(this);

        contentId = getIntent().getStringExtra("content_id");

        initData();
        getTopicInfo(page);

        tv_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publish_comment();
            }
        });
    }

    private void initData() {
        commentList = new ArrayList();
        totalList = new ArrayList<>();
        adapter = new TopicAdapter(MyApplication.getContext(), commentList);
        lvComment.setAdapter(adapter);

    }

    public void getTopicInfo(final int page) {
        Map formMap = new HashMap();
        formMap.put("act", "comment_list");
        formMap.put("hd_id", contentId);
        formMap.put("page", "" + page);
        formMap.put("type", "" + 7);
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.COMMENT, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(ZMTcommentsActivity.this, R.string.intent_error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            commentList.clear();
                            TopicInfoBean topicInfoBean = new Gson().fromJson(response, TopicInfoBean.class);
                            commentList = (ArrayList<TopicInfo>) topicInfoBean.getData().getList();
                            if (page == 1) {
                                totalList.clear();
                                totalList.addAll(commentList);
                                adapter.setList(totalList);
                            } else {
                                totalList.addAll(commentList);
                                adapter.setList(totalList);
                                lvComment.loadComplete();
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    //发表评论
    private void publish_comment() {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(MyApplication.getContext()).inflate(
                R.layout.pop_comment, null);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        et_comment = (EditText) contentView.findViewById(R.id.et_comment_pop);
        tv_pinglun_fabiao = (TextView) contentView.findViewById(R.id.tv_pinglun_fabiao);
        tv_pinglun_fabiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_comment.getText().toString())) {
                    FoToast.toast(ZMTcommentsActivity.this, "请输入内容！");
                } else {
                    commitComment(et_comment.getText().toString());
                }
            }
        });
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("mengdd", "onTouch : ");
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        Drawable d = getResources().getDrawable(R.color.white);
        popupWindow.setBackgroundDrawable(d);
        // 设置好参数之后再show
        popupWindow.showAtLocation(tv_write, Gravity.BOTTOM, 0, 0);
    }


    public void commitComment(String comment) {
        showLoading();
        Map formMap = new HashMap();
        formMap.put("act", "add_comment");
        formMap.put("hd_id", contentId);
        formMap.put("content", comment);
        formMap.put("type", "" + 7);
        formMap.put("parent_id", "" + 0);
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.COMMENT, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            FoToast.toast(MyApplication.getContext(), "发布成功");
                            page = 1;
                            getTopicInfo(page);

                            popupWindow.dismiss();
                            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                            et_comment.setText("");
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
        getTopicInfo(page);
    }
}

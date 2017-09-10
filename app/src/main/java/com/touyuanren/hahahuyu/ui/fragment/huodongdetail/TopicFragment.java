package com.touyuanren.hahahuyu.ui.fragment.huodongdetail;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.HuoDongInfo;
import com.touyuanren.hahahuyu.bean.TopicInfo;
import com.touyuanren.hahahuyu.bean.TopicInfoBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.adapter.TopicAdapter;
import com.touyuanren.hahahuyu.ui.fragment.BaseFragment;
import com.touyuanren.hahahuyu.utils.DensityUtil;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.touyuanren.hahahuyu.widget.LoadListView;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 2016/6/24.
 * 活动评论
 */
public class TopicFragment extends BaseFragment implements LoadListView.ILoadListener {
    private static final String TAG = "TopicFragment";
    //将活动对象传入
    private HuoDongInfo mHuoDongInfo;
    private View mView;
    /**
     * 评论列表
     */
    private LoadListView lvComment;
    private ArrayList<TopicInfo> commentList;
    private ArrayList<TopicInfo> totalList;
    private TopicAdapter adapter;
    private int page = 1;
    /**
     * 刷新控件
     */
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tv_write,tv_pinglun_fabiao;
    private EditText et_comment;
    private PopupWindow popupWindow;

    @Override
    public View getShowView(LayoutInflater inflater) {
        mView = inflater.inflate(R.layout.frag_topic, null);
        Bundle mBundle = getArguments();
        mHuoDongInfo = (HuoDongInfo) mBundle.getSerializable("huodongInfo");
        lvComment = (LoadListView) mView.findViewById(R.id.lv_topiclist);
        tv_write = (TextView) mView.findViewById(R.id.tv_write_comment_topic);
        tv_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publish_comment();
            }
        });
        lvComment.setInterface(this);
        initSwipeLayout();
        initData();
        return mView;
    }

    private void initData() {
        commentList = new ArrayList();
        totalList = new ArrayList<>();
        adapter = new TopicAdapter(MyApplication.getContext(), commentList);
        lvComment.setAdapter(adapter);
        getTopicInfo(page);
    }

    private void initSwipeLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.srl_list_topic_frag);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        //swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        int size = DensityUtil.dip2pxComm(getActivity(), 25);
        // 第一次进入页面的时候显示加载进度条
        swipeRefreshLayout.setProgressViewOffset(false, 0, size);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getTopicInfo(page);
            }
        });
    }

    public static final TopicFragment newInstance(HuoDongInfo mHuoDongInfo) {
        TopicFragment f = new TopicFragment();
        Bundle bdl = new Bundle();
        bdl.putSerializable("huodongInfo", mHuoDongInfo);
        f.setArguments(bdl);
        return f;
    }

    public void getTopicInfo(final int page) {
        Map formMap = new HashMap();
        formMap.put("act", "comment_list");
        formMap.put("hd_id", mHuoDongInfo.getId());
        formMap.put("page", "" + page);
        formMap.put("type", "" + 5);
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.COMMENT, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(),"网络错误，请检查您的网络连接！",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                Log.e(TAG, response);
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
                        swipeRefreshLayout.setRefreshing(false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    @Override
    public void onLoad() {
        page++;
        getTopicInfo(page);
    }

    //发表评论
    private void publish_comment() {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(MyApplication.getContext()).inflate(
                R.layout.pop_comment, null);
        et_comment = (EditText) contentView.findViewById(R.id.et_comment_pop);
        tv_pinglun_fabiao = (TextView) contentView.findViewById(R.id.tv_pinglun_fabiao);
            tv_pinglun_fabiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(et_comment.getText().toString())) {
                        FoToast.toast(MyApplication.getContext(), "请输入评论内容");
                    } else {
                        commitComment(et_comment.getText().toString());
                    }
                }
            });
        et_comment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //do something;
                    //提交评论
                    if (TextUtils.isEmpty(et_comment.getText().toString())) {
                        FoToast.toast(MyApplication.getContext(), "请输入评论内容");
                    } else {
                        commitComment(et_comment.getText().toString());
                    }
                    return true;
                }
                return false;
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
        Map formMap = new HashMap();
        formMap.put("act", "add_comment");
        formMap.put("hd_id", mHuoDongInfo.getId());
        formMap.put("content", comment);
        formMap.put("type", "" + 5);
        formMap.put("parent_id", "" + 0);
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.COMMENT, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(),"网络错误，请检查您的网络连接！",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                Log.e(TAG, response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            FoToast.toast(MyApplication.getContext(), "发布成功");
                            page=1;
                            getTopicInfo(page);
                            popupWindow.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

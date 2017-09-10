package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.Img_listEntity;
import com.touyuanren.hahahuyu.bean.LogInfo;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.LargeImageActivity;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.touyuanren.hahahuyu.widget.GridviewForScrollView;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 2016/9/30.
 */
public class LogListAdapter extends BaseAdapter {

    private static final String INDEX = "index";
    private static final String TOTAL = "total";
    private static final String IMGS = "imgs";
    ArrayList<LogInfo> mList = new ArrayList<>();
    Context context;
    //评论弹窗
    private PopupWindow popupWindow;
    private EditText et_comment;
    //记录当前位置
    private int position_current;

    public LogListAdapter() {
    }

    public LogListAdapter(ArrayList<LogInfo> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    public void setList(ArrayList<LogInfo> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override

    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        position_current = position;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_log_list, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_logtitle_item);
            viewHolder.fabuTime = (TextView) convertView.findViewById(R.id.tv_logtime_item);
            viewHolder.content = (TextView) convertView.findViewById(R.id.tv_logcontent_item);
            viewHolder.gridView = (GridviewForScrollView) convertView.findViewById(R.id.gv_pic_log_item);
            viewHolder.nickName = (TextView) convertView.findViewById(R.id.tv_nickname_item);
            viewHolder.ima_comment = (ImageView) convertView.findViewById(R.id.ima_comment_log_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(mList.get(position).getTitle());
        viewHolder.fabuTime.setText(mList.get(position).getAdd_time());
        viewHolder.content.setText(mList.get(position).getContent());
        final ArrayList<Img_listEntity> picList = (ArrayList<Img_listEntity>) mList.get(position).getImg_list();
        viewHolder.gridView.setAdapter(new LogPicAdapter(context, picList));
        viewHolder.nickName.setText(mList.get(position).getNick_name());
        final ArrayList<String> mPathList = new ArrayList<String>();
        for (int i = 0; i < picList.size(); i++) {
            mPathList.add(HttpApi.ROOT_PATH + picList.get(i).getImg_path());
        }
        viewHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, LargeImageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(TOTAL, picList.size());
                bundle.putInt(INDEX, position);
                bundle.putStringArrayList(IMGS, mPathList);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        viewHolder.ima_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进行评论
                publish_comment(mList.get(position_current).getId(), viewHolder.ima_comment);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        TextView title, fabuTime, content, nickName;
        GridviewForScrollView gridView;
        ImageView ima_comment;
    }


    //发表评论
    private void publish_comment(final String logId, View view) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(MyApplication.getContext()).inflate(
                R.layout.pop_comment, null);
        et_comment = (EditText) contentView.findViewById(R.id.et_comment_pop);
        et_comment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //do something;
                    //提交评论
                    if (TextUtils.isEmpty(et_comment.getText().toString())) {
                        FoToast.toast(MyApplication.getContext(), "请输入评论内容");
                    } else {
                        commitComment(et_comment.getText().toString(), logId);
                    }
                    return true;
                }
                return false;
            }
        });
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //提交评论
//                if (TextUtils.isEmpty(et_comment.getText().toString())) {
//                    FoToast.toast(MyApplication.getContext(), "请输入评论内容");
//                } else {
//                    commitComment(et_comment.getText().toString(), logId);
//                }
//
//            }
//        });
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
        Drawable d = context.getResources().getDrawable(R.color.white);
        popupWindow.setBackgroundDrawable(d);
        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);
//        popupWindow.showAtLocation(tv_write, Gravity.BOTTOM, 0, 0);
    }

    public void commitComment(String comment, String contentId) {
        Map formMap = new HashMap();
        formMap.put("act", "add_comment");
        formMap.put("hd_id", contentId);
        formMap.put("content", comment);
        formMap.put("type", "" + 6);
        formMap.put("parent_id", "" + 0);
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.COMMENT, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            FoToast.toast(MyApplication.getContext(), "发布成功");
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

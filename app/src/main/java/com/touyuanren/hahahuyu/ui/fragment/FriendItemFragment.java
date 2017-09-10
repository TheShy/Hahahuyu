package com.touyuanren.hahahuyu.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.news.ZiMeiTiNewsAct;

/**
 * Created by Administrator on 2017/4/18 0018.
 */
public class FriendItemFragment extends BaseFragment {
    View mView;

    ImageView ima_friend_item;
    TextView tv_friend_title,tv_friend_item_time;

    String content_id,user_id,content,img_url,text_title,text_time;

    @Override
    public View getShowView(LayoutInflater inflater) {
        mView = inflater.inflate(R.layout.fragment_frienditem, null);

        this.content_id = getArguments().getString("content_id");
        this.user_id = getArguments().getString("user_id");
        this.content = getArguments().getString("content");
        this.img_url = getArguments().getString("img_url");
        this.text_title = getArguments().getString("text_title");
        this.text_time = getArguments().getString("text_time");

        Log.i("asdhgjhgkg","测试："+content_id+"   /   "+img_url);

        init();
        initData();
        return mView;
    }


    public void init(){
        ima_friend_item = (ImageView) mView.findViewById(R.id.ima_friend_item);
        tv_friend_title = (TextView) mView.findViewById(R.id.tv_friend_title);
        tv_friend_item_time = (TextView) mView.findViewById(R.id.tv_friend_item_time);
    }

    public void initData(){
        Glide.with(getContext()).load(HttpApi.ROOT_PATH +img_url).error(R.drawable.jiazaishibai).into(ima_friend_item);
        tv_friend_title.setText(text_title);
        tv_friend_item_time.setText(text_time);

        ima_friend_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getActivity(), ZiMeiTiNewsAct.class);
                mIntent.putExtra("content", content);
                mIntent.putExtra("user_id", user_id);
                mIntent.putExtra("content_id", content_id);
                startActivity(mIntent);
            }
        });
    }


    public static FriendItemFragment getInstance(String content_id,String user_id,String content,String img_url,String text_title,String text_time){

        FriendItemFragment instance = new FriendItemFragment();

        Bundle args = new Bundle();

        args.putString("content_id", content_id);
        args.putString("user_id", user_id);
        args.putString("content", content);
        args.putString("img_url", img_url);
        args.putString("text_title", text_title);
        args.putString("text_time", text_time);

        instance.setArguments(args);

        return instance;

    }
}
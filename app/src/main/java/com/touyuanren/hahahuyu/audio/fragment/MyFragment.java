package com.touyuanren.hahahuyu.audio.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.audio.activity.SendAcitivity;
import com.touyuanren.hahahuyu.audio.activity.SendNewsListActivity;
import com.touyuanren.hahahuyu.ui.fragment.BaseFragment;

/**
 * Created by Administrator on 2017/7/13/013.
 */

public class MyFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private LinearLayout send_news, send_audio, send_vedio, send;


    @Override
    public View getShowView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.my_fragment, null);
        initView();
        return view;
    }

    private void initView() {
        send = (LinearLayout) view.findViewById(R.id.my_fragment_layout_send);

        send_news = (LinearLayout) view.findViewById(R.id.my_fragment_layout_send_news);
        send_audio = (LinearLayout) view.findViewById(R.id.my_fragment_layout_send_audio);
        send_vedio = (LinearLayout) view.findViewById(R.id.my_fragment_layout_send_video);

        send.setOnClickListener(this);
        send_news.setOnClickListener(this);
        send_audio.setOnClickListener(this);
        send_vedio.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_fragment_layout_send:
                Intent send = new Intent(getActivity(), SendAcitivity.class);
                startActivity(send);
                break;
            case R.id.my_fragment_layout_send_news:
                Intent send_news = new Intent(getActivity(), SendNewsListActivity.class);
                startActivity(send_news);
                break;
            case R.id.my_fragment_layout_audio:

                break;
            case R.id.my_fragment_layout_send_video:

                break;
        }

    }
}

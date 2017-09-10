package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.util.Log;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.GameVideoInfo;
import com.touyuanren.hahahuyu.ui.adapter.recycleradapter.BaseViewHolder;
import com.touyuanren.hahahuyu.ui.adapter.recycleradapter.CommonAdapter;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Administrator on 2017/8/4/004.
 */

public class GameVideoListAdapter extends CommonAdapter<GameVideoInfo> {
    public GameVideoListAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }
    @Override
    public void convert(BaseViewHolder holder, GameVideoInfo gameVideoInfo) {
        holder.setText(R.id.tv_title_video, gameVideoInfo.getTitle());
        JCVideoPlayerStandard jcVideoPlayer = holder.getView(R.id.videoplayer_list);
        holder.setText(R.id.tv_time_video,gameVideoInfo.getAdd_time());
        jcVideoPlayer.setUp("http://www.hahahuyu.com/"+gameVideoInfo.getContent(), JCVideoPlayer.SCREEN_LAYOUT_LIST,gameVideoInfo.getDescription());
        Log.e("url", "http://www.hahahuyu.com/"+gameVideoInfo.getContent());
//        jcVideoPlayer.setUp("http://video.jiecao.fm/8/17/bGQS3BQQWUYrlzP1K4Tg4Q__.mp4",JCVideoPlayer.SCREEN_LAYOUT_LIST,vedioInfo.getName());
    }
}

package com.touyuanren.hahahuyu.audio.adapter;

import android.content.Context;
import android.util.Log;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.audio.bean.VedioInfo;
import com.touyuanren.hahahuyu.ui.adapter.recycleradapter.BaseViewHolder;
import com.touyuanren.hahahuyu.ui.adapter.recycleradapter.CommonAdapter;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


/**
 * Created by Liang on 2017/8/4 0004.
 * recyclerview 的适配器
 */

public class VideoAdapterRcy extends CommonAdapter<VedioInfo> {
    public VideoAdapterRcy(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }


    @Override
    public void convert(BaseViewHolder holder, VedioInfo vedioInfo) {
        holder.setText(R.id.tv_title_video, vedioInfo.getName());
        JCVideoPlayerStandard jcVideoPlayer = holder.getView(R.id.videoplayer_list);
        holder.setText(R.id.tv_time_video,vedioInfo.getAdd_time());
        jcVideoPlayer.setUp("http://www.hahahuyu.com/"+vedioInfo.getContent(),JCVideoPlayer.SCREEN_LAYOUT_LIST,vedioInfo.getTitle());
        Log.e("url", "http://www.hahahuyu.com/"+vedioInfo.getContent());
//        jcVideoPlayer.setUp("http://video.jiecao.fm/8/17/bGQS3BQQWUYrlzP1K4Tg4Q__.mp4",JCVideoPlayer.SCREEN_LAYOUT_LIST,vedioInfo.getName());
    }
}

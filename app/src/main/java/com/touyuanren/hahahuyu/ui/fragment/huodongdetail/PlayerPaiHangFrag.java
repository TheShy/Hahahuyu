package com.touyuanren.hahahuyu.ui.fragment.huodongdetail;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.adapter.PlayerSortAdapter;
import com.touyuanren.hahahuyu.ui.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * Created by Jing on 2016/9/22.
 * 选手排行
 */
public class PlayerPaiHangFrag extends BaseFragment {
    /**
     * 总布局
     */
    private View mView;
    private PlayerSortAdapter adapter;
    private GridView gv_player;
    private ArrayList<String> playerList;
    @Override
    public View getShowView(LayoutInflater inflater) {
        mView = inflater.inflate(R.layout.frag_player_sort, null);
        gv_player = (GridView) mView.findViewById(R.id.gv_player_sort_game);
        initData();
        return mView;
    }
    private void initData() {
        playerList = new ArrayList<>();
        for (int i=0;i<18;i++){
            playerList.add("ddddddddddddd");
        }
        adapter=new PlayerSortAdapter(MyApplication.getContext(),playerList);
        gv_player.setAdapter(adapter);
    }
}

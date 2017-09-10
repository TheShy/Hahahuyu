package com.touyuanren.hahahuyu.ui.fragment.huodongdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.adapter.PPLAdapter;
import com.touyuanren.hahahuyu.ui.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * Created by Jing on 2016/6/24.
 * 展示活动相册
 */
public class HuoDongAlbumFrag extends BaseFragment {
    /**
     * 上下文对象
     */
    private Context mContext;
    /**
     * 展示相册缩略图
     */
    private ArrayList picList;

    private RecyclerView recyclerView;
    private PPLAdapter adapter;

    @Override
    public View getShowView(LayoutInflater inflater) {
        View mView = inflater.inflate(R.layout.frag_album_huodong, null);

        recyclerView = (RecyclerView) mView.findViewById(R.id.gv_album_huodong);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter = new PPLAdapter());

        adapter.replaceAll(getData());
//                Intent intent = new Intent(getActivity(), LargeImageActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("index", position);
//                bundle.putInt("total", 9);
////                bundle.putInt("total", list.size());
//                ArrayList<String> list_url = new ArrayList<>();
//                for (int i=0;i<9;i++){
//                    list_url.add("ddd");
//                }
////                for (PhotosEntity photosEntity : list) {
////                    list_url.add(photosEntity.getImage());
////                }
//                bundle.putStringArrayList("imgs", list_url);
//                intent.putExtras(bundle);
//                startActivity(intent);
        return mView;
    }

    public ArrayList<Integer> getData() {
        ArrayList<Integer> list = new ArrayList<>();

        list.add( R.drawable.ppl_6);
        list.add( R.drawable.ppl_7);
        list.add( R.drawable.ppl_8);
        list.add( R.drawable.ppl_9);
        list.add( R.drawable.ppl_0);
        list.add( R.drawable.ppl_1);
        list.add( R.drawable.ppl_2);
        list.add( R.drawable.ppl_3);
        list.add( R.drawable.ppl_4);
        list.add( R.drawable.ppl_5);
        list.add( R.drawable.ppl_6);
        list.add( R.drawable.ppl_7);
        list.add( R.drawable.ppl_8);
        list.add( R.drawable.ppl_9);
        list.add( R.drawable.ppl_0);
        list.add( R.drawable.ppl_1);
        list.add( R.drawable.ppl_2);
        list.add( R.drawable.ppl_3);
        list.add( R.drawable.ppl_4);
        list.add( R.drawable.ppl_5);
        list.add( R.drawable.ppl_6);
        list.add( R.drawable.ppl_7);
        list.add( R.drawable.ppl_8);
        list.add( R.drawable.ppl_9);
        list.add( R.drawable.ppl_0);
        list.add( R.drawable.ppl_1);
        list.add( R.drawable.ppl_2);
        list.add( R.drawable.ppl_3);
        list.add( R.drawable.ppl_4);
        list.add( R.drawable.ppl_5);
        list.add( R.drawable.ppl_6);
        list.add( R.drawable.ppl_7);
        list.add( R.drawable.ppl_8);
        list.add( R.drawable.ppl_9);
        list.add( R.drawable.ppl_0);
        list.add( R.drawable.ppl_1);
        list.add( R.drawable.ppl_2);
        list.add( R.drawable.ppl_3);
        list.add( R.drawable.ppl_4);
        list.add( R.drawable.ppl_5);
        list.add( R.drawable.ppl_6);
        list.add( R.drawable.ppl_7);
        list.add( R.drawable.ppl_8);
        list.add( R.drawable.ppl_9);
        list.add( R.drawable.ppl_0);
        list.add( R.drawable.ppl_1);
        list.add( R.drawable.ppl_2);
        list.add( R.drawable.ppl_3);
        list.add( R.drawable.ppl_4);
        list.add( R.drawable.ppl_5);
        list.add( R.drawable.ppl_6);
        list.add( R.drawable.ppl_7);
        list.add( R.drawable.ppl_8);
        list.add( R.drawable.ppl_9);
        list.add( R.drawable.ppl_0);
        list.add( R.drawable.ppl_1);
        list.add( R.drawable.ppl_2);
        list.add( R.drawable.ppl_3);
        list.add( R.drawable.ppl_4);
        list.add( R.drawable.ppl_5);
        list.add( R.drawable.ppl_6);
        list.add( R.drawable.ppl_7);
        list.add( R.drawable.ppl_8);
        list.add( R.drawable.ppl_9);
        list.add( R.drawable.ppl_0);
        list.add( R.drawable.ppl_1);
        list.add( R.drawable.ppl_2);
        list.add( R.drawable.ppl_3);
        list.add( R.drawable.ppl_4);
        list.add( R.drawable.ppl_5);
        list.add( R.drawable.ppl_6);
        list.add( R.drawable.ppl_7);
        list.add( R.drawable.ppl_8);
        list.add( R.drawable.ppl_9);
        list.add( R.drawable.ppl_0);
        list.add( R.drawable.ppl_1);
        list.add( R.drawable.ppl_2);
        list.add( R.drawable.ppl_3);
        list.add( R.drawable.ppl_4);
        list.add( R.drawable.ppl_5);
        return list;
    }
}

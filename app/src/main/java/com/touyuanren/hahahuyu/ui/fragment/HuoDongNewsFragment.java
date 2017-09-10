package com.touyuanren.hahahuyu.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.PlayerMessage;
import com.touyuanren.hahahuyu.ui.adapter.MasonryAdapter;

import java.util.ArrayList;

/**
 * Created by Jing on 2016/1/27.
 * 展示活动资讯
 */
public class HuoDongNewsFragment extends Fragment {
    //测试用的图片地址
    private String url2 = "http://ww1.sinaimg.cn/crop.0.0.1080.1080.1024/005C5Stxjw8eu18m1c6q8j30u00u0di0.jpg";
    private String url = "http://img4.duitang.com/uploads/item/201405/10/20140510180701_HaMGF.jpeg";
    //瀑布流实现选手展示；
    private View mView;
    private RecyclerView recyclerView;
    /**
     * 瀑布流单元的list集合
     */
    private ArrayList<PlayerMessage> productList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.saishi_redian, null);
        recyclerView = (RecyclerView) mView.findViewById(R.id.recycler);
        //设置layoutManager
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //设置adapter
        initData();
        final MasonryAdapter adapter = new MasonryAdapter(productList);
        recyclerView.setAdapter(adapter);
        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(12);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置监听
        adapter.setOnClickListener(new MasonryAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int postion) {

            }

            @Override
            public void onItemLongClickListener(View view, int position) {
                adapter.deleteItem(position);

            }
        });

        return mView;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        productList = new ArrayList<PlayerMessage>();
        for (int i = 0; i < 100; i++) {
            PlayerMessage product = new PlayerMessage();
            product.setImg(url);
            product.setTitle("我是产品" + i);
            productList.add(product);
        }
        for (int i = 100; i < 200; i++) {
            PlayerMessage product = new PlayerMessage();
            product.setImg(url2);
            product.setTitle("我是产品" + i);
            productList.add(product);
        }
    }



    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space;
            }
        }
    }
}
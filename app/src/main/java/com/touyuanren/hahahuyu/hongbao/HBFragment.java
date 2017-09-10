package com.touyuanren.hahahuyu.hongbao;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.touyuanren.hahahuyu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 卡片Fragment
 *
 * @author xmuSistone
 */
@SuppressLint({"HandlerLeak", "NewApi", "InflateParams"})
public class HBFragment extends Fragment {

    private HBSlidePanel.CardSwitchListener cardSwitchListener;

    private String imagePaths[] = {R.drawable.hongbao_no_chai+"",
            "assets://wall02.jpg", "assets://wall03.jpg",
            "assets://wall04.jpg", "assets://wall05.jpg",
            "assets://wall06.jpg", "assets://wall07.jpg",
            "assets://wall08.jpg", "assets://wall09.jpg",
            "assets://wall10.jpg", "assets://wall11.jpg",
            "assets://wall12.jpg", "assets://wall01.jpg",
            "assets://wall02.jpg", "assets://wall03.jpg",
            "assets://wall04.jpg", "assets://wall05.jpg",
            "assets://wall06.jpg", "assets://wall07.jpg",
            "assets://wall08.jpg", "assets://wall09.jpg",
            "assets://wall10.jpg", "assets://wall11.jpg", "assets://wall12.jpg"}; // 24个图片资源名称

    private String names[] = {"郭富城", "刘德华", "张学友", "李连杰", "成龙", "谢霆锋", "李易峰",
            "霍建华", "胡歌", "曾志伟", "吴孟达", "梁朝伟", "周星驰", "赵本山", "郭德纲", "周润发", "邓超",
            "王祖蓝", "王宝强", "黄晓明", "张卫健", "徐峥", "李亚鹏", "郑伊健"}; // 24个人名

    private List<HBDataItem> dataList = new ArrayList<HBDataItem>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hb_layout, null);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        HBSlidePanel slidePanel = (HBSlidePanel) rootView
                .findViewById(R.id.image_slide_panel);
        cardSwitchListener = new HBSlidePanel.CardSwitchListener() {

            @Override
            public void onShow(int index) {
            }

            @Override
            public void onCardVanish(int index, int type) {
            }

            @Override
            public void onItemClick(View cardView, int index) {
            }
        };
        slidePanel.setCardSwitchListener(cardSwitchListener);

        prepareDataList();
        slidePanel.fillData(dataList);
    }

    private void prepareDataList() {
        int num = imagePaths.length;

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < num; i++) {
                HBDataItem dataItem = new HBDataItem();
                dataItem.userName = names[i];
                dataItem.imagePath = imagePaths[i];
                dataItem.likeNum = (int) (Math.random() * 10);
                dataItem.imageNum = (int) (Math.random() * 6);
                dataList.add(dataItem);
            }
        }
    }

}

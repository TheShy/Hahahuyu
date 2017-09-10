package com.touyuanren.hahahuyu.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.touyuanren.hahahuyu.Application.MyApplication;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

//import com.weifan.weifan.utils.L;

public class LargeImgViewPagerAdapter extends PagerAdapter {

    private List<String> list;

    public LargeImgViewPagerAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
//        photoView.setImageURI(Uri.parse(list.get(position)));
        Glide.with(MyApplication.getContext()).load(list.get(position)).into(photoView);
//        photoView.setImageResource(R.drawable.a2);
        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                L.i("LargeImageAdapter", "长按事件");
                return false;
            }
        });
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
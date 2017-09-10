package com.touyuanren.hahahuyu.ui.adapter;

import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.PlayerMessage;

import java.util.List;

/**
 * Created by Jing on 2016/1/18.
 */
public class MasonryAdapter extends RecyclerView.Adapter<MasonryAdapter.MasonryView> {
    private OnItemClickListener mListener;
    private List<PlayerMessage> products;
    //图片缓存路径
    private String imgFile = Environment.getExternalStorageState();

    public MasonryAdapter(List<PlayerMessage> list) {
        products = list;
    }

    public interface OnItemClickListener {
        void onItemClickListener(View view, int postion);

        void onItemLongClickListener(View view, int postion);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.masonry_item, viewGroup, false);
        return new MasonryView(view);
    }

    @Override
    public void onBindViewHolder(final MasonryView masonryView, final int position) {
//获取请求的图片地址，如果本地已经缓存，则直接加载本地；如果没有缓存则进行网络加载
        String imaUrl = products.get(position).getImg();
//        String url = imgFile + imaUrl;

        Glide.with(MyApplication.getContext()).load(imaUrl).placeholder(R.drawable.a1).error(R.drawable.a1).into(masonryView.imageView);
        masonryView.textView.setText(products.get(position).getTitle());
        if (mListener != null) {
            masonryView.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutposition = masonryView.getLayoutPosition();
                    mListener.onItemClickListener(masonryView.itemView, layoutposition);

                }
            });
            masonryView.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutposition = masonryView.getLayoutPosition();
                    mListener.onItemLongClickListener(masonryView.itemView, layoutposition);
                    return false;
                }
            });
        }

    }

    /**
     * 删除item
     *
     * @param position
     */
    public void deleteItem(int position) {

        products.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class MasonryView extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public MasonryView(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.masonry_item_img);
            textView = (TextView) itemView.findViewById(R.id.masonry_item_title);
        }

    }

}
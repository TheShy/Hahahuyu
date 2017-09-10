package com.touyuanren.hahahuyu.ui.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.utils.FoContents;

import java.io.File;
import java.util.List;

//展示小照片的adapter
public class PlayerImgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> list;
    private OnItemClickListener listener;

    public PlayerImgAdapter(List<String> list) {
        this.list = list;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setList(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        //判断list中的数据，如果为空，则显示添加图片的页面
        String value = list.get(position);
        if (TextUtils.isEmpty(value)) {
            return FoContents.ADD_TYPE;
        } else {
            return FoContents.IMAGE_TYPE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //判断view的类型 返回相对应的holder
        switch (viewType) {
            case FoContents.ADD_TYPE:
                View addView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_view, parent, false);
                return new AddViewHolder(addView);
            case FoContents.IMAGE_TYPE:
                View imageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_info_pic, parent, false);
                return new ImageViewHolder(imageView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int type = getItemViewType(position);
        final int location = position;

        switch (type) {
            case FoContents.ADD_TYPE:
                AddViewHolder addViewHolder = (AddViewHolder) holder;
                final View view = addViewHolder.imageView;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemClick(view, location, FoContents.ADD_TYPE);
                        }
                    }
                });
                break;

            case FoContents.IMAGE_TYPE:
                ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                final ImageView simpleDraweeView = imageViewHolder.simpleDraweeView;
                simpleDraweeView.setLayoutParams(new LinearLayout.LayoutParams(
                        (int) ((MyApplication.screenWidth - 10) / 4),
                        (int) ((MyApplication.screenWidth - 10) / 4)));

                Uri.fromFile(new File(list.get(position)));
                simpleDraweeView.setImageURI(Uri.fromFile(new File(list.get(position))));
//                simpleDraweeView.setImageURI(Uri.parse(list.get(position)));
                simpleDraweeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemClick(simpleDraweeView, location, FoContents.IMAGE_TYPE);
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    private class AddViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public AddViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.add_picture_view);
        }
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView simpleDraweeView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView = (ImageView) itemView.findViewById(R.id.setting_image_view);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View parent, int position, int type);
    }

}
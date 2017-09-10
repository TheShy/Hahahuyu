package com.touyuanren.hahahuyu.ui.fragment.huodongdetail;

import android.view.LayoutInflater;
import android.view.View;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.hongbao.HBFragment;
import com.touyuanren.hahahuyu.ui.fragment.BaseFragment;

/**
 * Created by Jing on 2016/6/24.
 * 展示红包墙;发红包，抢红包
 */
public class HongBaoWallFrag extends BaseFragment implements View.OnClickListener {
    /**
     * 页面总布局
     */
    private View mView;

    @Override
    public View getShowView(LayoutInflater inflater) {
        mView = inflater.inflate(R.layout.frag_hongbao_wall, null);
        initImageLoader();
        if (savedInstanceState_ == null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new HBFragment())
                    .commitAllowingStateLoss();
        }
        return mView;
    }

    @SuppressWarnings("deprecation")
    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getContext())
                .memoryCacheExtraOptions(480, 800)
                // default = device screen dimensions
                .threadPoolSize(3)
                // default
                .threadPriority(Thread.NORM_PRIORITY - 1)
                // default
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13) // default
                .discCacheSize(50 * 1024 * 1024) // 缓冲大小
                .discCacheFileCount(100) // 缓冲文件数目
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(getContext())) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs().build();

        // 2.单例ImageLoader类的初始化
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }

    @Override
    public void onClick(View view) {

    }
}

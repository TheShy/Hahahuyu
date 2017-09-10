package com.touyuanren.hahahuyu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.adapter.LargeImgViewPagerAdapter;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.touyuanren.hahahuyu.widget.HackyViewPager;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 显示活动圈的图片的大图界面，可以保存图片
 */
public class LargeImageActivity extends BaseActivity {

    private static final String TAG = "LargeImageActivity";

    private static final String INDEX = "index";
    private static final String TOTAL = "total";
    private static final String IMGS = "imgs";

    /**
     * 展示图片的viewpager
     */
    private HackyViewPager viewPager;
    /**
     * 当前是第几张图片
     */
    TextView textView_index;
    /**
     * 总共是第几张图片
     */
    TextView textView_total;

    /**
     * 保存图片
     */
    TextView textSave;
    /**
     * 点击回退,finish
     */
    TextView textView_back;
    private int index, total;
    /**
     *图片集合
     */
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_img);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        index = bundle.getInt(INDEX);
        total = bundle.getInt(TOTAL);
        list = bundle.getStringArrayList(IMGS);
        if (list != null && list.size() > 0) {
            initView();
        } else {
            FoToast.showToast("没有要查看的图片列表！");
            finish();
        }
    }

    private void initView() {
        viewPager = (HackyViewPager) findViewById(R.id.viewpager_large_img);
        textView_index = (TextView) findViewById(R.id.text_view_large_img_index);
        textView_total = (TextView) findViewById(R.id.text_view_large_img_total);
        textSave = (TextView) findViewById(R.id.text_save_img);
        textView_back = (TextView) findViewById(R.id.text_large_img_back);

        textView_index.setText(String.valueOf((index + 1)));
        textView_total.setText(String.valueOf(total));

        viewPager.setAdapter(new LargeImgViewPagerAdapter(list));
        viewPager.setCurrentItem(index);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                textView_index.setText(String.valueOf((position + 1)));
                index = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

        textView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//
        textSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                OkHttpUtils.get()
//                        .url(list.get(index))
//                        .build()
//                        .execute(new ImageDownLoadCallback(
//                                WeiFanApplication.imageFolderPath, getPhotoFileName()));
            }
        });
    }

    /**
     * 使用系统当前日期加以调整作为照片的名称
     *
     * @return 照片的名称
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss", Locale.getDefault());
        return dateFormat.format(date) + ".jpg";
    }

    private class ImageDownLoadCallback extends FileCallBack {

        public ImageDownLoadCallback(String destFileDir, String destFileName) {
            super(destFileDir, destFileName);
        }

        @Override
        public void inProgress(float progress) {

        }

        @Override
        public void onError(Request request, Exception e) {
            FoToast.showToast("图片保存失败！");
        }

        @Override
        public void onResponse(File response) {
            FoToast.showToast("图片保存成功！");
        }

    }

}
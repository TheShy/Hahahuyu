package com.touyuanren.hahahuyu.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.activity.MusicActivity;
import com.touyuanren.hahahuyu.utils.recorder.Util;

/**
 * Author by Jaiky, Email jaikydota@163.com, Date on 3/2/2015.
 * PS: Not easy to write code, please indicate.
 */
public class PublishDialog extends Dialog implements ViewPager.OnPageChangeListener{
    private RelativeLayout rlMain;

     Context mcontext;

    private LinearLayout llBtnArticle, llBtnMiniBlog, llBtnLetter, llBtnPhoto, ll_other , llBtnMenu,music;

    private Handler handler;

    private ImageView ivMenu;


    public PublishDialog(Context context) {
        this(context, R.style.main_publishdialog_style);
    }

    private PublishDialog(Context context, int theme) {
        super(context, theme);
        mcontext = context;
        init();
    }


    /**
     * 初始化
     */
    private void init() {
        handler = new Handler();
        //填充视图
        setContentView(R.layout.main_dialog_publish);
        rlMain = (RelativeLayout) findViewById(R.id.mainPublish_dialog_rlMain);
        llBtnArticle = (LinearLayout) findViewById(R.id.mainPublish_dialog_llBtnArticle);
        llBtnMiniBlog = (LinearLayout) findViewById(R.id.mainPublish_dialog_llBtnMiniBlog);
        llBtnLetter = (LinearLayout) findViewById(R.id.mainPublish_dialog_llBtnLetter);
        llBtnPhoto = (LinearLayout) findViewById(R.id.mainPublish_dialog_llBtnPhoto);
        ll_other = (LinearLayout) findViewById(R.id.linearLayout_other);
        music= (LinearLayout) findViewById(R.id.mainPublish_dialog_music);
        llBtnMenu = (LinearLayout) findViewById(R.id.mainPublish_dialog_llBtnMenu);
        ivMenu = (ImageView) findViewById(R.id.mainPublish_dialog_ivMenu);


        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mcontext, MusicActivity.class);
                    mcontext.startActivity(mIntent);
            }
        });
        llBtnMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                outputDialog();
            }
        });
        rlMain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                outputDialog();
            }
        });

    }



    /**
     * 进入对话框（带动画）
     */
    private void inputDialog() {
        llBtnArticle.setVisibility(View.INVISIBLE);
        llBtnMiniBlog.setVisibility(View.INVISIBLE);
        llBtnLetter.setVisibility(View.INVISIBLE);
        llBtnPhoto.setVisibility(View.INVISIBLE);
        ll_other.setVisibility(View.INVISIBLE);
        music.setVisibility(View.INVISIBLE);
        //背景动画
        rlMain.startAnimation(AnimationUtils.loadAnimation(mcontext, R.anim.mainactivity_fade_in));
        //菜单按钮动画
        ivMenu.startAnimation(AnimationUtils.loadAnimation(mcontext, R.anim.mainactivity_rotate_right));
        //选项动画
        llBtnArticle.setVisibility(View.VISIBLE);
        llBtnArticle.startAnimation(AnimationUtils.loadAnimation(mcontext, R.anim.mainactivity_push_bottom_in));
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                llBtnMiniBlog.setVisibility(View.VISIBLE);
                llBtnMiniBlog.startAnimation(AnimationUtils.loadAnimation(mcontext, R.anim.mainactivity_push_bottom_in));
            }
        }, 100);
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                llBtnLetter.setVisibility(View.VISIBLE);
                llBtnLetter.startAnimation(AnimationUtils.loadAnimation(mcontext, R.anim.mainactivity_push_bottom_in));
            }
        }, 200);
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                llBtnPhoto.setVisibility(View.VISIBLE);
                llBtnPhoto.startAnimation(AnimationUtils.loadAnimation(mcontext, R.anim.mainactivity_push_bottom_in));
            }
        }, 300);
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                ll_other.setVisibility(View.VISIBLE);
                ll_other.startAnimation(AnimationUtils.loadAnimation(mcontext, R.anim.mainactivity_push_bottom_in));
            }
        }, 400);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                music.setVisibility(View.VISIBLE);
                music.startAnimation(AnimationUtils.loadAnimation(mcontext, R.anim.mainactivity_push_bottom_in));
            }
        },500);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isShowing()) {
            outputDialog();
            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
    }


    /**
     * 取消对话框（带动画）
     */
    private void outputDialog() {
        //退出动画
        rlMain.startAnimation(AnimationUtils.loadAnimation(mcontext, R.anim.mainactivity_fade_out));
        ivMenu.startAnimation(AnimationUtils.loadAnimation(mcontext, R.anim.mainactivity_rotate_left));
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                dismiss();
            }
        }, 400);
        llBtnArticle.startAnimation(AnimationUtils.loadAnimation(mcontext, R.anim.mainactivity_push_bottom_out));
        llBtnArticle.setVisibility(View.INVISIBLE);
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                llBtnMiniBlog.startAnimation(AnimationUtils.loadAnimation(mcontext, R.anim.mainactivity_push_bottom_out));
                llBtnMiniBlog.setVisibility(View.INVISIBLE);
            }
        }, 50);
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                llBtnLetter.startAnimation(AnimationUtils.loadAnimation(mcontext, R.anim.mainactivity_push_bottom_out));
                llBtnLetter.setVisibility(View.INVISIBLE);
            }
        }, 100);
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                llBtnPhoto.startAnimation(AnimationUtils.loadAnimation(mcontext, R.anim.mainactivity_push_bottom_out));
                llBtnPhoto.setVisibility(View.INVISIBLE);
            }
        }, 150);
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                ll_other.startAnimation(AnimationUtils.loadAnimation(mcontext, R.anim.mainactivity_push_bottom_out));
                ll_other.setVisibility(View.INVISIBLE);
            }
        }, 200);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                music.startAnimation(AnimationUtils.loadAnimation(mcontext, R.anim.mainactivity_push_bottom_out));
                music.setVisibility(View.INVISIBLE);
            }
        },250);

    }


    @Override
    public void show() {
        super.show();
        inputDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((WindowManager.LayoutParams) params);

        initListener();
        initRecord();
    }



    private void initRecord() {
        Util.requestPermission((Activity) mcontext, Manifest.permission.RECORD_AUDIO);
        Util.requestPermission((Activity) mcontext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }


    public PublishDialog setMusicBtnClickListener(View.OnClickListener clickListener){
        music.setOnClickListener(clickListener);
        return this;
    }
    public PublishDialog setArticleBtnClickListener(View.OnClickListener clickListener) {
        llBtnArticle.setOnClickListener(clickListener);
        return this;
    }

    public PublishDialog setMiniBlogBtnClickListener(View.OnClickListener clickListener) {
        llBtnMiniBlog.setOnClickListener(clickListener);
        return this;
    }

    public PublishDialog setLetterBtnClickListener(View.OnClickListener clickListener) {
        llBtnLetter.setOnClickListener(clickListener);
        return this;
    }

    public PublishDialog setPhotoBtnClickListener(View.OnClickListener clickListener) {
        llBtnPhoto.setOnClickListener(clickListener);
        return this;
    }

    public PublishDialog setOtherBtnClickListener(View.OnClickListener clickListener) {
        ll_other.setOnClickListener(clickListener);
        return this;
    }



    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            viewPager.setCurrentItem(position);
        }

    };




    protected void initListener() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        //载入图片资源ID
        imgIdArray = new int[]{R.drawable.caishang_1, R.drawable.caishang_2};
        //将图片装载到数组中
        mImageViews = new ImageView[imgIdArray.length];
        for (int i = 0; i < mImageViews.length; i++) {
            ImageView imageView = new ImageView(getContext());
            mImageViews[i] = imageView;
            imageView.setBackgroundResource(imgIdArray[i]);
        }
        //设置Adapter
        viewPager.setAdapter(new MyAdapter());
        //设置监听，主要是设置点点的背景
        viewPager.setOnPageChangeListener(this);
        //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
        viewPager.setCurrentItem(0);

        //实现思路三
        viewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isTouched = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isTouched = false;
                    default:
                        break;
                }
                return false;
            }
        });

//        Thread change = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                while (true) {
//                    if (!isTouched) {
//                        position++;
//                        mHandler.sendEmptyMessage(position);
//                    }
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        change.start();
    }



    private boolean isTouched = false;
    int position = 0;


    /**
     * ViewPager
     */
    private ViewPager viewPager;

    /**
     * 装ImageView数组
     */
    private ImageView[] mImageViews;

    /**
     * 图片资源id
     */
    private int[] imgIdArray;

    /**
     * @author xiaanming
     */
    public class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 2;
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        @Override
        public void destroyItem(View container, int position, Object object) {}
        @Override
        public Object instantiateItem(View container, int position) {
            try {
                ((ViewPager) container).addView(mImageViews[position ], 0);
            } catch (Exception e) {
            }
            return mImageViews[position ];
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {}

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {}

    @Override
    public void onPageSelected(int arg0) {
        position = arg0;
    }
}

package com.touyuanren.hahahuyu.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.multi_image_selector.MultiImageSelectorActivity;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.service.UploadSystemInfo;
import com.touyuanren.hahahuyu.ui.activity.account.PublishDongTaiAct;
import com.touyuanren.hahahuyu.ui.activity.account.PublishMyLogAct;
import com.touyuanren.hahahuyu.ui.fragment.guanzhu.GuanZhuFrag;
import com.touyuanren.hahahuyu.ui.fragment.news.NewsFragment;
import com.touyuanren.hahahuyu.ui.fragment.shouye.ShouYeFragment;
import com.touyuanren.hahahuyu.ui.fragment.user.AccountCenterFragment;
import com.touyuanren.hahahuyu.utils.FoContents;
import com.touyuanren.hahahuyu.utils.PublishDialog;
import com.touyuanren.hahahuyu.utils.SystemBarTintManager;
import com.touyuanren.hahahuyu.utils.newapp.CheckVersionInfoTask;
import com.touyuanren.hahahuyu.utils.spacenavigation.SpaceItem;
import com.touyuanren.hahahuyu.utils.spacenavigation.SpaceNavigationView;
import com.touyuanren.hahahuyu.utils.spacenavigation.SpaceOnClickListener;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    /**
     * 首页fragment
     */
    private ShouYeFragment mShouYeFragment;
    /**
     * 资讯fragment
     */
    private NewsFragment mNewsFragment;
    /**
     * 好友frag不
     */
    private GuanZhuFrag guanZhuFrag;
    /**
     * 个人中心fragment
     */
    private AccountCenterFragment mAccountCenterFragment;
    /**
     * 底部导航栏
     */
    private SpaceNavigationView spaceNavigationView;
    /**
     * 弹框动画,发布前
     */

    private int num = 0;

    PublishDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(this, true);

        }


        //检测新版本
        new CheckVersionInfoTask(MainActivity.this, false).execute();

        // 创建状态栏的管理实例
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // 激活状态栏设置
        tintManager.setStatusBarTintEnabled(true);
        // 激活导航栏设置
        tintManager.setNavigationBarTintEnabled(true);
        // 使用颜色资源
//        tintManager.setStatusBarTintResource(R.color.color_green);
        setContentView(R.layout.activity_main);
        initView(savedInstanceState);
        initData();
        Intent mIntent = new Intent(MainActivity.this, UploadSystemInfo.class);
        startService(mIntent);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        spaceNavigationView.onSaveInstanceState(outState);
    }

    //默认显示首页
    private void initData() {
        changeFragment(0);
    }

    @TargetApi(19)
    private static void setTranslucentStatus(Activity activity, boolean on) {

        Window win = activity.getWindow();

        WindowManager.LayoutParams winParams = win.getAttributes();

        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

        if (on) {

            winParams.flags |= bits;

        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void initView(final Bundle savedInstanceState) {
        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space_shouye);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("首页", R.drawable.shouye_btm_1));
        spaceNavigationView.addSpaceItem(new SpaceItem("资讯", R.drawable.shouye_btm_2));
        spaceNavigationView.addSpaceItem(new SpaceItem("参与", R.drawable.shouye_btm_3));
        spaceNavigationView.addSpaceItem(new SpaceItem("我的", R.drawable.shouye_btm_4));
        spaceNavigationView.shouldShowFullBadgeText(true);
        spaceNavigationView.showIconOnly();
        spaceNavigationView.setCentreButtonIconColorFilterEnabled(false);

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                spaceNavigationView.shouldShowFullBadgeText(true);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                changeFragment(itemIndex);
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
            }
        });

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                if (pDialog == null) {
                    pDialog = new PublishDialog(MainActivity.this);
                    pDialog.setArticleBtnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(MainActivity.this, MultiImageSelectorActivity.class);
                            // 是否显示调用相机拍照
                            intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                            // 最大图片选择数量
                            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9 - num);
                            // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
                            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                            startActivityForResult(intent, FoContents.REQUEST_IMAGE);
                            pDialog.hide();
                        }
                    });
                    pDialog.setMiniBlogBtnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this, R.string.jingqingqidai, Toast.LENGTH_LONG).show();
                            pDialog.hide();
                        }
                    });
                    pDialog.setPhotoBtnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MainActivity.this, PublishMyLogAct.class));
                            pDialog.hide();
                        }
                    });
                    pDialog.setLetterBtnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MainActivity.this, PublishDongTaiAct.class));
                            pDialog.hide();
                        }
                    });
                    pDialog.setOtherBtnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(MainActivity.this, R.string.jingqingqidai, Toast.LENGTH_LONG).show();
                            pDialog.hide();
                        }
                    });
                }
                pDialog.show();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                changeFragment(itemIndex);
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onResume(this);
    }

    /**
     * 通过点击button切换fragment，
     *
     * @param checkedId：点击的button的id
     */
    private void changeFragment(int checkedId) {
        //Fragment事物对象
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        hideAllFragment(ft);

        /**
         * 每次Fragment切换时都需要实时修改标题内容
         */
        switch (checkedId) {
            case 0:
                if (null == mShouYeFragment) {
                    mShouYeFragment = new ShouYeFragment();
                }

                if (mShouYeFragment.isAdded()) {
                    ft.show(mShouYeFragment);
                } else {
                    ft.add(R.id.fl_home_content, mShouYeFragment);
                }

                break;

            case 1:
                if (null == mNewsFragment) {
                    mNewsFragment = new NewsFragment();
                }

                if (mNewsFragment.isAdded()) {
                    ft.show(mNewsFragment);
                } else {
                    ft.add(R.id.fl_home_content, mNewsFragment);
                }

                break;
            case 2:
                if (null == guanZhuFrag) {
                    guanZhuFrag = new GuanZhuFrag();
                }

                if (guanZhuFrag.isAdded()) {
                    ft.show(guanZhuFrag);
                } else {
                    ft.add(R.id.fl_home_content, guanZhuFrag);
                }
                break;

            case 3:
                if (null == mAccountCenterFragment) {
                    mAccountCenterFragment = new AccountCenterFragment();
                }
                if (mAccountCenterFragment.isAdded()) {
                    ft.show(mAccountCenterFragment);
                } else {
                    ft.add(R.id.fl_home_content, mAccountCenterFragment);
                }
                break;
        }
        ft.commitAllowingStateLoss();
    }

    /**
     * 在切换Fragment时隐藏所有的Fragment
     */

    private void hideAllFragment(FragmentTransaction ft) {
        if (null != ft) {
            if (null != mShouYeFragment) {
                ft.hide(mShouYeFragment);
            }
            if (null != mNewsFragment) {
                ft.hide(mNewsFragment);
            }
            if (null != guanZhuFrag) {
                ft.hide(guanZhuFrag);
            }
            if (null != mAccountCenterFragment) {
                ft.hide(mAccountCenterFragment);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "在按一次退出",
                        Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }

            return true;
        }
        //拦截MENU按钮点击事件，让他无任何操作
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FoContents.REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                //接收回传的图片路径；先压缩，上传,在显示在gridview上，
                originalPathListAll = data.getStringArrayListExtra("select_result");
                Log.e("originalPathListAll", "" + originalPathListAll.size());
                if (originalPathListAll != null && originalPathListAll.size() > 0) {
                    originalPathListAll.size();
                    Intent intent = new Intent(MainActivity.this, PublishDongTaiAct.class);
                    intent.putExtra("originalPathListAll", originalPathListAll);
                    intent.putExtra("BZTZ", true);
                    startActivity(intent);
                } else {
                    Log.e("dd", "返回数据为Null");
                }
            }
        }
    }

    /**
     * 选取的原始图片地址集合
     */
    private ArrayList<String> originalPathListAll = new ArrayList<>();
}

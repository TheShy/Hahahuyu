package com.touyuanren.hahahuyu.ui.fragment.shouye;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.audio.MainAcitity;
import com.touyuanren.hahahuyu.bean.HotBean;
import com.touyuanren.hahahuyu.bean.HuoDongInfo;
import com.touyuanren.hahahuyu.bean.ShouYeLBBean;
import com.touyuanren.hahahuyu.cardpage.CardFragmentPagerAdapter;
import com.touyuanren.hahahuyu.cardpage.CardItem;
import com.touyuanren.hahahuyu.cardpage.CardPagerAdapter;
import com.touyuanren.hahahuyu.cardpage.ShadowTransformer;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.GameActivity;
import com.touyuanren.hahahuyu.ui.activity.GameDetailActivity;
import com.touyuanren.hahahuyu.ui.activity.TestAty;
import com.touyuanren.hahahuyu.ui.activity.shouye.CaptureActivity;
import com.touyuanren.hahahuyu.ui.activity.shouye.CityPickerActivity;
import com.touyuanren.hahahuyu.ui.activity.shouye.HuoDongActivity;
import com.touyuanren.hahahuyu.ui.activity.shouye.HuoDongDetailAct;
import com.touyuanren.hahahuyu.ui.adapter.HuoDongAdapter;
import com.touyuanren.hahahuyu.ui.fragment.BaseFragment;
import com.touyuanren.hahahuyu.utils.DensityUtil;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Jing on 2016/1/27.
 * 首页fragment
 */
public class ShouYeFragment extends BaseFragment implements
        View.OnClickListener,
        ViewPager.OnPageChangeListener,
        CompoundButton.OnCheckedChangeListener {
    /**
     * ViewPager,首页轮播
     */
    private ViewPager mViewPager;

    private FragmentManager mFragmentManager;
    /**
     * 装点点的ImageView数组
     */
    private ImageView[] tips;

    /**
     * 装ImageView数组
     */
    private ImageView[] mImageViews = new ImageView[]{};
    /**
     * 图片资源id
     */
    private List<String> imgIdArray;
    /**
     * 放置小点点的viewgroup
     */
    private ViewGroup mViewGroup;
    /**
     * 当前fragment的完整布局
     */
    private View view;
    /**
     * 点击进入活动界面
     */
    private TextView tv_hd;
    /**
     * 专题界面
     */
    private TextView tv_shangcheng;
    /**
     * 招聘页面
     */
    private TextView tv_zhaopin;
    /**
     * 点击进入马拉松界面
     */
    private TextView tv_game;
    /**
     * 活动列表
     */
    private ListView activityList;
    /**
     * 显示用户当前地址
     */
    private TextView addressUser;
    /**
     * 点击进入二维码扫描
     */
    private ImageView ima_rqcode_shouye_frag;
    /**
     * 跳转到更多activity
     */
    private TextView moreActivity;
    /**
     * headview
     */
    private View headView;
    /**
     * 如果自动定位为true,手动定位为false
     */
    private Boolean isLocation = true;
    private int ResultCode = 2000;
    private int RequestCode = 1000;
    //分页加载需要的参数
    /**
     * 刷新控件
     */
    private SwipeRefreshLayout swipeRefreshLayout;
    /**
     * 每页的活动数据集合
     */
    ArrayList<HuoDongInfo> mList;
    /**
     * 活动列表总得数据集合
     */
    ArrayList<HuoDongInfo> mTotalList = new ArrayList<>();
    private HuoDongAdapter adapter;
    /**
     * 请求的页数
     */
    private int page = 1;
    /**
     * 请求列表的请求地址
     */
    private String listUrl = HttpApi.ROOT_PATH + HttpApi.SHOUYE_TUIJIAN + "?" + "&page=";
    //加载更多的总布局
    RelativeLayout id_rl_loading;
    ProgressBar id_pull_to_refresh_load_progress;
    TextView id_pull_to_refresh_loadmore_text;
    final String pull_Load_More = "拖动加载";
    final String loading_Load_More = "加载中...";
    final String comp_Load_More = "加载完成";
    String nowNormalText = "";//存放当前footview显示的文字
    boolean isLastItem = false;//是否是最后一项

    boolean isLoading = false;//是否正在加载中
    boolean isComp = false;//标记一次是否加载完成


    private static final int REQUEST_CODE_PICK_CITY = 233;

    @Override
    public View getShowView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_shouye, null);
        initView();
        initSwipeLayout();
        initData();

        //获取地理位置
        getLocation();
        return view;
    }

    private void initSwipeLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_list_shouye);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        //swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        int size = DensityUtil.dip2pxComm(getActivity(), 25);
        // 第一次进入页面的时候显示加载进度条
        swipeRefreshLayout.setProgressViewOffset(false, 0, size);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListInfo(1);
            }
        });
    }

    //获取赛事信息
    private void getListInfo(final int page) {
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        formMap.put("mark", "0");
        FoHttp.getMsg(listUrl + page, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getContext(), R.string.intent_error, Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(String response) {
                Log.e("respossssssnse", response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            HotBean hotBean = new Gson().fromJson(response, HotBean.class);

                            mList = new ArrayList<HuoDongInfo>();
                            for (int i = 0; i < hotBean.getData().size(); i++) {
                                HuoDongInfo huoDongInfo = new HuoDongInfo();
                                huoDongInfo.setAddress(hotBean.getData().get(i).getAddress());
                                huoDongInfo.setBrowse_num(hotBean.getData().get(i).getBrowse_num());
                                huoDongInfo.setCat_id(hotBean.getData().get(i).getCat_id());
                                huoDongInfo.setClose_time(hotBean.getData().get(i).getClose_time());
                                huoDongInfo.setCollection_num(hotBean.getData().get(i).getCollection_num());
                                huoDongInfo.setContent(hotBean.getData().get(i).getContent());
                                huoDongInfo.setDb_name(hotBean.getData().get(i).getDb_name());
                                huoDongInfo.setEndtime(hotBean.getData().get(i).getEndtime());
                                huoDongInfo.setId(hotBean.getData().get(i).getId());
                                huoDongInfo.setMark(hotBean.getData().get(i).getMark());
                                huoDongInfo.setMatch_status(hotBean.getData().get(i).getMatch_status());
                                huoDongInfo.setName(hotBean.getData().get(i).getName());
                                huoDongInfo.setPoster(hotBean.getData().get(i).getPoster());
                                huoDongInfo.setSign_num(hotBean.getData().get(i).getSign_num());
                                huoDongInfo.setStarttime(hotBean.getData().get(i).getStarttime());
                                huoDongInfo.setTicket(hotBean.getData().get(i).getTicket());
                                huoDongInfo.setTitle(hotBean.getData().get(i).getTitle());
                                huoDongInfo.setUser_id(hotBean.getData().get(i).getUser_id());
                                huoDongInfo.setUser_name(hotBean.getData().get(i).getUser_name());
                                mList.add(huoDongInfo);
                            }
//                            mList = (ArrayList<HuoDongInfo>) mHuoDongInfoBean.getData().getList();
                            if (page == 1) {
                                mTotalList.clear();
                                mTotalList.addAll(mList);
                                adapter = new HuoDongAdapter(MyApplication.getContext(), mTotalList);
                                activityList.setAdapter(adapter);
                                swipeRefreshLayout.setRefreshing(false);
                            } else if (mList.size() <= 0) {
                                //没有了，对底部进行处理
                                id_pull_to_refresh_loadmore_text.setText("没有更多了");
                                id_pull_to_refresh_loadmore_text.setClickable(false);
                                id_pull_to_refresh_load_progress.setVisibility(View.GONE);
                                isComp = true;
                                activityList.removeFooterView(id_rl_loading);
                            } else {
                                isLoading = false;
                                //再次对listview的footview进行处理
                                mTotalList.addAll(mList);
                                adapter.notifyDataSetChanged();
                                id_pull_to_refresh_loadmore_text.setText(nowNormalText);
                                id_pull_to_refresh_load_progress.setVisibility(View.GONE);
                                isComp = false;
                                id_pull_to_refresh_loadmore_text.setClickable(true);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * 上拉加载1
     */
    AbsListView.OnScrollListener OnScrollListenerOne = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //当滚到最后一行且停止滚动时，执行加载
            if (isLastItem && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && !isLoading) {
                //加载元素
//                loadMore();
                isLastItem = false;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            //判断是否滚到最后一行
            if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                isLastItem = true;
            }
        }
    };

    //加载更多
    private void loadMore() {
        id_rl_loading.setVisibility(View.VISIBLE);
        id_pull_to_refresh_loadmore_text.setText(loading_Load_More);
        id_pull_to_refresh_loadmore_text.setClickable(false);
        id_pull_to_refresh_load_progress.setVisibility(View.VISIBLE);
        isLoading = true;
        page++;
        getListInfo(page);
    }

    public void initView() {
        activityList = (ListView) view.findViewById(R.id.list_activity_main);
        View listview_footer_view = LayoutInflater.from(getActivity()).inflate(R.layout.listview_footer, null);
        id_rl_loading = (RelativeLayout) listview_footer_view.findViewById(R.id.id_rl_loading);
        id_pull_to_refresh_load_progress = (ProgressBar) listview_footer_view.findViewById(R.id.id_pull_to_refresh_load_progress);
        id_pull_to_refresh_load_progress.setVisibility(View.GONE);
        id_pull_to_refresh_loadmore_text = (TextView) listview_footer_view.findViewById(R.id.id_pull_to_refresh_loadmore_text);
        id_pull_to_refresh_loadmore_text.setClickable(false);
        activityList.addFooterView(listview_footer_view);
        headView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.head_shouye_listview, null);

        activityList.addHeaderView(headView);
        activityList.setOnScrollListener(OnScrollListenerOne);
        addressUser = (TextView) view.findViewById(R.id.address_user_shouye_fragment);
        ima_rqcode_shouye_frag = (ImageView) view.findViewById(R.id.ima_rqcode_shouye_frag);

        moreActivity = (TextView) headView.findViewById(R.id.more_shouye_frag);
        mViewPager = (ViewPager) headView.findViewById(R.id.viewpager_frag_shouye);
        mViewGroup = (ViewGroup) headView.findViewById(R.id.viewGroup_frag_shouye);
        tv_hd = (TextView) headView.findViewById(R.id.huodong_frag_shouye);
        tv_game = (TextView) headView.findViewById(R.id.game_frag_shouye);
        tv_shangcheng = (TextView) headView.findViewById(R.id.tv_shangcheng_shouye);
        tv_zhaopin = (TextView) headView.findViewById(R.id.tv_zhaopin_shouye);


        mViewPager_shouye = (ViewPager) headView.findViewById(R.id.viewPager_shouye);

        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem(R.string.ceshi_2, R.string.ceshi_1));
        mCardAdapter.addCardItem(new CardItem(R.string.ceshi_2, R.string.ceshi_1));
        mCardAdapter.addCardItem(new CardItem(R.string.ceshi_2, R.string.ceshi_1));
        mCardAdapter.addCardItem(new CardItem(R.string.ceshi_2, R.string.ceshi_1));
        mFragmentCardAdapter = new CardFragmentPagerAdapter(getActivity().getSupportFragmentManager(),
                dpToPixels(2, getContext()));

        mCardShadowTransformer = new ShadowTransformer(mViewPager_shouye, mCardAdapter);
        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager_shouye, mFragmentCardAdapter);

        mViewPager_shouye.setAdapter(mCardAdapter);
        mViewPager_shouye.setPageTransformer(false, mCardShadowTransformer);
        mViewPager_shouye.setOffscreenPageLimit(3);


        //绑定监听
        tv_game.setOnClickListener(this);
        tv_hd.setOnClickListener(this);
        tv_shangcheng.setOnClickListener(this);
        tv_zhaopin.setOnClickListener(this);
        moreActivity.setOnClickListener(this);

        addressUser.setOnClickListener(this);
        ima_rqcode_shouye_frag.setOnClickListener(this);

        activityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String mark = mTotalList.get(position - 1).getMark();
//                if ("0".equals(mark)) {
//                    Intent mIntent = new Intent(getActivity(), HuoDongDetailAct.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("huodongInfo", mTotalList.get(position - 1));
//                    mIntent.putExtras(bundle);
//                    startActivity(mIntent);
//                } else {
//                    Intent mIntent = new Intent(getActivity(), GameDetailActivity.class);
//                    mIntent.putExtra("gameinfo", mTotalList.get(position - 1));
//                    startActivity(mIntent);
//                }

                //当跳转到详情页面时，先判断是否收藏；在活动详情对象中加一个是否收藏的属性；收藏前判断是否登录
                Intent mIntent = null;
                if ("0".equals(mTotalList.get(position - 1).getMark())) {
                    //活动
                    mIntent = new Intent(getActivity(), HuoDongDetailAct.class);
                } else {
                    mIntent = new Intent(getActivity(), GameDetailActivity.class);
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("huodongInfo", mTotalList.get(position - 1));
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == getActivity().RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                addressUser.setText("" + city);
            } else {
                addressUser.setText("失败");
            }
        }
    }

    private void initViewPager() {
        mViewGroup.removeAllViews();

        /**
         * 在这里进行为空判断是因为在测试的过程中,imageviews有时会出现为空的错误
         * 由于还没有定位具体原因,所以现在先有为空判断进行限制
         */
        if (null != imgIdArray) {
            //将点点加入到ViewGroup中
            tips = new ImageView[imgIdArray.size()];
            for (int i = 0; i < tips.length; i++) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setLayoutParams(new LayoutParams(10, 10));
                tips[i] = imageView;
                if (null != imageView) {
                    imageView.setLayoutParams(new LayoutParams(50, 15));
                    tips[i] = imageView;
                    if (i == 0) {
                        tips[i].setImageResource(R.drawable.n_page_sel);
                    } else {
                        tips[i].setImageResource(R.drawable.n_page_normal);
                    }
                    mViewGroup.addView(imageView);
                }
            }
            mViewPager.setAdapter(new MyAdapter());
            //设置监听，主要是设置点点的背景
            mViewPager.setOnPageChangeListener(this);
//        //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
            mViewPager.setCurrentItem((mImageViews.length) * 100);
            Log.i("shujukutupianchangdu", imgIdArray.size() + "");

            //将图片装载到数组中
            mImageViews = new ImageView[imgIdArray.size()];
            for (int i = 0; i < mImageViews.length; i++) {
                ImageView imageView = new ImageView(getContext());
                mImageViews[i] = imageView;
                Glide.with(getContext()).load(HttpApi.ROOTPATH_LB + imgIdArray.get(i)).error(R.drawable.bg).into(imageView);
                //图片显示样式
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }
    }


    //获取网络图片，装填轮播图
    private void initData() {

        swipeRefreshLayout.measure(0, 0);
        swipeRefreshLayout.setRefreshing(true);

//        new AutoSwipeRefreshLayout(getContext()).autoRefresh();
        imgIdArray = new ArrayList<String>();
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.SHOUYEIMG, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getContext(), R.string.intent_error, Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            ShouYeLBBean shouYeLBBean = new Gson().fromJson(response, ShouYeLBBean.class);
                            for (int i = 0; i < shouYeLBBean.getData().size(); i++) {
                                imgIdArray.add(shouYeLBBean.getData().get(i).getPic_url());
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                initViewPager();

                //获取热门信息
                getListInfo(page);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //定位
    public void getLocation() {
//        if (isLocation) {
//            new GetLocation(MyApplication.getContext(), addressUser).getLocation();
//            isLocation = false;
//        }

        AMapLocationClient mLocationClient = new AMapLocationClient(getContext());
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        String city = aMapLocation.getCity();
                        addressUser.setText(city);
                    } else {
                        //定位失败
                        addressUser.setText(R.string.cp_located_failed);
                    }
                }
            }
        });
    }

    //点击事件的实现
    @Override
    public void onClick(View v) {
        Intent mIntent = null;
        switch (v.getId()) {
            case R.id.list_activity_main:
                break;
            case R.id.address_user_shouye_fragment:
                //弹出弹窗，进行城市选择，跳转到城市列表界面;
//                mIntent = new Intent(getActivity(), CityPickerActivity.class);
//                startActivityForResult(mIntent, RequestCode);
                startActivityForResult(new Intent(getActivity(), CityPickerActivity.class),
                        REQUEST_CODE_PICK_CITY);
                break;
            //点击进入搜索
            case R.id.ima_rqcode_shouye_frag:
                mIntent = new Intent(getActivity(), CaptureActivity.class);
                startActivity(mIntent);
                break;
            //跳转到更多界面
            case R.id.more_shouye_frag:
                mIntent = new Intent(getActivity(), MainAcitity.class);
                startActivity(mIntent);
                break;
            //进入活动列表页
            case R.id.huodong_frag_shouye:
                mIntent = new Intent(getActivity(), HuoDongActivity.class);
                startActivity(mIntent);
                break;
            //进入招聘页面
            case R.id.tv_zhaopin_shouye:
                Toast.makeText(getContext(), "敬请期待", Toast.LENGTH_LONG).show();
                break;
            case R.id.game_frag_shouye:
                mIntent = new Intent(getActivity(), GameActivity.class);
                startActivity(mIntent);
                break;
            case R.id.tv_shangcheng_shouye:
                mIntent = new Intent(getActivity(), TestAty.class);
                startActivity(mIntent);
                break;

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setImageBackground(position % mImageViews.length);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * @author xiaanming
     */
    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
//        @Override
//        public void destroyItem(View container, int position, Object object) {
////            ((ViewPager) container).removeView(mImageViews[position % mImageViews.length]);
//        }

        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(View container, int position) {
//            ((ViewPager) container).addView(mImageViews[position % mImageViews.length], 0);
//            return mImageViews[position % mImageViews.length];
            try {
                ((ViewPager) container).addView(mImageViews[position % mImageViews.length], 0);
            } catch (Exception e) {
                //handler something
            }
            if (mImageViews.length == 0) {
                return null;
            } else {
                return mImageViews[position % mImageViews.length];
            }
//            return mImageViews[position % mImageViews.length];
        }
    }

    /*
    * 设置选中的tip的背景
    */
    private void setImageBackground(int selectItems) {
        if (null != tips) {
            for (int i = 0; i < tips.length; i++) {
                if (i == selectItems) {
                    tips[i].setImageResource(R.drawable.n_page_sel);
                } else {
                    tips[i].setImageResource(R.drawable.n_page_normal);
                }
            }
        }
    }

    private ViewPager mViewPager_shouye;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mCardShadowTransformer.enableScaling(b);
        mFragmentCardShadowTransformer.enableScaling(b);
    }
}

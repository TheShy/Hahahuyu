package com.touyuanren.hahahuyu.ui.activity.news;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.ChannelBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.utils.view_pindao.DragAdapter;
import com.touyuanren.hahahuyu.utils.view_pindao.DragGridView;
import com.touyuanren.hahahuyu.utils.view_pindao.MyGridView;
import com.touyuanren.hahahuyu.utils.view_pindao.OtherAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jing on 2016/9/26.
 * 频道管理
 */
public class ChannelActivity extends BaseActivity implements AdapterView.OnItemClickListener {
//    private ArrayList<ChannelBean> channelListAll;
//    /**
//     * 更多频道
//     */
//    private GridviewForScrollView gv_more;
//    /**
//     * 我的频道
//     */
//    private GridviewForScrollView gv_mychannel;
//    /**
//     * 用户频道
//     */
//    private ArrayList<ChannelBean> userChannelList;
//    /**
//     * 用户频道adapter
//     */
//    private ChannelAdapter userChannelAdapter;
//    /**
//     * 全部频道adapter
//     */
//    private ChannelAdapter allChannelAdapter;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_channel);
//        setTitleLeftBtn();
//        setTitleName(R.string.channel_selcet);
//        initView();
//        initData();
//    }
//
//    private void initView() {
//        gv_more = (GridviewForScrollView) findViewById(R.id.gv_moer_channel);
//        gv_mychannel = (GridviewForScrollView) findViewById(R.id.userGridView_channel);
//    }
//
//    private void initData() {
//        channelListAll = new ArrayList<>();
//        userChannelList = new ArrayList<>();
//        getChannelInfo();
//
//        userChannelAdapter = new ChannelAdapter(MyApplication.getContext(), userChannelList);
//        gv_mychannel.setAdapter(userChannelAdapter);
//
//        gv_mychannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ChannelBean channelBean = userChannelList.get(position);
//                userChannelList.remove(channelBean);
//                channelListAll.add(channelBean);
//                userChannelAdapter.setList(userChannelList);
//                allChannelAdapter.setList(channelListAll);
//                //测试SP
//                SPUtils.put(ChannelActivity.this,"ChannelActivity_ceshi",channelListAll.get(position+1).getChannel_name(),false);
//            }
//        });
//        gv_more.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                //长按选择
//                ChannelBean channelBean = channelListAll.get(position);
//                channelListAll.remove(channelBean);
//                userChannelList.add(channelBean);
//                userChannelAdapter.setList(userChannelList);
//                allChannelAdapter.setList(channelListAll);
//                //测试SP
//                SPUtils.put(ChannelActivity.this,"ChannelActivity_ceshi",channelListAll.get(position+1).getChannel_name(),true);
//                return true;
//            }
//        });
//    }
//
//    获取赛事信息
//    private void getChannelInfo() {
//        OkHttpUtils.post().url(HttpApi.ROOTPATH_NEWS+"hd/Android/lanmu?siteId=6").
//                build().
//                execute(new StringCallback() {
//                    @Override
//                    public void onError(Request request, Exception e) {
//                        Toast.makeText(ChannelActivity.this,"网络错误，请检查您的网络连接！",Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onResponse(String response) {
//                        Log.e("response", response);
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String status = jsonObject.getString("status");
//                            if ("1".equals(status)) {
//                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
//                                JSONArray jsonArray = jsonObject1.getJSONArray("list");
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    JSONArray jsonArray1 = (JSONArray) jsonArray.get(i);
//                                    String channelId = jsonArray1.getString(0);
//                                    String title = jsonArray1.getString(1);
//                                    ChannelBean channelBean = new ChannelBean();
//                                    channelBean.setChannel_id(channelId);
//                                    channelBean.setChannel_name(title);
//
//                                    //测试，sp
//                                    SPUtils.put(ChannelActivity.this,"ChannelActivity_ceshi",title,false);
//
//                                    channelListAll.add(channelBean);
//                                }
//                                sp_ceshi(channelListAll);
//                                allChannelAdapter = new ChannelAdapter(MyApplication.getContext(), channelListAll);
//                                gv_more.setAdapter(allChannelAdapter);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//    }
//
//    public void sp_ceshi(ArrayList<ChannelBean> channelListAll){
//        for (int j = 0;j<channelListAll.size();j++){
//            if (SPUtils.getValue(ChannelActivity.this,"ChannelActivity_ceshi").getBoolean(channelListAll.get(j).getChannel_name(),false)){
//                userChannelList.add(new ChannelBean(channelListAll.get(j).getChannel_id(), channelListAll.get(j).getChannel_name()));
//            }
//        }
//    }






    private MyGridView mOtherGv;
    private DragGridView mUserGv;
    private List<ChannelBean> mUserList = new ArrayList<>();
    private List<ChannelBean> mOtherList = new ArrayList<>();
    private OtherAdapter mOtherAdapter;
    private DragAdapter mUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_channel);
        setTitleLeftBtn();
        setTitleName(R.string.channel_selcet);
        initView();
    }

    public void initView() {
        mUserGv = (DragGridView) findViewById(R.id.userGridView);
        mOtherGv = (MyGridView) findViewById(R.id.otherGridView);

        mUserList.add(new ChannelBean("-2","推荐"));
        mUserList.add(new ChannelBean("-1","热点"));

        mUserList.add(new ChannelBean("-1","自媒体"));
        mUserList.add(new ChannelBean("36","媒体资讯"));
        mUserList.add(new ChannelBean("-1","百家言论"));
        mUserList.add(new ChannelBean("67","财经"));
        mUserList.add(new ChannelBean("70","娱乐"));
        mUserList.add(new ChannelBean("71","体育"));

        OkHttpUtils.post().url(HttpApi.ROOTPATH_NEWS+"hd/Android/lanmu?siteId=6").
                build().
                execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(ChannelActivity.this,"网络错误，请检查您的网络连接！",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if ("1".equals(status)) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                JSONArray jsonArray = jsonObject1.getJSONArray("list");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONArray jsonArray1 = (JSONArray) jsonArray.get(i);
                                    String channelId = jsonArray1.getString(0);
                                    String title = jsonArray1.getString(1);

//                                    Boolean aBoolean = false;
//                                    for (int j =  0; j<mUserList.size(); j++){
//                                        if (title.equals(mUserList.get(j).getChannel_name())){
//                                            aBoolean = !aBoolean;
//                                        }else {
//                                            if (aBoolean){
                                                ChannelBean channelBean = new ChannelBean();
                                                channelBean.setChannel_id(channelId);
                                                channelBean.setChannel_name(title);

                                                mOtherList.add(channelBean);
//                                            }
//                                            aBoolean = !aBoolean;
//                                        }
//                                    }

                                }
                            }

                            mUserAdapter = new DragAdapter(getBaseContext(), mUserList,true);
                            mOtherAdapter = new OtherAdapter(getBaseContext(), mOtherList,false);
                            mUserGv.setAdapter(mUserAdapter);
                            mOtherGv.setAdapter(mOtherAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        mUserGv.setOnItemClickListener(this);
        mOtherGv.setOnItemClickListener(this);
    }

    /**
     *获取点击的Item的对应View，
     *因为点击的Item已经有了自己归属的父容器MyGridView，所有我们要是有一个ImageView来代替Item移动
     * @param view
     * @return
     */
    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(cache);
        return iv;
    }
    /**
     * 获取移动的VIEW，放入对应ViewGroup布局容器
     * @param viewGroup
     * @param view
     * @param initLocation
     * @return
     */
    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    /**
     * 创建移动的ITEM对应的ViewGroup布局容器
     * 用于存放我们移动的View
     */
    private ViewGroup getMoveViewGroup() {
        //window中最顶层的view
        ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(this);
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }
    /**
     * 点击ITEM移动动画
     *
     * @param moveView
     * @param startLocation
     * @param endLocation
     * @param moveChannel
     * @param clickGridView
     */
    private void MoveAnim(View moveView, int[] startLocation, int[] endLocation, final String moveChannel,
                          final GridView clickGridView, final boolean isUser) {
        int[] initLocation = new int[2];
        //获取传递过来的VIEW的坐标
        moveView.getLocationInWindow(initLocation);
        //得到要移动的VIEW,并放入对应的容器中
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
        //创建移动动画
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(300L);//动画时间
        //动画配置
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);//动画效果执行完毕后，View对象不保留在终止的位置
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(mMoveView);
                // 判断点击的是DragGrid还是OtherGridView
                if (isUser) {
                    mOtherAdapter.setVisible(true);
                    mOtherAdapter.notifyDataSetChanged();
                    mUserAdapter.remove();
                } else {
                    mUserAdapter.setVisible(true);
                    mUserAdapter.notifyDataSetChanged();
                    mOtherAdapter.remove();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        switch (parent.getId()) {
            case R.id.userGridView:
                //position为 0，1 的不可以进行任何操作
                if (position != 0 && position != 1) {
                    final ImageView moveImageView = getView(view);
                    if (moveImageView != null) {
                        TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                        final int[] startLocation = new int[2];
                        newTextView.getLocationInWindow(startLocation);
                        final ChannelBean channel = ((DragAdapter) parent.getAdapter()).getItem(position);//获取点击的频道内容
                        mOtherAdapter.setVisible(false);
                        //添加到最后一个
                        mOtherAdapter.addItem(channel);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                try {
                                    int[] endLocation = new int[2];
                                    //获取终点的坐标
                                    mOtherGv.getChildAt(mOtherGv.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                    MoveAnim(moveImageView, startLocation, endLocation, channel.getChannel_name(), mUserGv, true);
                                    mUserAdapter.setRemove(position);
                                } catch (Exception localException) {
                                }
                            }
                        }, 50L);
                    }
                }
                break;
            case R.id.otherGridView:
                final ImageView moveImageView = getView(view);
                if (moveImageView != null) {
                    TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final ChannelBean channel = ((OtherAdapter) parent.getAdapter()).getItem(position);
                    mUserAdapter.setVisible(false);
                    //添加到最后一个
                    mUserAdapter.addItem(channel);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                int[] endLocation = new int[2];
                                //获取终点的坐标
                                mUserGv.getChildAt(mUserGv.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                MoveAnim(moveImageView, startLocation, endLocation, channel.getChannel_name(), mOtherGv,false);
                                mOtherAdapter.setRemove(position);
                            } catch (Exception localException) {
                            }
                        }
                    }, 50L);
                }
                break;
            default:
                break;
        }
    }
}

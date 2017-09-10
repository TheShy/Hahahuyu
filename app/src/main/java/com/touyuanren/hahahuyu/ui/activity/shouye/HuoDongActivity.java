package com.touyuanren.hahahuyu.ui.activity.shouye;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.FenLeiInfo;
import com.touyuanren.hahahuyu.bean.FenLeiInfoBean;
import com.touyuanren.hahahuyu.bean.HuoDongInfo;
import com.touyuanren.hahahuyu.bean.HuoDongInfoBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.adapter.HDFenLeiAdapter;
import com.touyuanren.hahahuyu.ui.adapter.HuoDongAdapter;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.touyuanren.hahahuyu.widget.LoadListView;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Jing on 2016/3/2.
 * 展示活动列表，根据分类进行切换
 */
public class HuoDongActivity extends BaseActivity implements View.OnClickListener, LoadListView.ILoadListener , PopupWindow.OnDismissListener{
    /**
     * 活动列表
     */
    private LoadListView lv_hd;
    /**
     * 活动列表
     */
    private ArrayList<HuoDongInfo> hdList;
    /**
     * 全部的活动
     */
    private ArrayList<HuoDongInfo> hdTotalList;
    /**
     * 控制全部数据时页数
     */
    private int page = 1;
    private String key_fenlei;
    private String value_fenlei;

    private HuoDongAdapter hdAdapter;
    /**
     * 全部分类
     */
    private TextView tv_fenlei;
    /**
     * 只能排序
     */
    private TextView tv_sort;
    /**
     * 筛选
     */
    private TextView tv_shaixuan;
    /**
     * popupwindow中listview控件
     */
    private ListView list_popupWindow;
    /**
     * popupwindow中listview控件
     */
    private ListView list_popupWindow1;
    /**
     * 活动类别数据源
     */
    private ArrayList<FenLeiInfo> arr_fenlei_all;
    /**
     * 活动主题
     */
    private ArrayList<FenLeiInfo> arr_leixing;
    /**
     * 活动形式
     */
    private ArrayList<FenLeiInfo> arr_xingshi;
    /**
     * 智能排序
     */
    private ArrayList<FenLeiInfo> arr_sort;
    /**
     * 筛选
     */
    private ArrayList<FenLeiInfo> arr_shaixuan;
    /**
     * 活动时间
     */
    private ArrayList<FenLeiInfo> arr_shijian;
    /**
     * 活动地点
     */
    private ArrayList<FenLeiInfo> arr_address;
    /**
     * 活动状态
     */
    private ArrayList<FenLeiInfo> arr_status;
    /**
     * 活动费用
     */
    private ArrayList<FenLeiInfo> arr_price;
    /**
     * 记录分类选择时的第一级联动位置
     */
    private int position_first;
    /**
     * 记录筛选位置
     */
    private int position_shaixuan;
    /**
     * 展示的弹窗
     */
    private PopupWindow popupWindow;
    private static final String TAG = "HuoDongActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_huodong);
        setTitleLeftBtn();
        setTitleName(R.string.huodong);
        initView();
        initData();
    }

    private void initData() {
        hdList = new ArrayList<>();
        hdTotalList = new ArrayList<>();
        hdAdapter = new HuoDongAdapter(MyApplication.getContext(), hdTotalList);
        lv_hd.setAdapter(hdAdapter);
        arr_fenlei_all = new ArrayList<>();
        arr_fenlei_all.add(new FenLeiInfo("0", "类型"));
        arr_fenlei_all.add(new FenLeiInfo("1", "形式"));
        arr_shaixuan = new ArrayList<>();
        arr_shaixuan.add(new FenLeiInfo("0", "时间"));
        arr_shaixuan.add(new FenLeiInfo("1", "地点"));
        arr_shaixuan.add(new FenLeiInfo("2", "状态"));
        arr_shaixuan.add(new FenLeiInfo("3", "费用"));
        getTypeInfo();
        lv_hd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent mIntent = new Intent(HuoDongActivity.this, HuoDongDetailAct.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("huodongInfo", hdTotalList.get(position));
                    mIntent.putExtras(bundle);
                    startActivity(mIntent);
            }
        });
    }

    private void initView() {
        lv_hd = (LoadListView) findViewById(R.id.lv_huodong_act);
        lv_hd.setInterface(this);
        tv_fenlei = (TextView) findViewById(R.id.tv_fenlei_hd_act);
        tv_sort = (TextView) findViewById(R.id.tv_sort_hd_act);
        tv_shaixuan = (TextView) findViewById(R.id.tv_shaixuan_hd_act);
        //绑定监听
        tv_fenlei.setOnClickListener(this);
        tv_sort.setOnClickListener(this);
        tv_shaixuan.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_fenlei_hd_act:
                //弹出联动列表
                select_fenlei();
                break;
            case R.id.tv_sort_hd_act:
                sort_hd();
                break;
            case R.id.tv_shaixuan_hd_act:
                shaixuan_hd();
                break;
        }
    }

    public void getHDList(final int page, String key, String value) {
        showLoading();
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        if (null != key) {
            formMap.put(key, value);
        }

        formMap.put("page", page + "");
        formMap.put("mark", "0");
        formMap.put("f", "10");
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.SEARCH_PATH, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                Log.e("response+loglist", response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            hdList.clear();
                            HuoDongInfoBean mHuoDongInfoBean = new Gson().fromJson(response, HuoDongInfoBean.class);
                            hdList = (ArrayList<HuoDongInfo>) mHuoDongInfoBean.getData().getList();
                            hdTotalList.addAll(hdList);
                            if (hdList.size() < 1) {
                                hdAdapter.onDateChange(hdList);
                                FoToast.toast(MyApplication.getContext(), "没用更多了");
                                lv_hd.loadComplete();
                                hideLoading();
                                return;
                            }
                            if (page == 1) {
                                hdAdapter.onDateChange(hdList);
                            } else {
                                hdAdapter.onDateChange(hdTotalList);
                                lv_hd.loadComplete();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                hideLoading();
            }
        });
    }

    //获取活动分类
    private void getTypeInfo() {
        showLoading();
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "list");
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.HD_TYPE, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                Log.e(TAG, response);
                hideLoading();
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            FenLeiInfoBean mFenLeiInfoBean = new Gson().fromJson(response, FenLeiInfoBean.class);
                            arr_leixing = (ArrayList<FenLeiInfo>) mFenLeiInfoBean.getData().getFenlei();
                            arr_xingshi = (ArrayList<FenLeiInfo>) mFenLeiInfoBean.getData().getXingshi();
                            arr_sort = (ArrayList<FenLeiInfo>) mFenLeiInfoBean.getData().getPaixu();
//                            arr_shaixuan = (ArrayList<FenLeiInfo>) mFenLeiInfoBean.getData().getShijian();
                            arr_shijian = (ArrayList<FenLeiInfo>) mFenLeiInfoBean.getData().getShijian();
                            arr_address = (ArrayList<FenLeiInfo>) mFenLeiInfoBean.getData().getDizhi();
                            arr_status = (ArrayList<FenLeiInfo>) mFenLeiInfoBean.getData().getZhuangtai();
                            arr_price = (ArrayList<FenLeiInfo>) mFenLeiInfoBean.getData().getFeiyong();
                            getHDList(page, key_fenlei, value_fenlei);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //分类
    private void select_fenlei() {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(MyApplication.getContext()).inflate(
                R.layout.listview_fenlei_select, null);
        // 设置listview的点击事件
        list_popupWindow = (ListView) contentView.findViewById(R.id.lv_yiji_fenlei);
        setAdapter(arr_fenlei_all, list_popupWindow);
        list_popupWindow1 = (ListView) contentView.findViewById(R.id.lv_erji_fenlei);
        setAdapter(arr_leixing, list_popupWindow1);
        list_popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position_first = position;
                if (position == 0) {
                    setAdapter(arr_leixing, list_popupWindow1);
                } else {
                    setAdapter(arr_xingshi, list_popupWindow1);
                }
            }
        });
        list_popupWindow1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                page=1;
                if (0 == position_first) {
//                    FoToast.toast(MyApplication.getContext(), arr_leixing.get(position).getName());
                    key_fenlei = "cat_id";
                    value_fenlei = arr_leixing.get(position).getId();
                    getHDList(page, key_fenlei, value_fenlei);
                } else {
                    key_fenlei = "cat_id";
                    value_fenlei = arr_xingshi.get(position).getId();
                    getHDList(page, key_fenlei, value_fenlei);
//                    FoToast.toast(MyApplication.getContext(), arr_xingshi.get(position).getName());
                }
                popupWindow.dismiss();
            }
        });
        showPopupWindow(tv_fenlei, contentView);
    }

    //筛选
    private void shaixuan_hd() {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(MyApplication.getContext()).inflate(
                R.layout.listview_fenlei_select, null);
        // 设置listview的点击事件
        list_popupWindow = (ListView) contentView.findViewById(R.id.lv_yiji_fenlei);
        setAdapter(arr_shaixuan, list_popupWindow);
        list_popupWindow1 = (ListView) contentView.findViewById(R.id.lv_erji_fenlei);
        setAdapter(arr_shijian, list_popupWindow1);
        list_popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position_shaixuan = position;
                if (position == 0) {
                    setAdapter(arr_shijian, list_popupWindow1);
                } else if (position == 1) {
                    setAdapter(arr_address, list_popupWindow1);
                } else if (position == 2) {
                    setAdapter(arr_status, list_popupWindow1);
                } else {
                    setAdapter(arr_price, list_popupWindow1);
                }
            }
        });
        list_popupWindow1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                page=1;
                if (position_shaixuan == 0) {
//                    FoToast.toast(MyApplication.getContext(), arr_shijian.get(position).getName());
                    key_fenlei = "starttime";
                    value_fenlei = arr_shijian.get(position).getId();
                    getHDList(page, key_fenlei, value_fenlei);
                } else if (position_shaixuan == 1) {
                    key_fenlei = "address";
                    value_fenlei = arr_address.get(position).getId();
                    getHDList(page, key_fenlei, value_fenlei);
//                    FoToast.toast(MyApplication.getContext(), arr_address.get(position).getName());
                } else if (position_shaixuan == 2) {
                    key_fenlei = "status";
                    value_fenlei = arr_status.get(position).getId();
                    getHDList(page, key_fenlei, value_fenlei);
//                    FoToast.toast(MyApplication.getContext(), arr_status.get(position).getName());
                } else {
                    key_fenlei = "is_pay";
                    value_fenlei = arr_price.get(position).getId();
                    getHDList(page, key_fenlei, value_fenlei);
//                    FoToast.toast(MyApplication.getContext(), arr_price.get(position).getName());
                }
                popupWindow.dismiss();
            }
        });
        showPopupWindow(tv_fenlei, contentView);
    }

    //排序
    private void sort_hd() {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(MyApplication.getContext()).inflate(
                R.layout.popupwindow_activity, null);
        // 设置listview的点击事件
        list_popupWindow = (ListView) contentView.findViewById(R.id.list_popunwindow_activity);
        list_popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                page=1;
                key_fenlei = "sort_id";
                value_fenlei = arr_sort.get(position).getId();
                getHDList(page, key_fenlei, value_fenlei);
//                FoToast.toast(MyApplication.getContext(), arr_sort.get(position).getName());
                popupWindow.dismiss();
            }
        });
        setAdapter(arr_sort, list_popupWindow);
        showPopupWindow(tv_sort, contentView);
    }


    private void showPopupWindow(View loactionview, View contnentView) {
        popupWindow = new PopupWindow(contnentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("mengdd", "onTouch : ");
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        Drawable d = getResources().getDrawable(R.color.white);
        popupWindow.setBackgroundDrawable(d);
        // 设置好参数之后再show
        popupWindow.showAsDropDown(loactionview);

        //添加pop窗口关闭事件
        popupWindow.setOnDismissListener(this);
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    public void setAdapter(ArrayList<FenLeiInfo> aa, ListView list) {
        HDFenLeiAdapter adapter = new HDFenLeiAdapter(aa, MyApplication.getContext());
        list.setAdapter(adapter);
    }

    @Override
    public void onLoad() {
        page++;
        getHDList(page, key_fenlei, value_fenlei);
    }

    @Override
    public void onDismiss() {
    }
}



package com.touyuanren.hahahuyu.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.utils.AreaDataUtil;

import java.util.ArrayList;



/**
 * 项目名称：workSpace
 * 创建人：霍凯丽
 * 创建时间：2015/11/18 9:33
 * 类描述：地址选择特效
 */
public class CityPicker extends LinearLayout {

    private WheelView mProvincePicker;
    private WheelView mCityPicker;

    private int mCurrProvinceIndex = -1;
    private int mTemCityIndex = -1;

    private AreaDataUtil mAreaDataUtil;
    private ArrayList<String> mProvinceList = new ArrayList<>();

    private String pro = "直辖市";

    private String city = "北京";

    public String getPro() {
        return pro;
    }

    public String getCity() {
        return city;
    }

    public CityPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAreaInfo();
    }

    public CityPicker(Context context) {
        this(context, null);
    }

    private void getAreaInfo() {
        mAreaDataUtil = new AreaDataUtil();
        mProvinceList = mAreaDataUtil.getProvinces();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.city_picker, this);

        mProvincePicker = (WheelView) findViewById(R.id.province);
        mCityPicker = (WheelView) findViewById(R.id.city);

        mProvincePicker.setData(mProvinceList);
        mProvincePicker.setDefault(0);
        String defaultProvince = mProvinceList.get(0);
        mCityPicker.setData(mAreaDataUtil.getCitysByProvince(defaultProvince));
        mCityPicker.setDefault(0);

        mProvincePicker.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                if (TextUtils.isEmpty(text))
                    return;
                if (mCurrProvinceIndex != id) {
                    mCurrProvinceIndex = id;
                    pro = mProvincePicker.getSelectedText();
                    if (pro == null || pro.equals(""))
                        return;
                    // get city names by province
                    ArrayList<String> cityList = mAreaDataUtil.getCitysByProvince(mProvinceList.get(id));
                    if (cityList.size() == 0) {
                        return;
                    }

                    mCityPicker.setData(cityList);

                    if (cityList.size() > 1) {
                        //if city is more than one,show start index == 1
                        mCityPicker.setDefault(1);
                    } else {
                        mCityPicker.setDefault(0);
                    }
                }

                city = mCityPicker.getSelectedText();
                pro = mProvincePicker.getSelectedText();
            }

            @Override
            public void selecting(int id, String text) {
                pro = mProvincePicker.getSelectedText();
                city = mCityPicker.getSelectedText();
            }
        });

        mCityPicker.setOnSelectListener(new WheelView.OnSelectListener() {

            @Override
            public void endSelect(int id, String text) {
                if (TextUtils.isEmpty(text))
                    return;
                if (mTemCityIndex != id) {
                    mTemCityIndex = id;
                    String city1 = mCityPicker.getSelectedText();

                    if (city1 == null || city1.equals(""))
                        return;
                    int lastIndex = mCityPicker.getListSize();
                    if (id > lastIndex) {
                        mCityPicker.setDefault(lastIndex - 1);
                    }
                }
                city = mCityPicker.getSelectedText();
            }

            @Override
            public void selecting(int id, String text) {
                city = mCityPicker.getSelectedText();
            }
        });

    }

}
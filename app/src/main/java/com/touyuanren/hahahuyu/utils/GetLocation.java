package com.touyuanren.hahahuyu.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.touyuanren.hahahuyu.Application.MyApplication;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jing on 2016/2/1.
 * 获取用户的定位
 */
public class GetLocation {
    /**
     * 传入上下文
     */
    private Context context;
    /**
     * 传入控件用来显示省份
     */
    private TextView textView;
    /**
     * 纬度
     */
    private double latitude = 0.0;
    /**
     * 经度
     */
    private double longitude = 0.0;

    public GetLocation(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    /**
     * 获取用户位置
     */
    public void getLocation() {
        PackageManager pm = context.getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.ACCESS_FINE_LOCATION", "packageName"));
//        if (!permission) {
//            return;
//        }
        LocationManager locationManager = (LocationManager) MyApplication.getContext().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        } else {
            LocationListener locationListener = new LocationListener() {

                // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                // Provider被enable时触发此函数，比如GPS被打开
                @Override
                public void onProviderEnabled(String provider) {

                }

                // Provider被disable时触发此函数，比如GPS被关闭
                @Override
                public void onProviderDisabled(String provider) {

                }

                //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        Log.e("Map", "Location changed : Lat: "
                                + location.getLatitude() + " Lng: "
                                + location.getLongitude());
                    }
                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude(); //经度
                longitude = location.getLongitude(); //纬度

            }
        }
        String urlString = "http://api.map.baidu.com/geocoder?location=" + latitude + "," + longitude + "&output=json";
        OkHttpClientManager.getAsyn(urlString, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("addressComponent");
                    String cityString = jsonObject2.getString("city");
                    String provinceString = jsonObject2.getString("province");
//                    text_comment_newsdetail.setText(provinceString + cityString);
                    textView.setText(cityString);
                    //告诉用户当前的城市地址
                    Toast.makeText(context, cityString, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("location", response);
            }
        });
//        text_comment_newsdetail.setText("latitude:" + latitude + "\r" + "longitude:" + longitude);
    }
}

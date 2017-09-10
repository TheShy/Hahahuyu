package com.touyuanren.hahahuyu.location;

/**
 * Created by Jing on 2016/2/22.
 */
public class UserLocation {
    String location_city;
    private static UserLocation userLocation = null;

    //静态工厂方法
    public static UserLocation getInstance() {
        if (userLocation == null) {
            userLocation = new UserLocation();
        }
        return userLocation;
    }

    public String getLocation_city() {
        return location_city;
    }

    public UserLocation() {
    }

    public void setLocation_city(String location_city) {
        this.location_city = location_city;
    }
}

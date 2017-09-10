package com.touyuanren.hahahuyu.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.utils.CommonUtils;
import com.touyuanren.hahahuyu.widget.CityPicker;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jing on 2016/1/25.
 * 发布活动界面
 */
public class PublishHuoDongActivity extends FragmentActivity implements View.OnClickListener {
    private TextView address;
    /**
     * 弹出popupwindow,选择地址
     */
    private PopupWindow popupWindow;
    /**
     * 计算屏幕宽度
     */
    private int width;
    private String[] arrString = {"相机", "图库"};

    private static String path = Environment.getExternalStorageDirectory().getAbsolutePath();
    /**
     * 弹出对话框，设置票的信息
     */
    private AlertDialog mDialog;
    /**
     * 地区选择器
     */
    private CityPicker cityPicker;
    /**
     * 确定选择的城市，并保存
     */
    private Button citySure;
    /**
     * 取消选择的城市
     */
    private Button cityCancel;
    /**
     * 发布活动的海报
     */
    private ImageView haiBao;
    /**
     * popupwindow 的布局
     */
    private View contentView;
    /**
     * 发布活动界面的总布局
     */
    private View showView;
    /**
     * 保存活动主题列表
     */
    private List<String> activityTopicList = new ArrayList<String>();
    /**
     * 保存活动形式，当选择后进行存储；
     */
    private List<String> activityFormList = new ArrayList<String>();
    /**
     * 选择活动主题
     */
    private Spinner topicSpinner;
    /**
     * 选择活动形式
     */
    private Spinner formSpinner;
    /**
     * 存储图片的绝对路径
     */
    private String imgPath;
    /**
     * spinner的适配器
     */
    private ArrayAdapter<String> adapter;
    private final int REQUEST_CODE_CAPTURE_CAMEIA = 200;
    /**
     * 增加票种
     */
    private ImageView addTicket;
    /**
     * 活动的开始时间
     */
    private TextView timeStart;
    /**
     * 活动的结束时间
     */
    private TextView timeEnd;
    /**
     * 活动发布
     */
    private Button submitPhoto;
    //省份
    private String pro;
    private ImageView btnBack;
    /**
     * 格式化时间
     */
    private SimpleDateFormat mFormatter = new SimpleDateFormat("MMMM dd yyyy hh:mm aa");
    //保存图片路径
    private String mFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.huodongfabu);
        //计算屏幕的宽高
        width = getWindowManager().getDefaultDisplay().getWidth();
        initView();
        initData();
        selectTopic();
        selectForm();
        viewAction();
    }

    public void initData() {
        //初始化活动主题集合
        activityTopicList.add("创业");
        activityTopicList.add("商务");
        activityTopicList.add("公益");
        activityTopicList.add("社交");
        activityTopicList.add("亲子");
        activityTopicList.add("电影");
        activityTopicList.add("娱乐");
        activityTopicList.add("生活");
        activityTopicList.add("音乐");
        activityTopicList.add("科技");
        activityTopicList.add("运动");
        activityTopicList.add("课程");
        activityTopicList.add("校园");
        activityTopicList.add("文化");
        activityTopicList.add("其他");
//初始化活动形式列表集合
        activityFormList.add("展览");
        activityFormList.add("沙龙");
        activityFormList.add("会议");
        activityFormList.add("培训");
        activityFormList.add("演出");
        activityFormList.add("派对");
        activityFormList.add("户外");
        activityFormList.add("盛典");
        activityFormList.add("比赛");
        activityFormList.add("其他");
        mFilePath = Environment.getExternalStorageDirectory().getPath();
        mFilePath = mFilePath + "/" + "tem.png";
    }

    public void initView() {
        address = (TextView) findViewById(R.id.address_text_huodong);
        showView = findViewById(R.id.huodong_activity);
        contentView = LayoutInflater.from(getApplication()).inflate(R.layout.my_address_layout, null);
        cityPicker = (CityPicker) contentView.findViewById(R.id.city_picker);
        citySure = (Button) contentView.findViewById(R.id.city_picker_sure);
        cityCancel = (Button) contentView.findViewById(R.id.city_picker_cancel);
        topicSpinner = (Spinner) findViewById(R.id.select_topic_spinner);
        formSpinner = (Spinner) findViewById(R.id.select_form_spinner);
        timeStart = (TextView) findViewById(R.id.activity_time_start);
        timeEnd = (TextView) findViewById(R.id.activity_time_end);
        btnBack = (ImageView) findViewById(R.id.back_huodongfabu);
        submitPhoto = (Button) findViewById(R.id.submit_phpto_fabu);
        haiBao = (ImageView) findViewById(R.id.haibao_fabu_activity);
        addTicket = (ImageView) findViewById(R.id.add_ticket_fabu);
        //绑定监听
        addTicket.setOnClickListener(this);
        address.setOnClickListener(this);
        timeStart.setOnClickListener(this);
        timeEnd.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        submitPhoto.setOnClickListener(this);
        haiBao.setOnClickListener(this);
    }

    //下拉选择活动主题，并进行监听
    public void selectTopic() {
        initAdapter(activityTopicList);
        //第四步：将适配器添加到下拉列表上
        topicSpinner.setAdapter(adapter);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        topicSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //当选择后进行存储，提交至服务器端
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    //下拉选择活动形式，并进行监听
    public void selectForm() {
        initAdapter(activityFormList);
        //第四步：将适配器添加到下拉列表上
        formSpinner.setAdapter(adapter);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        formSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //当选择后进行存储，提交至服务器端
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    //传入集合，初始化适配器
    public void initAdapter(List<String> list) {
        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    //选择城市时弹出窗的点击事件
    private void viewAction() {
        citySure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pro = cityPicker.getPro();
                address.setText(String.format("%s%s%s", pro, " ", cityPicker.getCity()));
                popupWindow.dismiss();
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });

        cityCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

    /**
     * 实现监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_text_huodong:
                setAddress();
                break;
            case R.id.activity_time_start:
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(new Date())
                        .build()
                        .show();
                break;
            case R.id.activity_time_end:
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(timeEndListener)
                        .setInitialDate(new Date())
                        .build()
                        .show();
                break;
            case R.id.back_huodongfabu:
                finish();
                break;
            case R.id.submit_phpto_fabu:
                new AlertDialog.Builder(this)
                        .setTitle("上传图片")
                        .setItems(arrString, new PartyDialogClickListener())
                        .create()
                        .show();
                break;
            case R.id.haibao_fabu_activity:
                Intent intent = new Intent(getApplicationContext(), ShowPicActivity.class);
                intent.putExtra("imgPath", imgPath);
                startActivityForResult(intent, 2);
                break;
            //点击增加票种
            case R.id.add_ticket_fabu:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View view = getLayoutInflater().inflate(R.layout.ticket_info_dialog, null);
                builder.setTitle("设置票种");
                builder.setView(view);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        mDialog.dismiss();
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        mDialog.dismiss();
                    }
                });
                mDialog = builder.create();
                mDialog.show();

                break;
        }
    }

    private class PartyDialogClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                    //相机
                    getImageFromCamera();
                    break;

                case 1:
                    //图库
                    getImageFromAlbum();
                    break;
            }
        }

    }

    //从照相机获取照片
    protected void getImageFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path, "temp" + 1)));
        Uri uri = Uri.fromFile(new File(mFilePath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 200);
    }

    //从本地相册获取照片
    protected void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path, "temp" + 0)));
        startActivityForResult(intent, 1000);
    }

    /**
     * 提交图片
     *
     * @param file 当前上传的图片文件
     */
    @SuppressWarnings("unchecked")
    private void commitPic(File file) {
        OkHttpUtils.post()
                .addFile("image", file.getName(), file)
                .url("http://192.168.0.16/hd/api/hd/test.php")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e("image", ""+response);
                    }
                });

    }

    /**
     * 上传头像回调接口
     */
//    private class UpLoadImageResultCallback extends Callback<PictureInfo> {
//
//        @Override
//        public PictureInfo parseNetworkResponse(Response response) throws IOException {
//            String jsonData = response.body().string();
//            return JsonUtils.getInstance().fromJSON(PictureInfo.class, jsonData);
//        }
//
//        @Override
//        public void onError(Request request, Exception e) {
//            CrashReport.postCatchedException(e);
//            L.e(TAG, "图片提交出错" + e.getMessage());
//            ToastUtils.showToast(getApplicationContext(), toastUpLoadFailure);
//        }
//
//        @Override
//        public void onResponse(PictureInfo response) {
//            if (response != null) {
//                int status = response.getStatus();
//                switch (status) {
//                    case 1:
//                        ToastUtils.showToast(getApplicationContext(), toastUpLoadSuccess);
//                        if (response.getData() != null) {
//                            imagePathList.remove("");
//
//                            String newImageUrl = response.getData().getImage();
//                            imagePathList.add(newImageUrl);
//                            checkHeight(imagePathList.size());
//
//                            imagePathList.add("");
//
//                            picAdapter.setImagePathList(imagePathList);
//
//                            imageIdList.add(response.getData().getId());
//                        } else {
//                            L.e(TAG, "返回图片数据出错");
//                        }
//                        break;
//
//                    default:
//                        L.e(TAG, "图片数据出错");
//                        ToastUtils.showToast(getApplicationContext(), toastUpLoadFailure);
//                        break;
//                }
//
//            } else {
//                L.e(TAG, "解析出错");
//                ToastUtils.showToast(getApplicationContext(), toastUpLoadSuccess);
//            }
//        }
//
//    }

    //选择时间开始的监听器
    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
//            Toast.makeText(HuoDongActivity.this,
//                    mFormatter.format(date), Toast.LENGTH_SHORT).show();
            timeStart.setText(mFormatter.format(date));
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
            Toast.makeText(PublishHuoDongActivity.this,
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };
    //选择时间结束的监听器
    private SlideDateTimeListener timeEndListener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
//            Toast.makeText(HuoDongActivity.this,
//                    mFormatter.format(date), Toast.LENGTH_SHORT).show();
            timeEnd.setText(mFormatter.format(date));
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
            Toast.makeText(PublishHuoDongActivity.this,
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 设置地址
     */
    private void setAddress() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.animPublishPopup);
        popupWindow.showAtLocation(showView, Gravity.BOTTOM | Gravity.START, 0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case 200:
//                        Bitmap photo = data.getParcelableExtra("data");
//                Bitmap photo = (Bitmap) data.getExtras().get("data");
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(mFilePath);
                    Bitmap mBitmap = BitmapFactory.decodeStream(fis);
                    haiBao.setImageBitmap(mBitmap);
                    Log.e("mFilePath",mFilePath);
                    commitPic(new File(mFilePath));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
//                haiBao.setImageBitmap(photo);
                //计算缩小的比例
                break;

            case 1000:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Uri uri = data.getData();
                        imgPath = changeUriToPath(uri);
                        //计算缩小的比例
                        int scale = CommonUtils.getScale(getApplicationContext(), imgPath, width / 5);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = false;
                        options.inSampleSize = scale;
                        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
                        commitPic(new File(imgPath));
                        haiBao.setImageBitmap(bitmap);
                        break;
                    case Activity.RESULT_CANCELED:// 取消
                        break;
                }
        }
    }


    protected void doCropPhoto(Bitmap data) {
        Intent intent = getCropImageIntent(data);
        startActivityForResult(intent, 1001);
    }

    //对照片进行裁剪
    public static Intent getCropImageIntent(Bitmap data) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        intent.putExtra("data", data);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 128);
        intent.putExtra("outputY", 128);
        intent.putExtra("return-data", true);
        return intent;
    }

    // 将URI转换为真实路径
    private String changeUriToPath(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualImageCursor = managedQuery(uri, proj, null, null, null);
        int actual_image_column_index = actualImageCursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualImageCursor.moveToFirst();
        String currentImagePath = actualImageCursor
                .getString(actual_image_column_index);
        return currentImagePath;
    }
}

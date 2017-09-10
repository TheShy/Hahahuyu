package com.touyuanren.hahahuyu.ui.activity.account;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.multi_image_selector.MultiImageSelectorActivity;
import com.multi_image_selector.utils.ImageCompressUtils;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.adapter.PlayerImgAdapter;
import com.touyuanren.hahahuyu.utils.FoContents;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.utils.FoImage;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 2016/10/26.
 * 发布动态
 */
public class PublishDongTaiAct extends BaseActivity {
    private static final String TAG = "PublishDongTaiAct";
    private EditText et_dongtai;
    /**
     * 用gridview 展示将要上传的图片
     */
    private RecyclerView recyclerView;
    /**
     * gridview的适配器
     */
    private PlayerImgAdapter adapter;
    /**
     * 处理后的图片集合
     */
    private List<String> list_small_image = new ArrayList<>();
    /**
     * 选取的原始图片地址集合
     */
    private ArrayList<String> originalPathListAll = new ArrayList<>();
    /**
     * 处理后的图片文件集合
     */
    private List<File> newImageFileList = new ArrayList<>();
    private int num = 0;
    private List<String> list_id = new ArrayList<>();
    //新的图片路径集合
    private List<String> newImageFilePathList = new ArrayList<>();
    ArrayList<String> ima_list = new ArrayList();
    //加载时显示的对话框
    private ProgressDialog imageHandlerDialog;
    //上传图片的dialog
    private ProgressDialog uploadDialog;
    //关于图片处理的参数
    private int imageHandlerCount = 0;
    //记录图片上传张数
    private int count = 0;
    //图片总张数
    private int countAll = 0;
    //返回来的图片张数
    private int totalSize = 0;
    private String blogId;
    private Handler imageHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    doImageHandler();
                    break;
                case 300:
                    if (imageHandlerDialog != null && imageHandlerDialog.isShowing()) {
                        imageHandlerDialog.dismiss();
                    }
                    //开始上传
                    Log.e("newImageFileList", newImageFileList.size() + "");
                    for (int i = 0; i < newImageFileList.size(); i++) {
                        String path = newImageFileList.get(i).getPath();
                        Log.e("path", path);
                        list_small_image.add(path);
                    }
                    adapter.setList(list_small_image);
                    checkHeight(list_small_image);
                    break;
                case 400:
                    //循环上传照片
                    if (count < newImageFileList.size()) {
                        uploadImageFile(newImageFileList.get(count), blogId);
                    } else {
                        FoToast.toast(MyApplication.getContext(), "动态发布成功");
                        setResult(RESULT_OK);
//                            finish();
                        finish();
                    }
                    break;
            }
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_publish_dongtai);
        et_dongtai = (EditText) findViewById(R.id.et_dongtai_publish);
        setTitleLeftBtn();
        setTitleName(R.string.publish_dongtai);
        setTitleRightBtn(R.string.fabu, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_dongtai.getText().toString())) {
                    FoToast.toast(MyApplication.getContext(), "请输入动态");
                    return;
                }
                fabu();
            }
        });

        Intent intent = getIntent();
        //判断跳转步骤
        if (intent.getBooleanExtra("BZTZ",false)){
            originalPathListAll = intent.getStringArrayListExtra("originalPathListAll");
            num = originalPathListAll.size();
            if (originalPathListAll != null && originalPathListAll.size() > 0) {
                totalSize = originalPathListAll.size();
                num = num + totalSize;
                imageHandler.sendEmptyMessage(200);
            } else {
                Log.e("dd", "返回数据为Null");
            }
        }

        initView();
    }
    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.rcv_select_pic_publishdongtai);
        adapter = new PlayerImgAdapter(list_small_image);
        list_small_image.add("");
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.setList(list_small_image);
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = (int) (MyApplication.screenWidth - 20) / 4;
        recyclerView.setLayoutParams(params);
        viewAction();
        uploadDialog = new ProgressDialog(this);
        uploadDialog.setMessage("正在上传,请稍后...");
        uploadDialog.setCanceledOnTouchOutside(false);
        //创建对话框
        imageHandlerDialog = new ProgressDialog(this);
        imageHandlerDialog.setMessage("正在处理，请稍候...");
        imageHandlerDialog.setCanceledOnTouchOutside(false);
        imageHandlerDialog.setCancelable(false);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
    }

    public void fabu() {
        showLoading();
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "add_dt");
        formMap.put("content", et_dongtai.getText().toString());
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.USER_DONGTAI, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                Log.e(TAG, response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
//                            FoToast.toast(MyApplication.getContext(), jsonObject.getString("msg"));
                            //开始上传图片
                            JSONObject jsonObject1 = (JSONObject) jsonObject.get("data");
                            blogId=jsonObject1.getString("id");
                            imageHandler.sendEmptyMessage(400);
//                            setResult(RESULT_OK);
//                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                hideLoading();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FoContents.REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                //接收回传的图片路径；先压缩，上传,在显示在gridview上，
                originalPathListAll = data.getStringArrayListExtra("select_result");
                Log.e("originalPathListAll", "" + originalPathListAll.size());
                if (originalPathListAll != null && originalPathListAll.size() > 0) {
                    totalSize = originalPathListAll.size();
                    num = num + totalSize;
                    imageHandler.sendEmptyMessage(200);
                } else {
                    Log.e("dd", "返回数据为Null");
                }
            }
        }
    }

    private void viewAction() {
        adapter.setListener(new PlayerImgAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View parent, int position, int type) {
                switch (type) {
                    case FoContents.ADD_TYPE:
                        Intent intent = new Intent(PublishDongTaiAct.this, MultiImageSelectorActivity.class);
                        // 是否显示调用相机拍照
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                        // 最大图片选择数量
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9 - num);
                        // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                        startActivityForResult(intent, FoContents.REQUEST_IMAGE);
                        break;

                    case FoContents.IMAGE_TYPE:
                        imageClick(position);
                        break;
                }
            }
        });
    }

    /**
     * @param position 点击的位置
     */
    private void imageClick(int position) {
        final int location = position;
        new AlertDialog.Builder(PublishDongTaiAct.this)
                .setTitle("选择操作")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (list_id != null && list_id.size() > location) {
                            list_id.remove(location);
                            list_small_image.remove(location);
                            list_small_image.remove("");
                        }
                        num--;
                        checkHeight(list_small_image);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    //暂时不知道此方法作用
    private void checkHeight(List<String> picDataFile) {
        LinearLayout.LayoutParams params;
        picDataFile.remove("");
        if (picDataFile.size() <= 3) {
            int paramsHeight = (int) (MyApplication.screenWidth - 20) / 4;
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, paramsHeight);
            recyclerView.setLayoutParams(params);
            picDataFile.add("");
            adapter.setList(picDataFile);
            return;
        }

        if (picDataFile.size() > 3 && picDataFile.size() < 8) {
            int paramsHeight = (int) (MyApplication.screenWidth - 20) / 2;
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, paramsHeight);
            recyclerView.setLayoutParams(params);
            picDataFile.add("");
            adapter.setList(picDataFile);
            return;
        }

        if (picDataFile.size() == 8) {
            int paramsHeight = (int) (MyApplication.screenWidth - 20) / 4 * 3;
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, paramsHeight);
            picDataFile.add("");
            adapter.setList(picDataFile);
            recyclerView.setLayoutParams(params);
            return;
        }

        if (picDataFile.size() == 9) {
            int paramsHeight = (int) (MyApplication.screenWidth - 20) / 4 * 3;
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, paramsHeight);
            adapter.setList(picDataFile);
            recyclerView.setLayoutParams(params);
        }
    }

    /**
     * 新开线程进行图片处理
     */
    private void doImageHandler() {

        if (imageHandlerDialog != null && !imageHandlerDialog.isShowing()) {
            imageHandlerDialog.show();
        }

        if (imageHandlerDialog != null) {
            imageHandlerDialog.setMessage("正在处理...");
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //图片原始路径
                String originalPath = originalPathListAll.get(imageHandlerCount);
                //图片原始文件对象
                File originalFile = new File(originalPath);
                //图片文件的父级路径
                String originalFileParenPath = originalFile.getParent();
                //图片文件名
                String originalFileName = originalFile.getName();
                //去掉扩展名后的图片文件名
                String originalFileNoExtensionName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
                //新文件的完整路径
                String newFilCompletePath = String.format("%s%s%s%s%s", originalFileParenPath, File.separator, originalFileNoExtensionName, "_new", ".jpg");

                int[] imageParamsArray = ImageCompressUtils.getImageParams(originalPath);

                int imageWidth = imageParamsArray[0];
                int imageHeight = imageParamsArray[1];

                Bitmap zoomBitmap = BitmapFactory.decodeFile(originalPath, FoImage.zoom(imageWidth, imageHeight));

                File zoomFile = ImageCompressUtils.saveBitmapToFile(zoomBitmap, newFilCompletePath);
                ImageCompressUtils.recycleBitmap(zoomBitmap);
                newImageFileList.add(zoomFile);

                imageHandlerCount++;

                if (imageHandlerCount < originalPathListAll.size()) {
                    imageHandler.sendEmptyMessage(200);
                } else {
                    imageHandler.sendEmptyMessage(300);
                    imageHandlerCount = 0;
                }
            }
        }
        );
        thread.start();
    }
    public void upLoadLog() {
        showLoading();
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "fabu");
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.PUBLISH_LOG, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                Log.e(TAG, response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = (String) jsonObject.get("status");
                    JSONObject jsonObject1 = (JSONObject) jsonObject.get("data");
                    if (status.equals("1")) {
                        String myblog_id = jsonObject1.getString("myblog_id");
                        blogId = myblog_id;
                        imageHandler.sendEmptyMessage(400);
//                        uploadImageFile(newImageFileList.get(0),blogId);
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideLoading();
            }
        });

    }

    /**
     * 上传图片
     */
    private void uploadImageFile(File file, String myblog_id) {
        showLoading();
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "upload_pic");
        formMap.put("type", "2");
        formMap.put("id", myblog_id);
        FoHttp.upLoadFile(HttpApi.ROOT_PATH + HttpApi.PUBLISH_LOG, formMap, file, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                Log.e(TAG, response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        count++;
                        imageHandler.sendEmptyMessage(400);
                    } else {
                        FoToast.toast(MyApplication.getContext(), "照片上传失败");
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideLoading();
            }
        });
    }
}

package com.touyuanren.hahahuyu.ui.activity;

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
import android.widget.LinearLayout;

import com.multi_image_selector.MultiImageSelectorActivity;
import com.multi_image_selector.utils.ImageCompressUtils;
import com.multi_image_selector.utils.ToastUtils;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.adapter.PlayerImgAdapter;
import com.touyuanren.hahahuyu.utils.FoContents;
import com.touyuanren.hahahuyu.utils.FoPreference;
import com.touyuanren.hahahuyu.widget.L;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Jing on 2016/9/7.
 * 摄影比赛提交作品
 */
public class CommitWorksAct extends BaseActivity {
    /**
     * 用gridview 展示将要上传的图片
     */
    RecyclerView recyclerView;
    private static final String TAG = "CommitWorksAct";
    //gridview的适配器
    private PlayerImgAdapter adapter;
    //加载时显示的对话框
    private ProgressDialog imageHandlerDialog;
    /**
     * 上传图片的dialog
     */
    private ProgressDialog uploadDialog;
    //关于图片处理的参数
    private int imageHandlerCount = 0;
    //记录图片上传张数
    private int count = 0;
    //图片总张数
    private int countAll = 0;
    //返回来的图片张数
    private int totalSize = 0;
    private int num = 0;
    private List<String> list_id = new ArrayList<>();
    /**
     * 选取的原始图片地址集合
     */
    private ArrayList<String> originalPathListAll = new ArrayList<>();
    /**
     * 处理后的图片集合
     */
    private List<String> list_small_image = new ArrayList<>();
    /**
     * 处理后的图片文件集合
     */
    private List<File> newImageFileList = new ArrayList<>();

    //新的图片路径集合
    private List<String> newImageFilePathList = new ArrayList<>();
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
                    Log.e("总张数", totalSize + "");
                    //开始上传
                    Log.i(TAG, "countAll=" + countAll);
                    File newImageFile = newImageFileList.get(countAll);
                    uploadImageFile(newImageFile);
                    break;
            }
            return false;
        }
    });
    /**
     * 赛事id
     */
    private String gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_commit_works);
        gameId = getIntent().getStringExtra("gameId");
        setTitleLeftBtn();
        setTitleName(R.string.upload_works);
        setTitleRightBtn(R.string.accomplish, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //完成上传
                finish();
            }
        });
        adapter = new PlayerImgAdapter(list_small_image);
        list_small_image.add("");
        recyclerView = (RecyclerView) findViewById(R.id.rcv_select_pic);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.setList(list_small_image);
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = (int) (MyApplication.screenWidth - 20) / 4;
        recyclerView.setLayoutParams(params);
        viewAction();
        imageHandlerDialog = new ProgressDialog(this);
        imageHandlerDialog.setMessage("正在处理，请稍候...");
        imageHandlerDialog.setCanceledOnTouchOutside(false);
        imageHandlerDialog.setCancelable(false);

        uploadDialog = new ProgressDialog(this);
        uploadDialog.setMessage("正在上传,请稍后...");
        uploadDialog.setCanceledOnTouchOutside(false);
    }

    private void viewAction() {
        adapter.setListener(new PlayerImgAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View parent, int position, int type) {
                switch (type) {
                    case FoContents.ADD_TYPE:
                        Intent intent = new Intent(CommitWorksAct.this, MultiImageSelectorActivity.class);
                        // 是否显示调用相机拍照
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
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
        new AlertDialog.Builder(CommitWorksAct.this)
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FoContents.REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                //接收回传的图片路径；先压缩，上传,在显示在gridview上，
                originalPathListAll = data.getStringArrayListExtra("select_result");
                if (originalPathListAll != null && originalPathListAll.size() > 0) {
                    totalSize = originalPathListAll.size();
                    num = num + totalSize;
                    imageHandler.sendEmptyMessage(200);
                } else {
                    Log.e(TAG, "返回数据为Null");
                }
            }
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
                Log.i(TAG, "originalFileParenPath=" + originalFileParenPath);
                //图片文件名
                String originalFileName = originalFile.getName();
                Log.i(TAG, "originalFileName=" + originalFileName);
                //去掉扩展名后的图片文件名
                String originalFileNoExtensionName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
                Log.i(TAG, "originalFileNoExtensionName=" + originalFileNoExtensionName);
                //新文件的完整路径
                String newFilCompletePath = String.format("%s%s%s%s%s", originalFileParenPath, File.separator, originalFileNoExtensionName, "_new", ".jpg");
                Log.i(TAG, "newFilCompletePath=" + newFilCompletePath);

                int[] imageParamsArray = ImageCompressUtils.getImageParams(originalPath);
                Log.i(TAG, "imageParamsArray=before" + Arrays.toString(imageParamsArray));

                int imageWidth = imageParamsArray[0];
                int imageHeight = imageParamsArray[1];

                Bitmap zoomBitmap = BitmapFactory.decodeFile(originalPath, zoom(imageWidth, imageHeight));

                File zoomFile = ImageCompressUtils.saveBitmapToFile(zoomBitmap, newFilCompletePath);
                ImageCompressUtils.recycleBitmap(zoomBitmap);
                newImageFileList.add(zoomFile);
                Log.e("newImageFileList",newImageFileList.size()+"");
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

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    /**
     * 上传图片
     */
    private void uploadImageFile(File file) {
        Log.i(TAG, "要上传的文件的路径=" + file.getAbsolutePath());
        newImageFilePathList.add(file.getAbsolutePath());
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "sczp");
        formMap.put("hd_id", gameId);
        formMap.put("token", FoPreference.getString(FoContents.LOGIN_TOKEN));
        OkHttpUtils.post()
                .params(formMap)
                .addFile("pic", file.getName(), file)
                .url(HttpApi.ROOT_PATH + HttpApi.UPLOAD_INFO)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        if (count == 0) {
                            uploadDialog.show();
                        }
//                        count++;
                    }

                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                        if (TextUtils.isEmpty(response)) {
                            ToastUtils.showToast(getApplicationContext(), "无返回数据！");
                        } else
                            try {
                                Date date = new Date(System.currentTimeMillis());
                                String nowTime = format.format(date);
                                L.i(TAG, "(成功)上传结束时间=" + nowTime);
                                JSONObject object = new JSONObject(response);
                                if ("1".equals(object.getString("status"))) {
                                    JSONObject object1 = object.getJSONObject("data");
                                    String img = object1.getString("path");
//                                String img = object1.getString("min_image");
//                                list_id.add(id);
                                    list_small_image.add(HttpApi.ROOT_PATH + img);
                                    adapter.setList(list_small_image);
                                    checkHeight(list_small_image);
                                } else {
//                                ToastUtils.showToast(getApplicationContext(), R.string.toast_up_load_failure);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    }
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        count++;
                        Log.i(TAG, "count=" + count);
                        countAll++;
                        Log.i(TAG, "countAll=" + countAll);
                        if (count < totalSize) {
                            imageHandler.sendEmptyMessage(300);
                        } else {
                            if (uploadDialog != null && uploadDialog.isShowing())
                                uploadDialog.dismiss();
                        }
                    }
                });
    }

    /**
     * 计算缩放设置
     *
     * @param imageWidth  图像宽度
     * @param imageHeight 图像高度
     * @return 缩放设置实例
     */
    private BitmapFactory.Options zoom(int imageWidth, int imageHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        int baseHeight = 720, baseWidth = 560;
        /*if (number<=2){
            baseHeight = 1440;
            baseWidth = 1024;}*/
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        if (imageWidth > baseWidth * 15 || imageHeight > baseHeight * 15) {
            options.inSampleSize = 15;
        } else if (imageWidth > baseWidth * 14 || imageHeight > baseHeight * 14) {
            options.inSampleSize = 14;
        } else if (imageWidth > baseWidth * 13 || imageHeight > baseHeight * 13) {
            options.inSampleSize = 13;
        } else if (imageWidth > baseWidth * 12 || imageHeight > baseHeight * 12) {
            options.inSampleSize = 12;
        } else if (imageWidth > baseWidth * 11 || imageHeight > baseHeight * 11) {
            options.inSampleSize = 11;
        } else if (imageWidth > baseWidth * 10 || imageHeight > baseHeight * 10) {
            options.inSampleSize = 10;
        } else if (imageWidth > baseWidth * 9 || imageHeight > baseHeight * 9) {
            options.inSampleSize = 9;
        } else if (imageWidth > baseWidth * 8 || imageHeight > baseHeight * 8) {
            options.inSampleSize = 8;
        } else if (imageWidth > baseWidth * 7 || imageHeight > baseHeight * 7) {
            options.inSampleSize = 7;
        } else if (imageWidth > baseWidth * 6 || imageHeight > baseHeight * 6) {
            options.inSampleSize = 6;
        } else if (imageWidth > baseWidth * 5 || imageHeight > baseHeight * 5) {
            options.inSampleSize = 5;
        } else if (imageWidth > baseWidth * 4 || imageHeight > baseHeight * 4) {
            options.inSampleSize = 4;
        } else if (imageWidth > baseWidth * 3 || imageHeight > baseHeight * 3) {
            options.inSampleSize = 3;
        } else if (imageWidth < baseWidth * 2 || imageHeight < baseHeight * 2) {
            options.inSampleSize = 2;
        } else if (imageWidth < baseWidth || imageHeight < baseHeight) {
            options.inSampleSize = 1;
        } else {
            options.inSampleSize = 1;
        }
        return options;
    }

}

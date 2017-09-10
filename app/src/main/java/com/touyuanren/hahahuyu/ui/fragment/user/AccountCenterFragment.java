package com.touyuanren.hahahuyu.ui.fragment.user;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.common.Constant;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.manage.LogoManager;
import com.touyuanren.hahahuyu.ui.activity.TestAty;
import com.touyuanren.hahahuyu.ui.activity.account.AccountManagementAct;
import com.touyuanren.hahahuyu.ui.activity.account.LoginActivity;
import com.touyuanren.hahahuyu.ui.activity.account.ManageMoneyAct;
import com.touyuanren.hahahuyu.ui.activity.account.OrganizerActivity;
import com.touyuanren.hahahuyu.ui.activity.users.ChongzhiActivity;
import com.touyuanren.hahahuyu.ui.activity.users.MyAlbumActivity;
import com.touyuanren.hahahuyu.ui.activity.users.MyCollectActivity;
import com.touyuanren.hahahuyu.ui.activity.users.MyJoinActivity;
import com.touyuanren.hahahuyu.ui.activity.users.MyLogAct;
import com.touyuanren.hahahuyu.ui.activity.users.MyOrderAct;
import com.touyuanren.hahahuyu.ui.activity.users.OthersActivity;
import com.touyuanren.hahahuyu.ui.activity.users.SettingActivity;
import com.touyuanren.hahahuyu.ui.activity.users.UserDongTaiAct;
import com.touyuanren.hahahuyu.ui.activity.users.UserpackActivity;
import com.touyuanren.hahahuyu.ui.fragment.BaseFragment;
import com.touyuanren.hahahuyu.utils.FoContents;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.utils.FoPreference;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.touyuanren.hahahuyu.utils.moveimg.KenBurnsView;
import com.touyuanren.hahahuyu.utils.moveimg.Transition;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Jing on 2016/1/27.
 * 个人中心界面，相关的设置，及用户信息展示
 */
public class AccountCenterFragment extends BaseFragment implements View.OnClickListener, KenBurnsView.TransitionListener {

    private ImageView icon;
    private File tempFile = null;
    private TextView userName, tv_yue, tv_zbi, tv_dongjieyue, tv_manageMoney;
    private View mView;
    private RelativeLayout tv_myjoin, tv_zhuban, tv_chongzhi, tv_guanzhu,
            tv_order, tv_xiangce, tv_rizhi, tv_beibao, tv_dongtai, tv_shoucang, tv_shezhi, tv_gengduo;

    private Dialog dialog;

    String zhanghuyue;
    String yue_dongjie;
    String zbi;

    //图片移动
    private static final int TRANSITIONS_TO_SWITCH = 3;
    private ViewSwitcher mViewSwitcher;
    private int mTransitionsCount = 0;

    @Override

    public View getShowView(LayoutInflater inflater) {
        mView = inflater.inflate(R.layout.fragment_account_center, null);
        initView();
        initData();
        return mView;
    }


    private void initView() {
        tv_myjoin = (RelativeLayout) mView.findViewById(R.id.tv_myjoin_center);
        icon = (ImageView) mView.findViewById(R.id.persion_img);
        userName = (TextView) mView.findViewById(R.id.user_name_frag_center);
        tv_yue = (TextView) mView.findViewById(R.id.tv_zhanghuyue_center);
        tv_zbi = (TextView) mView.findViewById(R.id.tv_zbicenter);
        tv_dongjieyue = (TextView) mView.findViewById(R.id.tv_dongjieyue_center);
        tv_zhuban = (RelativeLayout) mView.findViewById(R.id.tv_zhuban_center);
        tv_guanzhu = (RelativeLayout) mView.findViewById(R.id.tv_guanzhu_center);
        tv_chongzhi = (RelativeLayout) mView.findViewById(R.id.tv_chongzhi_center);
        tv_manageMoney = (TextView) mView.findViewById(R.id.tv_manage_money_center);
        tempFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
        tv_order = (RelativeLayout) mView.findViewById(R.id.tv_order_center);
        tv_xiangce = (RelativeLayout) mView.findViewById(R.id.tv_xiangce_center);
        tv_rizhi = (RelativeLayout) mView.findViewById(R.id.tv_rizhi_center);
        tv_beibao = (RelativeLayout) mView.findViewById(R.id.tv_beibao_center);
        tv_dongtai = (RelativeLayout) mView.findViewById(R.id.tv_dongtai_center);
        tv_shoucang = (RelativeLayout) mView.findViewById(R.id.tv_shoucang_center);
        tv_shezhi = (RelativeLayout) mView.findViewById(R.id.tv_shezhi_center);
        tv_gengduo = (RelativeLayout) mView.findViewById(R.id.tv_gengduo_center);

        //图片移动
        mViewSwitcher = (ViewSwitcher) mView.findViewById(R.id.viewSwitcher);
        KenBurnsView kenBurnsView = (KenBurnsView) mView.findViewById(R.id.iv_moveimg);
        kenBurnsView.setImageResource(R.drawable.moveimg_bg);
        kenBurnsView.setTransitionListener(this);

        //自定义图片形状
        userimg_dig();

        //绑定监听
        userName.setOnClickListener(this);
        tv_myjoin.setOnClickListener(this);
        icon.setOnClickListener(this);
        tv_zhuban.setOnClickListener(this);
        tv_guanzhu.setOnClickListener(this);
        tv_manageMoney.setOnClickListener(this);
        tv_chongzhi.setOnClickListener(this);
        tv_order.setOnClickListener(this);
        tv_xiangce.setOnClickListener(this);
        tv_rizhi.setOnClickListener(this);
        tv_beibao.setOnClickListener(this);
        tv_dongtai.setOnClickListener(this);
        tv_shoucang.setOnClickListener(this);
        tv_shezhi.setOnClickListener(this);
        tv_gengduo.setOnClickListener(this);
    }

    private void initData() {
        getUserInfo();
    }

    private Button btn_cancel;
    private Button btn_userimg;
    private Button btn_usermsg;
    private Button btn_user$;

    public void userimg_dig() {
        dialog = new Dialog(getContext(), R.style.ActionSheetDialogStyle);
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.user_topimg, null);

        btn_cancel = (Button) inflate.findViewById(R.id.btn_cancel);
        btn_userimg = (Button) inflate.findViewById(R.id.btn_userimg);
        btn_usermsg = (Button) inflate.findViewById(R.id.btn_usermsg);
        btn_user$ = (Button) inflate.findViewById(R.id.btn_user$);

        btn_cancel.setOnClickListener(this);
        btn_userimg.setOnClickListener(this);
        btn_usermsg.setOnClickListener(this);
        btn_user$.setOnClickListener(this);

        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;
        lp.width = (int) getResources().getDisplayMetrics().widthPixels - 30; // 宽度
        dialogWindow.setAttributes(lp);
    }

    //监听
    @Override
    public void onClick(View v) {
        Intent mIntent;
        switch (v.getId()) {
            case R.id.tv_myjoin_center:
                mIntent = new Intent(getActivity(), MyJoinActivity.class);
                startActivity(mIntent);
                break;
            //进入头像修改；
            case R.id.persion_img:
                dialog.show();
                break;
            //进入到主办方界面
            case R.id.tv_zhuban_center:
                mIntent = new Intent(getActivity(), OrganizerActivity.class);
                startActivity(mIntent);
                break;
            case R.id.tv_manage_money_center:
                break;
            case R.id.tv_guanzhu_center:
                mIntent = new Intent(getActivity(), TestAty.class);
                startActivity(mIntent);
                break;
            case R.id.tv_chongzhi_center:
                mIntent = new Intent(getActivity(), ChongzhiActivity.class);
                startActivity(mIntent);
                break;
            //订单列表
            case R.id.tv_order_center:
                mIntent = new Intent(getActivity(), MyOrderAct.class);
                startActivity(mIntent);
                break;
            case R.id.btn_cancel:
                if (dialog != null) {
                    dialog.dismiss();
                }
                break;
            case R.id.btn_userimg:
                if (dialog != null) {
                    dialog.dismiss();
                }
                startChoose();
                break;
            case R.id.btn_usermsg:
                if (dialog != null) {
                    dialog.dismiss();
                }
                mIntent = new Intent(getActivity(), AccountManagementAct.class);
                startActivity(mIntent);
                break;
            case R.id.btn_user$:
                if (dialog != null) {
                    dialog.dismiss();
                }
                mIntent = new Intent(getActivity(), ManageMoneyAct.class);
                startActivity(mIntent);
                break;
            case R.id.tv_xiangce_center:
                mIntent = new Intent(getActivity(), MyAlbumActivity.class);
                startActivity(mIntent);
                break;
            case R.id.tv_rizhi_center:
                mIntent = new Intent(getActivity(), MyLogAct.class);
                startActivity(mIntent);
                break;
            case R.id.tv_beibao_center:
                mIntent = new Intent(getActivity(), UserpackActivity.class);
                mIntent.putExtra("zhanghuyue", zhanghuyue);
                mIntent.putExtra("yue_dongjie", yue_dongjie);
                mIntent.putExtra("zbi", zbi);
                startActivity(mIntent);
                break;
            case R.id.tv_dongtai_center:
                mIntent = new Intent(getActivity(), UserDongTaiAct.class);
                startActivity(mIntent);
                break;
            case R.id.tv_shoucang_center:
                mIntent = new Intent(getActivity(), MyCollectActivity.class);
                startActivity(mIntent);
                break;
            case R.id.tv_shezhi_center:
                mIntent = new Intent(getActivity(), SettingActivity.class);
                startActivity(mIntent);
                break;
            case R.id.tv_gengduo_center:
                mIntent = new Intent(getActivity(), OthersActivity.class);
                startActivity(mIntent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FoContents.PHOTO_REQUEST_TAKE_PHOTO:
                startPhotoZoom(Uri.fromFile(tempFile), 200);
                break;

            case FoContents.PHOTO_REQUEST_GALLERY:
                if (data != null) {
                    startPhotoZoom(data.getData(), 200);
                } else {
                    Log.e("ima", "没有返回图片");
                }
                break;
            case FoContents.PHOTO_REQUEST_CUT:
                Bundle bundle = null;
                if (data != null) {
                    bundle = data.getExtras();
                }
                if (bundle != null) {
                    File tempFile1;
                    getPhotoFileName();
                    Bitmap photo = bundle.getParcelable("data");
                    try {
                        //保存裁剪后的数据并压缩
                        tempFile1 = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
                        FileOutputStream fos = new FileOutputStream(tempFile1);
                        if (photo != null) {
                            photo.compress(Bitmap.CompressFormat.JPEG, 99, fos);
                        }
                        commitPic(tempFile1);
                    } catch (FileNotFoundException e) {
                        // bugly会将这个throwable上报
                        e.printStackTrace();
                    }
                }
                break;
            case FoContents.RESULT_Account_center:
                String name = data.getExtras().getString("name");
                Log.i("name", name);
                userName.setText(name);
                break;
            //未登录时重新请求数据

        }
    }

    //获取用户信息
    private void getUserInfo() {
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "user_info");
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.USER_INFO, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(), "网络错误，请检查您的网络连接！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            JSONObject jsonObject2 = jsonObject1.getJSONObject("info");
                            zhanghuyue = jsonObject2.getString("balance");
                            yue_dongjie = jsonObject2.getString("frozen_money");
                            zbi = jsonObject2.getString("zb");
                            String nickname = jsonObject2.getString("nick_name");
                            FoPreference.putString(FoContents.REGISTER_PHONE, jsonObject2.getString("mobile_phone"));
                            FoPreference.putString(FoContents.NICKNAME, nickname);
                            //设置头像
//                            icon.setImageURI(Uri.parse(HttpApi.ROOT_PATH + jsonObject2.getString("photo")));
                            LogoManager.setImageViewBitmap(getActivity(), HttpApi.ROOT_PATH + jsonObject2.getString("photo"),
                                    icon, Constant.HEAD_URL, R.drawable.icon_user);
                            userName.setText(nickname);
                            tv_yue.setText(zhanghuyue + "元");
                            tv_zbi.setText(zbi + "Z币");
                            tv_dongjieyue.setText(yue_dongjie + "元");
                        } else if (status.equals("103")) {
                            Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(mIntent);
                            //暂时销毁
                            getActivity().finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 添加头像图片
     */
    private void startChoose() {
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.my_set_photo)
                .setPositiveButton(R.string.my_photo, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 调用系统的拍照功能
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // 指定调用相机拍照后照片的储存路径
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                        startActivityForResult(intent, FoContents.PHOTO_REQUEST_TAKE_PHOTO);
                    }
                })
                .setNegativeButton(R.string.my_set_album, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, FoContents.PHOTO_REQUEST_GALLERY);
                    }
                })
                .create()
                .show();
    }

    /**
     * 提交图片
     *
     * @param file 当前上传的图片文件;
     */
    private void commitPic(File file) {
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        formMap.put("token", FoPreference.getString(FoContents.LOGIN_TOKEN));
        OkHttpUtils.post().params(formMap).url(HttpApi.ROOT_PATH + HttpApi.UPLOAD_ICON).addFile("photo", file.getName(), file).build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(), "网络错误，请检查您的网络连接！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                Log.e("iconpath", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ("1".equals(jsonObject.getString("status"))) {
                        FoToast.toast(MyApplication.getContext(), jsonObject.getString("msg"));
                        String iconpath = HttpApi.ROOT_PATH + jsonObject.getJSONObject("data").getString("photo");
                        Log.e("iconpath", iconpath);
                        //加载头像
                        LogoManager.setImageViewBitmap(getActivity(), iconpath,
                                icon, Constant.HEAD_URL, R.drawable.icon_user);
//                        icon.setImageURI(Uri.parse(iconpath));
//                        Glide.with(MyApplication.getContext()).load(iconpath).placeholder(R.drawable.icon_user).error(R.drawable.icon_user).into(icon);
                    } else {
                        FoToast.toast(MyApplication.getContext(), jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 使用系统当前日期加以调整作为照片的名称
     *
     * @return 照片的名称
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss", Locale.getDefault());
        return dateFormat.format(date) + ".jpg";
    }

    /**
     * 转到裁剪缩放页面
     *
     * @param uri  图片Uri
     * @param size 目标宽高
     */
    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, FoContents.PHOTO_REQUEST_CUT);
    }

    /**
     * 移动图片
     *
     * @param transition the transition that just started.
     */
    @Override
    public void onTransitionStart(Transition transition) {

    }

    @Override
    public void onTransitionEnd(Transition transition) {
        mTransitionsCount++;
        if (mTransitionsCount == TRANSITIONS_TO_SWITCH) {
            mViewSwitcher.showNext();
            mTransitionsCount = 0;
        }
    }

}

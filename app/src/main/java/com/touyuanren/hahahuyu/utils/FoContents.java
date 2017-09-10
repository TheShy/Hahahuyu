package com.touyuanren.hahahuyu.utils;

/**
 * Created by Jing on 2016/3/7.
 * 存储常量；
 */
public class FoContents {

    /**
     * 跳转到登录界面的请求码
     */
    public static final int RESQUEST_LOGIN = 100;
    /**
     * 跳转到个人中心界面的请求码
     */
    public static final int RESULT_Account_center = 200;
    /**
     * 进行实名认证的请求码
     */
    public static final int REQUSET_REALNAME = 101;
    /**
     * 实名认证后的结果吗码
     */
    public static final int RESULT_REALNAME = 201;
    /**
     * 用户信息
     * 是否绑定手机,是否实名认证,是否设置交易密码,是否绑定银行卡
     */
    public final static String IS_SET_PAY = "is_set_pay";

    public final static String IS_PHONE = "isPhone";
    public final static String IS_REAL_NAME = "isRealName";
    public final static String IS_TRADE_PWD = "isTradePwd";
    public final static String IS_CARD = "isCard";
    public final static String ID_CARD = "id_card";
    public final static String BIND_PHONE = "bind_phone";
    public final static String NICKNAME = "nick_name";
    public static final String REALNAME = "realName"; //真实姓名
    /**
     * 图片类型:网络
     */
    public static final int IMAGE_TYPE = 1;
    /**
     * 图片类型:添加图片
     */
    public static final int ADD_TYPE = 2;
    /**
     * 多图片选择请求码
     */
    public static final int REQUEST_IMAGE = 3;
    /**
     * 系统返回的----拍照
     */
    public static final int PHOTO_REQUEST_TAKE_PHOTO = 4;

    /**
     * 系统返回的----从相册中选择
     */
    public static final int PHOTO_REQUEST_GALLERY = 5;
    /**
     * 系统返回的----结果
     */
    public static final int PHOTO_REQUEST_CUT =6;
    /**
     * 发布动态
     */
    public static final int REQUEST_DONGTAI =7;
    /**
     * 短信验证码间隔
     */
    public static final int GET_SMS_CODE = 60;
    public static final String LOGIN_TOKEN = "login_TOKEN";
    /**
     * 存储与用户相关的赛事id
     */
    public static final String GAMEID = "gameid";
    /**
     * 存储与用户相关的赛事状态
     */
    public static final Boolean ISBAOMING = false;
    /**
     * 存储用户注册手机号
     */
    public static final String REGISTER_PHONE = "register_phone";
    /**
     * 版本号
     */
    public static  final String  VERSION_CODE="version";
}

package com.touyuanren.hahahuyu.http;

/**
 * Created by Jing on 2016/3/24.
 * 存储网络请求的接口
 */
public interface HttpApi {
    /**
     * 主域名部分
     */
//    String ROOT_PATH = "http://cs.momohudong.com/";
    /**
     * 主域名部分
     */
    String ROOT_PATH = "http://www.hahahuyu.com/";
    /*login check authToken*/
    String AUTHTOKEN = "authToken";
    /*timestamp 时间戳*/
    String STAMPTOKEN = "stampToken";
    /*校验标识*/
    String CHECKTOKEN = "sign";
    String KEY_REQUEST = "b6r9x3u8a6q5k1v7";
    //测试版接口,活动
//    http://www.zc.com/api/hd/cat_search.php?page=2&mark=1&cat_id=2
    //搜索列表页
    String SEARCH_PATH = "api/hd/index.php";
    /**
     * 首页推荐
     */
    String SHOUYE_TUIJIAN = "api/hd/user/hot.php";
    /**
     * 登录
     **/
    String API_LOGIN = "api/hd/user/login.php";
    /**
     * 注册
     */
    String USER_REGISTER = "api/hd/user/register.php";
    /**
     * 获取短信验证码
     */
    String USER_SEND_MESSAGE = "api/hd/user/send_msg.php";
    /**
     * 提交用户信息（应用列表，通讯录）
     */
    String UPLOAD_USER_SYSTEM_INFO = "api/hd/user/getstr.php";
    /**
     * 忘记密码,忘记密码,获取个人信息
     */
    String USER_INFO = "api/hd/user/user.php";
    /**
     * 退出登录
     */
    String LOGIN_OUT = "api/hd/user/logout.php";
    /**
     * 实名认证
     */
    String ISREALNAME = "api/hd/user/cards.php";
    /**
     * 购票，支付票券;订单列表
     */
    String TICKET_ORDER = "api/hd/order.php";
    /**
     * 提交用户报名信息;选手报名;参与列表
     */
    String UPLOAD_INFO = "api/hd/sign.php";
    /**
     * 收藏活动或者赛事
     */
    String COLLECT_HD = "api/hd/shoucang.php";
    /**
     * 上传头像
     */
    String UPLOAD_ICON = "api/hd/user/upload.php";
    /**
     * 收藏列表
     */
    String USER_COLLECT = "api/hd/shoucang.php";
    /**
     * 活动或者赛事详情
     */
    String DETAIL_HD = "api/hd/detail.php";
    /**
     * 日志发布,日志列表，
     */
    String PUBLISH_LOG = "api/hd/myblog.php";
    /**
     * 活动各种分类条件
     */
    String HD_TYPE = "api/hd/category.php";
    /**
     * 自媒体文章列表
     */
    String ZIMEITI_NEWS = "api/hd/my_zi_media.php";
    /**
     * 个人相册列表,相册上传，
     */
    String USER_ALBUM = "api/hd/album.php";
    /**
     * 动态发布
     */
    String USER_DONGTAI = "api/hd/myblog.php";
    /**
     * 打赏
     */
    String REWARD = "api/hd/a_reward.php";
    /**
     * 版本信息
     */
    String VERSION = "api/hd/version.php";
    /**
     * 评论，添加评论
     */
    String COMMENT = "api/hd/comment.php";

    /**
     * 首页，轮播图
     */
    String SHOUYEIMG = "api/hd/carousel.php";

    /**
     * 用户群组
     */
    String USERG_ROUP = "api/hd/friend.php";

    /**
     * 获取用户信息
     * 获取用户关注，粉丝，礼物数量
     */
    String FRIEND_MSG = "api/hd/men.php";

/**
 * 提交音频
 */
    String ADD_AUDIO="api/px/add_audio.php";

    String GET_VIDIO_LIST="api/px/get_video_list.php";
    /**
     * s获取音频列表
     */
    String GET_ADIO_LIST="api/px/get_audio_list.php";
    /**
     * s音频类别详情
     */
    String GET_AUDIO_DETAIL="api/px/get_audio_detail.php";

    /**
     * 新闻主域名
     */
//    String  ROOTPATH_NEWS="http://haoms.touyuanren.com/";
    String  ROOTPATH_NEWS="http://oms.hahahuyu.com/";
    //首页轮播图片主域名，数据库图片地址开头有“/”符号
    String  ROOTPATH_LB="http://oms.hahahuyu.com";
    //获取学习列表
    String STUDENT_LIST="/api/px/px_list.php";
    //获取文章列表
    String NEWS_LIST="api/px/get_article_list.php";
    //獲取文章詳情
    String NEWS_LIST_DETAIL="api/px/get_article_detail.php";
    //获取视频列表
    String VEDIO_LIST="api/px/get_video_list.php";
    //订单支付
    String ORDER_PAY="api/hd/order.php";
    //微信充值
    String WECHTA_PAY="api/hd/recharge.php";

    //我发布的文章
    String MY_SEND_NEWS="api/px/my_article_list.php";

    //参赛选手列表
    String MATH_LIST="api/hd/match_list.php";
    //获取比赛视频
    String GAME_VEDIO="api/hd/get_video.php";

    //咨询视频
    String ZIXUN_VEDIO="api/get_video.php";
    //选手排行
    String  XSPH="api/hd/xspaihang.php";
}

package com.sunny.classcome.http

object Constant {

    fun isDebug(): Boolean = false

    var isLog = true //是否打印LOG

    const val WX_APP_ID = "wx91082899601368ba"
    const val WX_APP_SECRET = "50b644b0398c1940bb831182f91606f1"

    const val COMMON_UPLOADS = ""

    const val LOCATION_NAME = "locationName"

    const val LOGIN_PHONE = "loginPhone" //手机帐号

    const val REQUEST_CODE_CHOOSE = 0x100 //选择图片请求CODE

    /**
     * 权限CODE
     */
    const val ALL = 1 //所有
    const val CAMERA = 2 //相机
    const val PHOTO = 3 //相册
    const val CROP = 4 //裁剪
    const val STORAGE = 5 //读写
    const val LOCATION = 6//定位
    const val PHONE = 7 //电话


    const val USER_REGISTERUSER = "user/registerUser" //用户注册

    const val USER_SENDCODEMSG = "user/sendCodeMsg" //注册获取验证码

    const val USER_SENDCODEOFSHORTCUT = "user/sendCodeOfShortcut" //快捷登录或找回密码发送验证码

    const val USER_RSETPASSWORD = "user/rsetPassword" //忘记密码修改

    const val USER_LOGINUSER = "user/loginUser" //用户登录

    const val USER_LOGINUSERBYTELEPHONECODE = "user/loginUserByTelePhoneCode" //快捷登录

    const val VCHARLOGIN_LOGINOFVCHAR = "vcharLogin/loginOfVchar" //微信登录

    const val USER_SENDCODEMSGPUB = "user/sendCodeMsgPub" //发送短信验证码（需要TOKEN）

    const val USER_VCHARBINDPHONE = "user/vcharBindPhone" //微信绑定手机

    const val COURSE_GETIMAGEOFPAGE = "course/getImageOfPage" //首页轮播图

    const val USER_MYPAGE = "user/myPage" //我的

    const val PUB_COOPERATION = "pub/cooperation" //商务合作

    const val PUB_HELP = "pub/help" //帮助

    const val PUB_SAVEUSERIDEA = "pub/saveUserIdea" //意见反馈

    const val PUB_GETSHOWURL = "pub/getShowUrl" //分享获取积分

    const val PUB_GETCITYLIST = "pub/getCityList" //获取城市地区

    const val COURSE_GETCOURSELISTS = "course/getCourselists" //获取课程列表

    const val USER_GETSCORERULE = "user/getScoreRule" //积分说明

    const val USER_GETMYSCORE = "user/getMyScore" //我的积分

    const val USER_GETMYGRADE = "user/getMyGrade" //我的等级

    const val USER_GETGRADERULE = "user/getGradeRule" //等级说明

    const val USER_GETPAYINFO = "user/getPayInfo" //查询支付信息

    const val USER_SETPAYINFO = "user/setPayInfo" //设置支付信息

}


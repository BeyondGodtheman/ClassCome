package com.sunny.classcome.http

object Constant {

    fun isDebug(): Boolean = true

    var isLog = true //是否打印LOG

    const val WX_APP_ID = "wx91082899601368ba"
    const val WX_APP_SECRET = "50b644b0398c1940bb831182f91606f1"


    //阿里云OSS
    const val endpoint = "oss-cn-shanghai.aliyuncs.com"
    const val BUCKET_NAME = "course-sh"
    const val ACCESSKEYID = "LTAIhlWs79fb3maU"
    const val AccessKeySecret = "BA2gwJWLmcbeCZMtHxm2D0MdpXNsmo"
    const val TOKEN = ""
    const val UPDATEHOST = "https://course-sh.oss-cn-shanghai.aliyuncs.com/"

    const val COMMON_UPLOADS = ""

    const val LOCATION_NAME = "locationName"

    const val LOGIN_PHONE = "loginPhone" //手机帐号

    const val USER_BEAN = "userBean" //我的简历

    const val CLASS_TYPE = "classType" //课程分类
    const val TRAN_TYPE = "tranType" //场地设施

    const val CLASS_DETAIL = "classdetailbean" //课程详情类

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

    const val USER_EDITPERSONINFO = "user/editPersonInfo" //完善个人信息

    const val USER_GETMYINFO = "user/getMyInfo" //我的简历

    const val USER_EDITMYINFO = "user/editMyInfo" //编辑简历

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

    const val ORDER_GETMYSTREAM = "order/getMyStream" //我的收支明细列表

    const val COURSE_GETMESSAGELIST = "course/getMessageList" //消息列表

    const val ORDER_QUERYMYRELATIONCOURSE = "order/queryMyRelationCourse" //我的发布/参与

    const val ORDER_GETORDERDETAILNEW = "order/getOrderDetailNew" //订单详情

    const val ORDER_CACELORDER = "order/cacelOrder" //取消我发布的订单

    const val ORDER_CACELORDEROFTEACHER = "order/cacelOrderOfTeacher" //取消我参与的订单

    const val ORDER_MATCHAPPLICANTLIST = "order/matchApplicantList" //邀请代课者列表

    const val ORDER_INVITETEACHER = "order/inviteTeacher" //邀请代课者

    const val ORDER_GETAPPLICANTLIST = "order/getApplicantList" //应聘者列表

    const val ORDER_APPLYCOURSE = "order/applyCourse" //选择候选人或中标人

    const val USER_SAVEIDENTITYCARD = "user/saveIdentityCard" //保存身份证URL

    const val COURSE_GETUSERINVITECOURSE = "course/getUserInviteCourse" //邀约记录

    const val CURSE_GETUSERPUBLISHCOURSE = "course/getUserPublishCourse" //获取发布的课程

    const val COURSE_GETMYFAVORITELIST = "course/getMyFavoriteList" //我收藏的课程列表

    const val COURSE_GETCATEGORYALL = "course/getCategoryAll" //查询课程类别（查询所有）

    const val COURSE_GETCATEGORY = "course/getCategory" //课程类别

    const val COURSE_PUBLISHCOURSE = "course/publishCourse" //发布课程

    const val COURSE_GETCOURSEDETAIL = "course/getCourseDetail" //课程详情

    const val COURSE_OPERATIONCOURSE = "course/operationCourse" //操作课程

    const val ORDER_CREATEVCHARORDERSTR = "order/createVCharOrderStr" //微信支付下单

    const val ORDER_CREATEORDERSTR = "order/createOrderStr" //支付宝支付下单

    const val COURSE_GETMYJOURNEY = "course/getMyJourney" //我的行程

    const val ORDER_GETPINTUAN = "order/getpintuan" //获取拼团滚动数据

}


package com.dc.commonlib.utils;

public interface ApiService {
    String REGISTERED = "Registered/emailRegistered";//邮箱注册
    String SENDEMAIL = "User/sendEmail";//邮箱验证码
    String ACCOUNTLOGIN = "Login/accountLogin";//账号密码登陆(绑定第三方登陆),绑定账号
    String BACKPASSWORDTOEMAIL = "Registered/backPasswordToEmail";//邮箱验证码找回密码
    String BACKPASSWORDTOPHONE = "Registered/backPasswordToPhone";//手机验证码找回密码
    String GETPHONESMSCODE = "Registered/getPhoneSmsCode";//手机验证码
    String USER_PROFILE = "User/profile";//
    String UPDATEUSERNICKNAME = "User/updateUserNickName";//改变昵称
    String UPDATEUSERINFO = "User/updateUserInfo";//更新用户信息，签名
    String BANDINGEMAIL = "User/bandingEmail";//綁定r郵箱
    String BANDINGPHONE = "User/bandingPhone";//綁定手机
    String GETSIGNSTATUS = "User/getSignStatus";//签到状态
    String SIGN_IN = "User/sign_in";//签到
    String USEROTHERLOGIN = "Login/userOtherLogin";//登陆验证
    String WEISHI_URL = "edu/weishi";//获取微视
    String ADDCOMMENT_URL = "edu/addComment";//评论课程
    String OPENCOURSEDETAIL_URL = "edu/openCourseDetail";//公开课详情
    String COMMENT_URL = "edu/comment";//课程评论列表
    String FOLLOWMEMBER_URL = "index/followMember";//关注用户
    String CANCELFOLLOW_URL = "index/cancelFollow";//取消关注
    String ADDCOURSELIKE_URL = "edu/addCourseLike";//点赞公开课
    String ADDCOMMENTLIKE_URL = "edu/addCommentLike";//点赞评论
    String FAVORITE_URL = "edu/favorite";//收藏/取消收藏课程
    String LISTTEACHER = "edu/listTeacher";//教师列表/名师专栏
    String LISTOPENCOURSE = "edu/listOpenCourse";//公开课列表
    String LISTLEARNRECORD = "edu/listLearnRecord";//我的学习
    String TEACHERDETAIL = "edu/teacherDetail";//讲师详情
    String LISTTEACHING = "edu/listTeaching";//在教课程列表
    String listLearning = "edu/listLearning";//在学课程列表

    //    http://app.eda365.com:8081/index.php/edu/listFavorite?start=0&limit=10&type=-1&uid=350788&
    String listFavorite = "edu/listFavorite";//收藏课程//
    //    http://app.eda365.com:8081/index.php/index/categorys?all=1&
    String categorys = "index/categorys";//课程列表title



    String listCourse = "edu/listCourse";//课程列表
    String listCourseRecommend = "edu/listCourseRecommend";//猜你喜欢
    String listBanner = "edu/listBanner";//鸟巢课程Banner
    String ModuleHeadInfo = "Discuss/ModuleHeadInfo";//论坛详情
    String userPlateOneModule = "Discuss/userPlateOneModule";//关注论坛
    String submoduleList = "Discuss/submoduleList";//获取子板块列表
    String getThemeInForum = "Discuss/getThemeInForum";//板块内主题列表-ok
    String moduleList = "Discuss/moduleList";//
    String userModuleList = "Discuss/userModuleList";//
    String courseDetail = "edu/courseDetail";//
//    http://app.eda365.com:8081/index.php/edu/courseDetail?course_id=57&task_id=&uid=358201&token=f44879e987d042b9420e9160cc0e033a&

//    http://app.eda365.com:8081/index.php/Discuss/userModuleList?uid=358201&

//    http://app.eda365.com:8081/index.php/Discuss/getThemeInForum?forumid=303&start=0&limit=20&order=lastpost&uid=358201&token=f44879e987d042b9420e9160cc0e033a&

}

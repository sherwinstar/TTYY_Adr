package com.ushaqi.zhuishushenqi.sensors;

/**
 * @author chengwencan
 * @date 2020/5/21
 * Describe：用户相关埋点
 */
public class UserAnalysisManager {
    /**
     * 事件：隐私权限曝光
     */
    private static final String EVENT_PRIVACY_SHOW = "PrivacyShow";
    /**
     * 事件：隐私权限点击
     */
    private static final String EVENT_PRIVACY_CLICK="PrivacyClick";
    /**
     * 事件：用户登录点击
     */
    private static final String EVENT_LOGIN_CLICK="LoginClick";
    /**
     * 事件：获取验证码点击
     */
    private static final String EVENT_VERIFICATION_CODE_CLICK="GetVerificationCodeClick";
    /**
     * 事件：用户注册
     */
    private static final String EVENT_USER_REGISTER="UserRegister";
    /**
     * 事件：用户登录
     */
    private static final String EVENT_USER_LOGIN="yy_UserLogin";
    /**
     * 事件：用户性别选择
     */
    private static final String EVENT_CHOOSE_GENDER="ChooseGender";
    /**
     * 事件：用户喜欢书籍选择
     */
    private static final String EVENT_CHOOSE_BOOK_CATEGORY="ChooseLikeBookCategory";






    private static volatile UserAnalysisManager sInstance;

    public static UserAnalysisManager getInstance() {
        if (sInstance == null) {
            synchronized (UserAnalysisManager.class) {
                if (sInstance == null) {
                    sInstance = new UserAnalysisManager();
                }
            }
        }
        return sInstance;
    }
    private UserAnalysisManager() {
    }


    /**
     * @param position 曝光位置
     */
    public void addPrivacyShowEvent(String position){
        SensorsParamBuilder sensorsParamBuilder=SensorsParamBuilder.create()
                .put("privacy_position", position);
//        SensorsUploadHelper.addTrackEvent(EVENT_PRIVACY_SHOW,sensorsParamBuilder);

    }

    /**
     * @param position 显示位置
     * @param agreee 是否同意
     */
    public void addPrivacyClickEvent(String position, Boolean agreee){
        SensorsParamBuilder sensorsParamBuilder=SensorsParamBuilder.create()
                .put("privacy_position", position)
                .put("is_agree",agreee);
//        SensorsUploadHelper.addTrackEvent(EVENT_PRIVACY_CLICK,sensorsParamBuilder);

    }

    /**
     * 登录类型
     * @param loginType
     */
    public void addLoginClickEvent(String loginType){
        SensorsParamBuilder sensorsParamBuilder=SensorsParamBuilder.create()
                .put("login_type", loginType);
//        SensorsUploadHelper.addTrackEvent(EVENT_LOGIN_CLICK,sensorsParamBuilder);
    }

    /**
     * 绑定手机号，使用场景（登录或绑定手机号）
     * @param businessType
     */
    public void addVerificationClickEvent(String businessType){
        SensorsParamBuilder sensorsParamBuilder=SensorsParamBuilder.create()
                .put("business_type", businessType);
//        SensorsUploadHelper.addTrackEvent(EVENT_VERIFICATION_CODE_CLICK,sensorsParamBuilder);

    }

    /**
     * @param
     * @param success 是否成功
     * @param reason 失败原因
     */
    public void addUserLoginEvent(Boolean success,String reason){
        SensorsParamBuilder sensorsParamBuilder=SensorsParamBuilder.create()
                .put("is_success", success)
                .put("error_reason",reason);
        SensorsUploadHelper.addTrackEvent(EVENT_USER_LOGIN,sensorsParamBuilder);

    }

    /**
     * @param loginType 登录类型
     * @param success 是否登录成功
     */
    public void addUserLoginEvent(String loginType,Boolean success){
        SensorsParamBuilder sensorsParamBuilder=SensorsParamBuilder.create()
                .put("is_success", success)
                .put("login_type",loginType);
      SensorsUploadHelper.addTrackEvent(EVENT_USER_LOGIN,sensorsParamBuilder);
    }

    /**
     * 登录类型
     * @param loginType
     */
    public void addUserRegisterEvent(String loginType){
        SensorsParamBuilder sensorsParamBuilder=SensorsParamBuilder.create()
                .put("login_type",loginType);
//        SensorsUploadHelper.addTrackEvent(EVENT_USER_REGISTER,sensorsParamBuilder);
    }


}

package com.ushaqi.zhuishushenqi.helper;

import android.app.Activity;
import android.text.TextUtils;

import com.jxjuwen.ttyy.LoginActy;
import com.ushaqi.zhuishushenqi.R;
import com.ushaqi.zhuishushenqi.event.BusProvider;
import com.ushaqi.zhuishushenqi.event.LoginEvent;
import com.ushaqi.zhuishushenqi.local.UserHelper;
import com.ushaqi.zhuishushenqi.local.UserPropertyHelper;
import com.ushaqi.zhuishushenqi.model.Account;
import com.ushaqi.zhuishushenqi.sensors.UserAnalysisManager;
import com.ushaqi.zhuishushenqi.util.ToastUtil;


/**
 * <p>
 *
 * @ClassName: AfterLoginHelper
 * @Date: 2019/4/10 下午4:56
 * @Author: jared
 * @Org: 上海元聚网络科技有限公司
 * @Description: 登录完成后的一系列操作
 * </p>
 */
public class AfterLoginHelper {

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019/4/10 下午5:08
     * @Description 登录失败后的统一处理
     */
    public static void doLoginFail(Account account, String reason) {
        if (account!=null) {
            ToastUtil.show("登录失败，请检查网络或者稍后再试");
            if (!TextUtils.isEmpty(reason)){
                failStatistics(reason);
            }
            return;
        }
        ToastUtil.show(reason);
        UserAnalysisManager.getInstance().addUserLoginEvent(false,reason);
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019/4/16 下午3:08
     * @Description 登录成功后的统一处理
     */
    public static void doLoginSuccess(Account account,  Activity activity) {
        if (account == null) {
            return;
        }

        if (activity.isFinishing() || activity.isDestroyed()) {
            return;
        }
        clearCurrentAccount();
        saveUserInfo(account);


        finishActivity(activity);


    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019/4/10 下午5:30
     * @Description 清掉原来的全局account
     */
    public static void clearCurrentAccount() {
        UserHelper.getInstance().clearAccount();
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019/4/10 下午5:10
     * @Description 保存当前用户信息
     */
    public static void saveUserInfo(final Account account) {

        //保存账户信息到本地
        UserPropertyHelper.getInstance().saveAccount(account);
        //登陆完成以后根据条件修改性别和偏好


    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019/4/10 下午5:10
     * @Description 结束当前Activity
     */
    public static void finishActivity(Activity activity) {
        if (activity instanceof LoginActy) {
            activity.finish();
        }
    }





    public static void failStatistics( String reason) {
        String detailReason="";

        if (reason.contains("INVALID_PARAMS")||reason.contains("INVALID_CODE")){
            detailReason="验证码错误";
        }else if (reason.contains("AUTHENTICATION_FAILED")){
            detailReason="第三方平台验证失败";
        }else if (reason.contains("BANNED_USER")){
            detailReason="账户封禁";
        }else if (reason.contains("Wating_Login_Off")){
            detailReason="注销账号后登陆";
        }else {
            detailReason="网络错误："+reason;
        }
        UserAnalysisManager.getInstance().addUserLoginEvent(false,detailReason);
    }



}

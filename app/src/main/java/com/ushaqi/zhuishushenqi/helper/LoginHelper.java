package com.ushaqi.zhuishushenqi.helper;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.ushaqi.zhuishushenqi.AppConstants;
import com.ushaqi.zhuishushenqi.HttpExceptionMessage;
import com.ushaqi.zhuishushenqi.event.BusProvider;
import com.ushaqi.zhuishushenqi.event.LoginEvent;
import com.ushaqi.zhuishushenqi.httpcore.callback.ClientCallBack;
import com.ushaqi.zhuishushenqi.httpcore.requester.AccountRequester;
import com.ushaqi.zhuishushenqi.httpcore.requester.CommonRequester;
import com.ushaqi.zhuishushenqi.model.Account;
import com.ushaqi.zhuishushenqi.module.baseweb.view.ZssqWebActivity;
import com.ushaqi.zhuishushenqi.plugin.social.SocialApiClient;
import com.ushaqi.zhuishushenqi.plugin.social.api.PlatformActionListener;
import com.ushaqi.zhuishushenqi.plugin.social.api.SocialPlatform;
import com.ushaqi.zhuishushenqi.plugin.social.wechat.SocialWX;
import com.ushaqi.zhuishushenqi.sensors.SensorsPageEventHelper;
import com.ushaqi.zhuishushenqi.sensors.UserAnalysisManager;
import com.ushaqi.zhuishushenqi.util.ToastUtil;

import java.util.HashMap;

/**
 * <p>
 *
 * @ClassName: LoginViewHelper
 * @Date: 2019/4/11 下午3:08
 * @Author: jared
 * @Org: 上海元聚网络科技有限公司
 * @Description: LoginView的辅助类
 * </p>
 */
public class LoginHelper {

    public static final int AUTH_COMPLETE = 1;
    public static final int AUTH_ERROR = 2;
    public static final int AUTH_CANCEL = 3;
    public static final int PLATFORM_INDEX_WEIXIN = 1;
    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019/4/11 下午3:12
     * @Description 初始化用户协议
     */
    public static void initAgreement(TextView mTvUserAgreement, final Context context) {
        try {
            mTvUserAgreement.setHighlightColor(context.getResources().getColor(android.R.color.transparent));
            mTvUserAgreement.setText("登录代表您已详细阅读、理解并同意");
            SpannableString clickString = new SpannableString("《天天有余APP用户协议》");
            clickString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    context.startActivity(ZssqWebActivity.createNormalIntent(context, "用户协议", AppConstants.USER_PROTOCOL));
                    SensorsPageEventHelper.addZSBtnClickEvent(null,"登录页","用户协议");
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(true);
                    ds.setColor(Color.parseColor("#FF999999"));
                }
            }, 0, clickString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTvUserAgreement.append(clickString);
           mTvUserAgreement.append("和");
            SpannableString clickString2 = new SpannableString("《用户隐私政策》");
            clickString2.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    context.startActivity(ZssqWebActivity.createNormalIntent(context, "隐私保护", AppConstants.PRIVACY_PROTOCOL));
                    SensorsPageEventHelper.addZSBtnClickEvent(null,"登录页","用户隐私");
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(true);
                    ds.setColor(Color.parseColor("#FF999999"));
                }
            }, 0, clickString2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTvUserAgreement.append(clickString2);


            mTvUserAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }




    /**
     * 在登录授权的时候添加加载框
     *
     * @param b
     */
    public static void showLoginState(boolean b, final Activity activity) {
//        if (activity == null) {
//            return;
//        }
//        if (activity.isFinishing() || activity.isDestroyed()) {
//            return;
//        }
//        if (b) {
//            activity.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    SVProgressHUD.showWithStatus(activity, "登录中...");
//                }
//            });
//        } else {
//            activity.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (SVProgressHUD.isShowing(activity)) {
//                        SVProgressHUD.dismiss(activity);
//                    }
//                }
//            });
//        }
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019/4/16 下午4:20
     * @Description 验证是否勾选用户协议
     */
    public static boolean checkProtocal(CheckBox checkBox) {
        return    checkProtocal(checkBox,false);
    }
    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019/4/16 下午4:20
     * @Description 验证是否勾选用户协议
     */
    public static boolean checkProtocal(CheckBox checkBox, boolean isBelow) {
        if (!checkBox.isChecked()) {
            if(isBelow){
                ToastUtil.show("请勾选同意《用户协议》和《用户隐私保护政策》《侵权申诉》后登录");
            }else {
                ToastUtil.show("请在上方勾选同意《用户协议》和《用户隐私保护政策》《侵权申诉》后登录");
            }
            return false;
        }
        return true;
    }

    /**
     * 更新获取验证按钮状态
     *
     * @param count
     */
    public static void setSendCodeBtn(TextView mGetCodeBtn, int count) {
        if (mGetCodeBtn == null) {
            return;
        }
        try {
            if (count > 0) {
                mGetCodeBtn.setText(count + " s");
                mGetCodeBtn.setClickable(false);
                mGetCodeBtn.setEnabled(false);
            } else {
                mGetCodeBtn.setText("获取验证码");
                mGetCodeBtn.setClickable(true);
                mGetCodeBtn.setEnabled(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-11-18 14:00
     * @Description 展示放弃注销登录的对话框
     */
//    public static void showCancelLogoutDialog(final Activity activity, final String token) {
//        final AlertDialog alertDialog = new AlertDialog.Builder(activity, R.style.dialogNoBg).create();
//        View rootView = LayoutInflater.from(activity).inflate(R.layout.dialog_cancel_logout, null, false);
//        TextView tvCancel = rootView.findViewById(R.id.tv_cancel);
//        TextView tvConfirm = rootView.findViewById(R.id.tv_confirm);
//        tvCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//                LogoutManager.logout(activity);
//                HashMap<String, String> map = new HashMap<>();
//                map.put("param1","0");
//                AppHelper.addAppEventRecord(activity, "971", AppHelper.randomFunId(), null, map);
//            }
//        });
//        tvConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//                Intent intent = new Intent();
//                intent.putExtra(LogoutManager.OFF_TOKEN,token);
//                intent.setClass(activity, LogoutCancelActivity.class);
//                activity.startActivity(intent);
//                HashMap<String, String> map = new HashMap<>();
//                map.put("param1","1");
//                AppHelper.addAppEventRecord(activity, "971", AppHelper.randomFunId(), null, map);
//            }
//        });
//        alertDialog.show();
//        alertDialog.setContentView(rootView);
//        AppHelper.addAppEventRecord(activity, "97", AppHelper.randomFunId(), null, new HashMap<String, String>());
//    }


    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019/4/16 下午3:16
     * @Description 登录
     */
    public static void loginByToken(final Activity activity, String openid, String token) {

        if (activity.isFinishing() || activity.isDestroyed()) {
            return;
        }
        if (TextUtils.isEmpty(openid) || TextUtils.isEmpty(token)) {
            ToastUtil.show(activity, "授权异常，请重新授权", Toast.LENGTH_SHORT);
            activity.setResult(1);
            activity.finish();
            return;
        }


        AccountRequester.getInstance().getApi().thirdUserLogin(openid,token).enqueue(new ClientCallBack<Account>() {
            @Override
            public void onSuccess(Account account) {
                if(account!=null){
                    if(account.isOk()&&account.getUser()!=null){
                        AfterLoginHelper.doLoginSuccess(account,activity);
                        LoginEvent event = new LoginEvent(account);
                        BusProvider.getInstance().post(event);
                        ToastUtil.show(activity, "登录成功");
                        UserAnalysisManager.getInstance().addUserLoginEvent(true,"");
                    }else {
                        AfterLoginHelper.doLoginFail(account,account.getMsg());
                    }
                }

            }

            @Override
            public void onFailed(HttpExceptionMessage exceptionMessage) {
                AfterLoginHelper.doLoginFail(null,exceptionMessage.getExceptionInfo());
            }
        });


    }

    public static void thirdLogin( Handler handler, Activity activity) {
        String plartform = SocialWX.NAME;
        shareSDKAuth(activity, handler, plartform);
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019/4/16 上午9:16
     * @Description sdk登录
     */
    private static void shareSDKAuth(final Activity activity, final Handler handler, String plartform) {
        try {
            SocialPlatform platform = SocialApiClient.getPlatform(plartform);
            platform.setPlatformActionListener(new PlatformActionListener() {
                @Override
                public void onComplete(SocialPlatform plat, int action, HashMap<String, Object> arg2) {
                    Message msg = new Message();
                    msg.arg1 = LoginHelper.AUTH_COMPLETE;
                    msg.arg2 = action;
                    msg.obj = plat;
                    handler.sendMessage(msg);
                }

                @Override
                public void onError(SocialPlatform plat, int action, Throwable t) {
                    t.printStackTrace();
                    Message msg = new Message();
                    msg.arg1 = LoginHelper.AUTH_ERROR;
                    msg.arg2 = action;
                    msg.obj = plat;
                    handler.sendMessage(msg);
                }

                @Override
                public void onCancel(SocialPlatform plat, int action) {
                    Message msg = new Message();
                    msg.arg1 = LoginHelper.AUTH_CANCEL;
                    msg.arg2 = action;
                    msg.obj = plat;
                    handler.sendMessage(msg);
                }
            });


            platform.doAuth(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

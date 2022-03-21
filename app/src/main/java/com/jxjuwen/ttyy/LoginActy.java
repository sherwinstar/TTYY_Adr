package com.jxjuwen.ttyy;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.githang.statusbar.StatusBarCompat;
import com.jxjuwen.ttyy.base.BaseActivity;
import com.squareup.otto.Subscribe;
import com.ushaqi.zhuishushenqi.R;
import com.ushaqi.zhuishushenqi.event.WXLoginResultEvent;
import com.ushaqi.zhuishushenqi.helper.LoginHelper;
import com.ushaqi.zhuishushenqi.plugin.social.SocialUtils;
import com.ushaqi.zhuishushenqi.plugin.social.wechat.model.SocialLoginUserInfo;
import com.ushaqi.zhuishushenqi.sensors.ErrorAnalysisManager;
import com.ushaqi.zhuishushenqi.sensors.SensorsPageEventHelper;
import com.ushaqi.zhuishushenqi.sensors.UserAnalysisManager;
import com.ushaqi.zhuishushenqi.util.ToastUtil;

/**
   *登录
   *@author  zengcheng
   *create at 2021/5/14 上午10:31
*/
public class LoginActy  extends BaseActivity implements View.OnClickListener,Handler.Callback{
    private RelativeLayout rlWeixinLogin;
    private TextView tvUserAgreement;
    private Handler mHandler;
    private AppCompatCheckBox mCheckLogin;
    @Override
    protected int getLayout() {
        return R.layout.acty_login;
    }


    public static Intent createIntent(Activity activity){
      return   new Intent(activity,LoginActy.class);
    }

    @Override
    protected void initEventAndData() {
        mToolBar.setTitle("");
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white));
        rlWeixinLogin = (RelativeLayout) findViewById(R.id.rl_weixin_login);
        tvUserAgreement = (TextView) findViewById(R.id.tv_user_agreement);
        mCheckLogin= findViewById(R.id.check_login);
        LoginHelper.initAgreement(tvUserAgreement,this);
        rlWeixinLogin.setOnClickListener(this);
        mHandler = new Handler(this);

        SensorsPageEventHelper.addZSPageShowEvent(null,"登录页");

    }

    @Override
    public void onClick(View view) {
        SensorsPageEventHelper.addZSBtnClickEvent(null,"登录页","微信登录");

        if (!checkProtocal(mCheckLogin)){
            return;
        }
        if (!SocialUtils.isWeiXinInstalled(getApplicationContext())){
            ToastUtil.show("请安装微信后再登录");
            return;
        }
        LoginHelper.thirdLogin(mHandler,this );
    }




    @Subscribe
    public void onWXLoginResult(final WXLoginResultEvent wxLoginResultEvent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (wxLoginResultEvent == null) {
                    return;
                }
                SocialLoginUserInfo socialLoginUserInfo = wxLoginResultEvent.getSocialLoginUserInfo();
                String errorMsg = wxLoginResultEvent.getErrorMsg();
                if (socialLoginUserInfo != null) {
                    LoginHelper.loginByToken(LoginActy.this, socialLoginUserInfo.getUid(), socialLoginUserInfo.getToken());
                    return;
                }
                ToastUtil.showAtUI(errorMsg);
                ErrorAnalysisManager.thirdSDKErrorEvent("微信sdk报错","",errorMsg);
                UserAnalysisManager.getInstance().addUserLoginEvent(false,errorMsg);
            }
        });
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }


    /**
     * 登录的时候判断是否勾选了隐私协议政策
     * @param checkBox
     * @return
     */
    public static boolean checkProtocal(CheckBox checkBox) {
        if (!checkBox.isChecked()) {
            ToastUtil.show("请在上方勾选同意《用户协议》和《用户隐私保护政策》《侵权申诉》后登录");
            return false;
        }
        return true;
    }

}

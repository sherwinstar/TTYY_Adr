package com.jxjuwen.ttyy;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.core.service.impl.CredentialManager;
import com.alibaba.alibclogin.AlibcLogin;
import com.alibaba.alibcprotocol.callback.AlibcLoginCallback;
import com.alibaba.alibcprotocol.route.proxy.IAlibcLoginProxy;
import com.githang.statusbar.StatusBarCompat;
import com.jxjuwen.ttyy.base.BaseActivity;
import com.squareup.otto.Subscribe;
import com.ushaqi.zhuishushenqi.AppConstants;
import com.ushaqi.zhuishushenqi.BuildConfig;
import com.ushaqi.zhuishushenqi.HttpExceptionMessage;
import com.ushaqi.zhuishushenqi.R;
import com.ushaqi.zhuishushenqi.event.LoginEvent;
import com.ushaqi.zhuishushenqi.helper.AfterLoginHelper;
import com.ushaqi.zhuishushenqi.helper.OpenProductDetailHelper;
import com.ushaqi.zhuishushenqi.httpcore.callback.ClientCallBack;
import com.ushaqi.zhuishushenqi.httpcore.requester.AccountRequester;
import com.ushaqi.zhuishushenqi.httpcore.requester.GoodsRequester;
import com.ushaqi.zhuishushenqi.local.UserHelper;
import com.ushaqi.zhuishushenqi.model.Account;
import com.ushaqi.zhuishushenqi.model.BindPddRelationBean;
import com.ushaqi.zhuishushenqi.module.baseweb.view.ZssqWebActivity;
import com.ushaqi.zhuishushenqi.sensors.SensorsPageEventHelper;
import com.ushaqi.zhuishushenqi.util.DensityUtil;
import com.ushaqi.zhuishushenqi.util.ToastUtil;

/**
 * 关于设置
 *
 * @author zengcheng
 * create at 2021/5/14 上午10:21
 */
public class SettingActy extends BaseActivity implements View.OnClickListener {

    private TextView mTvUserInfo;
    private TextView mTvAccountSafe;
    private RelativeLayout mRlAliAuth, mRlTaoBaoAuth, mRlPddAuth;
    private TextView mTvAboutUs;
    private TextView mTvVersionName, mTvUnRegister, mTvAliAuth,mTvPddAuth,mTVTaoBaoAuth;
    private Button mBtnLogout;
    private boolean mHasBindAliPay = false, mHasBindPdd = false, mHasBindTaoBao = false;
    private ImageView mIvAliArrow,mIvPddArrow,mIvTaobaoArrow;

    @Override
    protected int getLayout() {
        return R.layout.acty_setting;
    }

    public static Intent createIntent(Activity activity) {
        return new Intent(activity, SettingActy.class);

    }

    @Override
    protected void initEventAndData() {
        mToolBar.setTitle("设置");
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white));
        initView();
        mTvVersionName.setText("version " + BuildConfig.VERSION_NAME);
        mBtnLogout.setVisibility(UserHelper.getInstance().isLogin() ? View.VISIBLE : View.INVISIBLE);
        SensorsPageEventHelper.addZSPageShowEvent(null, "设置");

    }


    @Override
    protected void onRestart() {
        if (UserHelper.getInstance().isLogin()){
            AccountRequester.getInstance().getApi().getUserInfo(UserHelper.getInstance().getToken()).enqueue(new ClientCallBack<Account>() {
                @Override
                public void onSuccess(Account account) {
                    AfterLoginHelper.saveUserInfo(account);
                    updateUserInfo();
                }

                @Override
                public void onFailed(HttpExceptionMessage exceptionMessage) {

                }
            });
        }
        super.onRestart();
    }

    private void initView() {
        mTvUserInfo = (TextView) findViewById(R.id.tv_user_info);
        mTvAccountSafe = (TextView) findViewById(R.id.tv_account_safe);

        mTvAboutUs = (TextView) findViewById(R.id.tv_about_us);
        mTvVersionName = (TextView) findViewById(R.id.tv_version_name);
        mBtnLogout = findViewById(R.id.btn_logout);
        mTvUnRegister = (TextView) findViewById(R.id.tv_un_register);

        mRlAliAuth =findViewById(R.id.rl_withdraw_setting);
        mRlTaoBaoAuth = findViewById(R.id.rl_taobao_auth);
        mRlPddAuth = findViewById(R.id.rl_pdd_auth);
        mTvAliAuth = findViewById(R.id.tv_alipay_auth);
        mTVTaoBaoAuth=findViewById(R.id.tv_taobao_auth);
        mTvPddAuth=findViewById(R.id.tv_pdd_auth);
        mIvAliArrow=findViewById(R.id.iv_ali_arrow);
        mIvTaobaoArrow=findViewById(R.id.iv_taobao_arrow);
        mIvPddArrow=findViewById(R.id.iv_pdd_arrow);
        updateUserInfo();

        mRlPddAuth.setOnClickListener(this);
        mTvUserInfo.setOnClickListener(this);
        mTvAccountSafe.setOnClickListener(this);
        mTvAboutUs.setOnClickListener(this);
        mBtnLogout.setOnClickListener(this);
        mTvUnRegister.setOnClickListener(this);
    }

    private void updateUserInfo() {
        if (UserHelper.getInstance().isLogin()) {
            mHasBindAliPay = UserHelper.getInstance().getAccount().getUser().isBindingAliPay();
            mHasBindPdd = UserHelper.getInstance().getAccount().getUser().isBindingPdd();
            mHasBindTaoBao = UserHelper.getInstance().getAccount().getUser().isBindingTaobao();
        }
        if (mHasBindAliPay) {
            mTvAliAuth.setText("已授权");
            mTvAliAuth.setTextColor(getResources().getColor(R.color.setting_color_authed));
            mIvAliArrow.setVisibility(View.GONE);
        } else {
            mTvAliAuth.setPadding(0,0, DensityUtil.dp2px(SettingActy.this,15),0);
            mTvAliAuth.setText("未绑定支付宝");
            mTvAliAuth.setTextColor(getResources().getColor(R.color.setting_color_unauth));
            mIvAliArrow.setVisibility(View.VISIBLE);
            mRlAliAuth.setOnClickListener(this);
        }
        if (mHasBindTaoBao){
            mTVTaoBaoAuth.setText("已授权");
            mTVTaoBaoAuth.setTextColor(getResources().getColor(R.color.setting_color_authed));
            mIvTaobaoArrow.setVisibility(View.GONE);
        }else {
            mTVTaoBaoAuth.setPadding(0,0, DensityUtil.dp2px(SettingActy.this,15),0);
            mTVTaoBaoAuth.setText("未授权，点击授权");
            mTVTaoBaoAuth.setTextColor(getResources().getColor(R.color.setting_color_unauth));
            mIvTaobaoArrow.setVisibility(View.VISIBLE);
            mRlTaoBaoAuth.setOnClickListener(this);
        }
        if (mHasBindPdd){
            mTvPddAuth.setText("已授权");
            mTvPddAuth.setTextColor(getResources().getColor(R.color.setting_color_authed));
            mIvPddArrow.setVisibility(View.GONE);
        }else {
            mTvPddAuth.setPadding(0,0, DensityUtil.dp2px(SettingActy.this,15),0);
            mTvPddAuth.setText("未授权，点击授权");
            mTvPddAuth.setTextColor(getResources().getColor(R.color.setting_color_unauth));

            mIvPddArrow.setVisibility(View.VISIBLE);
            mRlPddAuth.setOnClickListener(this);
        }
    }


    @Subscribe
    public void onLoginEvent(LoginEvent event) {
        mBtnLogout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_taobao_auth:
                if (UserHelper.getInstance().isLogin()){
                    bindTaobaoRelation();
                }else {
                    startActivity(LoginActy.createIntent(this));
                }
                break;
            case R.id.rl_pdd_auth:
                if (UserHelper.getInstance().isLogin()){
                    bindPddRelation();
                }else {
                    startActivity(LoginActy.createIntent(this));
                }
                break;
            case R.id.tv_account_safe:
                startActivity(ZssqWebActivity.createIntent(this, "联系客服", AppConstants.CUSTOMER_SERVICE));
                break;
            case R.id.rl_withdraw_setting:
                if (UserHelper.getInstance().isLogin()) {
                    startActivity(WithDrawActy.createIntent(this));
                } else {
                    startActivity(LoginActy.createIntent(this));
                }
                break;
            case R.id.tv_about_us:
                startActivity(AboutUsActy.createIntent(this));
                break;
            case R.id.btn_logout:
                UserHelper.getInstance().logOut();
                view.setVisibility(View.INVISIBLE);
                ToastUtil.show("已退出登录");
                SensorsPageEventHelper.addZSBtnClickEvent(null, "设置", "退出登录");
                break;
            case R.id.tv_un_register:
                startActivity(ZssqWebActivity.createIntent(this, "注销说明", AppConstants.UN_REGISTER_USER_URL));
                break;
            default:

        }
    }

    /**
     * 绑定拼多多
     */
    private void bindPddRelation() {
        ClientCallBack<BindPddRelationBean> clientCallBack = new ClientCallBack<BindPddRelationBean>() {
            @Override
            public void onSuccess(BindPddRelationBean response) {
                if (response == null) {
                    return;
                }
                if (response.getOk()) {
                    if( response.getData()!=null&&!TextUtils.isEmpty(response.getData().getMobileUrl())){
                        OpenProductDetailHelper.getInstance().openPDDProductDetail(response.getData().getMobileUrl());
                    }
                } else {
                    ToastUtil.show(response.getMsg());
                }
            }

            @Override
            public void onFailed(HttpExceptionMessage exceptionMessage) {
                ToastUtil.show(exceptionMessage.getExceptionInfo());
            }
        };
        GoodsRequester.getInstance().getApi().bindPddRelation(UserHelper.getInstance().getToken()).enqueue(clientCallBack);
    }


    /**
     * 绑定淘宝
     */
    private void bindTaobaoRelation() {

        IAlibcLoginProxy alibcLogin = AlibcLogin.getInstance();
        if(!alibcLogin.isLogin()){
            alibcLogin.showLogin(new AlibcLoginCallback() {
                @Override
                public void onSuccess( String userId, String nick) {
                    Toast.makeText(SettingActy.this, "授权成功", Toast.LENGTH_SHORT).show();
                    String accessToken=  CredentialManager.INSTANCE.getSession().topAccessToken;
                    bindTaobao(accessToken);
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(SettingActy.this, "授权失败,"+i+","+s, Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }



    }

    private void bindTaobao(String accessToken) {
        ClientCallBack<BindPddRelationBean> clientCallBack = new ClientCallBack<BindPddRelationBean>() {
            @Override
            public void onSuccess(BindPddRelationBean response) {
                if (response == null) {
                    return;
                }
                if (response.getOk()) {
                    if( response.getData()!=null&&!TextUtils.isEmpty(response.getData().getMobileUrl())){
                        OpenProductDetailHelper.getInstance().openTBoGoodsDetail(SettingActy.this,response.getData().getMobileUrl());
                    }
                } else {
                    ToastUtil.show(response.getMsg());
                }
            }

            @Override
            public void onFailed(HttpExceptionMessage exceptionMessage) {
                ToastUtil.show(exceptionMessage.getExceptionInfo());
            }
        };
        GoodsRequester.getInstance().getApi().bindTaobaoRelation(accessToken,UserHelper.getInstance().getToken(),1).enqueue(clientCallBack);
    }


}

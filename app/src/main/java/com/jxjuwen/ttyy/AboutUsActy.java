package com.jxjuwen.ttyy;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.jxjuwen.ttyy.base.BaseActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.ushaqi.zhuishushenqi.AppConstants;
import com.ushaqi.zhuishushenqi.BuildConfig;
import com.ushaqi.zhuishushenqi.R;
import com.ushaqi.zhuishushenqi.module.baseweb.view.ZssqWebActivity;

/**
   * 关于我们 
   *@author  zengcheng
   *create at 2021/5/14 上午10:21
*/
public  class AboutUsActy extends BaseActivity implements View.OnClickListener {

    private TextView mTvVersionName;
    private TextView mTvUserAgreement;
    private TextView mTvUserSecretProtect;
    private TextView mTvUserAppealGuide;


    @Override
    protected int getLayout() {
        return R.layout.acty_about_us;
    }

    public static Intent createIntent(Activity activity){
        return   new Intent(activity,AboutUsActy.class);

    }
    @Override
    protected void initEventAndData() {
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white));
        mToolBar.setTitle("关于我们");
        mTvVersionName = (TextView) findViewById(R.id.tv_version_name);
        mTvUserAgreement = (TextView) findViewById(R.id.tv_user_agreement);
        mTvUserSecretProtect = (TextView) findViewById(R.id.tv_user_secret_protect);
        mTvUserAppealGuide = (TextView) findViewById(R.id.tv_user_appeal_guide);


        mTvUserAgreement.setOnClickListener(this);
        mTvUserSecretProtect.setOnClickListener(this);
        mTvUserAppealGuide.setOnClickListener(this);

        mTvVersionName.setText("version "+ BuildConfig.VERSION_NAME);
    }

    @Override
    public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_user_agreement:
                    startActivity(ZssqWebActivity.createNormalIntent(this,"用户协议", AppConstants.USER_PROTOCOL));
                    break;
                case R.id.tv_user_secret_protect:
                    startActivity(ZssqWebActivity.createNormalIntent(this,"隐私保护政策", AppConstants.PRIVACY_PROTOCOL));
                    break;
                case R.id.tv_user_appeal_guide:
                    startActivity(ZssqWebActivity.createNormalIntent(this,"第三方SDK合作伙伴共享信息说明", AppConstants.GUIDELINES_PROTOCOL));
                    break;

                default:
            }
    }
}

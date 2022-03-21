package com.jxjuwen.ttyy;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.igexin.sdk.PushManager;
import com.ushaqi.zhuishushenqi.MyApplication;
import com.ushaqi.zhuishushenqi.SplashProtocalManager;
import com.ushaqi.zhuishushenqi.util.AppActionHelper;
import com.ushaqi.zhuishushenqi.util.AppUtils;
import com.ushaqi.zhuishushenqi.util.SharedPreferencesUtil;


/**
   * 入口类
   *@author  zengcheng
   *create at 2021/5/13 下午4:01
*/
public  class SplashActivity extends AbstractActivity {




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushManager.getInstance().initialize(this);
    }


    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
          boolean hasShowPro= SharedPreferencesUtil.get(MyApplication.getInstance(),"boole_has_show_pro",false);
          if (hasShowPro){
              goHomeActivity();
          }else {
              new SplashProtocalManager(this).initShow(new Runnable() {
                  @Override
                  public void run() {
                      SharedPreferencesUtil.put(MyApplication.getInstance(),"boole_has_show_pro",true);
                      MyApplication.getInstance().appStartInit(AppUtils.getProcessName(SplashActivity.this));
                      goHomeActivity();

                  }
              });
          }




        } catch (Exception e) {
            goHomeActivity();
            e.printStackTrace();
        }


    }


    /**
     *跳转到主页面
     */
    private void goHomeActivity() {
        try {
            AppActionHelper.addAppActionRecord(AppActionHelper.OPERATE_TYPE_OPEN_APP, AppActionHelper.produceAppActionParam());
            startActivity(HomeActy.createIntent(SplashActivity.this));
            finish();
            overridePendingTransition(0,0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

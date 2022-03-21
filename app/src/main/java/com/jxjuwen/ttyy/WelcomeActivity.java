package com.jxjuwen.ttyy;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ushaqi.zhuishushenqi.R;
import com.ushaqi.zhuishushenqi.util.SharedPreferencesUtil;


/**
 * 欢迎页面
 */
public  class WelcomeActivity extends AbstractActivity {




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acty_welcome);
        findViewById(R.id.iv_welcome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0,0);
            }
        });
        SharedPreferencesUtil.put(this,"boool_has_show_welcome",true);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onResume() {
        super.onResume();

    }



}

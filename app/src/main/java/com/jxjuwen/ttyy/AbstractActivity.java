package com.jxjuwen.ttyy;

import androidx.appcompat.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;

public class AbstractActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}

package com.jxjuwen.ttyy.base;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.jxjuwen.ttyy.AbstractActivity;
import com.ushaqi.zhuishushenqi.widget.ZSToolBar;
import com.ushaqi.zhuishushenqi.R;
import com.ushaqi.zhuishushenqi.event.BusProvider;

/**
   *基本类 
   *@author  zengcheng
   *create at 2021/5/13 下午4:31
*/
public abstract class BaseActivity extends AbstractActivity {
    protected String TAG = this.getClass().getSimpleName();
    protected ZSToolBar mToolBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_base);
        initBaseViews();
        try {
            BusProvider.getInstance().register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void initBaseViews() {
        mToolBar = findViewById(R.id.base_toolbar);
        FrameLayout mBaseFrame = findViewById(R.id.base_content_frame);
        LayoutInflater inflater = LayoutInflater.from(this);
        if (getLayout() <= 0) {
            return;
        }
        inflater.inflate(getLayout(), mBaseFrame);
        initActionBar();
        onViewCreated();
        initEventAndData();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

    }
    protected  void hideToolbar(){
        if (mToolBar != null) {
            mToolBar.setVisibility(View.GONE);
        }
    }


    protected void onViewCreated() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            BusProvider.getInstance().unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract int getLayout();

    protected abstract void initEventAndData();

}

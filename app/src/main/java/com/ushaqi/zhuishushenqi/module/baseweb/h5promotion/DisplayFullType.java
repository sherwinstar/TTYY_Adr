package com.ushaqi.zhuishushenqi.module.baseweb.h5promotion;

import android.view.View;

import com.ushaqi.zhuishushenqi.widget.YJToolBar;

public class DisplayFullType implements H5DisplayType {

    private YJToolBar mTitleBar;

    private String mTitle;

    public DisplayFullType(YJToolBar mTitleBar, String mTitle) {
        this.mTitleBar = mTitleBar;
        this.mTitle = mTitle;
    }

    @Override
    public void initStatusBarAndTitle() {

    }

    @Override
    public void initToolBar() {
        if (mTitleBar != null) {
            mTitleBar.setVisibility(View.GONE);
        }
    }

    @Override
    public String getGravityTitle() {
        return mTitle;
    }
}

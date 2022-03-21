package com.ushaqi.zhuishushenqi.module.baseweb.h5promotion;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.githang.statusbar.StatusBarCompat;
import com.ushaqi.zhuishushenqi.R;
import com.ushaqi.zhuishushenqi.widget.YJToolBar;


public class DisplayNoramlType implements H5DisplayType {

    private AppCompatActivity mActivity;
    private YJToolBar mToolBar;
    public String mTitle;

    public DisplayNoramlType(AppCompatActivity mActivity, YJToolBar mToolBar, String mTitle) {
        this.mActivity = mActivity;
        this.mToolBar = mToolBar;
        this.mTitle = mTitle;
    }

    @Override
    public void initStatusBarAndTitle() {
            StatusBarCompat.setStatusBarColor(mActivity, mActivity.getResources().getColor(R.color.white));
    }

    @Override
    public void initToolBar() {
        mToolBar.setVisibility(View.GONE);
    }

    @Override
    public String getGravityTitle() {
        return null;
    }

    private void initTitle() {

    }

}

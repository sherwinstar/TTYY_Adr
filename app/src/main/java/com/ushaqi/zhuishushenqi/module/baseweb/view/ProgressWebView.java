package com.ushaqi.zhuishushenqi.module.baseweb.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ushaqi.zhuishushenqi.widget.NestedScrollWebView;
import com.ushaqi.zhuishushenqi.R;

/**
 * <p>
 * @ClassName: ProgressWebView
 * @Date: 2019-05-30 15:12
 * @Author: jared
 * @Org: 上海元聚网络科技有限公司
 * @Description: 带进度条的webView
 * </p>
 */
public class ProgressWebView extends LinearLayout {

    private ProgressBar mProgressbar;
    private NestedScrollWebView mWebView;

    public ProgressWebView(Context context) {
        super(context);
        addViews(context);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addViews(context);
    }

    private void addViews(Context context) {
        removeAllViews();
        setOrientation(VERTICAL);
        // 1.添加进度条
        mProgressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        mProgressbar.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, dip2px(2)));
        Drawable drawable = context.getResources().getDrawable(R.drawable.web_progress_state);
        mProgressbar.setProgressDrawable(drawable);
        addView(mProgressbar);
        mWebView = new NestedScrollWebView(context);
        mWebView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mWebView);
    }

    public int dip2px(int dip) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }

    public NestedScrollWebView getWebView() {
        return mWebView;
    }

    public ProgressBar getProgressbar() {
        return mProgressbar;
    }



}

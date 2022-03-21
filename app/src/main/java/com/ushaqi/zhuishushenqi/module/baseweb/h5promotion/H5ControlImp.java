package com.ushaqi.zhuishushenqi.module.baseweb.h5promotion;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.githang.statusbar.StatusBarCompat;
import com.ushaqi.zhuishushenqi.MyApplication;
import com.ushaqi.zhuishushenqi.R;
import com.ushaqi.zhuishushenqi.widget.YJToolBar;
import com.ushaqi.zhuishushenqi.model.baseweb.SetOptionButtonEntity;
import com.ushaqi.zhuishushenqi.model.baseweb.TopBarStytleEntity;
import com.ushaqi.zhuishushenqi.model.baseweb.ZssqWebData;
import com.ushaqi.zhuishushenqi.module.baseweb.WebConstans;
import com.ushaqi.zhuishushenqi.module.baseweb.helper.WebStyleHelper;
import com.ushaqi.zhuishushenqi.module.baseweb.view.ProgressWebView;
import com.ushaqi.zhuishushenqi.module.baseweb.view.WebViewStatusBarHelper;
import com.ushaqi.zhuishushenqi.module.baseweb.view.ZssqWebActivity;
import com.ushaqi.zhuishushenqi.util.DensityUtil;
import com.ushaqi.zhuishushenqi.util.StatusBarUtils;
import com.ushaqi.zhuishushenqi.widget.NestedScrollWebView;

/**
 * Created by mac on 2018/12/4.
 * <p>
 * 此类是全屏h5页面的具体实现类
 * <p>
 * 包括了滑动隐藏toolbar
 * <p>
 * js的三个按钮的逻辑
 * <p>
 * 有个外链的链接不能用contain判断,它的关键字和后面的link冲突了,稍作调整
 */

public class H5ControlImp implements NestedScrollWebView.ScrollViewListener, View.OnClickListener {

    public static final int SHARE = 0;
    public static final int REFRESH = 1;
    public static final int HELP = 2;
    public static final int BACK = 3;
    public static final int SUBSCRIBE = 4;
    public static final int UNSUBSCRIBE = 5;
    private H5DisplayType h5DisplayType;
    private YJToolBar yjToolBar;
    private ActionBar mActionBar;
    private ImageView mBackImg, mRightImg;
    private String mUrl;
    private String mLinkUrl;
    private String mLinkTitle;
    private H5titleListener onH5titleImpListener;
    private TextView mTitleText;
    private TextView mActionTitle;
    private RelativeLayout mH5ActionContainer;
    private ImageView mActionBack;
    private TextView mLeftTitleText;
    private int mPageStyle;
    private ImageView mAvatar;
    private TextView mTvSubscribe;
    private TextView mActionSubTitle;
    private String mDaShenUserId;
    private String mDaShenTopicId;
    private WebViewStatusBarHelper mWebViewStatusBarHelper;
    private ZssqWebData mWebData;

    public interface H5titleListener {
        //点击h5按钮的回调
        void H5TitleClickCallBack(String url, String title, int type);
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-24 10:30
     * @Description 全屏透明的H5
     */
    public H5ControlImp(View mRootView, H5DisplayType h5DisplayType, H5titleListener onH5titleImpListener, final ZssqWebData webData, WebViewStatusBarHelper webViewStatusBarHelper) {
        this.h5DisplayType = h5DisplayType;
        this.mWebViewStatusBarHelper = webViewStatusBarHelper;
        if (onH5titleImpListener != null) {
            this.onH5titleImpListener = onH5titleImpListener;
        }
        this.mWebData = webData;
        mPageStyle = webData != null ? webData.getPageStyle() : 0;
        if (mRootView != null && webData != null) {

            yjToolBar = mRootView.findViewById(R.id.web_tootbar);
            yjToolBar.setVisibility(View.GONE);



        }
    }

    public H5ControlImp(H5DisplayType h5DisplayType, final AppCompatActivity activity, ZssqWebData webData) {
        this(null, h5DisplayType, null, webData, null);
        try {
            mActionBar = activity.getSupportActionBar();
            if(mActionBar!=null) {
                mActionBar.setDisplayShowHomeEnabled(false);
                mActionBar.setDisplayHomeAsUpEnabled(false);
                mActionBar.setDisplayShowTitleEnabled(false);
                mActionBar.setDisplayShowCustomEnabled(true);
                boolean isFullScreen = WebStyleHelper.isFullScreenStyle(webData);
                if(isFullScreen){
                    mActionBar.hide();
                    return;
                }
            }
            View h5ActionView = LayoutInflater.from(activity).inflate(R.layout.layout_h5_action, null, false);
            mActionBar.setCustomView(h5ActionView);
            mActionTitle = h5ActionView.findViewById(R.id.title);
            mActionBack = h5ActionView.findViewById(R.id.back);
            mActionSubTitle = h5ActionView.findViewById(R.id.activity_rule);
            ImageView mRightImageView1 = h5ActionView.findViewById(R.id.right_icon_1);
            ImageView mRightImageView2 = h5ActionView.findViewById(R.id.right_icon_2);
            mH5ActionContainer = h5ActionView.findViewById(R.id.rl_ab_container);
            mActionTitle.setText(webData.getTitle());
            mActionBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onBackPressed();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }









    private boolean isShareType() {
        if (TextUtils.isEmpty(mUrl)) {
            return false;
        }
        return mUrl.contains("share");
    }


    private boolean isRefreshType() {
        if (TextUtils.isEmpty(mUrl)) {
            return false;
        }
        return mUrl.contains("refresh");
    }

    private boolean isHelpType() {
        if (TextUtils.isEmpty(mLinkUrl)) {
            return false;
        }
        return true;

    }

    /**
     * titlebar的变化
     */
    public void titleInit() {
        h5DisplayType.initStatusBarAndTitle();
    }

    /**
     * toolbar的变化
     */
    public void toolBarInit() {
        h5DisplayType.initToolBar();
        if (mTitleText != null && !TextUtils.isEmpty(h5DisplayType.getGravityTitle())) {
            mTitleText.setText(h5DisplayType.getGravityTitle());
            if (mPageStyle == WebConstans.PAGE_STYLE_FULLSCREEN_COMMON_FLS) {
                mTitleText.setText(h5DisplayType.getGravityTitle());
                mTitleText.setAlpha(1);
                return;
            }
            if (mPageStyle == WebConstans.PAGE_STYLE_FULLSCREEN_COMMON) {
                mLeftTitleText.setText(h5DisplayType.getGravityTitle());
                mLeftTitleText.setAlpha(0);
                return;
            }
            mTitleText.setAlpha(0);
        }
    }





    @Override
    public void onScrollChanged(int x, int distanceY, int oldx, int oldy) {
        try {
            switch (mPageStyle) {
                case WebConstans.PAGE_STYLE_DASHEN_TUWEN:
                    if (distanceY >= 0 && distanceY < mH5ActionContainer.getHeight()) {
                        mActionTitle.setAlpha(0);
                        mAvatar.setAlpha(0f);
                        mTvSubscribe.setAlpha(0);
                    } else {
                        mActionTitle.setAlpha(1);
                        mAvatar.setAlpha(1f);
                        mTvSubscribe.setAlpha(1);
                    }
                    break;
                case WebConstans.PAGE_STYLE_FULLSCREEN_COMMON:
                    if (distanceY >= 0 && distanceY < yjToolBar.getHeight() / 2) {
                        float precent = (float) (distanceY) / yjToolBar.getHeight();
                        float alpha = precent * 255;
                        yjToolBar.getBackground().mutate().setAlpha((int) alpha);
                        mBackImg.setImageResource(R.drawable.bookdetails_icon_back_24_24_white);
                        mLeftTitleText.setAlpha(0);
                        mTitleText.setAlpha(1);
                        StatusBarCompat.setLightStatusBar(((Activity) mBackImg.getContext()).getWindow(), false);
                        return;
                    } else {
                        if (distanceY >= yjToolBar.getHeight() / 2) {
                            yjToolBar.getBackground().mutate().setAlpha(255);
                            mLeftTitleText.setAlpha(1);
                            mTitleText.setAlpha(0);
                            mBackImg.setImageResource(R.drawable.bookdetails_icon_back_24_24);
                            StatusBarCompat.setLightStatusBar(((Activity) mBackImg.getContext()).getWindow(), true);
                        }
                    }
                    break;
                case WebConstans.PAGE_STYLE_FULLSCREEN_COMMON_DASHEN_MASTER:
                    if (distanceY >= 0 && distanceY < yjToolBar.getHeight()) {
                        float precent = (float) (distanceY) / yjToolBar.getHeight();
                        float alpha = precent * 255;
                        yjToolBar.getBackground().mutate().setAlpha((int) alpha);
                        mBackImg.setImageResource(R.drawable.bookdetails_icon_back_24_24_white);
                        mTitleText.setAlpha(0);
                        mTvSubscribe.setAlpha(0);
                        mAvatar.setAlpha(0f);
                        StatusBarCompat.setLightStatusBar(((Activity) mBackImg.getContext()).getWindow(), false);
                        return;
                    } else {
                        if (distanceY >= yjToolBar.getHeight()) {
                            yjToolBar.getBackground().mutate().setAlpha(255);
                            mTitleText.setAlpha(1);
                            mTvSubscribe.setAlpha(1);
                            mAvatar.setAlpha(1f);
                            mBackImg.setImageResource(R.drawable.bookdetails_icon_back_24_24);
                            StatusBarCompat.setLightStatusBar(((Activity) mBackImg.getContext()).getWindow(), true);
                        }
                    }
                    break;
                case WebConstans.PAGE_STYLE_FULLSCREEN_SPECIAL:
                    if (distanceY >= 0 && distanceY < yjToolBar.getHeight() / 2) {
                        float precent = (float) (distanceY) / yjToolBar.getHeight();
                        float alpha = precent * 255;
                        yjToolBar.getBackground().mutate().setAlpha((int) alpha);
                        setImgResource(distanceY);
                        mTitleText.setAlpha(precent);
                    } else {
                        yjToolBar.getBackground().mutate().setAlpha(255);
                        mTitleText.setAlpha(1);
                    }
                    break;
                case WebConstans.PAGE_STYLE_FULLSCREEN_COMMON_FLS:
                    if (distanceY >= 0 && distanceY < yjToolBar.getHeight() / 2) {
                        float precent = (float) (distanceY) / yjToolBar.getHeight();
                        float alpha = precent * 255;
                        yjToolBar.getBackground().mutate().setAlpha((int) alpha);
                        if (mWebViewStatusBarHelper != null) {
                            mWebViewStatusBarHelper.changeStatusColor();
                        }
                        mLeftTitleText.setAlpha(0);
                        mTitleText.setAlpha(1);
                        mTitleText.setTextColor(Color.WHITE);
                        mBackImg.setImageResource(R.drawable.bookdetails_icon_back_24_24_white);

                    } else {
                        yjToolBar.getBackground().mutate().setAlpha(255);
                        if (mWebViewStatusBarHelper != null) {
                            mWebViewStatusBarHelper.changeStatusColor(Color.WHITE);
                        }
                        mLeftTitleText.setAlpha(1);
                        mTitleText.setAlpha(1);
                        mTitleText.setTextColor(Color.BLACK);
                        mBackImg.setImageResource(R.drawable.bookdetails_icon_back_24_24);

                    }

                    break;
                default:
                    break;

            }
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    private void setImgResource(int distanceY) {
//        if (!WebStyleHelper.isFlsFullScreenStyle(mWebData)) {
//            mBackImg.setImageResource(distanceY == 0 ? R.drawable.h5_back_gray_icon : R.drawable.h5_back_icon);
//        }
//
//        if (isShareType()) {
//            mRightImg.setImageResource(distanceY == 0 ? R.drawable.h5_share_gray_icon : R.drawable.h5_share_icon);
//            return;
//        }
//        if (isRefreshType()) {
//            mRightImg.setImageResource(distanceY == 0 ? R.drawable.h5_update_gray_icon : R.drawable.h5_update_icon);
//            return;
//        }
//        if (isHelpType()) {
//            mRightImg.setImageResource(distanceY == 0 ? R.drawable.h5_help_gray_icon : R.drawable.h5_help_icon);
//            return;
//        }
//        mRightImg.setImageResource(distanceY == 0 ? R.drawable.h5_update_gray_icon : R.drawable.h5_update_icon);
    }





    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.img_right_icon) {
            if (onH5titleImpListener != null) {
                try {
                    if (isShareType()) {
                        onH5titleImpListener.H5TitleClickCallBack(mUrl, "", SHARE);
                        return;
                    }
                    if (isRefreshType()) {
                        onH5titleImpListener.H5TitleClickCallBack("", "", REFRESH);
                        return;
                    }
                    if (isHelpType()) {
                        onH5titleImpListener.H5TitleClickCallBack(mLinkUrl, mLinkTitle, HELP);
                        return;
                    }
                    onH5titleImpListener.H5TitleClickCallBack("", "", REFRESH);
                    return;
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        } else if (id == R.id.img_back) {
            onH5titleImpListener.H5TitleClickCallBack("", "", BACK);
        }
    }


}

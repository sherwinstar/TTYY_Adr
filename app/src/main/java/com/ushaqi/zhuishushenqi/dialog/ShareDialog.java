package com.ushaqi.zhuishushenqi.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ushaqi.zhuishushenqi.R;
import com.ushaqi.zhuishushenqi.model.baseweb.ShareImageEntity;
import com.ushaqi.zhuishushenqi.module.baseweb.WebConstans;
import com.ushaqi.zhuishushenqi.module.baseweb.helper.WebShareHelper;


/**
 * 分享dialog
 *
 * @author zengcheng
 * create at 2021/6/24 下午4:59
 */
public class ShareDialog extends Dialog implements View.OnClickListener {

    private static final String TAG ="CommandGoodsDialog" ;
    private ImageView mIvClose;

    private TextView mTvWxFriend;
    private TextView mTvWxCircle;
    private TextView mTvSavePic;
    /**
     * 上下文  （dialog必须与Activity 绑定）
     */
    private final  Activity mActivity;

    private    ShareImageEntity mShareImageEntity;
    private WebView  mWebview;

    public ShareDialog(Activity activity,  ShareImageEntity entity,WebView webView) {
        super(activity,R.style.Bottom_Dialog);
        this.mActivity = activity;
        this.mShareImageEntity = entity;
        this.mWebview=webView;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_share);
        mTvWxFriend = (TextView) findViewById(R.id.tv_wx_friend);
        mTvWxCircle = (TextView) findViewById(R.id.tv_wx_circle);
        mTvSavePic = (TextView) findViewById(R.id.tv_save_pic);
       mIvClose = (ImageView) findViewById(R.id.iv_close);
        mTvWxFriend.setOnClickListener(this);
        mTvWxCircle.setOnClickListener(this);
        mTvSavePic.setOnClickListener(this);
        mIvClose.setOnClickListener(this);
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        }
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_wx_friend) {
            if (isShowing()) {
                dismiss();
            }
            WebShareHelper.shareSpread(mShareImageEntity, mActivity, mWebview, WebConstans.SHARE_TYPE_WECHAT);
        } else if (v.getId() == R.id.tv_wx_circle) {
            if (isShowing()) {
                dismiss();
            }
            WebShareHelper.shareSpread(mShareImageEntity, mActivity, mWebview, WebConstans.SHARE_TYPE_WECHAT_MOMENT);
        } else if (v.getId() == R.id.tv_save_pic) {
            if (isShowing()) {
                dismiss();
            }
            WebShareHelper.shareSpread(mShareImageEntity, mActivity, mWebview, WebConstans.DOWNLOAD_IMAGE);
        }else if(v.getId()==R.id.iv_close){
            if (isShowing()) {
                dismiss();
            }

        }


    }



}


package com.ushaqi.zhuishushenqi.module.baseweb.view;

import android.app.Activity;

import com.githang.statusbar.StatusBarCompat;
import com.ushaqi.zhuishushenqi.model.baseweb.ZssqWebData;
import com.ushaqi.zhuishushenqi.module.baseweb.helper.WebStyleHelper;

public class WebViewStatusBarHelper implements StatusBarState {
    private ZssqWebData mZssqWebData;
    private Activity activity;
    private int statusBarColor = 0;

    public void showStatusBar(ZssqWebData zssqWebData, Activity activity) {
        mZssqWebData = zssqWebData;
        this.activity = activity;
    }

    /**
     * change statusBar's color by sStatusColorAlpha
     */
    @Override
    public void setStatusColor(int color) {
        if (mZssqWebData != null) {
            mZssqWebData.setStatusBarColor(color);
        }
    }

    /**
     * change statusBar's color by sStatusColorAlpha
     */
    public void changeStatusColor() {
        if (statusBarColor == mZssqWebData.getStatusBarColor()) {
            return;
        }
        if (activity != null) {
            activity.runOnUiThread(() -> StatusBarCompat.setStatusBarColor(activity, mZssqWebData.getStatusBarColor()));
            statusBarColor = mZssqWebData.getStatusBarColor();
        }
    }

    /**
     * determined activity statusBar'color
     *
     * @param color the color of  this web's statusBar Color determined by the web page
     */
    @Override
    public void changeStatusColor(int color) {
        try {
            if (statusBarColor == color) {
                return;
            }
            if (!WebStyleHelper.isFlsFullScreenStyle(mZssqWebData)) {
                return;
            }

            if (color != 0 && activity != null) {
                activity.runOnUiThread(() -> StatusBarCompat.setStatusBarColor(activity, color));
                statusBarColor = color;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

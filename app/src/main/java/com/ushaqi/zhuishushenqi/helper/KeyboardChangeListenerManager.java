package com.ushaqi.zhuishushenqi.helper;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

import com.ushaqi.zhuishushenqi.MyApplication;
import com.ushaqi.zhuishushenqi.util.ScreenUtils;


/**
 * 键盘监听管理类
 * @author shijingxing
 * @date 2020/9/17
 */
public class KeyboardChangeListenerManager implements ViewTreeObserver.OnGlobalLayoutListener {

    /**
     * 最好是DecorView
     */
    private View mRootView;
    private Rect mRect;
    private int mScreenHeight;
    private KeyboardChangetListener mKeyboardChangeListener;
    private boolean mActiveState = false;

    public KeyboardChangeListenerManager(View rootView) {
        this.mRootView = rootView;
        mScreenHeight = ScreenUtils.getScreenHeight(MyApplication.getInstance());
        mRect = new Rect();
    }

    public void setKeyboardChangetListener(KeyboardChangetListener keyboardChangetListener) {
        this.mKeyboardChangeListener = keyboardChangetListener;
    }

    public void startKeyBoardListener() {
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public void stopKeyBoardListener() {
        mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        // 获取当前页面窗口的显示范围
        mRootView.getWindowVisibleDisplayFrame(mRect);
        int keyboardHeight = mScreenHeight - mRect.bottom;
        boolean isActive = false;
        if (Math.abs(keyboardHeight) > mScreenHeight / 5) {
            // 超过屏幕五分之一则表示弹出了输入法
            isActive = true;
        }
        if(mActiveState != isActive){
            if(mKeyboardChangeListener != null){
                mKeyboardChangeListener.onKeyboardStateChanged(isActive, keyboardHeight);
            }
            mActiveState = isActive;
        }
    }


    /**
     * 软键盘弹出回落监听
     */
    public interface KeyboardChangetListener {
        void onKeyboardStateChanged(boolean isActive, int keyboardHeight);
    }
}

package com.jxjuwen.ttyy.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ushaqi.zhuishushenqi.event.BusProvider;
import com.ushaqi.zhuishushenqi.util.LogUtil;

/**
 * Created by JackHu on 16/8/11.
 * 无MVP的Fragment基类
 */

public abstract class ClassicsFragment extends Fragment {

    public static final String  TAG="ClassicsFragment";

    protected View mView;
    public AppCompatActivity mActivity;
    protected volatile boolean mIsVisible;//是否显示
    private volatile boolean mIsPrepared;//是否加载组件完毕
    private volatile boolean mIsFirstInit;//是否第一次加载
    private volatile boolean mCreated;//是否第一次创建


    @Override
    public void onAttach(Context context) {
        mActivity = (AppCompatActivity) context;
        super.onAttach(context);
        if (!mCreated) {
            processBeforeAnything();
            mCreated = true;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getUserVisibleHint()){
            onVisibilityChanged(true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), container, false);
        setPreparedStatus(true);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewAndData(view);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(getUserVisibleHint()){
            onVisibilityChanged(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCreated = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            BusProvider.getInstance().unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initViewAndData(View view);

    /**
     * 在fragment第一次加载时执行操作
     */
    protected void processBeforeAnything() {
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.d(TAG, "setUserVisibleHint" + isVisibleToUser);
        if (!mCreated) {
            processBeforeAnything();
            mCreated = true;
        }
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }
        if(isResumed()){
            onVisibilityChanged(isVisibleToUser);
        }
    }

    protected void onVisible() {
        if (!mIsPrepared || !mIsVisible || mIsFirstInit) {
            return;
        }
        lazyLoad();
        mIsFirstInit = true;
    }

    protected void lazyLoad() {
        LogUtil.d(TAG, "lazyload");
    }

    protected void onInvisible() {
        LogUtil.d(TAG, "onInvisible");
    }

    public boolean getVisibleStatus() {
        return mIsVisible;
    }

    public boolean getPreparedStatus() {
        return mIsPrepared;
    }

    //是否加载组件完毕
    public void setPreparedStatus(boolean mIsPrepared) {
        this.mIsPrepared = mIsPrepared;
    }

    public boolean getFirstInitStatus() {
        return mIsFirstInit;
    }

    //是否第一次加载
    public void setFirstInitStatus(boolean mIsFirstInit) {
        this.mIsFirstInit = mIsFirstInit;
    }

    /**
     * 正确记录fragmnt从可见-不可见，不可见-可见的回调
     * @param visible
     */
    protected void onVisibilityChanged(boolean visible) {}
}

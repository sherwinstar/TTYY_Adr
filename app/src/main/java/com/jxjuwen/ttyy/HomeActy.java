package com.jxjuwen.ttyy;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.githang.statusbar.StatusBarCompat;
import com.jxjuwen.ttyy.base.BaseActivity;
import com.jxjuwen.ttyy.fragment.FragmentBackListener;
import com.jxjuwen.ttyy.fragment.GameFragment;
import com.squareup.otto.Subscribe;
import com.ushaqi.zhuishushenqi.BuildConfig;
import com.ushaqi.zhuishushenqi.HttpExceptionMessage;
import com.ushaqi.zhuishushenqi.MyApplication;
import com.ushaqi.zhuishushenqi.R;
import com.ushaqi.zhuishushenqi.channel.AppChannelManager;
import com.ushaqi.zhuishushenqi.dialog.AppGuideDialog;
import com.ushaqi.zhuishushenqi.dialog.CommandGoodsDialog;
import com.ushaqi.zhuishushenqi.dialog.UpdateAppDialog;
import com.ushaqi.zhuishushenqi.event.BusProvider;
import com.ushaqi.zhuishushenqi.event.LoadFinishEvent;
import com.ushaqi.zhuishushenqi.event.LocateHomeTabEvent;
import com.ushaqi.zhuishushenqi.event.PermissionEvent;
import com.ushaqi.zhuishushenqi.event.TabChangeEvent;
import com.ushaqi.zhuishushenqi.helper.CommandGoodsHelper;
import com.ushaqi.zhuishushenqi.httpcore.HttpUrlProvider;
import com.ushaqi.zhuishushenqi.httpcore.callback.ClientCallBack;
import com.ushaqi.zhuishushenqi.httpcore.requester.CommonRequester;
import com.ushaqi.zhuishushenqi.model.HomeTapEntity;
import com.ushaqi.zhuishushenqi.model.UpdateApkConfig;
import com.ushaqi.zhuishushenqi.module.baseweb.WebConstans;
import com.ushaqi.zhuishushenqi.module.baseweb.ZssqWebFragment;
import com.ushaqi.zhuishushenqi.permission.SysPermissionHelper;
import com.ushaqi.zhuishushenqi.sensors.SensorsParamBuilder;
import com.ushaqi.zhuishushenqi.sensors.SensorsUploadHelper;
import com.ushaqi.zhuishushenqi.util.AppHelper;
import com.ushaqi.zhuishushenqi.util.LogUtil;
import com.ushaqi.zhuishushenqi.util.SharedPreferencesUtil;
import com.ushaqi.zhuishushenqi.util.ToastUtil;
import com.ushaqi.zhuishushenqi.view.HomeViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
   *首页
   *@author  zengcheng
   *create at 2021/5/19 上午9:41
*/
public class HomeActy  extends BaseActivity {
    private static int sActivityCreateCount = 0;

    private  static  boolean sIsHideGame;
    private  static int TAB_SIZE ;
    private static String[] title;
    private static int mSelectPosition;
    private HomeViewPager mHomeMainVp;
    private CommonTabLayout mHomeMainTab;
    private final  static int[] mSelectIcons = {R.drawable.ic_index_select,R.drawable.goshopping_select,R.drawable.ic_partner_select, R.drawable.ic_game_select, R.drawable.ic_mine_select};
    private final  static  int[] mUnSelectIcons = {R.drawable.ic_index,R.drawable.ic_goshopping,R.drawable.ic_partner,R.drawable.ic_game, R.drawable.ic_mine};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private HomeFragmentPagerAdapter mViewPagerAdapter;
    private Map<String, Fragment> mFragmentMap = new HashMap<>(5);
    private static final int START_WELCOME_ACTIVITY_ROR_REQUEST_CODE=2021;

    public static final String MINE_URL= HttpUrlProvider.getH5Url()+"/ttyy/mine?hideGameCenter=%s";
    public static final String GO_SHOPPING_URL=HttpUrlProvider.getH5Url()+"/ttyy/home";

    public static final String OPTIMIZATION_URL= HttpUrlProvider.getH5Url()+"/ttyy/optimization";

    public static final String PARTNER_HOT_PRODUCT = HttpUrlProvider.getH5Url()+"/ttyy/hotproduct";


    private long mCurrentBackTime = 0;
    private static final int BACK_PRESSED_INTERVAL = 2000;

    private FragmentBackListener backListener;
    private boolean isInterception = true;

    private LinearLayout mSplashContainer;




    @Override
    protected int getLayout() {
        return R.layout.activity_home_bottom_tab;

    }

    public static Intent createIntent(Activity activity){
        return   new Intent(activity,HomeActy.class);

    }

    @Subscribe
    public void onPageFinish(LoadFinishEvent event){
        LogUtil.e("webview","加载完成");
        if(mSplashContainer!=null&&mSplashContainer.getVisibility()==View.VISIBLE){
            mSplashContainer.setVisibility(View.GONE);
        }
    }


    @Override
    protected void initEventAndData() {
        sIsHideGame= SharedPreferencesUtil.get(MyApplication.getInstance(),"home_tab_game_need_show",true);
        TAB_SIZE =sIsHideGame?4:5;
        if (sIsHideGame){
            title = new String[]{"首页口令商品","优选口令商品","合伙人口令商品","我的口令商品"};
        }else {
            title = new String[]{"首页口令商品","优选口令商品","合伙人口令商品","游戏口令商品","我的口令商品"};
        }
        hideToolbar();
        StatusBarCompat.setStatusBarColor(HomeActy.this, getResources().getColor(R.color.white));
        mHomeMainVp = (HomeViewPager) findViewById(R.id.home_main_vp);
        mHomeMainTab = (CommonTabLayout) findViewById(R.id.home_main_tab);
        mSplashContainer=findViewById(R.id.rl_splash_top_container);

        try {
            mViewPagerAdapter = new HomeFragmentPagerAdapter(getSupportFragmentManager());
            mHomeMainVp.setOffscreenPageLimit(TAB_SIZE);
            mHomeMainVp.setAdapter(mViewPagerAdapter);
            String[] titles = new String[]{"首页","优选", "爆品","游戏", "我的"};
            for (int i = 0; i < titles.length; i++) {
                if(i==3&&sIsHideGame){
                    continue;
                }
                mTabEntities.add(new HomeTapEntity(titles[i], mSelectIcons[i], mUnSelectIcons[i]));
            }
            mHomeMainTab.setTabData(mTabEntities);
            homeActivityCreateEvent();
            mHomeMainTab.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelect(int position) {
                    mHomeMainVp.setCurrentItem(position, false);

                }

                @Override
                public void onTabReselect(int i) {

                }
            });
            mHomeMainVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mSelectPosition=position;
//                    if (!UserHelper.getInstance().isLogin()&&(position==1||position==2)){
//                        startActivityForResult(LoginActy.createIntent(HomeActy.this),2021);
//                    }else {
//
////                        SharedPreferencesUtil.put(MyApplication.getInstance(),"int_select_position",position);
//                        mHomeMainTab.setCurrentTab(position);
//                        int tab=mHomeMainTab.getCurrentTab();
//                        String tabName=  mHomeMainTab.getTitleView(tab).getText().toString();
//                        if(TextUtils.equals("我的",tabName)){
//                            StatusBarCompat.setStatusBarColor(HomeActy.this, getResources().getColor(R.color.top_status_color));
//                        }else {
//                            StatusBarCompat.setStatusBarColor(HomeActy.this, getResources().getColor(R.color.white));
//                        }
//                        setInterception(true);
//                        BusProvider.getInstance().post(new TabChangeEvent());
//                    }
                    mHomeMainTab.setCurrentTab(position);
                    int tab=mHomeMainTab.getCurrentTab();
                    String tabName=  mHomeMainTab.getTitleView(tab).getText().toString();
                    if(TextUtils.equals("我的",tabName)){
                        StatusBarCompat.setStatusBarColor(HomeActy.this, getResources().getColor(R.color.top_status_color));
                    }else {
                        StatusBarCompat.setStatusBarColor(HomeActy.this, getResources().getColor(R.color.white));
                    }
                    setInterception(true);
                    BusProvider.getInstance().post(new TabChangeEvent());

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } catch (Error e) {
            e.printStackTrace();
        }
        homeActivityShowEvent();

    }

    private void homeActivityCreateEvent() {
        if (sActivityCreateCount == 0) {
            SensorsParamBuilder  sensorsParamBuilder = SensorsParamBuilder.create();
            sensorsParamBuilder.put("app_start_time",AppHelper.getEnterAppTime());
            SensorsUploadHelper.addTrackEvent("yy_EnterHomePage", sensorsParamBuilder);
        }
        sActivityCreateCount++;
    }

    private void homeActivityShowEvent() {
        if (!SharedPreferencesUtil.get(this,"boool_has_show_welcome",false)){
            Intent intent=new Intent(this,WelcomeActivity.class);
            startActivityForResult(intent,START_WELCOME_ACTIVITY_ROR_REQUEST_CODE);
            overridePendingTransition(0,0);
        }else {
            requestUpdateApkConfig();
        }
    }

    /**
     * 首次进入展示引导结界面
     */
    private void showAppGuideView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            AppGuideDialog appGuideDialog=new AppGuideDialog(this);
            appGuideDialog.show();
        }
    }

    /**
     * 我的
     */
    public ZssqWebFragment getMineFragment(String tag) {
        ZssqWebFragment fragment = (ZssqWebFragment) mFragmentMap.get(tag);
        if (fragment == null) {
            fragment = ZssqWebFragment.newInstance("我的",String.format(MINE_URL,sIsHideGame));
            mFragmentMap.put(tag, fragment);
        }
        return fragment;
    }

    /**
     * 爆品
     */
    public ZssqWebFragment getPartnerFragment(String tag) {
        ZssqWebFragment fragment = (ZssqWebFragment) mFragmentMap.get(tag);
        if (fragment == null) {
            fragment = ZssqWebFragment.newInstance("爆品", PARTNER_HOT_PRODUCT);
            mFragmentMap.put(tag, fragment);
        }
        return fragment;
    }

    /**
     * 优选
     */
    public ZssqWebFragment getOptimizationFragment(String tag) {
        ZssqWebFragment fragment = (ZssqWebFragment) mFragmentMap.get(tag);
        if (fragment == null) {
            fragment = ZssqWebFragment.newInstance("优选",OPTIMIZATION_URL);
            mFragmentMap.put(tag, fragment);
        }
        return fragment;
    }
    /**
     * 首页
     */
    public ZssqWebFragment getGoShoppingFragment(String tag) {
        ZssqWebFragment fragment = (ZssqWebFragment) mFragmentMap.get(tag);
        if (fragment == null) {
            fragment = ZssqWebFragment.newInstance("首页",GO_SHOPPING_URL);
            mFragmentMap.put(tag, fragment);
        }
        return fragment;
    }
    /**
     * 游戏
     */
    public GameFragment getGameFragment(String tag) {
        GameFragment fragment = (GameFragment) mFragmentMap.get(tag);
        if (fragment == null) {
            fragment = GameFragment.newInstance();
            mFragmentMap.put(tag, fragment);
        }
        return fragment;
    }

    /**
     * FragmentPagerAdapter
     */
    private class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

        private String[] tagString = {"homeTag0", "homeTag1", "homeTag2","homeTag3","homeTag4"};

        public HomeFragmentPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return getGoShoppingFragment(tagString[0]);
            }
            if (position == 1) {
                return getOptimizationFragment(tagString[1]);
            }
            if (position == 2) {
                return getPartnerFragment(tagString[2]);
            }
            if (position == 3) {
                if(sIsHideGame){
                    return getMineFragment(tagString[3]);
                }else {
                    return getGameFragment(tagString[3]);
                }

            }
            return getMineFragment(tagString[4]);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String[] navName ;
            if(sIsHideGame){
                navName = getResources().getStringArray(R.array.home_no_game_tabs);
            }else {
                navName = getResources().getStringArray(R.array.home_tabs);
            }
            return navName[position];
        }

        @Override
        public int getCount() {
            return TAB_SIZE;
        }
    }

    @Override
    public void onBackPressed() {
        if(isInterception&&backListener!=null){
            callJsHideMethod();
            backListener.onBackForward();
        }else {
            long time = System.currentTimeMillis();
            if (time - mCurrentBackTime > BACK_PRESSED_INTERVAL) {
                mCurrentBackTime = time;
                callJsHideMethod();
                ToastUtil.show(this, getString(R.string.exit_hint), Toast.LENGTH_SHORT);
            } else {
                super.onBackPressed();
            }
        }

    }

    private void callJsHideMethod() {
        if ("首页口令商品".equals(getGoodsTitle())){
            if (getGoShoppingFragment("homeTag0").getCurrentWebview()!=null){
                AppHelper.hideSoftKeyboard(HomeActy.this);
                getGoShoppingFragment("homeTag0").getCurrentWebview().loadUrl("javascript:pageBack()");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebConstans.resetJsCallParam();
        CommandGoodsDialog commandGoodsDialog = CommandGoodsHelper.getInstance().getCommandGoodsDialog();
        if(commandGoodsDialog !=null&& commandGoodsDialog.isShowing()){
            commandGoodsDialog.dismiss();
        }
        CommandGoodsHelper.getInstance().setDialogNull();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==START_WELCOME_ACTIVITY_ROR_REQUEST_CODE){
            showAppGuideView();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (SysPermissionHelper.PHONE_REQUEST_CODE == requestCode && permissions != null
                && grantResults != null && permissions.length == grantResults.length) {
            for (int i = 0, length = permissions.length; i < length; ++i) {
                final String permission = permissions[i];

                if (SysPermissionHelper.PERMISSION_PHONE.equals(permission)) {
                    int result=grantResults[i];
                    if(result== PackageManager.PERMISSION_GRANTED){
                        LogUtil.e(TAG,"permission："+permission+"获取成功");
                        BusProvider.getInstance().post(new PermissionEvent());
                    }
                    break;
                }
            }
        }
    }

    public FragmentBackListener getBackListener() {
        return backListener;
    }

    /**
     * 设置Fragment中返回键监听
     * @param backListener
     */
    public void setBackListener(FragmentBackListener backListener) {
        this.backListener = backListener;
    }

    public boolean isInterception() {
        return isInterception;
    }

    /**
     * 区别Activity和Fragment返回键的监听事件
     * 可以自主在Fragment中设置监听事件
     * @param isInterception
     */
    public void setInterception(boolean isInterception) {
        this.isInterception = isInterception;
    }


    @Subscribe
    public void onLocateHomeTab(LocateHomeTabEvent event){
        if(event.index>=0&&event.index<TAB_SIZE){
            mHomeMainTab.setCurrentTab(event.index);
            mHomeMainVp.setCurrentItem(event.index, false);
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.e(TAG,"onStart:");
       int tab=mHomeMainTab.getCurrentTab();
        String category1=  mHomeMainTab.getTitleView(tab).getText().toString();
        CommandGoodsHelper.getInstance().checkCommandGoods(this,category1);
    }

    /**当前的title名字
     * @return
     */
    public static String getGoodsTitle(){
        return title[mSelectPosition];
    }


    /**
     * 判断是否需要更新apk，弹出更新sdk的对话框
     */
    private void  requestUpdateApkConfig(){

//        UpdateAppDialog appDialog=new UpdateAppDialog(HomeActy.this,null);
//        appDialog.show();
////
        CommonRequester.getInstance().getApi().getUpdateApkConfig(AppChannelManager.getChannelId(),String.valueOf(BuildConfig.VERSION_CODE)).enqueue(new ClientCallBack<UpdateApkConfig>() {
            @Override
            public void onSuccess(UpdateApkConfig response) {
                //0是隐藏游戏，1是开启游戏
                if(response!=null&&response.isOk()&&response.getData()!=null){
                            UpdateAppDialog appDialog=new UpdateAppDialog(HomeActy.this,response);
                        appDialog.show();

                }
            }

            @Override
            public void onFailed(HttpExceptionMessage exceptionMessage) {

            }
        });
    }






}

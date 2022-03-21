package com.ushaqi.zhuishushenqi.local;

import android.text.TextUtils;


import com.alibaba.alibclogin.AlibcLogin;
import com.alibaba.alibcprotocol.callback.AlibcLoginCallback;
import com.alibaba.alibcprotocol.route.proxy.IAlibcLoginProxy;
import com.ushaqi.zhuishushenqi.event.BusProvider;
import com.ushaqi.zhuishushenqi.event.LogoutEvent;
import com.ushaqi.zhuishushenqi.model.Account;
import com.ushaqi.zhuishushenqi.model.User;
import com.ushaqi.zhuishushenqi.util.LogUtil;

/**
 * 用户信息类
 * Created by JackHu on 2018/8/22
 */
public class UserHelper {

    private static volatile UserHelper sInstance;
    private  Account account;
    private String  token="";
    private String userId;

    public UserHelper() {
        LogUtil.d("UserHelper", "created+" + System.currentTimeMillis());
    }

    public static UserHelper getInstance() {
        if (sInstance == null) {
            synchronized (UserHelper.class) {
                if (sInstance == null) {
                    sInstance = new UserHelper();
                }
            }
        }
        return sInstance;
    }


    /**
     * 获取当前用户token
     *
     * @return
     */
    public String getToken() {
        if(!TextUtils.isEmpty(token)){
            return token;
        }
        User user =getUser();
        if (user != null) {
            token= user.getToken();
        }
        return token;
    }

    public String getUserId() {
        if(!TextUtils.isEmpty(userId)){
            return userId;
        }
        User user =getUser();
            if (user != null) {
                userId= user.getUserId();
        }
        return userId;
    }

    public User getUser() {
        Account account = getAccount();
        if (account != null) {
            User user = account.getUser();
            if (user != null) {
                return user;
            }
        }
        return null;
    }


    public   Account getAccount() {
        if (account != null) {
            return account;
        } else {
            account = UserPropertyHelper.getInstance().getAccount();
            return account;

        }
    }



    /**
     * 获取用户是否登录
     *
     * @return
     */
    public boolean isLogin() {
   return !TextUtils.isEmpty(getToken());
    }

    /**
     * 获取用户书架排序规则
     * 0  按照update排序,1  按照最近阅读排序
     *
     * @return
     */


    /**
     * 验证本地保存的账户是否有效
     *
     * @@zhy@@ 20161214 token过期重新登录后需要把account刷新
     */
    public  void clearAccount() {
        account = null;
        token=null;
        userId=null;
        UserPropertyHelper.getInstance().removeAccount();
    }


    public void logOut() {
        clearAccount();
        BusProvider.getInstance().post(new LogoutEvent());
        taobaoLogout();
    }

    /**
     *@desc  退出淘宝授权
     *@param
     *@return
     */
    private void taobaoLogout(){
        IAlibcLoginProxy instance = AlibcLogin.getInstance();
        if(instance.isLogin()){
            instance.logout(new AlibcLoginCallback() {
                @Override
                public void onSuccess(String s, String s1) {

                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
        }
    }


}

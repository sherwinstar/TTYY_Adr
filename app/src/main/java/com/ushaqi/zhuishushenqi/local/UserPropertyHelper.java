package com.ushaqi.zhuishushenqi.local;

import android.content.Context;
import android.text.TextUtils;

import com.ushaqi.zhuishushenqi.MyApplication;
import com.ushaqi.zhuishushenqi.model.Account;
import com.ushaqi.zhuishushenqi.model.User;
import com.ushaqi.zhuishushenqi.util.StringUtils;

import java.util.Properties;

/**
 * 用户属性相关
 *
 * @author Andy.zhang
 * @date 2019/5/13
 */
public final class UserPropertyHelper {

    private static volatile UserPropertyHelper sInstance;

    public static UserPropertyHelper getInstance() {
        if (sInstance == null) {
            synchronized (UserPropertyHelper.class) {
                if (sInstance == null) {
                    sInstance = new  UserPropertyHelper();
                }
            }
        }

        return sInstance;
    }

    private UserPropertyHelper() {
    }

    private Context getContext() {
        return MyApplication.getInstance();
    }






    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-09-11 17:29
     * @Description 设置绑定手机成功
     */


    /**
     * 用户登录后的信息
     *
     * @return account or null
     */
    public Account getAccount() {

        String userId = getProperty("user.userId");
        String token = getProperty("user.token");

        if (userId != null && token != null) {
            Account account = new Account();
            User user = new User();
            user.setUserId(userId);
            user.setToken(token);
            user.setNikeName(getProperty("user.nikeName"));
            user.setHeadImgurl(getProperty("user.avatar"));
            user.setSex(getProperty("user.sex"));
            user.setBindingMobile(getProperty("user.mobile"));
            user.setIdCardNum(getProperty("user.idCardNum"));
            user.setIdCardName(getProperty("user.idCardName"));
            user.setAliPayAccountUserName(getProperty("user.aliPayAccount"));

            String isBindingPdd=getProperty("user.isBindingPdd");
            if(isBindingPdd==null){
                isBindingPdd="false";
            }
            user.setBindingPdd(Boolean.parseBoolean(isBindingPdd));

            String isBindingTaobao=getProperty("user.isBindingTaobao");
            if(isBindingTaobao==null){
                isBindingTaobao="false";
            }
            user.setBindingTaobao(Boolean.parseBoolean(isBindingTaobao));

            String userType=getProperty("user.userType");
            if(userType==null){
                userType="0";
            }
            user.setUserType(Integer.parseInt(userType));

            user.setPromoterId(getProperty("user.promoterId"));

            user.setFpromoterId(getProperty("user.fpromoterId"));

            account.setOk(true);
            account.setUser(user);
            return account;
        }
        return null;
    }

    /**
     * 保存用户信息
     */
    private void saveUser(final User user) {
        if (user == null) {
            return;
        }

        setProperties(new Properties() {
            private static final long serialVersionUID = 8794384850518743201L;

            @Override
            public synchronized Object setProperty(String key, String value) {
                if(value==null){
                    value="";
                }
                return super.setProperty(key, value);
            }

            {
                setProperty("user.userId", user.getUserId());
                setProperty("user.nikeName", user.getNikeName());
                setProperty("user.token", user.getToken());
                setProperty("user.avatar", user.getHeadImgurl());
                setProperty("user.mobile", user.getBindingMobile());
                setProperty("user.sex", user.getSex());
                setProperty("user.idCardNum", user.getIdCardNum());
                setProperty("user.idCardName", user.getIdCardName());
                setProperty("user.aliPayAccount", user.getAliPayAccountUserName());

                setProperty("user.isBindingPdd", String.valueOf(user.isBindingPdd()));
                setProperty("user.isBindingTaobao", String.valueOf(user.isBindingTaobao()));
                setProperty("user.userType", String.valueOf(user.getUserType()));
                setProperty("user.promoterId", user.getPromoterId());
                setProperty("user.fpromoterId", user.getFpromoterId());
            }
        });
    }




    /**
     * 保存账户信息
     */
    public void saveAccount(final Account account) {
        if (account == null) {
            return;
        }

        saveUser(account.getUser());
    }


    /**
     * @param key
     * @param value
     */
    public void setProperty(String key, String value) {
        AppConfig.getAppConfig(getContext()).set(key, value);
    }

    /**
     * @param ps
     */
    public void setProperties(Properties ps) {
        AppConfig.getAppConfig(getContext()).set(ps);
    }

    /**
     * @param key
     * @return
     */
    public String getProperty(String key) {
        return AppConfig.getAppConfig(getContext()).get(key);
    }

    /**
     * 删除本地账户信息
     */
    public void removeAccount() {
        removeProperty("user.userId", "user.nikeName", "user.token", "user.avatar",
                "user.mobile", "user.sex", "user.idCardNum", "user.idCardName",
                "user.aliPayAccount","user.isBindingPdd","user.isBindingTaobao",
                "user.userType","user.promoterId","user.fpromoterId");
    }

    /**
     * @param key
     */
    public void removeProperty(String... key) {
        AppConfig.getAppConfig(getContext()).remove(key);
    }

}

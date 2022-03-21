package com.ushaqi.zhuishushenqi.module.baseweb.helper;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;

import com.jxjuwen.ttyy.InviteCodeActy;
import com.jxjuwen.ttyy.LoginActy;
import com.jxjuwen.ttyy.SettingActy;
import com.jxjuwen.ttyy.WithDrawActy;
import com.ushaqi.zhuishushenqi.event.BusProvider;
import com.ushaqi.zhuishushenqi.event.LocateHomeTabEvent;
import com.ushaqi.zhuishushenqi.helper.OpenProductDetailHelper;
import com.ushaqi.zhuishushenqi.local.UserHelper;
import com.ushaqi.zhuishushenqi.model.baseweb.JumpEntity;
import com.ushaqi.zhuishushenqi.module.baseweb.WebConstans;
import com.ushaqi.zhuishushenqi.module.baseweb.view.ZssqWebActivity;
import com.ushaqi.zhuishushenqi.util.ToastUtil;

import java.net.URLDecoder;

/**
 * <p>
 *
 * @ClassName: WebJumpHelper
 * @Date: 2019-05-23 18:01
 * @Author: jared
 * @Org: 上海元聚网络科技有限公司
 * @Description: H5跳转辅助类
 * </p>
 */
public class WebJumpHelper {

    public static void startJump(JumpEntity jumpEntity, Activity activity, Fragment fragment) {
        if (jumpEntity == null) {
            return;
        }

        if (!WebJsHelper.checkActivityAlive(activity)) {
            return;
        }

        if (WebConstans.JUMP_WEB.equals(jumpEntity.getJumpType())) {
            startJumpWeb(activity, fragment, jumpEntity);
            return;
        }
//
        if (WebConstans.JUMP_NATIVE.equals(jumpEntity.getJumpType())) {
            startjumpNative(activity, jumpEntity);
            return;
        }

    }



    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-23 18:12
     * @Description 跳转到原生页面
     */
    private static void startjumpNative(Activity activity, JumpEntity jumpEntity) {
        String pageType = jumpEntity.getPageType();
//        String jumpId = jumpEntity.getId();
//        H5BookExposureBean sensors = jumpEntity.getSensors();
//        Intent intent;
        switch (pageType) {

            case WebConstans.JUMP_LOGIN:
                if (!UserHelper.getInstance().isLogin()) {
                    activity.startActivity(LoginActy.createIntent(activity));
                }
                break;
            case WebConstans.JUMP_JOIN_PARTNER:
                BusProvider.getInstance().post(new LocateHomeTabEvent(2));
                break;
            case WebConstans.JUMP_OPTIMIZATION:
                BusProvider.getInstance().post(new LocateHomeTabEvent(1));
                break;
            case WebConstans.JUMP_ALIPAY_WITHDRAW_SETTING:
                activity.startActivity(WithDrawActy.createIntent(activity));
                break;
            case WebConstans.JUMP_SETTING:
                activity.startActivity(SettingActy.createIntent(activity));
                break;
            case WebConstans.JUMP_BIND_INVITE_CODE:
                activity.startActivity(InviteCodeActy.createIntent(activity));
                break;
            case WebConstans.JUMP_PRODUCT_DETAIL:
                OpenProductDetailHelper.getInstance().openProductDetail(activity,jumpEntity.getType(),jumpEntity.getUrl(),jumpEntity.getGoodsId(), jumpEntity);
                break;
            case WebConstans.JUMP_GAME_CENTER:
                BusProvider.getInstance().post(new LocateHomeTabEvent(3));
                break;
            default:
                break;
        }
    }


    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-23 18:10
     * @Description 跳转到H5
     */
    private static void startJumpWeb(Activity activity, Fragment fragment, JumpEntity jumpEntity) {
        if (jumpEntity == null) {
            return;
        }
        if (!WebJsHelper.checkActivityAlive(activity)) {
            return;
        }

        String url = URLDecoder.decode(jumpEntity.getLink());
        String title = jumpEntity.getTitle();

            if (!TextUtils.isEmpty(url)) {
                Intent intent;
                if(jumpEntity.isTransparentTitle()){
                     intent = ZssqWebActivity.createImmerseIntent(activity, title, url);
                }else {
                    intent = ZssqWebActivity.createIntent(activity, title, url);
                }
                if(fragment != null){
                    fragment.startActivityForResult(intent,  WebConstans.START_H5_REQUEST_CODE);
                }else{
                    activity.startActivityForResult(intent, WebConstans.START_H5_REQUEST_CODE);
                }
                }

        }


}

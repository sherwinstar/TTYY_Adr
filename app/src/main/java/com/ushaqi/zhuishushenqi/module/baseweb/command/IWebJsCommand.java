package com.ushaqi.zhuishushenqi.module.baseweb.command;

import android.app.Activity;
import android.webkit.WebView;

/**
 * <p>
 *
 * @ClassName: IWebJsCommand
 * @Date: 2019-05-29 15:26
 * @Author: jared
 * @Org: 上海元聚网络科技有限公司
 * @Description: 抽象命令的接口, 用与拦截Web的jsbridge，根据js的类型创建不同的指令类型,不同的js指令创建不同的类实现该接口
 * </p>
 */
public interface IWebJsCommand {

    /**
     * 执行jsBridge指令
     *
     * @param activity 当前activity
     * @param webView 当前webview
     * @param decodeUrl 当前jsBridge
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-30 11:38
     * @Description
     */
    void execute(Activity activity, WebView webView, String decodeUrl);

}

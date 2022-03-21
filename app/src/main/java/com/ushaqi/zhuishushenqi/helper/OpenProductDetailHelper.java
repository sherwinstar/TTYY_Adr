package com.ushaqi.zhuishushenqi.helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.ali.auth.third.core.service.impl.CredentialManager;
import com.alibaba.alibclogin.AlibcLogin;
import com.alibaba.alibcprotocol.callback.AlibcLoginCallback;
import com.alibaba.alibcprotocol.param.AlibcDegradeType;
import com.alibaba.alibcprotocol.param.AlibcShowParams;
import com.alibaba.alibcprotocol.param.AlibcTaokeParams;
import com.alibaba.alibcprotocol.param.OpenType;
import com.alibaba.alibcprotocol.route.proxy.IAlibcLoginProxy;
import com.alibaba.baichuan.trade.common.utils.AlibcLogger;
import com.baichuan.nb_trade.AlibcTrade;
import com.baichuan.nb_trade.callback.AlibcTradeCallback;
import com.kepler.jd.Listener.OpenAppAction;
import com.kepler.jd.login.KeplerApiManager;
import com.kepler.jd.sdk.bean.KelperTask;
import com.kepler.jd.sdk.bean.KeplerAttachParameter;
import com.ushaqi.zhuishushenqi.HttpExceptionMessage;
import com.ushaqi.zhuishushenqi.dialog.LoadingDialog;
import com.ushaqi.zhuishushenqi.httpcore.callback.ClientCallBack;
import com.ushaqi.zhuishushenqi.httpcore.requester.GoodsRequester;
import com.ushaqi.zhuishushenqi.local.UserHelper;
import com.ushaqi.zhuishushenqi.model.GoodsUrlBean;
import com.ushaqi.zhuishushenqi.model.baseweb.JumpEntity;
import com.ushaqi.zhuishushenqi.module.baseweb.helper.WebLoadUtil;
import com.ushaqi.zhuishushenqi.module.baseweb.view.ZssqWebActivity;
import com.ushaqi.zhuishushenqi.util.AppHelper;
import com.ushaqi.zhuishushenqi.util.AppUtils;
import com.ushaqi.zhuishushenqi.util.LogUtil;
import com.ushaqi.zhuishushenqi.util.ToastUtil;
import com.xunmeng.duoduojinbao.JinbaoUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class OpenProductDetailHelper {
    public static final String TAG = "OpenProductDetailHelper";

    LoadingDialog dialog;

    KelperTask mKelperTask;

    private static OpenProductDetailHelper sInstance;

    private OpenProductDetailHelper() {
    }


    public static OpenProductDetailHelper getInstance() {
        if (sInstance == null) {
            synchronized (OpenProductDetailHelper.class) {
                if (sInstance == null) {
                    sInstance = new OpenProductDetailHelper();
                }
            }
        }
        return sInstance;
    }

    public void openProductDetail(Activity activity, String type, String url, String goodsId, JumpEntity jumpEntity) {
        LogUtil.e(TAG, "type:" + type + ",url:" + url);
        try {
            url = URLDecoder.decode(url, "UTF-8");
            LogUtil.e(TAG, "decoder-url:" + url);
            if ("taobao".equals(type)) {
                openTBProductDetail(activity, url, goodsId);
            } else if ("pdd".equals(type)) {
                if (!AppHelper.isPinduoduoClientAvailable(activity)) {
                    if (jumpEntity != null && !TextUtils.isEmpty(jumpEntity.getWechatGHID())
                            && !TextUtils.isEmpty(jumpEntity.getWechatPath())) {
                        Intent intent = ZssqWebActivity.createIntent(activity, "拼多多小程序", WebLoadUtil.getInstance().getPddSmallAppUrl(jumpEntity));
                        activity.startActivity(intent);
                    } else {
                        ToastUtil.show("请安装拼多多客户端后重试");
                    }
                    return;
                }
                if (TextUtils.isEmpty(url)) {
                    getPddAuthGoodsDetailUrl(goodsId);
                    return;
                }
                openPDDProductDetail(url);
            } else {
                openJDProductDetail(activity, url);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    /**
     * 注：如需要通过 SDK 呼起京东 APP 首页，请使用如下 url 转链后，再调用 SDK 呼起：
     * https://m.jd.com/?isopen=1&ad_od=1&allowJDApp=1
     *
     * @param
     * @return
     * @desc // 通过 url 呼京东主站
     */
    public void openJDProductDetail(Activity activity, String url) {
        KeplerAttachParameter keplerAttachParameter = new KeplerAttachParameter();
        OpenAppAction openAppAction = (status, url1) -> HandlerHelper.post(() -> {
            if (status == OpenAppAction.OpenAppAction_start) {//开始状态未必一定执行，
                dialogShow(activity);
            } else {
                dialogDiss();
                mKelperTask = null;
            }
            if (status == OpenAppAction.OpenAppAction_result_NoJDAPP) {
                ToastUtil.show("您未安装京东app");
            } else if (status == OpenAppAction.OpenAppAction_result_BlackUrl) {
                LogUtil.e(TAG, "不在白名单");
            } else if (status == OpenAppAction.OpenAppAction_result_ErrorScheme) {
                LogUtil.e(TAG, "协议错误");
            } else if (status == OpenAppAction.OpenAppAction_result_APP) {
                LogUtil.e(TAG, "呼京东成功");
            } else if (status == OpenAppAction.OpenAppAction_result_NetError) {
                LogUtil.e(TAG, "网络异常");
            }
        });

        mKelperTask = KeplerApiManager.getWebViewService().openAppWebViewPage(activity,
                url,
                keplerAttachParameter,
                openAppAction);
    }


    private void dialogShow(Activity activity) {
        if (dialog == null) {
            dialog = new LoadingDialog(activity);
            dialog.setOnCancelListener(dialog -> {
                if (mKelperTask != null) {//取消
                    mKelperTask.setCancel(true);
                }
            });
        }
        if (!AppUtils.isActivityInValid(activity)) {
            dialog.show();
        }
    }

    private void dialogDiss() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }


    /**
     * @param
     * @return
     * @desc 淘宝详情页
     */
    public void openTBProductDetail(Activity activity, String url, String goodsId) {
        IAlibcLoginProxy alibcLogin = AlibcLogin.getInstance();
        if (!alibcLogin.isLogin()) {
            alibcLogin.showLogin(new AlibcLoginCallback() {
                @Override
                public void onSuccess(String userId, String nick) {
                    Toast.makeText(activity, "授权成功", Toast.LENGTH_SHORT).show();
                    String accessToken = CredentialManager.INSTANCE.getSession().topAccessToken;
                    if (TextUtils.isEmpty(goodsId)){
                        openTBoGoodsDetail(activity, url);
                    }else {
                        getTaoBaoAuthGoodsDetailUrl(activity, goodsId, accessToken);
                    }

                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(activity, "授权失败," + i + "," + s, Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        openTBoGoodsDetail(activity, url);

    }

    /**
     * @param
     * @return
     * @desc 获取淘宝授权链接
     */
    private void getTaoBaoAuthGoodsDetailUrl(final Activity activity, String goodsId, String accessToken) {
        ClientCallBack<GoodsUrlBean> clientCallBack = new ClientCallBack<GoodsUrlBean>() {
            @Override
            public void onSuccess(GoodsUrlBean response) {
                if (response == null) {
                    return;
                }
                if (response.isOk()) {
                    GoodsUrlBean.GoodsUrl goodsUrl = response.getGoodsUrl();
                    if (goodsUrl != null && !TextUtils.isEmpty(goodsUrl.getMobileUrl())) {
                        openTBoGoodsDetail(activity, goodsUrl.getMobileUrl());
                    }
                } else {
                    ToastUtil.show(response.getMsg());
                }

            }

            @Override
            public void onFailed(HttpExceptionMessage exceptionMessage) {
                ToastUtil.show(exceptionMessage.getExceptionInfo());
            }
        };

        GoodsRequester.getInstance().getApi().getTaobaoGoodsUrl(goodsId, UserHelper.getInstance().getToken(), accessToken).enqueue(clientCallBack);
    }

    /**
     * @param
     * @return
     * @desc 获取拼多多授权链接
     */
    private void getPddAuthGoodsDetailUrl(String goodsId) {
        ClientCallBack<GoodsUrlBean> clientCallBack = new ClientCallBack<GoodsUrlBean>() {
            @Override
            public void onSuccess(GoodsUrlBean response) {
                if (response == null) {
                    return;
                }
                if (response.isOk()) {
                    GoodsUrlBean.GoodsUrl goodsUrl = response.getGoodsUrl();
                    if (goodsUrl != null && !TextUtils.isEmpty(goodsUrl.getMobileUrl())) {
                        openPDDProductDetail(goodsUrl.getMobileUrl());
                    }
                } else {
                    ToastUtil.show(response.getMsg());
                }

            }

            @Override
            public void onFailed(HttpExceptionMessage exceptionMessage) {
                ToastUtil.show(exceptionMessage.getExceptionInfo());
            }
        };
        GoodsRequester.getInstance().getApi().getPddGoodsUrl(goodsId, UserHelper.getInstance().getToken()).enqueue(clientCallBack);
    }


    public void openTBoGoodsDetail(Activity activity, String url) {
        AlibcShowParams showParams = new AlibcShowParams();
        showParams.setOpenType(OpenType.Auto);
        showParams.setBackUrl("alisdk://");
        showParams.setDegradeUrl(url);
        showParams.setDegradeType(AlibcDegradeType.H5);
        Map<String, String> trackParams = new HashMap<>();
        AlibcTaokeParams taokeParams = new AlibcTaokeParams("", "", "");
        // 以显示传入url的方式打开页面（第二个参数是套件名称）
        AlibcTrade.openByUrl(activity, url, showParams,
                taokeParams, trackParams, new AlibcTradeCallback() {
                    @Override
                    public void onSuccess(int code) {
                        AlibcLogger.i(TAG, "request success");
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        AlibcLogger.e(TAG, "code=" + code + ", msg=" + msg);
                        if (code == -1) {
                            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * @param
     * @return
     * @desc 拼多多详情页
     */
    public void openPDDProductDetail(String url) {
        if (!URLUtil.isNetworkUrl(url)) {
            ToastUtil.show("url不合法," + url);
            return;
        }
        Uri parse = Uri.parse(url);
        String path = parse.getPath();
        if (!TextUtils.isEmpty(path) && path.length() > 1) {
            String host = parse.getHost().concat("/");
            String scheme = parse.getScheme().concat("://");
            path = url.replace(scheme, "").replace(host, "");
            LogUtil.e(TAG, "path:" + path);
            JinbaoUtil.openPdd(path);
        }
    }

}

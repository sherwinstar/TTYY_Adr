package com.ushaqi.zhuishushenqi.module.baseweb.helper;

import android.app.Activity;

import com.google.gson.Gson;
import com.ushaqi.zhuishushenqi.helper.ShareHelper;
import com.ushaqi.zhuishushenqi.model.baseweb.ShareImageEntity;
import com.ushaqi.zhuishushenqi.plugin.social.SocialUtils;
import com.ushaqi.zhuishushenqi.plugin.social.api.PlatformActionListener;
import com.ushaqi.zhuishushenqi.plugin.social.api.SocialPlatform;
import com.ushaqi.zhuishushenqi.util.ToastUtil;

import java.util.HashMap;

/**
 * <p>
 *
 * @ClassName: WebJsDataHelper
 * @Date: 2019-05-30 14:57
 * @Author: jared
 * @Org: 上海元聚网络科技有限公司
 * @Description: 数据处理类
 * </p>
 */
public class WebJsDataHelper {

    private static WebJsDataHelper instance;

    private ShareStateCallBack shareStateCallBack;

    private WebJsDataHelper() {
    }

    public synchronized static WebJsDataHelper getInstance() {
        if (instance == null) {
            instance = new WebJsDataHelper();
        }
        return instance;
    }




    /**
     * 将Url转换成分享对象(收徒任务，图片分享)
     * 包括收徒分享，唤醒徒弟，收入分享
     *
     * @param url2
     * @return
     */
    public static Object changeUrlToShareEntity(String url2) {
        try {
            //截取json
            String data = url2.substring(url2.indexOf("{"), url2.lastIndexOf("}") + 1);
            Gson gson = new Gson();
            return gson.fromJson(data, ShareImageEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 通过服务器获取合成图片参数，回调给web页面
     *
     * @param token
     * @param coinTaskShareEntity
     */
//    public void getShareParamFromServer(String token, ShareImageEntity coinTaskShareEntity, boolean isSpringShare) {
//        String group = coinTaskShareEntity.getGroup();
//
//
//        UserTaskManager.getInstance().doGetShareContentTask(token, group, prentice, params, isSpringShare, new BaseCoinTask.GotCoinSuccessCallback<ShareContent>() {
//            @Override
//            public void onCoinGotSuccess(ShareContent shareContent) {
//                if (shareParamCallBack != null) {
//                    shareParamCallBack.onSuccess(shareContent);
//                }
//            }
//
//            @Override
//            public void onCoinGotFail(HttpExceptionMessage t) {
//                if (shareParamCallBack != null) {
//                    shareParamCallBack.onFailed(t);
//                }
//            }
//        });
//    }

    /**
     * 分享Url
     *
     * @param activity
     * @param content
     */
//    public void shareUrl(final Activity activity, ShareContent content, final CoinTaskShareEntity coinTaskShareEntity) {
//        final String tips = content.getTips();
//        if (content.getDoc() != null) {
//            final String title = content.getDoc().getTitle();
//            final String desc = content.getDoc().getDesc();
//            final String imgUrl = content.getDoc().getImg();
//            final String url = content.getDoc().getUrl();
//            try {
//                final String group = coinTaskShareEntity.getGroup();
//                final String items = coinTaskShareEntity.getItems();
//                new ShareShiTuImageDialogBuilder(activity, tips, group,items,
//                        new ShareShiTuImageDialogBuilder.OnShareListener() {
//                            @Override
//                            public void onShare(int platform) {
//                                ShareAnalysisManager.addH5ShareTypeChooseEvent(platform, group, "", title, url);
//                                ShareHelper.share(activity, title, desc, url, imgUrl, platform, new
//                                        PlatformActionListener() {
//                                            @Override
//                                            public void onComplete(SocialPlatform platform, int i, HashMap<String, Object> hashMap) {
//                                                if (shareStateCallBack != null) {
//                                                    shareStateCallBack.onSuccess(coinTaskShareEntity.getGroup());
//                                                }
//                                            }
//
//                                            @Override
//                                            public void onError(SocialPlatform platform, int i, Throwable throwable) {
//                                                if (shareStateCallBack != null) {
//                                                    shareStateCallBack.onFailed(coinTaskShareEntity.getGroup());
//                                                }
//                                            }
//
//                                            @Override
//                                            public void onCancel(SocialPlatform platform, int i) {
//
//                                            }
//                                        });
//                            }
//                        }).create().show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }





    /**
     * 直接分享微信朋友圈
     *
     * @param activity
     * @param path
     */
    public void shareWechatMoment(final Activity activity, final String path, final String imageUrl) {
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                        ShareHelper.imageShare(activity, path, imageUrl, ShareHelper.SHARE_WX_MOMENT, new PlatformActionListener() {
                            @Override
                            public void onComplete(SocialPlatform platform, int i, HashMap<String, Object> hashMap) {
                                if (shareStateCallBack != null) {
                                    shareStateCallBack.onSuccess("");
                                }
                            }

                            @Override
                            public void onError(SocialPlatform platform, int i, Throwable throwable) {
                                if (shareStateCallBack != null) {
                                    shareStateCallBack.onFailed("");
                                }
                            }

                            @Override
                            public void onCancel(SocialPlatform platform, int i) {

                            }
                        });
                    }

            });
        }
    }

    /**
     * 直接分享微信好友
     *
     * @param activity
     * @param path
     */
    public void shareWechat(final Activity activity,   final String path, final String imageUrl) {
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            activity.runOnUiThread(() -> ShareHelper.imageShare(activity, path, imageUrl, ShareHelper.SHARE_WX_FRIEND, new PlatformActionListener() {
                @Override
                public void onComplete(SocialPlatform platform, int i, HashMap<String, Object> hashMap) {
                    if (shareStateCallBack != null) {
                        shareStateCallBack.onSuccess("");

                    }
                }

                @Override
                public void onError(SocialPlatform platform, int i, Throwable throwable) {
                    if (shareStateCallBack != null) {
                        shareStateCallBack.onFailed("");
                    }
                }

                @Override
                public void onCancel(SocialPlatform platform, int i) {

                }
            }));
        }
    }












    /**
     * 获取分享结果的回调
     */
    public interface ShareStateCallBack {

        void onSuccess(String group);

        void onFailed(String group);

    }

    public void setShareStateCallBackCallBack(ShareStateCallBack shareStateCallBack) {
        this.shareStateCallBack = shareStateCallBack;
    }

}

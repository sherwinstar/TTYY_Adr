package com.ushaqi.zhuishushenqi;

import android.app.AlertDialog;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jxjuwen.ttyy.SplashActivity;
import com.ushaqi.zhuishushenqi.module.baseweb.view.ZssqWebActivity;
import com.ushaqi.zhuishushenqi.util.ToastUtil;

/**
 * Created by mac on 2019/1/8.
 * <p>
 * 包含两个弹框的逻辑
 * 初识弹框和下一步的弹框
 * 比较简单没啥说的
 */


public class SplashProtocalManager {
    private static final int DIALOG_INIT = 1;
    private static final int DIALOG_NEXT = 1 << 1;

    private DialogStyle mDialogInitStyle, mDialogNextStyle;

    private Context mContext;

    private Runnable mRunnable;

    /**
     * 协议是否更新
     */
//    private boolean isAgreementUpdate;
    /**
     * 协议更新版本
     */
    private int updateAgreementVersion;

    private int showTimes = 0;

    public boolean isSameContext(Context context) {
        return context == mContext;
    }

    public void initShow(Runnable runnable) {
        this.mRunnable = runnable;
        getDialogStyle(DIALOG_INIT).show();
    }

    public SplashProtocalManager(Context mContext) {
        this.mContext = mContext;
    }

    public void setUpdateAgreementVersion(int version) {
        this.updateAgreementVersion = version;
    }


    private DialogStyle getDialogStyle(int value) {
        switch (value) {
            case DIALOG_INIT:
                if (mDialogInitStyle == null) {
                    mDialogInitStyle = new DialogInitStyle();
                }
                return mDialogInitStyle;
            case DIALOG_NEXT:
                if (mDialogNextStyle == null) {
                    mDialogNextStyle = new DialogNextStyle();
                }
                return mDialogNextStyle;
        }
        return null;
    }

    private class DialogInitStyle implements DialogStyle, View.OnClickListener {
        private AlertDialog mInitDialog;
        private View mRootView;

        public void initView() {
            mInitDialog = new AlertDialog.Builder(mContext, R.style.dialogNoBg).create();
            mRootView = LayoutInflater.from(mContext).inflate(R.layout.dialog_protocal_init, null);
            TextView txTitle = mRootView.findViewById(R.id.dialog_title);
            TextView txContent = mRootView.findViewById(R.id.dialog_content);
            txContent.setHighlightColor(mContext.getResources().getColor(android.R.color.transparent));
            txContent.setText("     感谢您使用天天有余！\n" +
                    "天天有余非常重视您的个人信息和隐私保护。根据国家相关法律规定和标准更新了");
            SpannableString clickString = new SpannableString("《天天有余APP用户协议》");
            clickString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                    mContext.startActivity(ZssqWebActivity.createNormalIntent(mContext,"用户协议", AppConstants.USER_PROTOCOL));

                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(mContext.getResources().getColor(R.color.text_red_EE));


                }
            }, 0, clickString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            txContent.append(clickString);
            txContent.append("和");
            SpannableString clickString2 = new SpannableString("《用户隐私政策》");
            clickString2.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    mContext.startActivity(ZssqWebActivity.createNormalIntent(mContext,"隐私保护政策", AppConstants.PRIVACY_PROTOCOL));

                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(mContext.getResources().getColor(R.color.text_red_EE));

                }
            }, 0, clickString2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            txContent.append(clickString2);
            txContent.append(",请您在使用前仔细阅读并了解所有条款，包括：");
            txContent.append("\n\n     为向您提供包括账户注册、商品购物、交易支付在内的基本功能，我们可能会基于具体业务场景收集您的个人信息；\n" +
                    "我们会基于您的授权来为您提供更好的购物服务，这些授权包括定位（为您精确推荐相关的商品）、设备信息（为保障账户、交易安全及商品推荐，获取包括IMEI，IMSI在内的设备标识符）、您有权拒绝或取消这些授权；\n" +
                    "我们会基于先进的技术和管理措施保护您个人信息的安全；\n" +
                    "未经您的同意，我们不会将您的个人信息共享给第三方；\n" +
                    "为向您提供更好的服务，您同意提供及时、详尽准确的个人资料，并授权同意我们使用。如您不同意我们收集您的该等必要信息，您将无法享受由我们提供的该等业务功能；\n" +
                    "您在享用天天有余优质服务的同时，授权并同意接受向您的电子邮件、手机、通信地址等发送商业信息，包括但不限于最新的有余产品信息、促销信息等。若您选择不接受有余提供的各类信息服务，您可以按照有余提供的相应设置拒绝该类信息服务。");
            txContent.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
            TextView tvConfirm = mRootView.findViewById(R.id.tv_dialog_confirm);
            TextView txCancel = mRootView.findViewById(R.id.tv_dialog_cancel);
            tvConfirm.setOnClickListener(this);
            txCancel.setOnClickListener(this);
            mInitDialog.setCancelable(false);
        }

        @Override
        public void show() {
            try {
                if (mInitDialog == null) {
                    initView();
                }
                mInitDialog.show();
                mInitDialog.setContentView(mRootView);
            } catch (Exception e) {
                e.getStackTrace();
            }
        }

        @Override
        public void dismiss() {
            try {
                mInitDialog.dismiss();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_dialog_confirm:
                    mInitDialog.dismiss();
                    if (mRunnable != null) {
                        mRunnable.run();
                    }
                    break;
                case R.id.tv_dialog_cancel:
                    mInitDialog.dismiss();
                    getDialogStyle(DIALOG_NEXT).show();
                    ToastUtil.show("同意后可继续使用");
                    break;
                default:
                    break;
            }
        }
    }

    private class DialogNextStyle implements DialogStyle, View.OnClickListener {
        private AlertDialog mNextDialog;
        private View mRootView;

        @Override
        public void initView() {
            mNextDialog = new AlertDialog.Builder(mContext, R.style.dialogNoBg).create();
            mRootView = LayoutInflater.from(mContext).inflate(R.layout.dialog_protocal_next, null);
            TextView txTitle = mRootView.findViewById(R.id.dialog_title);
            TextView txContent = mRootView.findViewById(R.id.dialog_content);
            TextView txConfirm = mRootView.findViewById(R.id.tv_dialog_confirm);
            TextView txCancel = mRootView.findViewById(R.id.tv_dialog_cancel);
//            if (isAgreementUpdate) {
//                txTitle.setText("神咩提醒");
//                txContent.setText("     为了更好地保护您的隐私和个人信息安全，需要您同意用户协议和隐私协议才能使用我们的产品和服务哦！");
//                txConfirm.setText("确定");
//            } else {
//
//            }
            if (showTimes == 1) {
                txTitle.setText("亲，要不在想想");
                txCancel.setText("退出应用");
            }
            txContent.setHighlightColor(mContext.getResources().getColor(android.R.color.transparent));
            txContent.setText("     根据相关法律规定，请您同意");
            SpannableString clickString = new SpannableString("《天天有余APP用户协议》");
            clickString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    mContext.startActivity(ZssqWebActivity.createNormalIntent(mContext,"用户协议", AppConstants.USER_PROTOCOL));

                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(mContext.getResources().getColor(R.color.text_black_1E));


                }
            }, 0, clickString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            txContent.append(clickString);
            txContent.append("和");
            SpannableString clickString2 = new SpannableString("《用户隐私政策》");
            clickString2.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                    mContext.startActivity(ZssqWebActivity.createNormalIntent(mContext,"隐私保护政策", AppConstants.PRIVACY_PROTOCOL));


                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(mContext.getResources().getColor(R.color.text_black_1E));

                }
            }, 0, clickString2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            txContent.append(clickString2);
            txContent.append("后再开始使用我们的应用服务。");
            txContent.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件

            TextView tvConfirm = mRootView.findViewById(R.id.tv_dialog_confirm);
            TextView tvCancel = mRootView.findViewById(R.id.tv_dialog_cancel);
            tvConfirm.setOnClickListener(this);
            tvCancel.setOnClickListener(this);
            mNextDialog.setCancelable(false);
        }

        @Override
        public void show() {
            try {
                if (mNextDialog == null) {
                    initView();
                }
                mNextDialog.show();
                mNextDialog.setContentView(mRootView);
            } catch (Exception e) {
                e.getStackTrace();
            }
        }

        @Override
        public void dismiss() {
            try {
                mNextDialog.dismiss();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }


        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.tv_dialog_confirm) {//                    mNextDialog.dismiss();
//                    getDialogStyle(DIALOG_INIT).show();
                mNextDialog.dismiss();
                if (mRunnable != null) {
                    mRunnable.run();
                }
                //  同意权限后mob收集操作
//                    MobSDK.submitPolicyGrantResult(true,null);
            } else if (id == R.id.tv_dialog_cancel) {
                try {
                    showTimes++;
                    if (showTimes > 1) {
                        mNextDialog.dismiss();
                        ((SplashActivity) mContext).finish();
                    } else {
                        mNextDialog.dismiss();
                        mDialogNextStyle = null;
                        getDialogStyle(DIALOG_NEXT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

package com.jxjuwen.ttyy;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.githang.statusbar.StatusBarCompat;
import com.jxjuwen.ttyy.base.BaseActivity;
import com.ushaqi.zhuishushenqi.HttpExceptionMessage;
import com.ushaqi.zhuishushenqi.R;
import com.ushaqi.zhuishushenqi.httpcore.callback.ClientCallBack;
import com.ushaqi.zhuishushenqi.httpcore.requester.AccountRequester;
import com.ushaqi.zhuishushenqi.local.UserHelper;
import com.ushaqi.zhuishushenqi.local.UserPropertyHelper;
import com.ushaqi.zhuishushenqi.model.BaseModel;
import com.ushaqi.zhuishushenqi.model.User;
import com.ushaqi.zhuishushenqi.sensors.SensorsPageEventHelper;
import com.ushaqi.zhuishushenqi.util.LogUtil;
import com.ushaqi.zhuishushenqi.util.ToastUtil;

/**
 * 提现
 *
 * @author zengcheng
 * create at 2021/5/17 下午1:37
 */
public class WithDrawActy extends BaseActivity implements View.OnClickListener {
    private LinearLayout mLlUnbindZfbHint;

    private EditText mEtZfbAccount;
    private EditText mEtName;
    private EditText mEtIdNum;
    private Button mBtnSubmit;
    private boolean isInit;
    private int count;

    @Override
    protected int getLayout() {
        return R.layout.acty_withdraw;
    }

    public static Intent createIntent(Activity activity) {
        return new Intent(activity, WithDrawActy.class);

    }

    @Override
    protected void initEventAndData() {
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white));
        mToolBar.setTitle("支付宝提现设置");
        mLlUnbindZfbHint = (LinearLayout) findViewById(R.id.ll_unbind_zfb_hint);
        mEtZfbAccount = (EditText) findViewById(R.id.et_zfb_account);
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtIdNum = (EditText) findViewById(R.id.et_id_num);
//        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
        mBtnSubmit.setOnClickListener(this);
        mEtZfbAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                setBtnSubmitEnable();


            }
        });
        mEtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                setBtnSubmitEnable();
            }
        });
        mEtIdNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int count) {
                if (count == 1) {
                    int length = s.toString().length();
                    if (length == 6 || length == 11 || length == 16) {
                        mEtIdNum.setText(s + " ");
                        mEtIdNum.setSelection(mEtIdNum.getText().toString().length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                setBtnSubmitEnable();
            }
        });

        if (UserHelper.getInstance().isLogin()) {
            User u = UserHelper.getInstance().getUser();
            if (u != null && u.isBindingAliPay()) {
                isInit = true;
                mLlUnbindZfbHint.setVisibility(View.INVISIBLE);
                mEtZfbAccount.setText(u.getAliPayAccountUserName());
                mEtZfbAccount.setSelection(u.getAliPayAccountUserName().length());
                mEtName.setText(u.getIdCardName());
                mEtIdNum.setText(u.getIdCardNum());
            }
        }
        SensorsPageEventHelper.addZSPageShowEvent(null, "支付宝设置");
    }

    @Override
    public void onClick(View view) {
        SensorsPageEventHelper.addZSBtnClickEvent(null, "支付宝设置", "确认提交");
        String account = mEtZfbAccount.getText().toString().trim();
        String realName = mEtName.getText().toString().trim();
        String idCardNum = mEtIdNum.getText().toString().replace(" ", "").trim();
        AccountRequester.getInstance().getApi().bindAliPay(UserHelper.getInstance().getToken(), account, idCardNum, realName).enqueue(new ClientCallBack<BaseModel>() {

            @Override
            public void onSuccess(BaseModel response) {
                if (response != null) {
                    String msg = response.getMsg();
                    ToastUtil.show(msg);
                    if (response.isOk()) {
                        User u = UserHelper.getInstance().getUser();
                        u.setAliPayAccountUserName(account);
                        u.setIdCardNum(idCardNum);
                        u.setIdCardName(realName);
                        UserPropertyHelper.getInstance().setProperty("user.idCardNum", idCardNum);
                        UserPropertyHelper.getInstance().setProperty("user.idCardName", realName);
                        UserPropertyHelper.getInstance().setProperty("user.aliPayAccount", account);
                        finish();
                    }
                }

            }

            @Override
            public void onFailed(HttpExceptionMessage exceptionMessage) {
                ToastUtil.show("绑定失败," + exceptionMessage.getExceptionInfo());
            }
        });


    }

    private void setBtnSubmitEnable() {
        count++;
        LogUtil.e(TAG, "count:" + count + ",isInit:" + isInit);
        if (count == 3 && isInit) {
            mBtnSubmit.setEnabled(false);
            isInit = false;
            return;
        }

        boolean zfbAccountValid = !TextUtils.isEmpty(mEtZfbAccount.getText().toString());
        boolean etNameValid = !TextUtils.isEmpty(mEtName.getText().toString());
        String etIdNum = mEtIdNum.getText().toString().replace(" ", "");
        boolean etIdNumValid = !TextUtils.isEmpty(etIdNum) && etIdNum.length() == 18;
        mBtnSubmit.setEnabled(zfbAccountValid && etNameValid && etIdNumValid);
    }


}

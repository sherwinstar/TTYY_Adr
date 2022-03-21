package com.jxjuwen.ttyy;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.jxjuwen.ttyy.base.BaseActivity;
import com.squareup.otto.Bus;
import com.ushaqi.zhuishushenqi.HttpExceptionMessage;
import com.ushaqi.zhuishushenqi.R;
import com.ushaqi.zhuishushenqi.event.BindInviteCodeEvent;
import com.ushaqi.zhuishushenqi.event.BusProvider;
import com.ushaqi.zhuishushenqi.httpcore.callback.ClientCallBack;
import com.ushaqi.zhuishushenqi.httpcore.requester.AccountRequester;
import com.ushaqi.zhuishushenqi.httpcore.requester.CommonRequester;
import com.ushaqi.zhuishushenqi.local.UserHelper;
import com.ushaqi.zhuishushenqi.local.UserPropertyHelper;
import com.ushaqi.zhuishushenqi.model.BaseModel;
import com.ushaqi.zhuishushenqi.model.User;
import com.ushaqi.zhuishushenqi.util.ToastUtil;

/**
   *绑定邀请码 
   *@author  zengcheng
   *create at 2021/6/10 下午1:33
*/
public  class InviteCodeActy extends BaseActivity implements View.OnClickListener{
    private EditText mEtInviteCode;
    private Button mBtnSubmit;
    private TextView mTvJump;
    @Override
    protected int getLayout() {
        return R.layout.acty_invite_code;
    }
    public static Intent createIntent(Activity activity){
        return   new Intent(activity,InviteCodeActy.class);

    }
    @Override
    protected void initEventAndData() {
        mToolBar.setTitle("");
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white));
        mEtInviteCode = (EditText) findViewById(R.id.et_invite_code);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
        mTvJump = (TextView) findViewById(R.id.tv_jump);
        mBtnSubmit.setOnClickListener(this);
        mTvJump.setOnClickListener(this);

        mEtInviteCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean etInviteCodeValid =!TextUtils.isEmpty(mEtInviteCode.getText().toString());
                mBtnSubmit.setEnabled(etInviteCodeValid);
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
             bindInviteCode(mEtInviteCode.getText().toString().trim());
                break;
            case R.id.tv_jump:
                finish();
                break;
            default:
                break;
        }

    }

    private void bindInviteCode(String code){
        AccountRequester.getInstance().getApi().bindInviteCode(UserHelper.getInstance().getToken(),code).enqueue(new ClientCallBack<BaseModel>() {

            @Override
            public void onSuccess(BaseModel response) {
                if(response!=null){
                    if(response.isOk()){
                        User u= UserHelper.getInstance().getUser();
                        u.setFpromoterId(code);
                        UserPropertyHelper.getInstance().setProperty("user.fpromoterId", code);
                        ToastUtil.show("邀请码绑定成功");
                        finish();
                        BusProvider.getInstance().post(new BindInviteCodeEvent());
                    }else {
                        ToastUtil.show(response.getMsg());
                    }
                }

            }

            @Override
            public void onFailed(HttpExceptionMessage exceptionMessage) {
                ToastUtil.show("绑定失败,"+exceptionMessage.getExceptionInfo());
            }
        });
    }



}

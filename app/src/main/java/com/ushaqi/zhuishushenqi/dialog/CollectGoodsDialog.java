package com.ushaqi.zhuishushenqi.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.ushaqi.zhuishushenqi.HttpExceptionMessage;
import com.ushaqi.zhuishushenqi.R;
import com.ushaqi.zhuishushenqi.httpcore.callback.ClientCallBack;
import com.ushaqi.zhuishushenqi.httpcore.requester.AccountRequester;
import com.ushaqi.zhuishushenqi.local.UserHelper;
import com.ushaqi.zhuishushenqi.model.BaseModel;
import com.ushaqi.zhuishushenqi.model.CommandGoodsBean;
import com.ushaqi.zhuishushenqi.util.ToastUtil;

/**
 * @author chengwencan
 * @date 2021/7/12
 * Describe：
 */
public class CollectGoodsDialog extends Dialog implements View.OnClickListener{
    private TextView mTvUpdateConFirm;
    private ImageView mIvUpdateCancel;
    private Context context;
    private   CommandGoodsBean.CommandGoods commandGoods;

    public CollectGoodsDialog(Context context, CommandGoodsBean.CommandGoods commandGoods) {
        super(context);
        this.context=context;
        this.commandGoods=commandGoods;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_compare_order);
        mTvUpdateConFirm=findViewById(R.id.tv_collect_confirm);
        mIvUpdateCancel= findViewById(R.id.iv_collect_cancel);

        mTvUpdateConFirm.setOnClickListener(this);
        mIvUpdateCancel.setOnClickListener(this);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_collect_confirm){
            if (isShowing()) {
                dismiss();
            }
            collectGoods();

        }else if (v.getId()==R.id.iv_collect_cancel){
            if (isShowing()) {
                dismiss();
            }
        }

    }

    private void collectGoods(){
        if (commandGoods==null){
            return;
        }
        AccountRequester.getInstance().getApi().collectGoodsAdd(UserHelper.getInstance().getToken(),
                commandGoods.getsType(),
                commandGoods.getMerchantTitle(),
                commandGoods.getPayUser(),
                commandGoods.getGoodsImgUrl(),
                commandGoods.getGoodsTitle(),
                commandGoods.getGoodsId(),
                commandGoods.getCoupons(),
                Double.parseDouble(commandGoods.getSaveMoney()),
                commandGoods.getPrice(),commandGoods.getOriginalprice(),true).enqueue(new ClientCallBack<BaseModel>() {
            @Override
            public void onSuccess(BaseModel response) {
                if (response!=null){
                    ToastUtil.show(response.getMsg());
                }


            }

            @Override
            public void onFailed(HttpExceptionMessage exceptionMessage) {
                ToastUtil.show("必不会不必"+exceptionMessage.getMessage());
            }
        });
    }


}

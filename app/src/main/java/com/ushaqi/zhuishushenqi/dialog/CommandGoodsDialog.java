package com.ushaqi.zhuishushenqi.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jxjuwen.ttyy.HomeActy;
import com.jxjuwen.ttyy.LoginActy;
import com.squareup.otto.Subscribe;
import com.ushaqi.zhuishushenqi.HttpExceptionMessage;
import com.ushaqi.zhuishushenqi.MyApplication;
import com.ushaqi.zhuishushenqi.R;
import com.ushaqi.zhuishushenqi.event.BusProvider;
import com.ushaqi.zhuishushenqi.event.LoginEvent;
import com.ushaqi.zhuishushenqi.helper.OpenProductDetailHelper;
import com.ushaqi.zhuishushenqi.httpcore.HttpUrlProvider;
import com.ushaqi.zhuishushenqi.httpcore.callback.ClientCallBack;
import com.ushaqi.zhuishushenqi.httpcore.requester.GoodsRequester;
import com.ushaqi.zhuishushenqi.local.UserHelper;
import com.ushaqi.zhuishushenqi.model.CommandGoodsBean;
import com.ushaqi.zhuishushenqi.module.baseweb.view.ZssqWebActivity;
import com.ushaqi.zhuishushenqi.sensors.ErrorAnalysisManager;
import com.ushaqi.zhuishushenqi.sensors.SensorsPageEventHelper;
import com.ushaqi.zhuishushenqi.util.DensityUtil;
import com.ushaqi.zhuishushenqi.util.LogUtil;
import com.ushaqi.zhuishushenqi.widget.NewCoverView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


/**
 * 口令商品dialoa
 *
 * @author zengcheng
 * create at 2021/5/27 下午4:59
 */
public class CommandGoodsDialog extends Dialog implements View.OnClickListener {

    private static final String TAG ="CommandGoodsDialog" ;
    private ImageView mIvClose;
    private NewCoverView mIcGoodsPic;
    private ImageView mIvChannelFlag;
    private TextView mTvGoodsName;
    private TextView mTvFanliAmount,mTvNotFound;
    private TextView mTvDiscountPrice,mTvOriginalPrice,mTvCoupons;
    private TextView mTvLowestPrice;
    private TextView mTvGotoBuy,mTvSearch;
    private RelativeLayout mRlSearch,mRlGoodsContainer;
    private  LinearLayout mLlBuyAction;
    /**
     * 上下文  （dialog必须与Activity 绑定）
     */
    private final  Activity mActivity;
    private final String type;
    private String goodsDetailUrl;
    private final String command;
    private final  CommandGoodsBean.CommandGoods commandGoods;
    private final  String category1;
/*    链接上拼activePlatform:
            4  全网比价
           2 淘宝
       3 拼多多
      1 京东
*/
    private final String wholeLowestPriceUrl = HttpUrlProvider.getH5Url() + "/ttyy/search?search_source=%s&val=%s&activeSortType=%s&activeSortValue=0&activePlatform=%s";

   public CommandGoodsDialog(Activity activity,String category1,String type, String command, CommandGoodsBean.CommandGoods commandGoods) {
        super(activity);
        this.mActivity = activity;
        this.command = command;
        this.commandGoods = commandGoods;
        this.type=type;
        this.category1=category1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//清除title
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_comand_goods);
        mIvClose = (ImageView) findViewById(R.id.iv_close);
        mIcGoodsPic = (NewCoverView) findViewById(R.id.ic_goods_pic);
        mIvChannelFlag = (ImageView) findViewById(R.id.iv_channel_flag);
        mTvGoodsName = (TextView) findViewById(R.id.tv_goods_name);
        mTvFanliAmount = (TextView) findViewById(R.id.tv_fanli_amount);
        mTvCoupons = (TextView) findViewById(R.id.tv_coupons);
        mTvNotFound = (TextView) findViewById(R.id.tv_not_found);
        mRlGoodsContainer = (RelativeLayout) findViewById(R.id.rl_goods_container);
        mLlBuyAction = (LinearLayout) findViewById(R.id.ll_buy_action);
        mTvDiscountPrice = (TextView) findViewById(R.id.tv_discount_price);
        mTvOriginalPrice = (TextView) findViewById(R.id.tv_original_price);
        mTvLowestPrice = (TextView) findViewById(R.id.tv_lowest_price);
        mTvGotoBuy = (TextView) findViewById(R.id.tv_goto_buy);
        mRlSearch = (RelativeLayout) findViewById(R.id.rl_search);
        mTvSearch = (TextView) findViewById(R.id.tv_search);
        mTvLowestPrice.setOnClickListener(this);
        mTvGotoBuy.setOnClickListener(this);
        mIvClose.setOnClickListener(this);
        if(commandGoods==null){
            mRlGoodsContainer.setVisibility(View.GONE);
            mTvNotFound.setVisibility(View.VISIBLE);
            mTvNotFound.setText(command);
            mLlBuyAction.setVisibility(View.GONE);
            mRlSearch.setVisibility(View.VISIBLE);
            mRlSearch.setOnClickListener(this);
            int leftResId=R.drawable.ic_taobao_white;
            String name=" 搜淘宝/天猫";
            if(TextUtils.equals(type,"3")){
              leftResId=R.drawable.ic_pdd_white;
                 name=" 搜拼多多";
            }else if(TextUtils.equals(type,"1")){
                leftResId=R.drawable.ic_jd_white;
                name=" 搜京东";
            }
            mTvSearch.setCompoundDrawablesWithIntrinsicBounds(leftResId,0,0,0);
            mTvSearch.setText(name);
            return;
        }

        this.goodsDetailUrl=commandGoods.getJumpUrl();
        mIcGoodsPic.setImageUrl(commandGoods.getGoodsImgUrl(),0);
        if(TextUtils.equals(commandGoods.getsType(),"taobao")){
            mIvChannelFlag.setImageResource(R.drawable.ic_taobao);
        }else if(TextUtils.equals(commandGoods.getsType(),"pdd")){
            mIvChannelFlag.setImageResource(R.drawable.ic_pdd);
        }else if(TextUtils.equals(commandGoods.getsType(),"jd")) {
            mIvChannelFlag.setImageResource(R.drawable.ic_jd);
        }

        mTvGoodsName.setText(commandGoods.getGoodsTitle());
        mTvFanliAmount.setText("预计赚 ¥");
        addSpan(mTvFanliAmount,commandGoods.getSaveMoney(),14);
        mTvDiscountPrice.setText("到手约 ¥");
        addSpan(mTvDiscountPrice,commandGoods.getPrice()+"",19);
        mTvOriginalPrice.setText("¥".concat(commandGoods.getOriginalprice()+""));
        mTvOriginalPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        mTvOriginalPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
        if(commandGoods.getCoupons()>0){
            mTvGotoBuy.setText("领取优惠");
            mTvCoupons.setVisibility(View.VISIBLE);
            mTvCoupons.setText("券 ¥");
            addSpan(mTvCoupons,commandGoods.getCoupons()+"",14);
           RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) mTvFanliAmount.getLayoutParams();
           params.setMarginStart(DensityUtil.dp2px(6));
        }else {
            mTvGotoBuy.setText("任性购买");
        }

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        BusProvider.getInstance().register(this);
    }

    @Subscribe
    public void onLoginEvent(LoginEvent event) {
        getCommandGoodsInfo(command);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_goto_buy) {
            if (!UserHelper.getInstance().isLogin()) {
                mActivity.startActivity(LoginActy.createIntent(mActivity));
                return;
            }
            if (isShowing()) {
                dismiss();
            }
            if ("3".equals(type)){
                CollectGoodsDialog collectGoodsDialog=new CollectGoodsDialog(mActivity,commandGoods);
                collectGoodsDialog.show();
            }else {
                OpenProductDetailHelper.getInstance().openProductDetail(mActivity, commandGoods.getsType(), goodsDetailUrl, commandGoods.getGoodsId(), null);
                SensorsPageEventHelper.addZSBtnClickEvent(null,category1,"口令商品弹窗",mTvGotoBuy.getText().toString());
            }


        } else if (v.getId() == R.id.iv_close) {
            if (isShowing()) {
                dismiss();
            }
        } else if (v.getId() == R.id.tv_lowest_price) {
            if (isShowing()) {
                dismiss();
            }
            try {
                if ("3".equals(type)){
                    CollectGoodsDialog collectGoodsDialog=new CollectGoodsDialog(mActivity,commandGoods);
                    collectGoodsDialog.show();
                }else {
                    String  jumpUrl = String.format(wholeLowestPriceUrl, HomeActy.getGoodsTitle(),URLEncoder.encode(command,"UTF-8"),"price","4");
                    Intent intent = ZssqWebActivity.createIntent(mActivity, "搜索口令商品", jumpUrl);
                    mActivity.startActivity(intent);
                    SensorsPageEventHelper.addZSBtnClickEvent(null,category1,"口令商品弹窗","全网最低价");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if(v.getId()==R.id.rl_search){
            if (isShowing()) {
                dismiss();
            }
            try {
                String  jumpUrl = String.format(wholeLowestPriceUrl,HomeActy.getGoodsTitle(),URLEncoder.encode(command,"UTF-8"), "mult",type);
                Intent intent = ZssqWebActivity.createIntent(mActivity, "搜索口令商品", jumpUrl);
                mActivity.startActivity(intent);
                SensorsPageEventHelper.addZSBtnClickEvent(null,category1,"口令商品弹窗",mTvSearch.getText().toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        try {
            BusProvider.getInstance().unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCommandGoodsInfo(String command) {
        ClientCallBack<CommandGoodsBean> clientCallBack = new ClientCallBack<CommandGoodsBean>() {
            @Override
            public void onSuccess(CommandGoodsBean response) {
                if (response == null) {
                    return;
                }
                if (response.isOk()) {
                    List<CommandGoodsBean.CommandGoods> data = response.getData();
                    if (data != null && data.size() > 0) {
                        CommandGoodsBean.CommandGoods commandGoods = data.get(0);
                        if (commandGoods != null) {
                            goodsDetailUrl = commandGoods.getJumpUrl();
                        }
                    }
                }

            }

            @Override
            public void onFailed(HttpExceptionMessage exceptionMessage) {
                LogUtil.e(TAG,"error"+exceptionMessage.getExceptionInfo());
                String apiUrl=HttpUrlProvider.getServerRoot().concat("/shopping/Goods/SearchTkl");
                String errorCode= exceptionMessage.getCode();
                ErrorAnalysisManager.zssqApiERRorEvent("淘口令弹窗失败",apiUrl, ErrorAnalysisManager.getErrorType(errorCode),errorCode,exceptionMessage.getExceptionInfo());
            }
        };
        GoodsRequester.getInstance().getApi().getTaoCommandGoods( UserHelper.getInstance().getToken(),command,type).enqueue(clientCallBack);
    }

    public static void addSpan(TextView textView, String text, int size) {
        SpannableString spanString = new SpannableString(text);
        AbsoluteSizeSpan span2 = new AbsoluteSizeSpan(DensityUtil.dp2px(MyApplication.getInstance(), size));
        spanString.setSpan(span2, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(spanString);
    }
}


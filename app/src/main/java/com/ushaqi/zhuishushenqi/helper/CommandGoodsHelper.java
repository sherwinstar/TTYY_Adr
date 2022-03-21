package com.ushaqi.zhuishushenqi.helper;

import android.app.Activity;

import com.ushaqi.zhuishushenqi.HttpExceptionMessage;
import com.ushaqi.zhuishushenqi.dialog.CommandGoodsDialog;
import com.ushaqi.zhuishushenqi.httpcore.HttpUrlProvider;
import com.ushaqi.zhuishushenqi.httpcore.callback.ClientCallBack;
import com.ushaqi.zhuishushenqi.httpcore.requester.GoodsRequester;
import com.ushaqi.zhuishushenqi.local.UserHelper;
import com.ushaqi.zhuishushenqi.model.CommandGoodsBean;
import com.ushaqi.zhuishushenqi.sensors.ErrorAnalysisManager;
import com.ushaqi.zhuishushenqi.sensors.SensorsPageEventHelper;
import com.ushaqi.zhuishushenqi.util.AppUtils;
import com.ushaqi.zhuishushenqi.util.ListUtils;
import com.ushaqi.zhuishushenqi.util.LogUtil;

import java.util.List;

/**
   * 口令商品辅助类
   *@author  zengcheng
   *create at 2021/6/24 上午11:26
*/
public class CommandGoodsHelper {

    private static final String TAG = "CommandGoodsHelper";
    private static CommandGoodsHelper sInstance;
    private CommandGoodsDialog commandGoodsDialog;

    public CommandGoodsDialog getCommandGoodsDialog() {
        return commandGoodsDialog;
    }
    public void setDialogNull(){
        commandGoodsDialog=null;
    }

    private CommandGoodsHelper() {
    }


    public static CommandGoodsHelper getInstance() {
        if (sInstance == null) {
            synchronized (CommandGoodsHelper.class) {
                if (sInstance == null) {
                    sInstance = new CommandGoodsHelper();
                }
            }
        }
        return sInstance;
    }
    public  void  checkCommandGoods(Activity activity,String category1){
//        HandlerHelper.postDelay(() -> {
//            String command = AppUtils.getClipboardContent(MyApplication.getInstance());
//            LogUtil.e(TAG,"txt:"+command);
//
//
//            if (!TextUtils.isEmpty(command)) {//是淘宝/pdd/jd 商品链接
//                if (command.contains("天天有余")){
//                    return;
//                }
//                Uri parse = Uri.parse(command);
//                String host= parse.getHost();
//                LogUtil.e(TAG,"host:"+host);
//                if(TextUtils.isEmpty(host)){
//                    return;
//                }
//                String type="";
//                if(host.contains("tb.cn")){
//                    type="2";
//                }else if(host.contains("jd.com")){
//                    type="1";
//                }else if(host.contains("yangkeduo.com")){
//                    type="3";
//                }
//                if(!TextUtils.isEmpty(type)){
//                    AppUtils.clearClipboardContent(MyApplication.getInstance());
//                    getCommandGoodsInfo(activity,command,type,category1);
//                }
//
//            }
//        },300);

    }

    private void getCommandGoodsInfo(Activity activity,final String command,final String type,String category1){
        ClientCallBack<CommandGoodsBean> clientCallBack = new ClientCallBack<CommandGoodsBean>() {
            @Override
            public void onSuccess(CommandGoodsBean response) {

                try {
                    if(AppUtils.isActivityInValid(activity)||response==null||!response.isOk()){
                        return;
                    }
                    List<CommandGoodsBean.CommandGoods> data = response.getData();
                    CommandGoodsBean.CommandGoods commandGoods=null;
                    if(!ListUtils.isEmpty(data)){
                        commandGoods = data.get(0);
                    }



                    if(commandGoodsDialog!=null&&commandGoodsDialog.isShowing()){
                        commandGoodsDialog.dismiss();
                    }
                    commandGoodsDialog=new CommandGoodsDialog(activity,category1,type,command,commandGoods);
                    commandGoodsDialog.show();
                    SensorsPageEventHelper.addZSKeyItemExposureEvent(null,category1,"口令商品弹窗");
                } catch (Exception e) {
                    e.printStackTrace();

                }

            }
            @Override
            public void onFailed(HttpExceptionMessage exceptionMessage) {
                LogUtil.e(TAG,"error"+exceptionMessage.getExceptionInfo());
                String apiUrl= HttpUrlProvider.getServerRoot().concat("/shopping/Goods/SearchTkl");
                String errorCode= exceptionMessage.getCode();
                ErrorAnalysisManager.zssqApiERRorEvent("淘口令弹窗失败",apiUrl, ErrorAnalysisManager.getErrorType(errorCode),errorCode,exceptionMessage.getExceptionInfo());
            }
        };
        GoodsRequester.getInstance().getApi().getTaoCommandGoods(UserHelper.getInstance().getToken(),command,type).enqueue(clientCallBack);

    }
}

package com.ushaqi.zhuishushenqi.util.miit;

import android.content.Context;

import com.bun.miitmdid.core.ErrorCode;
import com.bun.miitmdid.core.MdidSdkHelper;
import com.bun.miitmdid.interfaces.IIdentifierListener;
import com.bun.miitmdid.interfaces.IdSupplier;
import com.ushaqi.zhuishushenqi.util.LogUtil;

/**
 *
 * @author zheng
 * @date 2019/8/22
 */
public class MiitHelper implements IIdentifierListener {
    /**
     * 匿名设备标识符 (最长64位)返回空字符串表示不支持，异
     * 常状态包括网络异常、 appid 异常、应用异常等
     *
     */
    public static final String IDENTIFIER_OAID = "OAID";
    /**
     * 应用匿名设备标识符
     * 设备唯一标识符最长64位，返回空字符串表示不支持
     * 异常状态包括网络异常、appid异常、应用异常
     */
    public static final String IDENTIFIER_UDID = "UDID";
    /**
     * 开发者匿名设备标识符
     * 开发者匿名设备标识符最长64 位，返回空字符串表示不支持
     * 异常状态包括网络异常、appid 异常、应用异常等
     * 初次调用时生成，生成需要访问网络
     */
    public static final String IDENTIFIER_VAID = "VAID";
    /**
     * 应用匿名设备标识符 最长64位，返回空字符串表示不支持
     * 异常状态包括网络异常、appid 异常、应用异常等
     * 初次调用时生成，生成需要访问网络
     */
    public static final String IDENTIFIER_AAID = "AAID";

    private static final String TAG = MiitHelper.class.getSimpleName();

    /**
     * 获取设备标识符
     *
     * @param context
     * @param updater
     */
    public static void getMobileDeviceIdentifier(final Context context, final AppIdsUpdater updater) {
        try {
            new MiitHelper(updater).getDeviceIds(context);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private AppIdsUpdater _listener;

    public MiitHelper(AppIdsUpdater callback) {
        _listener = callback;
    }

    /**
     *
     * @param cxt
     */
    public void getDeviceIds(Context cxt) {
        long timeb = System.currentTimeMillis();
        int nres = CallFromReflect(cxt);
//        int nres =DirectCall(cxt);
        long timee = System.currentTimeMillis();
        long offset = timee - timeb;


        if (nres == ErrorCode.INIT_ERROR_DEVICE_NOSUPPORT) {
            /** 不支持的设备 **/
            LogUtil.d(TAG, "device no support");
        } else if (nres == ErrorCode.INIT_ERROR_LOAD_CONFIGFILE) {
            /** 加载配置文件出错 **/
            LogUtil.d(TAG, "load configfile error");
        } else if (nres == ErrorCode.INIT_ERROR_MANUFACTURER_NOSUPPORT) {
            /** 不支持的设备厂商 **/
            LogUtil.d(TAG, "manufacturer no support");
        } else if (nres == ErrorCode.INIT_ERROR_RESULT_DELAY) {
            /** 获取接口是异步的，结果会在回调中返回，回调执行的回调可能在工作线程 **/
            LogUtil.d(TAG, "result delay");
        } else if (nres == ErrorCode.INIT_HELPER_CALL_ERROR) {
            /** 反射调用出错 **/
            LogUtil.d(TAG, "call error");
        }

        LogUtil.d(TAG, "return value: " + nres);
    }

    /**
     * 通过反射调用，解决android 9以后的类加载升级，导至找不到so中的方法
     *
     * @param cxt
     * @return
     */
    private int CallFromReflect(Context cxt) {
        return MdidSdkHelper.InitSdk(cxt, true, this);
    }

    /**
     *
     * @param isSupport  是否支持补充设备标识符获取
     * @param _supplier
     */
    @Override
    public void OnSupport(boolean isSupport, IdSupplier _supplier) {
        LogUtil.d(TAG, "OnSupport isSupport：" + isSupport + ", IdSupplier:" + (_supplier != null));
        if (_supplier == null) {
            return;
        }

        final String oaid = _supplier.getOAID();
        final String vaid = _supplier.getVAID();
        final String aaid = _supplier.getAAID();
        final String udid = "";

        if (LogUtil.sDebuggable) {
            StringBuilder builder = new StringBuilder();
            builder.append("support: ").append(isSupport).append("\n");
            builder.append("UDID: ").append(udid).append("\n");
            builder.append("OAID: ").append(oaid).append("\n");
            builder.append("VAID: ").append(vaid).append("\n");
            builder.append("AAID: ").append(aaid).append("\n");
            String idsText = builder.toString();
            LogUtil.d(TAG, "OnSupport ids:" + idsText);
        }

        if (_listener != null) {
            _listener.OnIdsAvalid(oaid, udid, vaid, aaid);
        }
    }

    public interface AppIdsUpdater {
        /**
         *
         * @param oaid
         * @param udid
         * @param vaid
         * @param aaid
         */
        void OnIdsAvalid(final String oaid, final String udid, final String vaid, final String aaid);
    }

}

package com.ushaqi.zhuishushenqi.permission;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;


import com.ushaqi.zhuishushenqi.repository.GlobalPreference;
import com.ushaqi.zhuishushenqi.util.AppHelper;
import com.ushaqi.zhuishushenqi.util.LogUtil;
import com.ushaqi.zhuishushenqi.util.ToastUtil;

import com.ushaqi.zhuishushenqi.R;

/**
 * 系统权限辅助类
 *
 * @author Andy.zhang
 * @date 2019/12/24
 */
public final class SysPermissionHelper {
    private static final String TAG = SysPermissionHelper.class.getSimpleName();
    /**
     * 电话权限
     */
    public static final String PERMISSION_PHONE = Manifest.permission.READ_PHONE_STATE;
    public static final int PHONE_REQUEST_CODE = 2000;
    /**
     * 电话权限弹窗开关(0: 关 1：开）
     */
    public static final String PHONE_PERMISSION_REQUST_DIALOG_SWITCH = "phone_permission_request_dialog_switch";
    public static final String PERMISSION_FIRST_REQUEST = "_FIRST_REQUEST";

    /**
     *
     * @param activity
     * @param requestCode
     * @return
     */
//    public static boolean requestAllFilesAccessPermission(Activity activity, int requestCode) {
//        try {
//            String packageName = activity.getPackageName();
//            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
//            intent.setData(Uri.parse("package:" + packageName));
//            activity.startActivityForResult(intent, requestCode);
//            return true;
//        } catch (Exception e) {
//        }
//        return false;
//    }

    /**
     * 是否是第一次请求权限
     *
     * @param permission
     * @return
     */
    public static boolean isFirstRequestPermission(final String permission) {
        return GlobalPreference.getInstance().getBoolean(permission + PERMISSION_FIRST_REQUEST, true);
    }

    /**
     *
     * @param permission
     */
    public static void saveFirstRequestPermission(final String  permission, final boolean isFirst) {
        GlobalPreference.getInstance().saveBoolean(permission + PERMISSION_FIRST_REQUEST, isFirst);
    }

    /**
     * 系统shouldShowRequestPermissionRationale方法是否需要向用户解释为何需要申请该权限，
     * 返回值：
     * 1.当首次申请权限时，该方法返回false
     * 2.申请了用户拒绝了, 返回true
     * 3.用户选择了拒绝并且不再提示, 返回false
     * 4.已经允许了, 返回false
     *
     * @param activity
     * @param permission
     * @return
     */
    public static boolean shouldShowRequestPermissionRationale(final Activity activity, final String permission) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M&&Build.VERSION.SDK_INT<=29) {
            return isFirstRequestPermission(permission) || activity.shouldShowRequestPermissionRationale(permission);
        }

        return false;
    }

    /**
     * 申请系统权限
     *
     * @param activity
     * @param requestCode
     * @param permissions
     */
    @TargetApi(23)
    public static boolean requestPermission(final Activity activity, final int requestCode, String... permissions) {
        if (permissions != null && permissions.length > 0) {
            try {
                final int length = permissions.length;
                final String[] permissionArray = new String[length];
                for (int i = 0; i < length; i++) {
                    permissionArray[i] = permissions[i];
                }

                activity.requestPermissions(permissionArray, requestCode);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * 是否拒绝权限了
     *
     * @param result
     * @return
     */
    public static boolean isPermissionDenied(final int result) {
        return (PackageManager.PERMISSION_DENIED == result);
    }


    /**
     * 是否有指定的权限
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean hasPermission(final Context context, String... permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (permissions != null && permissions.length > 0) {
            for (String permission: permissions) {
                if(ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                   return false;
                }
            }
        }

        return true;
    }

    /**
     * 跳过电话权限
     *
     * @param context
     * @return
     */
    public static boolean skipPhonePermission(final Context context) {
        return hasPermission(context, PERMISSION_PHONE);
    }


    /**
     * 电话权限申请提示框
     *
     * @param activity
     * @param whereFrom 位置 0默认/1启动app/2任务中心/3阅读器/4师徒邀请-绑定邀请码
     * @return
     */
    public static void showPhonePermissionDialog(final Activity activity, final String whereFrom) {
//        final Dialog dialog = SysPermissionHelper.showPermissionDialog(activity, "电话权限", "用于识别设备，发放阅读任务福利等",
//                 "【去开启】-【应用信息页】-【权限】-【电话权限开关】", PERMISSION_PHONE, whereFrom);
//        if (dialog != null) {
////            SysPermissionEvent.recordExposureEvent(PERMISSION_PHONE, whereFrom);
//        } else {
//        }
            ToastUtil.show(activity, "该功能需要电话权限才能更好的使用");
    }

    /**
     * 权限申请提示框
     *
     * @param activity
     * @param permissionDisplayName
     * @param permissionRationale
     * @param permission
     * @param whereFrom
     */
//    public static Dialog showPermissionDialog(final Activity activity,
//                                              final String permissionDisplayName,
//                                              final String permissionRationale,
//                                              final String permissionWhere,
//                                              final String permission,
//                                              final String whereFrom) {
//        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
//            return null;
//        }
//
//        try {
//            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
//            LayoutInflater inflater = LayoutInflater.from(activity);
//            View contentView = inflater.inflate(R.layout.dlg_universal_permission, null);
//            TextView permissionDisplayNameTv = contentView.findViewById(R.id.tv_permission_display_name);
//            permissionDisplayNameTv.setText(permissionDisplayName);
//            TextView permissionRationaleTv = contentView.findViewById(R.id.tv_permission_rationale);
//            permissionRationaleTv.setText(permissionRationale);
//            TextView permissionWhereTv = contentView.findViewById(R.id.tv_permission_where);
//            permissionWhereTv.setText(permissionWhere);
//            TextView goButton = contentView.findViewById(R.id.btn_go);
//            final Dialog dialog = builder.setCancelable(true).create();
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
//            dialog.getWindow().setContentView(contentView);
//            goButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    SysPermissionEvent.recordGrantResultEvent(permission, whereFrom, SysPermissionEvent.PERMISSION_RESULT_APP_SET);
//                    AppHelper.appPermissionSetting(activity);
//                    if (dialog != null && dialog.isShowing()) {
//                        try {
//                            dialog.dismiss();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });
//
//            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    LogUtil.d(TAG, "onCancel");
//                }
//            });
//            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialog) {
//                    LogUtil.d(TAG, "onDismiss");
//                }
//            });
//            return dialog;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

}

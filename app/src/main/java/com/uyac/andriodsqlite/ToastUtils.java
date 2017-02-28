package com.uyac.andriodsqlite;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast 辅助类
 * <p>
 * 单例模式
 * <p>
 * 只会显示一个toast 提示
 *
 * @author wangyang
 */
public class ToastUtils {

    /**
     * toast 对象
     */
    private static Toast toast;

    /**
     * 显示自定义Toast
     *
     * @param context 上下文
     * @param str     自定义提示
     */
    public static void show(Context context, String str) {
        if (str.isEmpty()) {
            return;
        }
        if (toast == null) {
            toast = new Toast(context);
        }
        toast.cancel();
        toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 显示自定义Toast
     *
     * @param context 上下文
     *                <p>
     *                自定义提示
     */
    public static void show(Context context, int resourceId) {
        if (toast == null) {
            toast = new Toast(context);
        }
        toast.cancel();
        toast = Toast.makeText(context, context.getString(resourceId), Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 显示自定义Toast
     *
     * @param context 上下文
     * @param str     自定义提示
     */
    public static void showLongToast(Context context, String str) {
        if (str.isEmpty()) {
            return;
        }
        if (toast == null) {
            toast = new Toast(context);
        }
        toast.cancel();
        toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * 显示自定义Toast
     *
     * @param context    上下文
     * @param resourceId 自定义提示
     */
    public static void showLongToast(Context context, int resourceId) {
        if (toast == null) {
            toast = new Toast(context);
        }
        toast.cancel();
        toast = Toast.makeText(context, context.getString(resourceId), Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * 显示网络错误Toast
     *
     * @param context 上下文
     */
    public static void showNetError(Context context) {
        if (toast == null) {
            toast = new Toast(context);
        }
        toast.cancel();
        toast = Toast.makeText(context, "网络无法连接,请稍后再试!", Toast.LENGTH_SHORT);
        toast.show();
    }
}

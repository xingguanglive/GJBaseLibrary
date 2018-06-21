package tv.guojiang.core.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.List;

/**
 * UI工具类.
 */
public class UIUtils {

    private static Toast mToast;

    private static Context mContext;

    public static void init(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 获得上下文
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 获得资源
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获得string类型的数据
     */
    public static String getString(int resId) {
        return getContext().getResources().getString(resId);
    }

    public static String getString(int resId, Object... formatArgs) {
        return getContext().getResources().getString(resId, formatArgs);
    }

    public static int getInt(int resId) {
        return getContext().getResources().getInteger(resId);
    }

    /**
     * 获得数组集合
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获得颜色值
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }

    /**
     * 获得handler
     */
    public static Handler getMainHandler() {
        return new Handler(Looper.getMainLooper());
    }

    /**
     * 在主线程中执行任务
     */
    public static void post(Runnable task) {
        getMainHandler().post(task);
    }

    /**
     * 在主线程中执行任务
     *
     * @param delayMillis 延迟的时间
     */
    public static void postDelayed(Runnable task, long delayMillis) {
        getMainHandler().postDelayed(task, delayMillis);
    }

    /**
     * 移除任务
     */
    public static void removeCallbacks(Runnable task) {
        getMainHandler().removeCallbacks(task);
    }

    /**
     * 像素转dp
     */
    public static int px2dp(int px) {
        // dpi = squa(w*w + h*h)/ 对象线
        // px = dp * (dpi / 160)
        // dp = px * 160 / dpi

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;
        return (int) (px / density + 0.5f);
    }

    /**
     * dp转px
     */
    public static int dp2px(int dp) {
        // px = dp * (dpi / 160)
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;
        return (int) (dp * density + 0.5f);
    }

    public static float getDimens(int id) {
        return getResources().getDimension(id);
    }

    /**
     * 获得包名
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color 状态栏颜色值
     */
    public static void setStateBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setStatusBarColor(color);
                return;
            }

            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // 生成一个状态栏大小的矩形
            View statusView = createStatusView(activity, color);
            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(
                android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    /**
     * 生成一个和状态栏大小相同的矩形条
     *
     * @param activity 需要设置的activity
     * @param color 状态栏颜色值
     * @return 状态栏矩形条
     */
    private static View createStatusView(Activity activity, int color) {
        // 获得状态栏高度
        int resourceId = activity.getResources()
            .getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }

    /**
     * 使状态栏透明
     * <p/>
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity 需要设置的activity
     */
    public static void setStateBarTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                return;
            }

            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(
                android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    public static void showToast(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }

        if (Looper.myLooper() != Looper.getMainLooper()) {
            // 切换到主线程
            post(new Runnable() {
                @Override
                public void run() {
                    showUIToast(message);
                }
            });
        } else {
            showUIToast(message);
        }
    }

    private static void showUIToast(String message) {
        if (mToast == null) {
            mToast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }

    public static void showToast(@StringRes int resId) {
        showToast(getString(resId));
    }

    /**
     * 判断应用是否已经启动
     */
    public static boolean isAppAlive() {
        ActivityManager activityManager = (ActivityManager) getContext().getSystemService(
            Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
            = activityManager.getRunningAppProcesses();
        for (int i = 0; i < processInfos.size(); i++) {
            if (processInfos.get(i).processName.equals(getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取App的名字
     */
    public static String getAppName() {
        ApplicationInfo applicationInfo = getContext().getApplicationInfo();
        int appNameId = applicationInfo.labelRes;
        return appNameId == 0 ? applicationInfo.nonLocalizedLabel.toString() : getString(appNameId);
    }
}

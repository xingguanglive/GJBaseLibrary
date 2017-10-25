package tv.guojiang.baselib.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.widget.Toast;

/**
 * @author Elvis
 * @date 25/10/2017
 * @description tv.guojiang.baselib.util Toast工具類
 */
public class ToastUtil {
	public static Toast toast;
	public static boolean toastSwitch = true;

	/**
	 * 顯示Toast.short
	 *
	 * @param context
	 * @param toastId
	 */
	@UiThread
	public static void showToast(@NonNull Context context, int toastId) {
		if (null == toast) {
			toast = Toast.makeText(context, toastId, Toast.LENGTH_SHORT);
		} else {
			show(toastId, Toast.LENGTH_SHORT);
		}
		show();
	}

	/**
	 * 顯示Toast.short
	 *
	 * @param context
	 * @param msg
	 */
	@UiThread
	public static void showToast(@NonNull Context context, String msg) {
		if (null == toast) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		} else {
			show(msg, Toast.LENGTH_SHORT);
		}
		show();
	}

	/**
	 * Toast 指定時間
	 *
	 * @param context
	 * @param msg
	 * @param duration
	 */
	@UiThread
	public static void showToast(@NonNull Context context, String msg, int duration) {
		if (null == toast) {
			toast = Toast.makeText(context, msg, duration > 0 ? duration : Toast.LENGTH_SHORT);
		} else {
			show(msg, duration > 0 ? duration : Toast.LENGTH_SHORT);
		}
		show();
	}

	/**
	 * Toast 指定時間
	 *
	 * @param context
	 * @param toastid
	 * @param duration
	 */
	@UiThread
	public static void showToast(@NonNull Context context, int toastid, int duration) {
		if (null == toast) {
			toast = Toast.makeText(context, toastid, duration > 0 ? duration : Toast.LENGTH_SHORT);
		} else {
			show(toastid, duration > 0 ? duration : Toast.LENGTH_SHORT);
		}
		show();
	}

	/**
	 * 展示Toast.Long
	 *
	 * @param context
	 * @param msg
	 */
	@UiThread
	public static void showToastLongTime(@NonNull Context context, String msg) {
		if (null == toast) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		} else {
			show(msg, Toast.LENGTH_LONG);
		}
		show();
	}

	/**
	 * 展示Toast.Long
	 *
	 * @param context
	 * @param toastid
	 */
	@UiThread
	public static void showToastLongTime(@NonNull Context context, int toastid) {
		if (null == toast) {
			toast = Toast.makeText(context, toastid, Toast.LENGTH_LONG);
		} else {
			show(toastid, Toast.LENGTH_LONG);
		}
		show();
	}

	/**
	 * toast不為空，更改內容，時間
	 *
	 * @param toastId
	 * @param duration
	 */
	private static void show(int toastId, int duration) {
		toast.setText(toastId);
		toast.setDuration(duration);
	}

	/**
	 * toast不為空，更改內容，時間
	 *
	 * @param toastId
	 * @param duration
	 */
	private static void show(String msg, int duration) {
		toast.setText(msg);
		toast.setDuration(duration);
	}

	/**
	 * 顯示toast
	 */
	private static void show() {
		if (toastSwitch) {
			toast.show();
		}
	}

	/**
	 * 關閉toast
	 */
	public static void cancel() {
		if (null != toast) {
			toast.cancel();
		}
	}
}

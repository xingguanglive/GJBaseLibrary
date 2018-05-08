package tv.guojiang.baselib.util;

import android.os.Looper;

/**
 * @author Elvis
 * @date 25/10/2017
 * @description tv.guojiang.baselib.util 線程工具類
 */

public class ThreadUtil {
	/**
	 * 是否是UI線程
	 * @return
	 */
	public static boolean isRunOnUiThread() {
		return Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId();
	}
}

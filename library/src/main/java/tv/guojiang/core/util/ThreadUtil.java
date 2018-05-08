package tv.guojiang.core.util;

import android.os.Looper;

/**
 * @author Elvis
 */

public class ThreadUtil {

    /**
     * 是否是UI線程
     */
    public static boolean isRunOnUiThread() {
        return Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId();
    }
}

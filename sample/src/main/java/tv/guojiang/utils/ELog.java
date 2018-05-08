package tv.guojiang.utils;

import tv.guojiang.core.log.Logs;

/**
 * @author KK
 */
public class ELog {

    public static void e(String tag, String msg) {
        Logs.e("MainActivity", "thread  ----------- " + msg, true);
    }

}

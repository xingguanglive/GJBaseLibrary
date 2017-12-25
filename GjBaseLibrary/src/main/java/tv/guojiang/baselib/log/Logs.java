package tv.guojiang.baselib.log;

import tv.guojiang.baselib.log.impl.IPrinter;

/**
 * author KK
 * date 2017/12/21
 */
public class Logs {

    private static IPrinter printer = new LogsPrinter();

    public static void d(String tag, String msg) {
        printer.d(tag, msg, false);
    }

    public static void e(String tag, String msg) {
        printer.e(tag, msg, false);
    }

    public static void w(String tag, String msg) {
        printer.w(tag, msg, false);
    }

    public static void i(String tag, String msg) {
        printer.i(tag, msg, false);
    }

    public static void v(String tag, String msg) {
        printer.v(tag, msg, false);
    }

    public static void d(String tag, String msg, boolean isWrite) {
        printer.d(tag, msg, isWrite);
    }

    public static void e(String tag, String msg, boolean isWrite) {
        printer.e(tag, msg, isWrite);
    }

    public static void w(String tag, String msg, boolean isWrite) {
        printer.w(tag, msg, isWrite);
    }

    public static void i(String tag, String msg, boolean isWrite) {
        printer.i(tag, msg, isWrite);
    }

    public static void v(String tag, String msg, boolean isWrite) {
        printer.v(tag, msg, isWrite);
    }
}

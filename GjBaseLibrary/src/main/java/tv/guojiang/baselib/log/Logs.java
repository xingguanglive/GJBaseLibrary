package tv.guojiang.baselib.log;

import tv.guojiang.baselib.log.impl.IPrinter;

/**
 * author KK
 * date 2017/12/21
 */
public class Logs {

    private static IPrinter printer;

    public static void build(String path){
        printer = new LogsPrinter(path);
    }

    public static void build(){
        printer = new LogsPrinter();
    }
    /**
     * 打印日志
     *
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg) {
        printer.d(tag, msg, false);
    }

    /**
     * 打印日志
     *
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        printer.e(tag, msg, false);
    }

    /**
     * 打印日志
     *
     * @param tag
     * @param msg
     */
    public static void w(String tag, String msg) {
        printer.w(tag, msg, false);
    }

    /**
     * 打印日志
     *
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        printer.i(tag, msg, false);
    }

    /**
     * 打印日志
     *
     * @param tag
     * @param msg
     */
    public static void v(String tag, String msg) {
        printer.v(tag, msg, false);
    }

    /**
     * 打印日志，并写到本地
     *
     * @param tag
     * @param msg
     * @param isWrite true:写到本地日子
     */
    public static void d(String tag, String msg, boolean isWrite) {
        printer.d(tag, msg, isWrite);
    }

    /**
     * 打印日志，并写到本地
     *
     * @param tag
     * @param msg
     * @param isWrite true:写到本地日子
     */
    public static void e(String tag, String msg, boolean isWrite) {
        printer.e(tag, msg, isWrite);
    }

    /**
     * 打印日志，并写到本地
     *
     * @param tag
     * @param msg
     * @param isWrite true:写到本地日子
     */
    public static void w(String tag, String msg, boolean isWrite) {
        printer.w(tag, msg, isWrite);
    }

    /**
     * 打印日志，并写到本地
     *
     * @param tag
     * @param msg
     * @param isWrite true:写到本地日子
     */
    public static void i(String tag, String msg, boolean isWrite) {
        printer.i(tag, msg, isWrite);
    }

    /**
     * 打印日志，并写到本地
     *
     * @param tag
     * @param msg
     * @param isWrite true:写到本地日子
     */
    public static void v(String tag, String msg, boolean isWrite) {
        printer.v(tag, msg, isWrite);
    }
}

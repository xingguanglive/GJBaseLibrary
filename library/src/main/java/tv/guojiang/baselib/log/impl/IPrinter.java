package tv.guojiang.baselib.log.impl;

/**
 * author KK
 * date 2017/12/21
 */
public interface IPrinter {


    void d(String tag, String msg, boolean isWrite);

    void e(String tag, String msg, boolean isWrite);

    void w(String tag, String msg, boolean isWrite);

    void i(String tag, String msg, boolean isWrite);

    void v(String tag, String msg, boolean isWrite);

}

package tv.guojiang.baselib.log;


import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import tv.guojiang.baselib.log.impl.IPrinter;

/**
 * author KK
 * date 2017/12/21
 */
public class LogsPrinter implements IPrinter {

    private LogsWriter mLogsWriter;

    public LogsPrinter() {
        Logger.addLogAdapter(new AndroidLogAdapter());
        mLogsWriter = new LogsWriter();
    }

    @Override
    public void d(String tag, String msg, boolean isWrite) {
        print(Log.DEBUG, tag, msg, isWrite);
    }

    @Override
    public void e(String tag, String msg, boolean isWrite) {
        print(Log.ERROR, tag, msg, isWrite);
    }

    @Override
    public void w(String tag, String msg, boolean isWrite) {
        print(Log.WARN, tag, msg, isWrite);
    }

    @Override
    public void i(String tag, String msg, boolean isWrite) {
        print(Log.INFO, tag, msg, isWrite);
    }

    @Override
    public void v(String tag, String msg, boolean isWrite) {
        print(Log.VERBOSE, tag, msg, isWrite);
    }

    private void print(int type, String tag, String msg, boolean isWrite) {
        Logger.log(type, tag, msg, null);
        if (isWrite) {
            mLogsWriter.logs(tag, msg);
        }
    }
}

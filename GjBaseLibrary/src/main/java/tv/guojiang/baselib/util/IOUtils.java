package tv.guojiang.baselib.util;

import com.orhanobut.logger.Logger;
import java.io.Closeable;
import java.io.IOException;

/**
 * IO工具类.
 */
public class IOUtils {

    /**
     * 关闭流
     */
    public static boolean close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                Logger.e(e, e.getMessage());
            }
        }
        return true;
    }
}

package tv.guojiang.core.util;

import android.util.Log;
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
                Log.e("IOUtils", e.getMessage(), e);
            }
        }
        return true;
    }
}

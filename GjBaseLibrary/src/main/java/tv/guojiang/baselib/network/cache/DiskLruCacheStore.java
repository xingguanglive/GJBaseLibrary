package tv.guojiang.baselib.network.cache;

import android.content.Context;
import android.content.pm.PackageManager;
import com.jakewharton.disklrucache.DiskLruCache;
import com.jakewharton.disklrucache.DiskLruCache.Editor;
import com.jakewharton.disklrucache.DiskLruCache.Snapshot;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import tv.guojiang.baselib.util.FileUtils;
import tv.guojiang.baselib.util.Md5Utils;

/**
 * 基于LRU算法的文件缓存.缓存格式为:
 * <pre>
 * 时间戳
 * 接口数据
 * </pre>
 *
 * @author leo
 */
public class DiskLruCacheStore implements ICacheStore {

    // 缓存最大为20M
    private static final int MAX_SIZE = 20 * 1024 * 1024;

    private DiskLruCache mDiskLruCache;

    public DiskLruCacheStore(Context context) {
        try {
            File directory = new File(FileUtils.getCacheDir(context));
            PackageManager packageManager = context.getPackageManager();
            int appVersion = packageManager.getPackageInfo(context.getPackageName(), 0).versionCode;
            mDiskLruCache = DiskLruCache.open(directory, appVersion, 1, MAX_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(String key, String value) {
        if (mDiskLruCache == null) {
            return;
        }

        // 时间戳
        String readKey = Md5Utils.getMD5(key);
        Editor editor = null;
        try {
            editor = mDiskLruCache.edit(readKey);
            OutputStream outputStream = editor.newOutputStream(0);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            long currentTimeSeconds = System.currentTimeMillis() / 1000;
            // 写入时间戳
            writer.write(currentTimeSeconds + "");
            // 写入新行
            writer.newLine();
            // 写入接口数据
            writer.write(value);
            writer.flush();

            editor.commit();
        } catch (IOException e) {
            if (editor != null) {
                try {
                    editor.abort();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            try {
                mDiskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String get(String key, long time) {
        if (mDiskLruCache == null) {
            return null;
        }

        String realKey = Md5Utils.getMD5(key);

        try {
            Snapshot snapshot = mDiskLruCache.get(realKey);
            InputStream inputStream = snapshot.getInputStream(0);

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String timeStr = reader.readLine();
            long storeTime = Long.parseLong(timeStr);

            if ((System.currentTimeMillis() / 1000 - storeTime) >= time) {
                // 缓存已过期，删除缓存
                mDiskLruCache.remove(realKey);
                return null;
            } else {
                // 缓存未过期
                return reader.readLine();
            }
        } catch (Exception e) {
            try {
                // 异常时删除删除
                mDiskLruCache.remove(realKey);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }
}

package tv.guojiang.baselib.network.cache;

import android.content.Context;
import android.content.pm.PackageManager;
import com.jakewharton.disklrucache.DiskLruCache;
import com.jakewharton.disklrucache.DiskLruCache.Editor;
import com.jakewharton.disklrucache.DiskLruCache.Snapshot;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import tv.guojiang.baselib.util.FileUtils;
import tv.guojiang.baselib.util.Md5Utils;

/**
 * 文件缓存
 *
 * @author leo
 */
public class FileStore implements IStore {

    // 缓存最大为20M
    private static final int MAX_SIZE = 20 * 1024 * 1024;

    private DiskLruCache mDiskLruCache;

    public FileStore(Context context) {
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

        String md5Key = Md5Utils.getMD5(key);
        Editor editor = null;
        try {
            editor = mDiskLruCache.edit(md5Key);
            OutputStream outputStream = editor.newOutputStream(0);
            outputStream.write(value.getBytes());
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
    public String get(String key) {
        if (mDiskLruCache == null) {
            return null;
        }

        String md5Key = Md5Utils.getMD5(key);
        try {
            Snapshot snapshot = mDiskLruCache.get(md5Key);
            return snapshot.getString(0);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isExpire() {
        return false;
    }
}

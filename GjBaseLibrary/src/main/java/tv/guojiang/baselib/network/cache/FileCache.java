package tv.guojiang.baselib.network.cache;

import okhttp3.internal.cache.DiskLruCache;

/**
 * 接口文件缓存
 *
 * @author leo
 */
public class FileCache implements ICache {

    private DiskLruCache mLruCache;

    @Override
    public void put(String key, String value) {

    }

    @Override
    public String get(String key) {
        return null;
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

package tv.guojiang.core.network.cache;

import android.content.Context;
import android.util.Log;
import io.reactivex.Observable;
import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import tv.guojiang.core.network.annotation.Cache;
import tv.guojiang.core.network.request.PostBodyRequest;
import tv.guojiang.core.util.JsonUtils;

/**
 * 接口缓存处理
 *
 * @author leo
 */
public class RxCache {

    public static final String TAG = "Cache";

    private ICacheStore mStore;

    public RxCache(Context context) {
        mStore = new DiskLruCacheStore(context);
    }

    /**
     * 构造一个{@link RxCache}
     *
     * @param directory 缓存保存的目录
     * @param maxSize 缓存的最大容量
     */
    public RxCache(Context context, File directory, int maxSize) {
        mStore = new DiskLruCacheStore(context, directory, maxSize);
    }

    /**
     * 获取缓存的原始key。同一个接口地址GET/POST提交的参数相同时，key相同
     *
     * @param url 接口地址
     * @param params 当前接口的参数。
     */
    private static String getCacheKey(String url, Map<String, String> params) {
        return url + params.toString();
    }

    private static String getCacheKey(String url, String body) {
        return url + body;
    }

    public static Cache getCacheAnnotation(Object obj) {
        return obj.getClass().getAnnotation(Cache.class);
    }

    /**
     * 获取缓存
     */
    public Observable<String> getCache(String url, Map<String, String> params, Cache cache) {
        return getCache(getCacheKey(url, params), cache);
    }

    public Observable<String> getCache(String url, PostBodyRequest request, Cache cache) {
        if (request.body instanceof String) {
            return getCache(getCacheKey(url, (String) request.body), cache);
        }

        return getCache(getCacheKey(url, JsonUtils.getInstance().toJson(request.body)), cache);
    }

    public Observable<String> getCache(String cacheKey, Cache cache) {

        return Observable.create(e -> {

            // 时间单位转换
            long maxAge = cache.maxAge();
            TimeUnit timeUnit = cache.timeUnit();
            long realMaxAgeTime = timeUnit.toSeconds(maxAge);

            // 读取缓存
            String json = mStore.get(cacheKey, realMaxAgeTime);
            // 缓存不为空时才触发下面的操作
            if (json != null) {
                Log.i(TAG, "get data from cache : " + cacheKey);
                e.onNext(json);
            }
            e.onComplete();
        });
    }

    public void saveCache(String url, PostBodyRequest request, String json) {
        if (request.body instanceof String) {
            saveCache(getCacheKey(url, (String) request.body), json);
            return;
        }

        saveCache(getCacheKey(url, JsonUtils.getInstance().toJson(request.body)), json);
    }

    /**
     * 缓存接口数据
     */
    public void saveCache(String url, Map<String, String> params, String json) {
        saveCache(getCacheKey(url, params), json);
    }

    public void saveCache(String key, String value) {
        mStore.put(key, value);
        Log.i(TAG, "store cache : " + key);
    }

}

package tv.guojiang.baselib.network.cache;

import android.content.Context;
import android.util.Log;
import io.reactivex.Observable;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import tv.guojiang.baselib.network.annotation.Cache;

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
     * 获取缓存的原始key。同一个接口地址GET/POST提交的参数相同时，key相同
     *
     * @param url 接口地址
     * @param params 当前接口的参数。
     */
    private static String getRealKey(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append(params.toString());
        return sb.toString();
    }

    public static Cache getCacheAnnotation(Object obj) {
        return obj.getClass().getAnnotation(Cache.class);
    }

    /**
     * 获取缓存
     */
    public Observable<String> getCache(String url, Map<String, String> params, Cache cache) {
        return Observable.create(e -> {

            String realKey = getRealKey(url, params);

            // 时间单位转换
            long maxAge = cache.maxAge();
            TimeUnit timeUnit = cache.timeUnit();
            long realMaxAgeTime = timeUnit.toSeconds(maxAge);

            // 读取缓存
            String json = mStore.get(realKey, realMaxAgeTime);
            // 缓存不为空时才触发下面的操作
            if (json != null) {
                Log.i(TAG, "get data from cache : " + realKey);
                e.onNext(json);
            }
            e.onComplete();
        });
    }

    /**
     * 缓存接口数据
     */
    public void saveCache(String url, Map<String, String> params, String json) {
        String realKey = getRealKey(url, params);
        mStore.put(realKey, json);
        Log.i(TAG, "store cache : " + realKey);
    }

}

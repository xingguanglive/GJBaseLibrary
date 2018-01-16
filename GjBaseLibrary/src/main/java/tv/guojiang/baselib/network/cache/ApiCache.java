package tv.guojiang.baselib.network.cache;

import android.content.Context;
import java.util.Map;

/**
 * 接口缓存
 *
 * @author leo
 */
public class ApiCache implements IStore {

    private IStore mStore;

    public ApiCache(Context context) {
        mStore = new FileStore(context);
    }

    /**
     * 获取缓存的原始key。同一个接口地址GET/POST提交的参数相同时，key相同
     *
     * @param url 接口地址
     * @param params 当前接口的参数。
     */
    public static String getKey(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append(params.toString());
        return sb.toString();
    }

    @Override
    public void put(String key, String value) {
        mStore.put(key, value);
    }

    @Override
    public String get(String key) {
        return mStore.get(key);
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

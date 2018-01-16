package tv.guojiang.baselib.network.cache;

import android.content.Context;

/**
 * 接口缓存
 *
 * @author leo
 */
public class ApiCache {

    private IStore mStore;

    public ApiCache(Context context) {
        mStore = new FileStore(context);
    }


}

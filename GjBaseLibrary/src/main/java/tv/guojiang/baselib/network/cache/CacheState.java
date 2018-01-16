package tv.guojiang.baselib.network.cache;

import tv.guojiang.baselib.network.request.BaseRequest;

/**
 * 缓存的几种状态
 *
 * @author leo
 */
public enum CacheState {

    /**
     * 只要缓存未过期，就只读取缓存
     */
    FOCUS_CACHE,

    /**
     * 先读取缓存，再读取网络，两者都会读取
     */
    FOCUS_CACHE_AND_NETWORK,

    /**
     * 没有网络的时候再读取缓存
     */
    FOCUS_NETWORK_UNTIL_OFFLINE,

    /**
     * 除非手动下拉刷新，否则只读取缓存。需要配合{@link BaseRequest#refreshApi}使用
     */
    FOCUS_CACHE_UNTIL_REFRESH,

}

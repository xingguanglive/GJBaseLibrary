package tv.guojiang.baselib.network.cache;

/**
 * 缓存的几种状态
 *
 * @author leo
 */
public enum CacheState {

    /**
     * 先读取缓存，再读取网络，两者都会读取
     */
    READ_CACHE_AND_NETWORK,

    /**
     * 没有网络的时候再读取缓存
     */
    ONLY_READ_CACHE_WHEN_OFFLINE,

    /**
     * 只要缓存未过期，就只读取缓存。
     */
    FOCUS_CACHE_UNTIL_OVERDUE,

    /**
     * 除非手动下拉刷新，否则只读取缓存
     */
    CACHE_UNLESS_REFRESH,

}

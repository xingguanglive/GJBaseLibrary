package tv.guojiang.core.network.cache;

import tv.guojiang.core.network.request.BaseRequest;

/**
 * 缓存策略<br/>
 * <b>"缓存有效"："缓存存在且缓存未过期"</b><br/>
 * <b>"缓存无效"："缓存已过期或者缓存不存在"</b>
 *
 * @author leo
 */
public enum CacheState {

    /**
     * 缓存有效：只读取缓存<br/>
     * 缓存无效：读取网络
     */
    FOCUS_CACHE,

    /**
     * 缓存有效：先读取缓存、再读取网络，两者都会读取<br/>
     * 缓存无效：只读取网络
     */
    FOCUS_CACHE_AND_NETWORK,

    /**
     * 缓存有效：有网络时读取网络；无网络时只读取缓存<br/>
     * 缓存无效：只读取网络
     */
    FOCUS_CACHE_UNTIL_ONLINE,

    /**
     * 需要配合{@link BaseRequest#refreshApi}使用<br/>
     * 缓存有效：{@code refreshApi=true}时读取网络；{@code refreshApi=false}时读取缓存<br/>
     * 缓存无效：只读取网络
     */
    FOCUS_CACHE_UNTIL_REFRESH,

}

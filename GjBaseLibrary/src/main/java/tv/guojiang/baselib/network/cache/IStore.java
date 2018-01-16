package tv.guojiang.baselib.network.cache;

/**
 * 接口缓存
 *
 * @author leo
 */
public interface IStore {

    /**
     * 缓存接口数据
     */
    void put(String key, String value);

    /**
     * 从缓存中获取数据
     */
    String get(String key);

    /**
     * 缓存数据是否为空
     */
    boolean isEmpty();

    /**
     * 缓存是否过期
     */
    boolean isExpire();

}

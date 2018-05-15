package tv.guojiang.core.network.cache;

/**
 * 接口缓存
 *
 * @author leo
 */
public interface ICacheStore {

    /**
     * 缓存接口数据
     */
    void put(String key, String value);

    /**
     * 从缓存中获取数据。
     *
     * @param maxAge 缓存有效期，单位：秒
     * @return 没有缓存或者缓存过期时，返回null；其他情况正常返回
     */
    String get(String key, long maxAge);

}

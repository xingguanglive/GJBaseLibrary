package tv.guojiang.baselib.network.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;
import tv.guojiang.baselib.network.cache.CacheState;
import tv.guojiang.baselib.network.request.BaseRequest;

/**
 * 接口中使用缓存的注解。当一个接口请求{@link BaseRequest}需要缓存时，使用该注解说明
 *
 * @author leo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Cache {

    /**
     * 缓存的有效期，单位：秒。默认永不过期
     */
    long maxAge() default Integer.MAX_VALUE;

    /**
     * 有效期时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 缓存的状态
     */
    CacheState state();

}

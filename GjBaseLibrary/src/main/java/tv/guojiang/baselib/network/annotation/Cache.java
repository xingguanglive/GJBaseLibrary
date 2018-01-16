package tv.guojiang.baselib.network.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import tv.guojiang.baselib.network.cache.CacheState;

/**
 * 接口中使用缓存的注解
 *
 * @author leo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Cache {

    /**
     * 缓存的有效期，单位：秒。默认永不过期
     */
    long time() default Long.MAX_VALUE;

    /**
     * 缓存的状态
     */
    CacheState state();

}

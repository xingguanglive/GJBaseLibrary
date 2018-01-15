package tv.guojiang.baselib.network.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口中使用缓存的注解
 *
 * @author leo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Cache {

    /**
     * 缓存的有效期，单位：秒
     */
    long time() default 0L;

    /**
     * 是否本机没有网络，再读取缓存
     */
    boolean focusOffline() default true;

    /**
     * 是否先读取缓存，再从接口获取
     */
    boolean focusCache() default false;

}

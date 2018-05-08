package tv.guojiang.baselib.network.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import tv.guojiang.baselib.network.request.BaseRequest;

/**
 * {@link BaseRequest}请求中的Header。
 *
 * @author leo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Header {

    /**
     * 参数的名称
     */
    String value() default "";

}

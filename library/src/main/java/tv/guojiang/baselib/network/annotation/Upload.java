package tv.guojiang.baselib.network.annotation;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import tv.guojiang.baselib.network.request.BaseRequest;

/**
 * 单个文件上传的标志。<br/>
 * - 若一个key对应一个文件，{@link BaseRequest}的对应字段(必须是{@link File})<br/>
 * - 若一个key对应多个文件，{@link BaseRequest}的对应字段(必须是{@link File})的{@link List}<br/>
 * @author leo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Upload {

    String value() default "";

}

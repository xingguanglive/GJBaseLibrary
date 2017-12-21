package tv.guojiang.baselib.network.interceptor;

/**
 * Url拦截器。可以在url的后面拼接一些公共的请求参数
 *
 * @author leo
 */
public interface UrlInterceptor {

    String intercept(String url);

}

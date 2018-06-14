package tv.guojiang.core.network.request;

import tv.guojiang.core.network.annotation.Ignore;

/**
 * post json/string 的request
 *
 * @author leo
 */
public class PostBodyRequest<T> extends BaseRequest {

    @Ignore
    public T body;

}

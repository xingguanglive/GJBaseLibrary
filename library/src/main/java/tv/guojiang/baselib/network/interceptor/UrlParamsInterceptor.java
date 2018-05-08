package tv.guojiang.baselib.network.interceptor;

import android.text.TextUtils;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * url上添加公共请求参数的{@link Interceptor}
 *
 * @author leo
 */
public class UrlParamsInterceptor implements Interceptor {

    private Map<String, String> mParams;

    public UrlParamsInterceptor(Map<String, String> params) {
        mParams = params;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request oldRequest = chain.request();

        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
            .newBuilder()
            .scheme(oldRequest.url().scheme())
            .host(oldRequest.url().host());

        for (Entry<String, String> entry : mParams.entrySet()) {
            if (TextUtils.isEmpty(entry.getValue()) || TextUtils.isEmpty(entry.getKey())) {
                continue;
            }
            authorizedUrlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }

        // 新的请求
        Request newRequest = oldRequest.newBuilder()
            .method(oldRequest.method(), oldRequest.body())
            .url(authorizedUrlBuilder.build())
            .build();

        return chain.proceed(newRequest);
    }
}

package tv.guojiang.baselib.network.interceptor;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 给Request统一添加 header 的{@link Interceptor}
 */
public final class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request()
            .newBuilder()
            .addHeader("header-key", "header-value");

        return chain.proceed(builder.build());
    }
}

package tv.guojiang.baselib.network.interceptor;

import android.text.TextUtils;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 给Request统一添加 header 的{@link Interceptor}
 */
public final class HeaderInterceptor implements Interceptor {

    private Map<String, String> mHeaders;

    public HeaderInterceptor(Map<String, String> headers) {
        mHeaders = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request()
            .newBuilder();

        for (Entry<String, String> entry : mHeaders.entrySet()) {
            if (TextUtils.isEmpty(entry.getKey()) || TextUtils.isEmpty(entry.getValue())) {
                continue;
            }

            builder.addHeader(entry.getKey(), entry.getValue());
        }

        return chain.proceed(builder.build());
    }
}

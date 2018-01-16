package tv.guojiang.baselib.network.interceptor;

import android.content.Context;
import java.io.IOException;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import tv.guojiang.baselib.util.NetworkUtils;

/**
 * 离线缓存 Interceptor
 *
 * {@link okhttp3.Cache}中限制了{@link okhttp3.OkHttpClient}仅支持 GET 请求
 */
@Deprecated
public final class OfflineCacheInterceptor implements Interceptor {

    private Context mContext;

    public OfflineCacheInterceptor(Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        boolean networkAvailable = NetworkUtils.isNetworkAvailable(mContext);

        Request request = chain.request();

        if (networkAvailable) {
            //网络可用 强制从网络获取数据
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build();
        } else {
            //网络不可用 从缓存获取
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build();
        }

        Response response = chain.proceed(request);

        if (networkAvailable) {
            // 有网络时 设置缓存超时时间1个小时
            response = response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, max-age=" + 60 * 60)
                .build();
        } else {
            // 无网络时，设置超时为1周
            response = response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, only-if-cached, max-stale=" + 7 * 24 * 60 * 60)
                .build();
        }

        return response;
    }
}

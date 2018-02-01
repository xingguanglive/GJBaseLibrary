package tv.guojiang.baselib.network;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tv.guojiang.baselib.network.config.ServerCode;
import tv.guojiang.baselib.network.interceptor.HeaderInterceptor;
import tv.guojiang.baselib.network.interceptor.UrlParamsInterceptor;

/**
 * OkHttp与Retrofit的配置信息。可通过{@link Builder}进行配置，例如
 * <pre>
 * ApiClient apiClient = new ApiClient.Builder(this)
 *      .baseUrl("http://www.baidu.com/")
 *      .log(true)
 *      .cookie(true)
 *      .joinParamsIntoUrl(false)
 *      .header("user-agent", "android")
 *      .param("copyright", "AppLive")
 *      .readTimeout(30)
 *      .writeTimeout(30)
 *      .connectTimeout(60)
 *      .addInterceptor(new MockInterceptor())
 *      .timeoutUnit(TimeUnit.SECONDS)
 *      .build();
 *
 * NetworkBiz.getInstance().setApiClient(apiClient);
 * </pre>
 *
 * - 接口业务状态码请在{@link ServerCode}中进行配置。
 */
public final class ApiClient {

    private Builder mBuilder;

    private Retrofit mRetrofit;

    private Map<String, String> mParams;

    private ApiClient() {
    }

    private void setBuilder(Builder builder) {
        mBuilder = builder;
    }

    private String getBaseUrl() {
        String baseUrl = mBuilder.baseUrl;
        if (baseUrl == null) {
            throw new NullPointerException(
                "call " + Builder.class.getName() + ".baseUrl(url) first !!");
        }
        return baseUrl;
    }

    /* internal */ Map<String, String> getParams() {
        return mParams;
    }

    /* internal */ Context getContext() {
        return mBuilder.context;
    }

    /**
     * 初始化Retrofit
     */
    private Retrofit initRetrofit() {

        if (mBuilder == null) {
            throw new NullPointerException("call ApiClient.build(build) first !!! ");
        }

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        // 注意几个Interceptor的顺序

        // 请求头
        if (mBuilder.headers != null && mBuilder.headers.size() > 0) {
            okHttpBuilder.addInterceptor(new HeaderInterceptor(mBuilder.headers));
        }

        // 公共参数
        if (mBuilder.params != null && mBuilder.params.size() > 0) {
            if (mBuilder.joinParamIntoUrl) {
                // 添加到Url上
                okHttpBuilder.addInterceptor(new UrlParamsInterceptor(mBuilder.params));
            } else {
                // 添加到参数中
                mParams = mBuilder.params;
            }
        }

        // cookie
        if (mBuilder.cookie != null) {
            okHttpBuilder.cookieJar(mBuilder.cookie);
        }

        // http 日志
        if (mBuilder.log) {
            HttpLoggingInterceptor loggerInterceptor = new HttpLoggingInterceptor();
            loggerInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpBuilder.addInterceptor(loggerInterceptor);
        }

        // timeout
        if (mBuilder.readTimeout > 0) {
            okHttpBuilder.readTimeout(mBuilder.readTimeout, mBuilder.timeoutUnit);
        }
        if (mBuilder.writeTimeout > 0) {
            okHttpBuilder.writeTimeout(mBuilder.writeTimeout, mBuilder.timeoutUnit);
        }
        if (mBuilder.connectTimeout > 0) {
            okHttpBuilder.connectTimeout(mBuilder.connectTimeout, mBuilder.timeoutUnit);
        }

        if (mBuilder.interceptors.size() != 0) {
            for (Interceptor interceptor : mBuilder.interceptors) {
                okHttpBuilder.addInterceptor(interceptor);
            }
        }

        if (mBuilder.networkInterceptors.size() != 0) {
            for (Interceptor interceptor : mBuilder.networkInterceptors) {
                okHttpBuilder.addNetworkInterceptor(interceptor);
            }
        }

        OkHttpClient okHttpClient = okHttpBuilder.build();

        return new Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build();
    }


    /**
     * 获取通用的 Retrofit Service
     *
     * @param serviceClazz Retrofit Api Service's Class
     * @return Retrofit Api Service Object
     */
    public <T> T getApiService(Class<T> serviceClazz) {
        if (mRetrofit == null) {
            mRetrofit = initRetrofit();
        }
        return mRetrofit.create(serviceClazz);
    }

    public final static class Builder {

        /**
         * 是否打印 Http Log
         */
        private boolean log;

        /**
         * 通用的参数
         */
        private Map<String, String> params;

        /**
         * 是否将公共参数拼接在 url上
         */
        private boolean joinParamIntoUrl;

        private Map<String, String> headers;

        private String baseUrl;

        /**
         * 是否添加cookie管理
         */
        private CookieJar cookie;

        private int readTimeout;

        private int writeTimeout;

        private int connectTimeout;

        private TimeUnit timeoutUnit = TimeUnit.SECONDS;

        private List<Interceptor> interceptors = new ArrayList<>();

        private List<Interceptor> networkInterceptors = new ArrayList<>();

        private Context context;

        public Builder(Context context) {
            this.context = context.getApplicationContext();
        }

        /**
         * 是否打印Http的日志信息
         */
        public Builder log(boolean log) {
            this.log = log;
            return this;
        }

        /**
         * 初始化BaseUrl.<em>Url格式为 http://www.guojiang.tv/ </em>
         */
        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * 接口统一添加的请求头
         */
        public Builder header(String key, String value) {
            if (headers == null) {
                headers = new ArrayMap<>();
            }
            headers.put(key, value);
            return this;
        }

        /**
         * 接口统一添加的请求头
         */
        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * 接口的通用参数
         */
        public Builder param(String key, String value) {
            if (params == null) {
                params = new ArrayMap<>();
            }
            params.put(key, value);
            return this;
        }

        /**
         * 接口的通用参数
         */
        public Builder params(Map<String, String> params) {
            this.params = params;
            return this;
        }

        /**
         * 是否添加cookie
         */
        public Builder cookie(CookieJar cookie) {
            this.cookie = cookie;
            return this;
        }

        /**
         * 是否将通用的参数拼接到url上
         *
         * @param joinParamIntoUrl true:通用参数将被添加到请求头上 false:通用参数根据请求方式的添加到对应的参数中(GET-请求行;POST-请求体)
         */
        public Builder joinParamsIntoUrl(boolean joinParamIntoUrl) {
            this.joinParamIntoUrl = joinParamIntoUrl;
            return this;
        }

        /**
         * 设置readTimeOut
         */
        public Builder readTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        /**
         * 设置writeTimeOut
         */
        public Builder writeTimeout(int writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        /**
         * 设置connectTimeOut
         */
        public Builder connectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        /**
         * timeout 时间单位。默认是秒
         */
        public Builder timeoutUnit(TimeUnit timeoutUnit) {
            this.timeoutUnit = timeoutUnit;
            return this;
        }

        /**
         * see {@link OkHttpClient.Builder#addInterceptor(Interceptor)}
         */
        public Builder addInterceptor(Interceptor interceptor) {
            this.interceptors.add(interceptor);
            return this;
        }

        /**
         * see {@link OkHttpClient.Builder#addNetworkInterceptor(Interceptor)}
         */
        public Builder addNetworkInterceptor(Interceptor interceptor) {
            this.networkInterceptors.add(interceptor);
            return this;
        }

        public ApiClient build() {
            ApiClient apiClient = new ApiClient();
            apiClient.setBuilder(this);
            return apiClient;
        }

    }
}

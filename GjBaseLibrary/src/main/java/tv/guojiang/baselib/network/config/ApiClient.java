package tv.guojiang.baselib.network.config;

import android.support.v4.util.ArrayMap;
import java.util.Map;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tv.guojiang.baselib.network.interceptor.HeaderInterceptor;
import tv.guojiang.baselib.network.interceptor.MockInterceptor;
import tv.guojiang.baselib.network.interceptor.UrlParamsInterceptor;

/**
 * OkHttp与Retrofit的配置信息。接口业务状态码请在{@link ServerCode}中进行配置。
 */
public final class ApiClient {

    private Builder mBuilder;

    private Retrofit mRetrofit;

    private Map<String, String> mParams;

    private ApiClient() {
    }

    private static final class Singleton {

        private static final ApiClient INSTANCE = new ApiClient();
    }

    public static ApiClient getInstance() {
        return Singleton.INSTANCE;
    }

    private void setBuilder(Builder builder) {
        mBuilder = builder;
    }

    public String getBaseUrl() {
        String baseUrl = mBuilder.mBaseUrl;
        if (baseUrl == null) {
            throw new NullPointerException(
                "call " + Builder.class.getName() + ".baseUrl(url) first !!");
        }
        return baseUrl;
    }

    public Map<String, String> getParams() {
        return mParams;
    }

    /**
     * 初始化Retrofit
     */
    private Retrofit initRetrofit() {

        if (mBuilder == null) {
            throw new NullPointerException("call ApiClient.build(builder) first !!! ");
        }

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        // 注意几个Interceptor的顺序
        // 请求头
        if (mBuilder.mHeaders != null && mBuilder.mHeaders.size() > 0) {
            okHttpBuilder.addInterceptor(new HeaderInterceptor(mBuilder.mHeaders));
        }

        // 公共参数
        if (mBuilder.mParams != null && mBuilder.mParams.size() > 0) {
            if (mBuilder.mJoinParamIntoUrl) {
                // 添加到Url上
                okHttpBuilder.addInterceptor(new UrlParamsInterceptor(mBuilder.mParams));
            } else {
                // 添加到参数中
                mParams = mBuilder.mParams;
            }
        }

        // http 日志
        if (mBuilder.mHttpLogEnable) {
            HttpLoggingInterceptor loggerInterceptor = new HttpLoggingInterceptor();
            loggerInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpBuilder.addInterceptor(loggerInterceptor);
        }

        // 模拟数据
        if (mBuilder.mMockData) {
            okHttpBuilder.addInterceptor(new MockInterceptor());
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
        private boolean mHttpLogEnable;

        /**
         * 是否需要模拟数据. 测试的时候使用
         */
        private boolean mMockData;

        /**
         * 通用的参数
         */
        private Map<String, String> mParams;

        /**
         * 是否将公共参数拼接在 url上
         */
        private boolean mJoinParamIntoUrl;

        private Map<String, String> mHeaders;

        private String mBaseUrl;

        public Builder httpLogEnable(boolean logEnable) {
            this.mHttpLogEnable = logEnable;
            return this;
        }

        public Builder mockData(boolean mockData) {
            this.mMockData = mockData;
            return this;
        }

        /**
         * 初始化BaseUrl.<em>Url格式为 http://www.guojiang.tv/ </em>
         */
        public Builder baseUrl(String baseUrl) {
            this.mBaseUrl = baseUrl;
            return this;
        }

        /**
         * 接口统一添加的请求头
         */
        public Builder header(String key, String value) {
            if (mHeaders == null) {
                mHeaders = new ArrayMap<>();
            }
            mHeaders.put(key, value);
            return this;
        }

        /**
         * 接口统一添加的请求头
         */
        public Builder headers(Map<String, String> headers) {
            this.mHeaders = headers;
            return this;
        }

        /**
         * 接口的通用参数
         */
        public Builder param(String key, String value) {
            if (mParams == null) {
                mParams = new ArrayMap<>();
            }
            mParams.put(key, value);
            return this;
        }

        /**
         * 接口的通用参数
         */
        public Builder params(Map<String, String> params) {
            this.mParams = params;
            return this;
        }

        /**
         * 是否将通用的参数拼接到url上
         *
         * @param joinParamIntoUrl true:通用参数将被添加到请求头上 false:通用参数根据请求方式的普通添加到对应的参数中
         */
        public Builder joinParamsIntoUrl(boolean joinParamIntoUrl) {
            this.mJoinParamIntoUrl = joinParamIntoUrl;
            return this;
        }

        public ApiClient builder() {
            ApiClient apiClient = ApiClient.getInstance();
            apiClient.setBuilder(this);
            return apiClient;
        }

    }
}

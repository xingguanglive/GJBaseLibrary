package tv.guojiang.baselib.network.config;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tv.guojiang.baselib.network.interceptor.MockInterceptor;
import tv.guojiang.baselib.network.interceptor.UrlInterceptor;

/**
 * OkHttp与Retrofit的配置信息。接口业务状态码请在{@link ServerCode}中进行配置。
 */
public class ApiClient {

    private Retrofit mRetrofit;

    private String mBaseUrl;

    private UrlInterceptor mUrlInterceptor;

    private ApiClient() {
    }

    private static final class Singleton {

        private static final ApiClient INSTANCE = new ApiClient();
    }

    public static ApiClient getInstance() {
        return Singleton.INSTANCE;
    }

    /**
     * 初始化BaseUrl.<em>Url格式为 http://www.guojiang.tv/ </em>
     */
    public void setBaseUrl(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }

    public void setUrlInterceptor(UrlInterceptor interceptor) {
        mUrlInterceptor = interceptor;
    }

    public UrlInterceptor getUrlInterceptor() {
        return mUrlInterceptor;
    }

    /**
     * 默认的OkHttpClient
     *
     * @param logEnable 是否打印网络日志
     */
    public OkHttpClient getOkHttpClient(boolean logEnable) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (logEnable) {
            // 网络请求Log信息
            HttpLoggingInterceptor loggerInterceptor = new HttpLoggingInterceptor();
            loggerInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggerInterceptor);
            builder.addInterceptor(new MockInterceptor());
        }

        return builder.build();
    }

    /**
     * 初始化Retrofit
     */
    public void initRetrofit(OkHttpClient okHttpClient) {
        mRetrofit = new Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build();
    }

    /**
     * 获取  Retrofit Service. 预留的,暂时可以不使用
     *
     * @param serviceClazz Retrofit Api Service's Class
     * @param retrofit Retrofit
     * @return Retrofit Api Service Object
     */
    public <T> T getApiService(Class<T> serviceClazz, Retrofit retrofit) {

        if (retrofit == null) {
            throw new NullPointerException(
                "call ApiClient.getInstance().initRetrofit(OkHttpClient client) first!!!");
        }

        if (mBaseUrl == null) {
            throw new NullPointerException(
                "call ApiClient.getInstance().setBaseUrl(String baseUrl) first!!!");
        }

        return retrofit.create(serviceClazz);
    }

    /**
     * 获取通用的 Retrofit Service
     *
     * @param serviceClazz Retrofit Api Service's Class
     * @return Retrofit Api Service Object
     */
    public <T> T getApiService(Class<T> serviceClazz) {
        return getApiService(serviceClazz, mRetrofit);
    }
}

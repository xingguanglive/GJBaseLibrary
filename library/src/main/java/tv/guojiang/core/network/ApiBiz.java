package tv.guojiang.core.network;

import android.content.Context;
import io.reactivex.Observable;
import java.io.File;
import java.util.Map;
import tv.guojiang.core.network.annotation.Cache;
import tv.guojiang.core.network.cache.CacheState;
import tv.guojiang.core.network.cache.RxCache;
import tv.guojiang.core.network.request.BaseRequest;
import tv.guojiang.core.network.request.RxNetwork;
import tv.guojiang.core.util.NetworkUtils;

/**
 * 处理网络与接口缓存的类
 */
public class ApiBiz {

    private Context mContext;

    /**
     * 网络数据处理
     */
    private RxNetwork mRxNetwork;

    /**
     * 缓存数据处理
     */
    private RxCache mRxCache;

    private ApiClient mApiClient;

    private ApiBiz() {
    }

    private static final class Singleton {

        private static final ApiBiz INSTANCE = new ApiBiz();
    }

    public static ApiBiz getInstance() {
        return Singleton.INSTANCE;
    }

    /**
     * 设置 ApiClient
     */
    public void setApiClient(ApiClient apiClient) {
        if (apiClient == null) {
            throw new NullPointerException("apiClient == null");
        }

        mApiClient = apiClient;
        mContext = apiClient.getContext();
        mRxNetwork = new RxNetwork(apiClient);
        mRxCache = new RxCache(mContext);
    }

    /**
     * 发送 GET 请求
     *
     * @param url 全路径接口地址
     * @param request 请求
     */
    public <T extends BaseRequest> Observable<String> get(String url, T request) {

        Map<String, String> params = concatParams(request.getParams());

        Cache cache = RxCache.getCacheAnnotation(request);
        if (cache == null) {
            // 不使用缓存
            return mRxNetwork.get(url, request);
        }

        Observable<String> cacheObservable = mRxCache.getCache(url, params, cache);
        Observable<String> networkObservable = mRxNetwork.get(url, request)
            .doOnNext(json -> mRxCache.saveCache(url, params, json));

        return concatObservable(cacheObservable, networkObservable, cache.state(), request);
    }

    /**
     * 发送 POST 请求
     *
     * @param url 全路径接口地址
     * @param request 请求
     */
    public <T extends BaseRequest> Observable<String> post(String url, T request) {

        Map<String, String> params = concatParams(request.getParams());

        Cache cache = RxCache.getCacheAnnotation(request);
        if (cache == null) {
            // 不使用缓存
            return mRxNetwork.post(url, request);
        }

        Observable<String> cacheObservable = mRxCache.getCache(url, params, cache);
        Observable<String> networkObservable = mRxNetwork.post(url, request)
            .doOnNext(json -> mRxCache.saveCache(url, params, json));

        return concatObservable(cacheObservable, networkObservable, cache.state(), request);
    }

    /**
     * 文件上传
     */
    public <T extends BaseRequest> Observable<String> upload(String url, T request) {
        concatParams(request.getParams());
        return mRxNetwork.uploadFile(url, request);
    }

    /**
     * 文件下载
     *
     * @param url 全路径文件地址
     * @param file 下载之后的文件
     */
    public Observable<File> download(String url, File file) {
        return mRxNetwork.download(url, file);
    }

    /**
     * 网络请求与缓存的合并处理
     */
    private Observable<String> concatObservable(Observable<String> cache,
        Observable<String> network,
        CacheState state, BaseRequest request) {
        switch (state) {
            case FOCUS_CACHE_UNTIL_REFRESH:
                if (request.refreshApi) {
                    return network;
                } else {
                    return Observable.concat(cache, network).firstElement().toObservable();
                }
            case FOCUS_CACHE_AND_NETWORK:
                return Observable.concat(cache, network);
            case FOCUS_CACHE_UNTIL_ONLINE:
                if (NetworkUtils.isNetworkAvailable(mContext)) {
                    return network;
                } else {
                    return Observable.concat(cache, network).firstElement().toObservable();
                }
            case FOCUS_CACHE:
            default:
                return Observable.concat(cache, network).firstElement().toObservable();
        }
    }

    /**
     * 合并通用的参数
     */
    private Map<String, String> concatParams(Map<String, String> source) {
        Map<String, String> params = mApiClient.getParams();
        if (params != null && params.size() > 0) {
            source.putAll(params);
        }
        return source;
    }
}

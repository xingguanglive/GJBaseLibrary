package tv.guojiang.baselib.network;

import io.reactivex.Observable;
import java.io.File;
import tv.guojiang.baselib.network.cache.ApiCache;
import tv.guojiang.baselib.network.config.ApiClient;
import tv.guojiang.baselib.network.request.BaseRequest;
import tv.guojiang.baselib.network.request.RxNetwork;

/**
 * 网络请求统一处理.
 */
public class NetworkBiz {

    private RxNetwork mRxNetwork;

    /**
     * 接口缓存
     */
    private ApiCache mApiCache;

    private NetworkBiz() {
    }

    private static final class Singleton {

        private static final NetworkBiz INSTANCE = new NetworkBiz();
    }

    public static NetworkBiz getInstance() {
        return Singleton.INSTANCE;
    }

    /**
     * 设置 ApiClient
     */
    public void setApiClient(ApiClient apiClient) {
        if (apiClient == null) {
            throw new NullPointerException("apiClient == null");
        }

        mRxNetwork = new RxNetwork(apiClient);
        mApiCache = new ApiCache(apiClient.getContext());
    }

    /**
     * 发送 GET 请求
     *
     * @param url 全路径接口地址
     * @param request 请求
     */
    public <T extends BaseRequest> Observable<String> get(String url, T request) {
        return mRxNetwork.get(url, request);
    }


    /**
     * 发送 POST 请求
     *
     * @param url 全路径接口地址
     * @param request 请求
     */
    public <T extends BaseRequest> Observable<String> post(String url, T request) {
        return mRxNetwork.post(url, request);
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
}

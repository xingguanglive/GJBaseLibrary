package tv.guojiang.baselib.network;

import io.reactivex.Observable;
import java.io.File;
import java.util.Map;
import okhttp3.ResponseBody;
import tv.guojiang.baselib.network.config.ApiClient;
import tv.guojiang.baselib.network.config.BaseApi;
import tv.guojiang.baselib.network.request.BaseRequest;
import tv.guojiang.baselib.network.response.DownloadFunction;

/**
 * 网络请求统一处理.
 */
public class NetworkBiz {

    private ApiClient mApiClient;

    private BaseApi mBaseApi;

    private NetworkBiz() {
    }

    private static final class Singleton {

        private static final NetworkBiz INSTANCE = new NetworkBiz();
    }

    /**
     * 设置 ApiClient
     */
    public void setApiClient(ApiClient apiClient) {
        if (apiClient == null) {
            throw new NullPointerException("apiClient == null");
        }
        mApiClient = apiClient;
        mBaseApi = mApiClient.getApiService(BaseApi.class);
    }

    public static NetworkBiz getInstance() {
        return Singleton.INSTANCE;
    }

    /**
     * 最终发起请求所用的Url
     */
    private Observable<String> getFinalUrl(String url) {
        return Observable.fromCallable(() -> {

            String baseUrl = mApiClient.getBaseUrl();

            StringBuilder sb = new StringBuilder();
            sb.append(baseUrl);
            sb.append(url);

            return sb.toString();
        });
    }

    /**
     * 发送 GET 请求
     *
     * @param url 接口地址
     * @param request 请求
     */
    public <T extends BaseRequest> Observable<String> get(String url, T request) {
        return getFinalUrl(url)
            .flatMap(finalUrl -> mBaseApi.get(finalUrl, joinParams(request.getRequestParams())))
            .map(ResponseBody::string);
    }

    /**
     * 发送 POST 请求
     *
     * @param url 接口地址
     * @param request 请求
     */
    public <T extends BaseRequest> Observable<String> post(String url, BaseRequest request) {
        return getFinalUrl(url)
            .flatMap(finalUrl -> mBaseApi.post(finalUrl, joinParams(request.getRequestParams())))
            .map(ResponseBody::string);
    }

    /**
     * 文件下载
     *
     * @param url 文件地址
     * @param file 下载之后的文件
     */
    public Observable<File> download(String url, File file) {
        return getFinalUrl(url)
            .flatMap(finalUrl -> mBaseApi.download(url))
            .map(new DownloadFunction(file));
    }

    private Map<String, String> joinParams(Map<String, String> source) {
        Map<String, String> params = mApiClient.getParams();
        if (params != null && params.size() > 0) {
            source.putAll(params);
        }
        return source;
    }

}

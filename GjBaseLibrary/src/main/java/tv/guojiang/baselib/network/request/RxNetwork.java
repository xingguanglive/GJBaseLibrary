package tv.guojiang.baselib.network.request;

import io.reactivex.Observable;
import java.io.File;
import java.util.Map;
import okhttp3.ResponseBody;
import tv.guojiang.baselib.network.config.ApiClient;
import tv.guojiang.baselib.network.config.BaseApi;
import tv.guojiang.baselib.network.response.ApiExceptionFilterFunction;
import tv.guojiang.baselib.network.response.DownloadFunction;

/**
 * 网络访问处理
 *
 * @author leo
 */
public class RxNetwork {

    /**
     * 接口通用配置
     */
    private ApiClient mApiClient;

    /**
     * Retrofit Api
     */
    private BaseApi mBaseApi;

    public RxNetwork(ApiClient apiClient) {
        mApiClient = apiClient;
        mBaseApi = mApiClient.getApiService(BaseApi.class);
    }

    /**
     * 最终发起请求所用的Url
     */
    private Observable<String> getFinalUrl(String url) {
        return Observable.just(url);
    }

    /**
     * 发送 GET 请求
     *
     * @param url 全路径接口地址
     * @param request 请求
     */
    public <T extends BaseRequest> Observable<String> get(String url, T request) {
        //@formatter:off
        return getFinalUrl(url)
            .flatMap(finalUrl -> mBaseApi.get(finalUrl, request.getHeaders(),joinParams(request.getParams())))
            .map(ResponseBody::string)//@formatter:on
            .map(new ApiExceptionFilterFunction());
    }

    /**
     * 发送 POST 请求
     *
     * @param url 全路径接口地址
     * @param request 请求
     */
    public <T extends BaseRequest> Observable<String> post(String url, T request) {
        //@formatter:off
        return getFinalUrl(url)
            .flatMap(finalUrl -> mBaseApi.post(finalUrl,request.getHeaders(), joinParams(request.getParams())))
            .map(ResponseBody::string)//@formatter:on
            .map(new ApiExceptionFilterFunction());
    }


    /**
     * 文件下载
     *
     * @param url 全路径文件地址
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

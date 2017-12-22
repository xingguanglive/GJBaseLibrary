package tv.guojiang.baselib.network;

import io.reactivex.Observable;
import java.io.File;
import okhttp3.ResponseBody;
import tv.guojiang.baselib.network.config.ApiClient;
import tv.guojiang.baselib.network.config.BaseApi;
import tv.guojiang.baselib.network.interceptor.UrlInterceptor;
import tv.guojiang.baselib.network.request.BaseRequest;
import tv.guojiang.baselib.network.response.DownloadFunction;

/**
 * 网络请求统一处理.
 */
public class NetworkBiz {

    private BaseApi mBaseApi = ApiClient.getInstance().getApiService(BaseApi.class);

    private NetworkBiz() {
    }

    private static final class Singleton {

        private static final NetworkBiz INSTANCE = new NetworkBiz();
    }

    public static NetworkBiz getInstance() {
        return Singleton.INSTANCE;
    }

    /**
     * 最终发起请求所用的Url
     */
    private Observable<String> getFinalUrl(String url) {
        return
            Observable.fromCallable(() -> {
                String baseUrl = ApiClient.getInstance().getBaseUrl();

                StringBuilder sb = new StringBuilder();
                sb.append(baseUrl);
                sb.append(url);

                // 公共拼接在url中的参数
                UrlInterceptor urlInterceptor = ApiClient.getInstance().getUrlInterceptor();
                if (urlInterceptor != null) {
                    sb.append(urlInterceptor.intercept(url));
                }
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
        return
            getFinalUrl(url)
                .flatMap(finalUrl -> mBaseApi.get(finalUrl, request.getRequestParams()))
                .map(ResponseBody::string);
    }

    /**
     * 发送 POST 请求
     *
     * @param url 接口地址
     * @param request 请求
     */
    public <T extends BaseRequest> Observable<String> post(String url, T request) {
        return
            getFinalUrl(url)
                .flatMap(finalUrl -> mBaseApi.post(finalUrl, request.getRequestParams()))
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

}

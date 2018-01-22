package tv.guojiang.baselib.network.request;

import android.support.v4.util.ArrayMap;
import com.orhanobut.logger.Logger;
import io.reactivex.Observable;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import tv.guojiang.baselib.network.config.ApiClient;
import tv.guojiang.baselib.network.config.BaseApi;
import tv.guojiang.baselib.network.exception.ApiExceptionFilterFunction;
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
            .flatMap(finalUrl->{
                                Logger.i("******** 从network获取 ********");
                return mBaseApi.get(finalUrl, request.getHeaders(),joinParams(request.getParams()));})
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

    /**
     * 单个文件上传
     */
    public <T extends BaseRequest> Observable<String> uploadFile(String url, T request) {

        // header
        Map<String, String> headers = request.getHeaders();

        // 额外的参数
        Map<String, String> params = request.getParams();
        Map<String, RequestBody> extra = new ArrayMap<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            extra.put(entry.getKey(), RetrofitFormWrapper.param2RequestBody(entry.getValue()));
        }

        // 文件
        Map<String, Object> uploads = request.getUploads();

        Iterator<Entry<String, Object>> iterator = uploads.entrySet().iterator();
        if (!iterator.hasNext()) {
            throw new NullPointerException("choose file first ！！");
        }

        Entry<String, Object> entry = iterator.next();

        String key = entry.getKey();
        Object value = entry.getValue();

        if (value instanceof File) {
            //@formatter:off
                MultipartBody.Part filePart = RetrofitFormWrapper.file2Part(key, (File) value, MediaType.parse("image/*"));
                return getFinalUrl(url)
                    .flatMap(finalUrl -> mBaseApi.uploadFile(finalUrl, headers, params, filePart))
                    .map(ResponseBody::string)
                    .map(new ApiExceptionFilterFunction());//@formatter:on

        } else if (value instanceof List) {
            try {
                List<File> files = (List<File>) value;
                List<MultipartBody.Part> fileParts = new ArrayList<>();
                for (File file : files) {
                    fileParts
                        .add(RetrofitFormWrapper.file2Part(key, file, MediaType.parse("image/*")));
                }
                return getFinalUrl(url)
                    .flatMap(finalUrl -> mBaseApi.uploadFiles(finalUrl, headers, params, fileParts))
                    .map(ResponseBody::string)
                    .map(new ApiExceptionFilterFunction());

            } catch (ClassCastException e) {
                throw new ClassCastException("@Upload supports only File or List<File> !!!");
            }
        } else {
            throw new ClassCastException("@Upload supports only File or List<File> !!!");
        }
    }

    private Map<String, String> joinParams(Map<String, String> source) {
        Map<String, String> params = mApiClient.getParams();
        if (params != null && params.size() > 0) {
            source.putAll(params);
        }
        return source;
    }
}

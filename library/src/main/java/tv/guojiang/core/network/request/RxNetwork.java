package tv.guojiang.core.network.request;

import android.support.v4.util.ArrayMap;
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
import tv.guojiang.core.network.ApiClient;
import tv.guojiang.core.network.config.BaseApi;
import tv.guojiang.core.network.exception.ApiExceptionFilterFunction;
import tv.guojiang.core.network.response.DownloadFunction;
import tv.guojiang.core.util.JsonUtils;

/**
 * 网络访问处理
 *
 * @author leo
 */
public class RxNetwork {

    /**
     * Retrofit Api
     */
    private BaseApi mBaseApi;

    public RxNetwork(ApiClient apiClient) {
        mBaseApi = apiClient.getApiService(BaseApi.class);
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
     * @param request 请求
     */
    public <T extends BaseRequest> Observable<String> get(T request) {
        //@formatter:off
        return getFinalUrl(request.url)
            .flatMap(finalUrl-> mBaseApi.get(finalUrl, request.getHeaders(), request.getParams()))
            .map(ResponseBody::string)//@formatter:on
            .map(new ApiExceptionFilterFunction());
    }

    /**
     * 发送 POST 请求
     *
     * @param request 请求
     */
    public <T extends BaseRequest> Observable<String> post(T request) {
        //@formatter:off
        return getFinalUrl(request.url)
            .flatMap(finalUrl -> mBaseApi.post(finalUrl,request.getHeaders(),  request.getParams()))
            .map(ResponseBody::string)//@formatter:on
            .map(new ApiExceptionFilterFunction());
    }

    public Observable<String> postBody(PostBodyRequest request) {
        return getFinalUrl(request.url)
            .flatMap(finalUrl -> {

                Object body = request.body;
                RequestBody requestBody;

                if (body instanceof String) {
                    requestBody = RetrofitFormWrapper.getStringBody((String)body);
                } else {
                    String json = JsonUtils.getInstance().toJson(request.body);
                    requestBody = RetrofitFormWrapper.getJsonBody(json);
                }

                return mBaseApi.post(finalUrl, request.getHeaders(), requestBody);
            })
            .map(ResponseBody::string)
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
    public <T extends BaseRequest> Observable<String> uploadFile(T request) {

        // 文件
        Map<String, Object> uploads = request.getUploads();

        if (uploads == null || uploads.isEmpty()) {
            return post(request);
        }

        // header
        Map<String, String> headers = request.getHeaders();

        // 额外的参数
        Map<String, String> params = request.getParams();

        Map<String, RequestBody> extra = new ArrayMap<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            extra.put(entry.getKey(), RetrofitFormWrapper.param2RequestBody(entry.getValue()));
        }

        // 文件的类型
        MediaType mediaType = MediaType.parse(request.contentType);

        Iterator<Entry<String, Object>> iterator = uploads.entrySet().iterator();

        Entry<String, Object> entry = iterator.next();

        String key = entry.getKey();
        Object value = entry.getValue();

        if (value instanceof File) {
            //@formatter:off
                MultipartBody.Part filePart = RetrofitFormWrapper.file2Part(key, (File) value,mediaType );
                return getFinalUrl(request.url)
                    .flatMap(finalUrl -> mBaseApi.uploadFile(finalUrl, headers, extra, filePart))
                    .map(ResponseBody::string)
                    .map(new ApiExceptionFilterFunction());//@formatter:on

        } else if (value instanceof List) {
            try {
                List<File> files = (List<File>) value;
                List<MultipartBody.Part> fileParts = new ArrayList<>();
                for (File file : files) {
                    fileParts
                        .add(RetrofitFormWrapper.file2Part(key, file, mediaType));
                }
                return getFinalUrl(request.url)
                    .flatMap(finalUrl -> mBaseApi.uploadFiles(finalUrl, headers, extra, fileParts))
                    .map(ResponseBody::string)
                    .map(new ApiExceptionFilterFunction());

            } catch (ClassCastException e) {
                throw new ClassCastException("@Upload supports only File or List<File> !!!");
            }
        } else {
            throw new ClassCastException("@Upload supports only File or List<File> !!!");
        }
    }
}

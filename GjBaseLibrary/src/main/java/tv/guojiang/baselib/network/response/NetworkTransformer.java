package tv.guojiang.baselib.network.response;

import com.google.gson.reflect.TypeToken;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import java.lang.reflect.Type;
import tv.guojiang.baselib.network.NetworkExceptionTransformer;
import tv.guojiang.baselib.network.config.ServerCode;
import tv.guojiang.baselib.network.exception.ApiException;
import tv.guojiang.baselib.util.JsonUtils;

/**
 * 将接口的数据转换为实体类.
 *
 * <pre>
 * 接口数据:
 *     {"errno":0, "msg":"", "data":{}}
 * </pre>
 *
 * @author leo
 */
public class NetworkTransformer<T> implements ObservableTransformer<String, BaseResponse<T>> {

    /**
     * "data"对应的类型
     */
    private Class<T> mDataClazz;

    public NetworkTransformer(Class<T> dataClazz) {
        mDataClazz = dataClazz;
    }

    @Override
    public ObservableSource<BaseResponse<T>> apply(Observable<String> upstream) {
        return upstream
            .map(json -> {
                // 将json->BaseResponse<T> 对象
                Type type = TypeToken.getParameterized(BaseResponse.class, mDataClazz).getType();
                BaseResponse<T> response = JsonUtils.getInstance().fromJson(json, type);

                int code = response.code;
                // 封装业务错误
                if (code != ServerCode.SUCCESS) {
                    throw new ApiException(code, response.msg);
                }

                return response;
            })
            .compose(new NetworkExceptionTransformer<>());
    }
}

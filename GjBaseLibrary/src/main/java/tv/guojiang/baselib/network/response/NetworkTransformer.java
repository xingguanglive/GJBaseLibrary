package tv.guojiang.baselib.network.response;

import com.google.gson.reflect.TypeToken;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import java.lang.reflect.Type;
import tv.guojiang.baselib.util.GsonProvider;

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
            .map(new Function<String, BaseResponse<T>>() {
                @Override
                public BaseResponse<T> apply(String json) throws Exception {
                    // 将json->BaseResponse<T> 对象
                    Type type = TypeToken.getParameterized(Response.class, mDataClazz).getType();
                    return GsonProvider.getInstance().fromJson(json, type);
                }
            });

        // todo 错误处理
    }
}

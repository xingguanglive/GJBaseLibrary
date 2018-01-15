package tv.guojiang.baselib.network.response;

import com.google.gson.reflect.TypeToken;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import java.lang.reflect.Type;
import org.json.JSONObject;
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

                // {"errno":0, "msg":"", "data":{}}

                JSONObject jsonObject = new JSONObject(json);
                int code = jsonObject.optInt("errno", ServerCode.SERVER_ERROR);

                // 封装业务错误
                // 避免后台给的 data 不是object 引发异常
                if (code != ServerCode.SUCCESS) {
                    String msg = jsonObject.optString("msg", "No error message from serve!");
                    throw new ApiException(code, msg);
                }

                // 将json->BaseResponse<T> 对象
                Type type = TypeToken.getParameterized(BaseResponse.class, mDataClazz).getType();

                return JsonUtils.getInstance().<BaseResponse<T>>fromJson(json, type);
            })
            .compose(new NetworkExceptionTransformer<>());
    }
}

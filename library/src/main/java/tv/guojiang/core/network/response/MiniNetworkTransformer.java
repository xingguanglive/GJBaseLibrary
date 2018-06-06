package tv.guojiang.core.network.response;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import tv.guojiang.core.network.exception.NetworkExceptionTransformer;
import tv.guojiang.core.util.JsonUtils;

/**
 * 将接口的数据转换为实体类.
 *
 * <pre>
 * 接口数据:不用关心data
 *     {"errno":0, "msg":""}
 * </pre>
 *
 * @author leo
 */
public class MiniNetworkTransformer implements ObservableTransformer<String, BaseResponse> {

    @Override
    public ObservableSource<BaseResponse> apply(Observable<String> upstream) {
        return upstream
            .map(json -> JsonUtils.getInstance().fromJson(json, BaseResponse.class))
            .compose(new NetworkExceptionTransformer<>());
    }
}

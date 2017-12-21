package tv.guojiang.baselib.network.response;

import com.google.gson.reflect.TypeToken;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import java.lang.reflect.Type;
import tv.guojiang.baselib.util.GsonProvider;

/**
 * 对分页的json数据进行解析
 * <pre>
 * 分页接口数据：
 *     {"errno":0, "msg":"", "data":[]}
 * </pre>
 *
 * @author leo
 */
public class PagerNetworkTransformer<T> implements ObservableTransformer<String, PagerResponse<T>> {

    /**
     * "data"中每个Item对象的类型
     */
    private Class<T> mItemClazz;

    public PagerNetworkTransformer(Class<T> itemClazz) {
        mItemClazz = itemClazz;
    }

    @Override
    public ObservableSource<PagerResponse<T>> apply(Observable<String> upstream) {
        return upstream
            .map(json -> {
                Type type = TypeToken.getParameterized(PagerResponse.class, mItemClazz).getType();
                return GsonProvider.getInstance().fromJson(json, type);
            });
    }
}

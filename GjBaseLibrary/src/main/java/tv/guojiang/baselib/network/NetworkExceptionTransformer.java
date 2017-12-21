package tv.guojiang.baselib.network;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import tv.guojiang.baselib.network.config.ServerCode;
import tv.guojiang.baselib.network.exception.ApiException;
import tv.guojiang.baselib.network.exception.NetworkExceptionWrapper;
import tv.guojiang.baselib.network.response.BaseResponse;

/**
 * 处理网络错误的Transformer.
 *
 * @author leo
 */
public class NetworkExceptionTransformer<T> implements
    ObservableTransformer<BaseResponse<T>, BaseResponse<T>> {

    @Override
    public ObservableSource<BaseResponse<T>> apply(Observable<BaseResponse<T>> upstream) {
        return upstream
            .doOnNext(response -> {
                int code = response.code;
                // 封装业务错误
                if (code != ServerCode.SUCCESS) {
                    throw new ApiException(code, response.msg);
                }
            })
            // 调用链只要有错误发生时，就会调用该方法
            // 不需要再做其他的错误处理
            .onErrorResumeNext(throwable -> {
                // 对错误进行封装处理
                // 最终会触发Subscriber的onError方法
                return Observable.error(NetworkExceptionWrapper.wrapException(throwable));
            })
            // 错误重试机制,放到租最后,错误已经封装
            .retryWhen(new RetryFunction());
    }
}

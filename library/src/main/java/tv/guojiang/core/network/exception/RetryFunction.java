package tv.guojiang.core.network.exception;

import com.orhanobut.logger.Logger;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import java.util.concurrent.TimeUnit;

/**
 * 错误重试机制.
 *
 * @author leo
 */
public class RetryFunction implements Function<Observable<Throwable>, Observable<Long>> {

    // 重试总次数
    private int mMaxRetryCount;

    // 重试延迟
    private int mRetryDelay;

    // 当前重试的次数
    private int mRetryCount;

    public RetryFunction() {
        this(3, 1000);
    }

    public RetryFunction(int maxRetryCount, int retryDelay) {
        mMaxRetryCount = maxRetryCount;
        mRetryDelay = retryDelay;
    }

    @Override
    public Observable<Long> apply(Observable<Throwable> throwableObservable) throws Exception {
        return throwableObservable
            .flatMap(throwable -> {
                if (throwable instanceof NetworkException) {
                    // TIMEOUT 或者 NO_CONNECTED 才有必要重试
                    NetworkException networkException = (NetworkException) throwable;
                    int code = networkException.getCode();
                    if (code != NetworkExceptionWrapper.SERVER_ERROR.NO_CONNECT
                        && code != NetworkExceptionWrapper.SERVER_ERROR.NETWORK_ERROR
                        && code != NetworkExceptionWrapper.SERVER_ERROR.TIMEOUT_ERROR) {
                        // 业务错误,无需重试
                        return Observable.error(throwable);
                    }
                }

                // 重试
                if (++mRetryCount <= mMaxRetryCount) {
                    Logger.i("正在重试中......");
                    return Observable.timer(mRetryDelay, TimeUnit.MILLISECONDS);
                }
                // 不重试
                return Observable.error(throwable);
            });
    }
}

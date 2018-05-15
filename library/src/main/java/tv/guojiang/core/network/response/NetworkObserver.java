package tv.guojiang.core.network.response;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import tv.guojiang.core.network.exception.ApiException;
import tv.guojiang.core.network.exception.NetworkException;
import tv.guojiang.core.util.UIUtils;

/**
 * 网络访问结果处理的{@link Observer}
 *
 * @author leo
 */
public abstract class NetworkObserver<T> implements Observer<T> {

    /**
     * 链条运作时的回调
     *
     * @param d 用来取消订阅
     */
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public abstract void onNext(T t);

    @Override
    public void onError(Throwable e) {
        if (e instanceof NetworkException) {
            NetworkException networkException = (NetworkException) e;
            Throwable cause = networkException.getCause();

            if (cause instanceof ApiException && onFailed(networkException.getCode())) {
                // 业务错误
                UIUtils.showToast(e.getMessage());
            } else if (onError(networkException.getCode())) {
                // 网络错误
                UIUtils.showToast(e.getMessage());
            }
        }
    }

    /**
     * 业务错误的回掉.
     *
     * @param errorCode 错误码
     * @return True 错误时给出Toast提示，默认为True
     */
    protected boolean onFailed(int errorCode) {
        return true;
    }

    /**
     * 网络错误的回掉
     *
     * @param errorCode 错误码
     * @return True 错误时给出Toast提示，默认为True
     */
    protected boolean onError(int errorCode) {
        return true;
    }

    /**
     * 网络加载完成回掉
     */
    @Override
    public void onComplete() {

    }

}

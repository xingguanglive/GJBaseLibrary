package tv.guojiang.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import tv.guojiang.base.R;
import tv.guojiang.baselib.network.NetworkBiz;
import tv.guojiang.baselib.network.NetworkExceptionTransformer;
import tv.guojiang.baselib.network.config.ApiClient;
import tv.guojiang.baselib.network.request.BaseRequest;
import tv.guojiang.baselib.network.response.BaseResponse;
import tv.guojiang.baselib.network.response.NetworkTransformer;

/**
 * @author leo
 */
public class NetworkSampleActivity extends AppCompatActivity {

    private static final String TAG = "Network";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_network_sample);

        OkHttpClient okHttpClient = ApiClient.getInstance().getOkHttpClient(true);
        ApiClient.getInstance().setBaseUrl("www.guojiang.tv/");
        ApiClient.getInstance().initRetrofit(okHttpClient);
    }

    public void get(View view) {

        NetworkBiz.getInstance().get("a/b/c", new BaseRequest())
            .compose(new NetworkTransformer<>(Person.class))
            .compose(new NetworkExceptionTransformer<Person>())
            .subscribe(new Observer<BaseResponse<Person>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(BaseResponse<Person> response) {
                    Log.d(TAG, response.data.name);
                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG, e.getMessage(), e);
                }

                @Override
                public void onComplete() {

                }
            });

    }

    static class Person {

        public String name;
        public int age;
    }
}

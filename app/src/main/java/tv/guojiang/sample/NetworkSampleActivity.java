package tv.guojiang.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.google.gson.annotations.SerializedName;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import tv.guojiang.base.R;
import tv.guojiang.baselib.network.ApiBiz;
import tv.guojiang.baselib.network.annotation.Cache;
import tv.guojiang.baselib.network.annotation.Header;
import tv.guojiang.baselib.network.cache.CacheState;
import tv.guojiang.baselib.network.config.ApiClient;
import tv.guojiang.baselib.network.config.ApiClient.Builder;
import tv.guojiang.baselib.network.request.BaseRequest;
import tv.guojiang.baselib.network.response.BaseResponse;
import tv.guojiang.baselib.network.response.NetworkObserver;
import tv.guojiang.baselib.network.response.NetworkTransformer;
import tv.guojiang.baselib.rx.NormalSchedulerTransformer;
import tv.guojiang.baselib.util.SSLUtils;

/**
 * @author leo
 */
public class NetworkSampleActivity extends AppCompatActivity {

    private static final String TAG = "Network";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_network_sample);

        Logger.addLogAdapter(new AndroidLogAdapter());

        // 初始化全局接口通用配置
        ApiClient apiClient = new Builder(this)
            .baseUrl("http://www.baidu.com/")
            .log(false)
            .cookie(true)
            .joinParamsIntoUrl(true)
            //            .mockData(true) // 模拟数据会直接跳过外网的访问，直接成功
            .header("user-agent", "android")
            .param("copyright", "GJ-Platform")
            .build();

        ApiBiz.getInstance().setApiClient(apiClient);
    }

    public void login(View view) {

        LoginRequest request = new LoginRequest();
        request.username = "18175140004";
        request.password = SSLUtils.getEncryptPassword("123456");
        request.remember = true;
        request.android = 999;
        request.business = "Seven-Android";
        request.seven = 77777;

        ApiBiz.getInstance().post("www.baidu.com", request)
            .compose(new NetworkTransformer<>(Person.class))
            // 网络访问已经成功
            // 对成功后的数据进行处理
            .map(personPagerResponse -> {
                Logger.i("数据变换时的线程信息:" + Thread.currentThread().getName());
                return personPagerResponse.data + "";
            })
            // 线程切换放到最后面
            .compose(new NormalSchedulerTransformer<>())
            .subscribe(new NetworkObserver<String>() {
                @Override
                public void onNext(String s) {
                    Logger.w("变换后的结果:" + s);
                }

                @Override
                protected boolean onError(int errorCode) {
                    Toast.makeText(NetworkSampleActivity.this, "onError code : " + errorCode,
                        Toast.LENGTH_SHORT).show();
                    return true;
                }

                @Override
                protected boolean onFailed(int errorCode) {
                    Toast.makeText(NetworkSampleActivity.this, "onFailed code : " + errorCode,
                        Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

    }

    public void home(View view) {
        BaseRequest request = new BaseRequest();

        ApiBiz.getInstance()
            .post("www.baidu.com", request)
            .compose(new NetworkTransformer<>(Data.class))
            .compose(new NormalSchedulerTransformer<>())
            .subscribe(new NetworkObserver<BaseResponse<Data>>() {
                @Override
                public void onNext(BaseResponse<Data> dataBaseResponse) {
                    Logger.w(dataBaseResponse.data.serverIp);
                }
            });
    }

    public static class LoginRequest extends BaseRequest {

        public String username;
        public String password;
        public boolean remember;

        public int android;

        @Header
        public String business;

        @Header("seven-leo")
        public int seven;

    }

    public static class Data {

        public String serverIp;
        public String serverPort;
    }

    public static class Person {

        @SerializedName("uid")
        public String uid;
    }

    // ========================================================================================
    // ================================== 接口缓存测试 =========================================

    @Cache(maxAge = 30,state = CacheState.FOCUS_CACHE)
    private class TestRequest extends BaseRequest {

    }

    public void testCache(View view) {

        TestRequest testRequest = new TestRequest();
        testRequest.refreshApi = true;

        ApiBiz.getInstance().get("http://www.baidu.com", testRequest)
            .compose(new NormalSchedulerTransformer<>())
            .subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(String s) {
                    Logger.d(s);
                }

                @Override
                public void onError(Throwable e) {
                    Logger.e(e, e.getMessage());
                }

                @Override
                public void onComplete() {

                }
            });
    }
}

package tv.guojiang.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.google.gson.annotations.SerializedName;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import tv.guojiang.base.R;
import tv.guojiang.baselib.network.NetworkBiz;
import tv.guojiang.baselib.network.NetworkObserver;
import tv.guojiang.baselib.network.annotation.Header;
import tv.guojiang.baselib.network.config.ApiClient;
import tv.guojiang.baselib.network.config.ApiClient.Builder;
import tv.guojiang.baselib.network.request.BaseRequest;
import tv.guojiang.baselib.network.response.BaseResponse;
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

        // 初始化全局配置
        ApiClient apiClient = new Builder(this)
            .baseUrl("http://www.baidu.com/")
            .httpLogEnable(true)
            .cookie(true)
            .joinParamsIntoUrl(false)
            //            .mockData(true) // 模拟数据会直接跳过外网的访问，直接成功
            .header("user-agent", "android")
            //            .param("param-key", "param-value")
            .build();

        NetworkBiz.getInstance().setApiClient(apiClient);
    }

    public void login(View view) {

        LoginRequest request = new LoginRequest();
        request.username = "18175140004";
        request.password = SSLUtils.getEncryptPassword("123456");
        request.remember = true;
        request.android = 999;
        request.business = "Seven-Android";
        request.seven = 77777;

        NetworkBiz.getInstance().post(
            "http://106.75.114.173/user/login?version=4.2.0&platform=android&packageId=7&channel=developer-default&deviceName=Vivo+X7&androidVersion=5.1.1",
            request)
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
            });

    }

    public void home(View view) {
        BaseRequest request = new BaseRequest();

        NetworkBiz.getInstance().post(
            "http://106.75.114.173/chat/getConfig?version=4.2.0&platform=android&packageId=7&channel=developer-default&deviceName=Vivo+X7&androidVersion=5.1.1",
            request)
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

        @Header("business")
        public String business;

        @Header("seven")
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
}

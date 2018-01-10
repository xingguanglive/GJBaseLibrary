package tv.guojiang.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.google.gson.annotations.SerializedName;
import tv.guojiang.base.R;
import tv.guojiang.baselib.network.NetworkBiz;
import tv.guojiang.baselib.network.NetworkObserver;
import tv.guojiang.baselib.network.config.ApiClient;
import tv.guojiang.baselib.network.config.ApiClient.Builder;
import tv.guojiang.baselib.network.request.BaseRequest;
import tv.guojiang.baselib.network.request.PagerRequest;
import tv.guojiang.baselib.network.response.PagerNetworkTransformer;
import tv.guojiang.baselib.network.response.PagerResponse;
import tv.guojiang.baselib.rx.NormalSchedulerTransformer;

/**
 * @author leo
 */
public class NetworkSampleActivity extends AppCompatActivity {

    private static final String TAG = "Network";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_network_sample);

        // 初始化
        ApiClient apiClient = new Builder()
            .baseUrl("http://www.baidu.com/")
            .httpLogEnable(true)
            .joinParamsIntoUrl(false)
            .mockData(true)
            .header("header-key", "header.value")
            .param("param-key", "param-value")
            .build();

        NetworkBiz.getInstance().setApiClient(apiClient);
    }

    public void get(View view) {

        LoginRequest request = new LoginRequest();
        request.user = "leo";
        request.password = "root";
        request.version = 7;

        NetworkBiz.getInstance().get("a/b/c", request)
            .compose(new PagerNetworkTransformer<>(Person.class))
            .compose(new NormalSchedulerTransformer<>())
            .subscribe(new NetworkObserver<PagerResponse<Person>>() {
                @Override
                public void onNext(PagerResponse<Person> personPagerResponse) {

                }
            });

    }

    public void post(View view) {
        PagerRequest request = new PagerRequest();
        request.pagerSize = 20;
        request.pager = 1;

        NetworkBiz.getInstance().post("a/b/c", request)
            .compose(new PagerNetworkTransformer<>(Person.class))
            .compose(new NormalSchedulerTransformer<>())
            .subscribe(new NetworkObserver<PagerResponse<Person>>() {
                @Override
                public void onNext(PagerResponse<Person> personPagerResponse) {

                }
            });
    }

    public static class LoginRequest extends BaseRequest {

        @SerializedName("androidVersion")
        public int version;

        public String user;

        public String password;

    }

    public static class Person {

        @SerializedName("name")
        public String name;

        @SerializedName("age")
        public int age;
    }
}

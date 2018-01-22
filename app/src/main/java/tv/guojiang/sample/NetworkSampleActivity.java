package tv.guojiang.sample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import tv.guojiang.base.R;
import tv.guojiang.baselib.network.ApiBiz;
import tv.guojiang.baselib.network.config.ApiClient;
import tv.guojiang.baselib.network.config.ApiClient.Builder;
import tv.guojiang.baselib.network.request.PagerRequest;
import tv.guojiang.baselib.network.response.NetworkObserver;
import tv.guojiang.baselib.network.response.NetworkTransformer;
import tv.guojiang.baselib.network.response.PagerNetworkTransformer;
import tv.guojiang.baselib.network.response.PagerResponse;
import tv.guojiang.baselib.rx.NormalSchedulerTransformer;
import tv.guojiang.helper.TakePhotoHelper;
import tv.guojiang.network.LoginRequest;
import tv.guojiang.network.Person;
import tv.guojiang.network.PostRequest;
import tv.guojiang.network.TestRequest;
import tv.guojiang.network.UploadRequest;

/**
 * @author leo
 */
public class NetworkSampleActivity extends AppCompatActivity {

    private static final String TAG = "Network";
    private TakePhotoHelper mTakePhotoHelper;

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
            .joinParamsIntoUrl(false)
            //            .mockData(true) // 模拟数据会直接跳过外网的访问，直接成功
            .header("user-agent", "android")
            .param("copyright", "GJ-Platform")
            .readTimeout(30)
            .writeTimeout(30)
            .connectTimeout(60)
            .timeoutUnit(TimeUnit.SECONDS)
            .build();

        ApiBiz.getInstance().setApiClient(apiClient);
    }

    public void get(View view) {

        LoginRequest request = new LoginRequest();
        request.username = "JonSnow";
        request.password = "King in the north";
        request.remember = true;
        request.android = 999;
        request.business = "Seven-Android";
        request.seven = 77777;

        ApiBiz.getInstance().get("http://localhost:3000/login", request)
            .compose(new NetworkTransformer<>(Person.class))
            // 网络访问已经成功
            // 对成功后的数据进行处理
            .map(response -> {
                Logger.i("数据变换时的线程信息:" + Thread.currentThread().getName());
                return response.data + "";
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

    public void post(View view) {

        PostRequest request = new PostRequest();
        request.age = 24;
        request.name = "Snow";
        request.location = "ShenZhen";

        ApiBiz.getInstance()
            .get("http://localhost:3000/post", request)
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

    public void page(View view) {
        PagerRequest request = new PagerRequest();

        request.pager = 1;
        request.pagerSize = 20;

        ApiBiz.getInstance()
            .get("http://localhost:3000/users", request)
            .compose(new PagerNetworkTransformer<>(Person.class))
            .compose(new NormalSchedulerTransformer<>())
            .subscribe(new NetworkObserver<PagerResponse<Person>>() {
                @Override
                public void onNext(PagerResponse<Person> response) {
                    List<Person> data = response.data;
                    Logger.d(data);
                }
            });
    }

    // ================================== 接口缓存测试 =========================================

    public void cache(View view) {

        TestRequest testRequest = new TestRequest();

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

    // ================================== 文件上传测试 =========================================


    public void upload(View view) {
        mTakePhotoHelper = new TakePhotoHelper(this);
        mTakePhotoHelper.openGallery();

//        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"store_v1.0.sql");
//        uploadFile(file);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        File file = mTakePhotoHelper.onActivityResult(requestCode, resultCode, data);
        if (file != null) {
            uploadFile(file);
        }
    }

    private void uploadFile(File file) {

        UploadRequest request = new UploadRequest();
        request.username = "JonSnow";
        request.password = "King in the north";

        List<File> files = new ArrayList<>();
        files.add(file);
//        files.add(file);

        request.file = files;

        ApiBiz.getInstance()
            .upload("http://api.nohttp.net/upload", request)
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

    // ============================= 文件下载 ====================================

    public void download(View view) {

        String url = "http://static.simpledesktops.com/uploads/desktops/2017/10/02/polymagnet.png";

        File download = new File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "download.png");

        ApiBiz.getInstance()
            .download(url, download)
            .compose(new NormalSchedulerTransformer<>())
            .subscribe(new Observer<File>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(File file) {
                    Toast.makeText(NetworkSampleActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                    Logger.d("下载文件的路径 ---> " + file.getAbsolutePath());
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(NetworkSampleActivity.this, "文件下载失败", Toast.LENGTH_SHORT).show();
                    Logger.e(e, "文件下载失败");
                }

                @Override
                public void onComplete() {

                }
            });


    }

}

package tv.guojiang.sample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import tv.guojiang.base.R;
import tv.guojiang.core.network.ApiBiz;
import tv.guojiang.core.network.ApiClient;
import tv.guojiang.core.network.request.PagerRequest;
import tv.guojiang.core.network.request.PostBodyRequest;
import tv.guojiang.core.network.response.NetworkObserver;
import tv.guojiang.core.network.response.NetworkTransformer;
import tv.guojiang.core.network.response.PagerNetworkTransformer;
import tv.guojiang.core.network.response.PagerResponse;
import tv.guojiang.core.rx.NormalSchedulerTransformer;
import tv.guojiang.helper.TakePhotoHelper;
import tv.guojiang.network.LoginRequest;
import tv.guojiang.network.MockInterceptor;
import tv.guojiang.network.Person;
import tv.guojiang.network.PostRequest;
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

        // 初始化全局接口通用配置
        ApiClient apiClient = new ApiClient.Builder(this)
            .baseUrl("http://www.baidu.com/")
            .log(true)
            //            .cookie(ApiCookie.getInstance(this))
            .joinParamsIntoUrl(false)
            .header("user-agent", "android")
            .param("copyright", "AppLive")
            .readTimeout(30)
            .writeTimeout(30)
            .connectTimeout(60)
            .addInterceptor(new MockInterceptor())
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
        request.url = "http://localhost:3000/login";

        ApiBiz.getInstance().get(request)
            .compose(new NetworkTransformer<>(Person.class))
            // 网络访问已经成功
            // 对成功后的数据进行处理
            .map(response -> {
                Log.i(TAG, "数据变换时的线程信息:" + Thread.currentThread().getName());
                return response.data + "";
            })
            // 线程切换放到最后面
            .compose(new NormalSchedulerTransformer<>())
            .subscribe(new NetworkObserver<String>() {
                @Override
                public void onNext(String s) {
                    Log.i(TAG, "变换后的结果:" + s);
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
        request.url = "http://localhost:3000/post";

        ApiBiz.getInstance()
            .post(request)
            .compose(new NormalSchedulerTransformer<>())
            .subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(String s) {
                    Log.i(TAG, s);
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

    public void page(View view) {
        PagerRequest request = new PagerRequest();

        request.pager = 1;
        request.pagerSize = 20;
        request.url = "http://localhost:3000/users";

        ApiBiz.getInstance()
            .get(request)
            .compose(new PagerNetworkTransformer<>(Person.class))
            .compose(new NormalSchedulerTransformer<>())
            .subscribe(new NetworkObserver<PagerResponse<Person>>() {
                @Override
                public void onNext(PagerResponse<Person> response) {
                    List<Person> data = response.data;
                    Log.i(TAG, data.toString());
                }
            });
    }

    // ================================== 接口缓存测试 =========================================

    public void cache(View view) {

        PostBodyRequest<String> testRequest = new PostBodyRequest<>();
        testRequest.url = "http://www.baidu.com";

        Person person = new Person();
        person.uid = "12345";
        person.username = "Seven";
        testRequest.body = "1,2,3,4,5,";

        ApiBiz.getInstance().postBody(testRequest)
            .compose(new NormalSchedulerTransformer<>())
            .subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(String s) {
                    Toast.makeText(NetworkSampleActivity.this, "哈哈哈，成功啦", Toast.LENGTH_SHORT)
                        .show();
                    Log.d("LEO", s);

                    //                    ApiCookie.getInstance(NetworkSampleActivity.this);

                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG, e.getMessage(), e);

                    //                    String baiduid = ApiCookie.getInstance(NetworkSampleActivity.this)
                    //                        .getCookieValue("BAIDUID");

                    //                    Toast.makeText(NetworkSampleActivity.this, baiduid, Toast.LENGTH_SHORT).show();

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
        request.url = "http://api.nohttp.net/upload";

        List<File> files = new ArrayList<>();
        files.add(file);
        //        files.add(file);

        request.file = files;

        ApiBiz.getInstance()
            .upload(request)
            .compose(new NormalSchedulerTransformer<>())
            .subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(String s) {
                    Log.d(TAG, s);
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
                    Log.d(TAG, "下载文件的路径 ---> " + file.getAbsolutePath());
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(NetworkSampleActivity.this, "文件下载失败", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "文件下载失败");
                }

                @Override
                public void onComplete() {

                }
            });
    }

    public void test(View view) {

        if(true){

            LoginRequest request = new LoginRequest();
            request.username = "JonSnow";
            request.password = "King in the north";
            request.remember = true;
            request.android = 999;
            request.business = "Seven-Android";
            request.seven = 77777;
            request.url = "http://localhost:3000/login";

            ApiBiz.getInstance().get(request)
                .compose(new NetworkTransformer<>(Person.class))
                .map(response -> response.data)
                .compose(new NormalSchedulerTransformer<>())
                .subscribe(new NetworkObserver<Person>() {
                    @Override
                    public void onNext(Person person) {

                    }

                    @Override
                    protected boolean onFailed(int errorCode) {
                        return super.onFailed(errorCode);
                    }
                });


            return;
        }


        new Thread("--> Seven") {
            @Override
            public void run() {

                Observable
                    .fromCallable(() -> {
                        Log.i(TAG, "fromCallable -> " + Thread.currentThread().getName());
                        return "";
                    })
                    // .subscribeOn(Schedulers.io())
                    // .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            Log.i(TAG, "doOnSubscribe -> " + Thread.currentThread().getName());
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    // .as(autoDisposable(from(NetworkSampleActivity.this, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.i(TAG, "onSubscribe -> " + Thread.currentThread().getName());
                        }

                        @Override
                        public void onNext(String s) {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, e.getMessage(), e);
                        }

                        @Override
                        public void onComplete() {
                            Log.w(TAG, "onComplete -> " + Thread.currentThread().getName());
                        }
                    });

            }
        }.start();

    }
}

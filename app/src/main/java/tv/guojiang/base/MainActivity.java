package tv.guojiang.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import tv.guojiang.baselib.image.ImageDirector;
import tv.guojiang.baselib.image.model.ImageConstants;
import tv.guojiang.baselib.log.Logs;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ImageDirector.getInstance(this)
//                .imageBuilder()
//                .imageUrl("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png")
//                .errorImage(R.mipmap.ic_launcher)
//                .loadingImage(R.mipmap.ic_launcher)
//                .into((ImageView) findViewById(R.id.iv_main));
//        ImageDirector.getInstance(this)
//                .imageBuilder()
//                .imageUrl("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png")
//                .errorImage(R.mipmap.ic_launcher)
//                .loadingImage(R.mipmap.ic_launcher)
//                .imageTransformation(ImageConstants.IMAGE_TRANSFOR_CROP_CIRCLE)
//                .into((ImageView) findViewById(R.id.iv_main_circle));
//        ImageDirector.getInstance(this)
//                .imageBuilder()
//                .imageUrl("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png")
//                .errorImage(R.mipmap.ic_launcher)
//                .loadingImage(R.mipmap.ic_launcher)
//                .imageTransformation(ImageConstants.IMAGE_TRANSFOR_CROP_CORNER)
//                .cornerType(RoundedCornersTransformation.CornerType.ALL)
//                .radius(10)
//                .into((ImageView) findViewById(R.id.iv_main_corner));
        log();
    }

    private void log() {
        new Thread() {

            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Logs.e("MainActivity", "thread  ----------- " + i, true);
                }
            }
        }.start();
    }
}

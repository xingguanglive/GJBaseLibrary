package tv.guojiang.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import tv.guojiang.baselib.image.ImageDirector;
import tv.guojiang.baselib.log.Logs;

public class MainActivity extends AppCompatActivity {

    Drawable bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        bitmap = (Drawable) ImageDirector.getInstance(MainActivity.this.getApplicationContext())
                            .imageBuilder().imageUrl(
                                "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png")
                            .intoSyn();
                        Message msg = Message.obtain();
                        msg.obj = bitmap;
                        handler.sendMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ImageView ivMain = findViewById(R.id.iv_main);
            ivMain.setImageDrawable(bitmap);
        }
    };

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

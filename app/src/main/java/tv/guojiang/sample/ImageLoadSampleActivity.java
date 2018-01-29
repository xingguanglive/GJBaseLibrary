package tv.guojiang.sample;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import tv.guojiang.base.R;
import tv.guojiang.baselib.image.ImageDirector;
import tv.guojiang.baselib.image.model.ImageConstants;

/**
 * @author leo
 */
public class ImageLoadSampleActivity extends AppCompatActivity {

    Drawable bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load_sample);

        ImageDirector.getInstance(this)
            .imageBuilder()
            .imageUrl(
                "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png")
            .errorImage(R.mipmap.ic_launcher)
            .loadingImage(R.mipmap.ic_launcher)
            .into((ImageView) findViewById(R.id.iv_main));

        ImageDirector.getInstance(this)
            .imageBuilder()
            .imageUrl(
                "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png")
            .errorImage(R.mipmap.ic_launcher)
            .loadingImage(R.mipmap.ic_launcher)
            .imageTransformation(ImageConstants.IMAGE_TRANSFOR_CROP_CIRCLE)
            .into((ImageView) findViewById(R.id.iv_main_circle));

        ImageDirector.getInstance(this)
            .imageBuilder()
            .imageUrl(
                "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png")
            .errorImage(R.mipmap.ic_launcher)
            .loadingImage(R.mipmap.ic_launcher)
            .imageTransformation(ImageConstants.IMAGE_TRANSFOR_CROP_CORNER)
            .cornerType(RoundedCornersTransformation.CornerType.ALL)
            .radius(10)
            .into((ImageView) findViewById(R.id.iv_main_corner));

        sync();
    }

    private void sync() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        bitmap = (Drawable) ImageDirector
                            .getInstance(ImageLoadSampleActivity.this.getApplicationContext())
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
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ImageView ivMain = findViewById(R.id.iv_main);
            ivMain.setImageDrawable(bitmap);
        }
    };

}

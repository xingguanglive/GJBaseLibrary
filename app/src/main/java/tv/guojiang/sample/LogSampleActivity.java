package tv.guojiang.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import tv.guojiang.base.R;
import tv.guojiang.baselib.log.Logs;

/**
 * @author leo
 */
public class LogSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_log_sample);

        log();
    }

    public void log() {
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

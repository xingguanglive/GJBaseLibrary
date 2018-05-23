package tv.guojiang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import tv.guojiang.base.R;
import tv.guojiang.sample.ImageLoadSampleActivity;
import tv.guojiang.sample.LogSampleActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void imageLoader(View view) {
        Intent intent = new Intent(this, ImageLoadSampleActivity.class);
        startActivity(intent);
    }

    public void log(View view) {
        Intent intent = new Intent(this, LogSampleActivity.class);
        startActivity(intent);
    }
}

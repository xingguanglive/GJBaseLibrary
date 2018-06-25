package tv.guojiang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import tv.guojiang.base.R;
import tv.guojiang.sample.LogSampleActivity;
import tv.guojiang.sample.NetworkSampleActivity;
import tv.guojiang.sample.glide.GlideGridActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Environment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.tv_message);

        // StringBuilder builder = new StringBuilder();
        //
        // File networkCacheDir = FileUtils.getNetworkCacheDir(this);
        //
        // builder.append("networkCacheDir:");
        // builder.append("\n");
        // builder.append(networkCacheDir);
        //
        // textView.setText(builder.toString());

    }

    public void imageLoader(View view) {
        Intent intent = new Intent(this, GlideGridActivity.class);
        //        Intent intent = new Intent(this, ImageLoadSampleActivity.class);
        startActivity(intent);
    }

    public void network(View view) {
        Intent intent = new Intent(this, NetworkSampleActivity.class);
        startActivity(intent);
    }

    public void log(View view) {
        Intent intent = new Intent(this, LogSampleActivity.class);
        startActivity(intent);
    }
}

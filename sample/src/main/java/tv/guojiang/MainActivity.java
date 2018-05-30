package tv.guojiang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import tv.guojiang.base.R;
import tv.guojiang.sample.ImageLoadSampleActivity;
import tv.guojiang.sample.LogSampleActivity;
import tv.guojiang.sample.NetworkSampleActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Environment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        TextView textView = findViewById(R.id.tv_message);
//
//        StringBuilder builder = new StringBuilder();
//
//        File externalStorageFile = FileUtils.getExternalStorageFile(this);
//        if (externalStorageFile == null) {
//            builder.append("externalStorageFile = null");
//        } else {
//            builder.append(externalStorageFile.getAbsolutePath());
//            builder.append("\n");
//            builder.append("=====================================================");
//            builder.append("\n");
//            File core = new File(externalStorageFile, "core");
//            builder.append(core.getAbsolutePath());
//        }
//
//        builder.append("\n");
//        builder.append("=====================================================");
//        builder.append("\n");
//
//        File innerCacheDir = FileUtils.getInnerCacheDir(this);
//        if (innerCacheDir == null) {
//            builder.append("innerCacheDir = null");
//        } else {
//            builder.append(innerCacheDir.getAbsolutePath());
//
//            builder.append("\n");
//            builder.append("=====================================================");
//            builder.append("\n");
//            File core = new File(innerCacheDir, "core");
//            builder.append(core.getAbsolutePath());
//        }
//
//        builder.append("\n");
//        builder.append("=====================================================");
//        builder.append("\n");
//
//        builder.append(FileUtils.getApiCacheDir(this));
//
//        textView.setText(builder.toString());

    }

    public void imageLoader(View view) {
        Intent intent = new Intent(this, ImageLoadSampleActivity.class);
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

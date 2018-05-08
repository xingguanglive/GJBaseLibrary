package tv.guojiang.base;

import android.app.Application;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import tv.guojiang.core.ConfigBuilder;
import tv.guojiang.core.BaseLibConfig;
import tv.guojiang.core.image.factory.GlideFactory;
import tv.guojiang.core.log.Logs;

/**
 * @author Elvis
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initImageConfig();
        Logs.build("guojjjj");
    }


    private void initImageConfig() {
        BaseLibConfig.init(new ConfigBuilder().imageFactory(new GlideFactory()));
    }

    @GlideModule
    public final class MyAppGlideModule extends AppGlideModule {

    }
}

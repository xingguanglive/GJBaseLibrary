package tv.guojiang.base;

import android.app.Application;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import tv.guojiang.baselib.image.ConfigBuilder;
import tv.guojiang.baselib.image.ImageConfig;
import tv.guojiang.baselib.image.factory.GlideFactory;

/**
 * @author Elvis
 * @date 30/11/2017
 * @description tv.guojiang.base
 */

public class App extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		initImageConfig();
	}

	private void initImageConfig() {
		ImageConfig.init(new ConfigBuilder().imageFactory(new GlideFactory()));
	}
	@GlideModule
	public final class MyAppGlideModule extends AppGlideModule {}
}

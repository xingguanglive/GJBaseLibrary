package tv.guojiang.base;

import android.app.Application;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import tv.guojiang.baselib.ConfigBuilder;
import tv.guojiang.baselib.BaseLibConfig;
import tv.guojiang.baselib.image.factory.GlideFactory;
import tv.guojiang.baselib.log.Logs;

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
		Logs.build("guojjjj");
	}


	private void initImageConfig() {
		BaseLibConfig.init(new ConfigBuilder().imageFactory(new GlideFactory()));
	}
	@GlideModule
	public final class MyAppGlideModule extends AppGlideModule {}
}

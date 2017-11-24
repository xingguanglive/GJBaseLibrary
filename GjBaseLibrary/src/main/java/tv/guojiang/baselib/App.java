package tv.guojiang.baselib;

import android.app.Application;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * @author Elvis
 * @date 20/11/2017
 * @description tv.guojiang.baselib
 */

public class App extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@GlideModule
	public final class MyAppGlideModule extends AppGlideModule {
	}
}

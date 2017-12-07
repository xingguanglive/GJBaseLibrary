package tv.guojiang.baselib.image;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import tv.guojiang.baselib.BaseLibConfig;
import tv.guojiang.baselib.image.builder.ImageBuilder;
import tv.guojiang.baselib.image.factory.GlideFactory;
import tv.guojiang.baselib.image.factory.ImageFactory;

/**
 * @author Elvis
 * @date 20/11/2017
 * @description 用于App使用
 */

public class ImageDirector {
	private static ImageDirector imageDirector;
	private static ImageFactory mFactory = BaseLibConfig.mConfigBuilder.imageFactory;
	private static ImageBuilder mImageBuilder;
	private Context mContext;

	private ImageDirector(Context context) {
		this.mContext = context;
		if (mFactory == null) {
			mFactory = new GlideFactory();
		}
	}

	public static ImageDirector getInstance(Context context) {
		if (imageDirector == null) {
			synchronized (ImageDirector.class) {
				if (imageDirector == null) {
					imageDirector = new ImageDirector(context);
				}
			}
		}
		return imageDirector;
	}

	public ImageBuilder imageBuilder() {
		if (mImageBuilder == null) {
			mImageBuilder = new ImageBuilder(mContext);
		}
		// 清除上一次的entity配置
		mImageBuilder.clearEntity();
		return mImageBuilder;
	}

	public ImageDirector loadImage() {
		if (mImageBuilder != null) {
			mFactory.loadImage(mContext, mImageBuilder.mImageEntity);
		}
		return this;
	}

	/**
	 * 加载显示图片
	 *
	 * @param imageBuilder
	 * @return
	 */
	public ImageDirector loadImage(@NonNull ImageBuilder imageBuilder) {
		mFactory.loadImage(mContext, imageBuilder.mImageEntity);
		return this;
	}

	public void clear(@NonNull ImageView imageView) {
		mFactory.clear(mContext, imageView);
	}

	public void clearMemory(@NonNull Context context) {
		mFactory.clearMemory(context);
	}

}

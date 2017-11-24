package tv.guojiang.baselib.image;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import tv.guojiang.baselib.image.builder.ImageBuilder;
import tv.guojiang.baselib.image.factory.ImageFactory;
import tv.guojiang.baselib.image.model.ImageEntity;

/**
 * @author Elvis
 * @date 20/11/2017
 * @description 用于操作工厂：讲builder+factory
 */

public class ImageDirector<T extends ImageBuilder> {
	private static ImageDirector imageDirector;
	public ImageFactory mFactory;
	public T mImageBuilder;
	private Context mContext;

	private ImageDirector(Context context) {
		this.mContext = context;
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

	public ImageDirector factory(ImageFactory factory) {
		mFactory = factory;
		return this;
	}

	public <T extends ImageBuilder> T imageBuilder(T imageBuilder) {
		return imageBuilder;
	}

	public <K extends ImageEntity> ImageDirector loadImage(@NonNull K imageEntity) {
		mFactory.loadImage(mContext, imageEntity);
		return this;
	}

	public void clear(@NonNull ImageView imageView) {
		mFactory.clear(mContext, imageView);
	}

	public void clearMemory(@NonNull Context context) {
		mFactory.clearMemory(context);
	}

}

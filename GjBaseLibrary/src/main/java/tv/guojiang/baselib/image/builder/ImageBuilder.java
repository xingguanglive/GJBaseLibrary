package tv.guojiang.baselib.image.builder;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import tv.guojiang.baselib.image.ImageDirector;
import tv.guojiang.baselib.image.listener.ImageLoadingListener;
import tv.guojiang.baselib.image.model.ImageEntity;
import tv.guojiang.baselib.image.model.ImageSize;

/**
 * @author Elvis
 * @date 20/11/2017
 * @description tv.guojiang.baselib.image
 */

public abstract class ImageBuilder<T extends ImageEntity> {
	public Context mContext;
	public T mImageEntity;

	public ImageBuilder(Context context, T imageEntity) {
		this.mContext = context;
		this.mImageEntity = imageEntity;
	}

	public ImageBuilder imageSize(@NonNull ImageSize imageSize) {
		mImageEntity.imageSize = imageSize;
		return this;
	}

	public ImageBuilder imageSize(int width, int height) {
		mImageEntity.imageSize = new ImageSize(width, height);
		return this;
	}

	public ImageBuilder imageUrl(@NonNull Object imageUrl) {
		mImageEntity.imageUrl = imageUrl;
		return this;
	}

	public ImageBuilder loadingImage(@DrawableRes int loadingImage) {
		mImageEntity.loadingImage = loadingImage;
		return this;
	}

	public ImageBuilder errorImage(@DrawableRes int errorImage) {
		mImageEntity.errorImage = errorImage;
		return this;
	}

	public ImageBuilder imageView(ImageView imageView) {
		mImageEntity.imageView = imageView;
		return this;
	}

	public ImageBuilder imageType(ImageEntity.ImageType imageType) {
		mImageEntity.imageType = imageType;
		return this;
	}

	public ImageBuilder imageLoadingListener(ImageLoadingListener imageLoadingListener) {
		mImageEntity.imageLoadingListener = imageLoadingListener;
		return this;
	}


	public void build() {
		ImageDirector.getInstance(mContext).loadImage(mImageEntity);
	}
}

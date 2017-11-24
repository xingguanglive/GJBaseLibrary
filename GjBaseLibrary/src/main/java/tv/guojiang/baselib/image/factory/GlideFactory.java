package tv.guojiang.baselib.image.factory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;

import tv.guojiang.baselib.App.GlideApp;
import tv.guojiang.baselib.image.model.GlideImageEntity;
import tv.guojiang.baselib.image.model.ImageEntity;

/**
 * @author Elvis
 * @date 20/11/2017
 * @description tv.guojiang.baselib.image
 */

public class GlideFactory implements ImageFactory<GlideImageEntity> {
	@Override
	public void loadImage(@NonNull Context context, @NonNull final GlideImageEntity entity) {
		RequestBuilder requestBuilder = GlideApp.with(context.getApplicationContext())
				.as(getMethod(entity.imageType))
				.load(entity.imageUrl)
				.placeholder(entity.loadingImage)
				.error(entity.errorDrawable == null ? entity.errorDrawable : ContextCompat.getDrawable(context, entity.errorImage))
				.transforms(entity.transformations)
				.diskCacheStrategy(DiskCacheStrategy.ALL);
		if (entity.imageView != null) {
			if (entity.imageLoadingListener == null) {
				requestBuilder.into(entity.imageView);
			} else {
				requestBuilder.into(new SimpleTarget<Drawable>(entity.imageSize.getWidth(), entity.imageSize.getHeigth()) {
					@Override
					public void onResourceReady(Drawable resource, Transition transition) {
						entity.imageLoadingListener.onLoadingComplete(resource);
					}

					@Override
					public void onLoadStarted(@Nullable Drawable placeholder) {
						entity.imageLoadingListener.onLoadStarted(placeholder);
					}

					@Override
					public void onLoadFailed(@Nullable Drawable errorDrawable) {
						entity.imageLoadingListener.onLoadFailed(errorDrawable);
					}

					@Override
					public void onLoadCleared(@Nullable Drawable placeholder) {
						entity.imageLoadingListener.onLoadCleared(placeholder);
					}
				});
			}
		}
	}

	@Override
	public void clear(@NonNull Context context, ImageView imageView) {
		GlideApp.with(context.getApplicationContext()).clear(imageView);
	}

	@Override
	public void clearMemory(@NonNull Context context) {
		GlideApp.with(context.getApplicationContext()).onLowMemory();
	}

	public Class getMethod(ImageEntity.ImageType imageType) {
		Class clazz;
		switch (imageType.type) {
			case ImageEntity.ImageType.IMAGE_TYPE_DRAWABLE:
				clazz = Drawable.class;
				break;
			case ImageEntity.ImageType.IMAGE_TYPE_BITMAP:
				clazz = Bitmap.class;
				break;
			case ImageEntity.ImageType.IMAGE_TYPE_FILE:
				clazz = File.class;
				break;
			case ImageEntity.ImageType.IMAGE_TYPE_GIF:
				clazz = GifDrawable.class;
				break;
			default:
				clazz = Drawable.class;
		}
		return clazz;
	}
}

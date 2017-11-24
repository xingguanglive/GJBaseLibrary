package tv.guojiang.baselib.image.builder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.load.Transformation;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import tv.guojiang.baselib.image.ImageDirector;
import tv.guojiang.baselib.image.model.GlideImageEntity;

/**
 * @author Elvis
 * @date 20/11/2017
 * @description tv.guojiang.baselib.image
 */

public class GlideImageBuilder extends ImageBuilder<GlideImageEntity> {

	public GlideImageBuilder(Context context, GlideImageEntity imageEntity) {
		super(context, imageEntity);
	}


	public GlideImageBuilder transformation(Transformation<Bitmap>... transformations) {
		mImageEntity.transformations = transformations;
		return this;
	}

	public GlideImageBuilder cornerType(RoundedCornersTransformation.CornerType cornerType) {
		mImageEntity.cornerType = cornerType;
		return this;
	}

	public GlideImageBuilder radius(int radius) {
		mImageEntity.radius = radius;
		return this;
	}

	public ImageBuilder loadingImage(Drawable loadingDrawable) {
		mImageEntity.loadingDrawable = loadingDrawable;
		return this;
	}

	public ImageBuilder errorDrawable(Drawable loadingDrawable) {
		mImageEntity.errorDrawable = loadingDrawable;
		return this;
	}

	public void build() {
		ImageDirector.getInstance(mContext).loadImage(mImageEntity);
	}
}

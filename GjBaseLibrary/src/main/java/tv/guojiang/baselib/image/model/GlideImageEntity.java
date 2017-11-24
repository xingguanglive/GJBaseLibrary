package tv.guojiang.baselib.image.model;

import android.graphics.Bitmap;

import com.bumptech.glide.load.Transformation;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * @author Elvis
 * @date 20/11/2017
 * @description tv.guojiang.baselib.image.model
 */

public class GlideImageEntity extends ImageEntity {
	public Transformation<Bitmap>[] transformations;
	public RoundedCornersTransformation.CornerType cornerType;
}

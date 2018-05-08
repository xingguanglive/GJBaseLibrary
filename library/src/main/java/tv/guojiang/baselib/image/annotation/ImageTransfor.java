package tv.guojiang.baselib.image.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import tv.guojiang.baselib.image.model.ImageConstants;

/**
 * @author Elvis
 * @date 30/11/2017
 * @description tv.guojiang.baselib.image.annotation
 */

@IntDef({ImageConstants.IMAGE_TRANSFOR_DEFAULT, ImageConstants.IMAGE_TRANSFOR_CROP_CIRCLE,
		ImageConstants.IMAGE_TRANSFOR_CROP, ImageConstants.IMAGE_TRANSFOR_CROP_SQUARE,
		ImageConstants.IMAGE_TRANSFOR_CROP_CORNER, ImageConstants.IMAGE_TRANSFOR_COLOR_FILTER,
		ImageConstants.IMAGE_TRANSFOR_GRAY_SCALE, ImageConstants.IMAGE_TRANSFOR_BLUR,
		ImageConstants.IMAGE_TRANSFOR_MASK})
@Retention(RetentionPolicy.SOURCE)
public @interface ImageTransfor {
}

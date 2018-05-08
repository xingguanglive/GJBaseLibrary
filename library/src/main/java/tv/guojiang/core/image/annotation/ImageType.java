package tv.guojiang.core.image.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import tv.guojiang.core.image.model.ImageConstants;

/**
 * @author Elvis
 */

@IntDef({ImageConstants.IMAGE_TYPE_DRAWABLE, ImageConstants.IMAGE_TYPE_BITMAP,
    ImageConstants.IMAGE_TYPE_FILE, ImageConstants.IMAGE_TYPE_GIF})
@Retention(RetentionPolicy.SOURCE)
public @interface ImageType {

}

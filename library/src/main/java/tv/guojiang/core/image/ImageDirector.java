package tv.guojiang.core.image;

import android.content.Context;
import android.widget.ImageView;
import tv.guojiang.core.BaseLibConfig;
import tv.guojiang.core.image.factory.GlideFactory;
import tv.guojiang.core.image.factory.ImageFactory;

/**
 * @author Elvis
 */

public class ImageDirector {

    private static ImageDirector imageDirector;

    private static ImageFactory mFactory = BaseLibConfig.mConfigBuilder.imageFactory;

    private ImageBuilder mImageBuilder;

    private ImageDirector() {
        if (mFactory == null) {
            mFactory = new GlideFactory();
        }
    }

    public static ImageDirector getInstance() {
        if (imageDirector == null) {
            synchronized (ImageDirector.class) {
                if (imageDirector == null) {
                    imageDirector = new ImageDirector();
                }
            }
        }
        return imageDirector;
    }

    public ImageBuilder imageBuilder() {
        mImageBuilder = new ImageBuilder();
        return mImageBuilder;
    }

    /**
     * 加载图片的方式仅限于 ImageBuilder直接调用，屏蔽掉其他类调用。在此跟builder防御同包下面
     */
    ImageDirector loadImage(Context context) {
        if (mImageBuilder != null) {
            mFactory.loadImage(context, mImageBuilder.mImageEntity);
        }
        return this;
    }

    Object loadImageSyn(Context context) throws Exception {
        if (mImageBuilder != null) {
            return mFactory.loadImageSyn(context, mImageBuilder.mImageEntity);
        }
        return null;
    }

    /**
     * 加载显示图片
     */
    ImageDirector loadImage(Context context, ImageBuilder imageBuilder) {
        mFactory.loadImage(context, imageBuilder.mImageEntity);
        return this;
    }

    public void clear(Context context, ImageView imageView) {
        mFactory.clear(context, imageView);
    }

    public void clearMemory(Context context) {
        mFactory.clearMemory(context);
    }

}

package tv.guojiang.core.image;

import android.content.Context;
import android.support.annotation.NonNull;
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

    public ImageBuilder imageBuilder(Context context) {
        mImageBuilder = new ImageBuilder(context);
        // 清除上一次的entity配置
        //        mImageBuilder.clearEntity();
        return mImageBuilder;
    }

    /**
     * 加载图片的方式仅限于 ImageBuilder直接调用，屏蔽掉其他类调用。在此跟builder防御同包下面
     */
    ImageDirector loadImage() {
        if (mImageBuilder != null) {
            mFactory.loadImage(mImageBuilder.getContext(), mImageBuilder.mImageEntity);
        }
        return this;
    }

    Object loadImageSyn() throws Exception {
        if (mImageBuilder != null) {
            return mFactory.loadImageSyn(mImageBuilder.getContext(), mImageBuilder.mImageEntity);
        }
        return null;
    }

    /**
     * 加载显示图片
     */
    ImageDirector loadImage(@NonNull ImageBuilder imageBuilder) {
        mFactory.loadImage(mImageBuilder.getContext(), imageBuilder.mImageEntity);
        return this;
    }

    public void clear(@NonNull ImageView imageView) {
        mFactory.clear(mImageBuilder.getContext(), imageView);
    }

    public void clearMemory() {
        mFactory.clearMemory(mImageBuilder.getContext());
    }

}

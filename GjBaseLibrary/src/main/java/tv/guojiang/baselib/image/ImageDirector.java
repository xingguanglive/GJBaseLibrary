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
 */

public class ImageDirector {

    private static ImageDirector imageDirector;
    private static ImageFactory mFactory = BaseLibConfig.mConfigBuilder.imageFactory;
    private static ImageBuilder mImageBuilder;

    private ImageDirector(Context context) {
        if (mImageBuilder == null) {
            mImageBuilder = new ImageBuilder(context);
        }
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
        // 清除上一次的entity配置
        mImageBuilder.clearEntity();
        return mImageBuilder;
    }

    public ImageDirector loadImage() {
        if (mImageBuilder != null) {
            mFactory.loadImage(mImageBuilder.getContext(), mImageBuilder.mImageEntity);
        }
        return this;
    }

    public Object loadImageSyn() throws Exception {
        if (mImageBuilder != null) {
            return mFactory.loadImageSyn(mImageBuilder.getContext(), mImageBuilder.mImageEntity);
        }
        return null;
    }

    /**
     * 加载显示图片
     */
    public ImageDirector loadImage(@NonNull ImageBuilder imageBuilder) {
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

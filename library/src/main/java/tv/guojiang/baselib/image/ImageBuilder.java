package tv.guojiang.baselib.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.bumptech.glide.request.transition.TransitionFactory;
import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import tv.guojiang.baselib.image.annotation.ImageTransfor;
import tv.guojiang.baselib.image.annotation.ImageType;
import tv.guojiang.baselib.image.listener.ImageLoadingListener;
import tv.guojiang.baselib.image.model.ImageEntity;
import tv.guojiang.baselib.image.model.ImageSize;

/**
 * @author Elvis
 */

public class ImageBuilder {

    private Context mContext;
    public ImageEntity mImageEntity;

    public ImageBuilder(Context context) {
        this.mContext = context;
        this.mImageEntity = new ImageEntity();
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

    public ImageBuilder loadingImage(Drawable loadingDrawable) {
        mImageEntity.loadingDrawable = loadingDrawable;
        return this;
    }

    public ImageBuilder errorImage(@DrawableRes int errorImage) {
        mImageEntity.errorImage = errorImage;
        return this;
    }

    public ImageBuilder errorImage(Drawable loadingDrawable) {
        mImageEntity.errorDrawable = loadingDrawable;
        return this;
    }

    public ImageBuilder imageType(@ImageType int imageType) {
        mImageEntity.imageType = imageType;
        return this;
    }

    public ImageBuilder scaleType(ImageView.ScaleType scaleType) {
        mImageEntity.scaleType = scaleType;
        return this;
    }

    public ImageBuilder imageLoadingListener(ImageLoadingListener imageLoadingListener) {
        mImageEntity.imageLoadingListener = imageLoadingListener;
        return this;
    }

    public ImageBuilder imageTransformation(@ImageTransfor int... transfors) {
        for (int transfor : transfors) {
            mImageEntity.imageTransfor.add(transfor);
        }
        return this;
    }

    public ImageBuilder radius(int radius) {
        mImageEntity.radius = radius;
        return this;
    }

    public ImageBuilder cornerType(RoundedCornersTransformation.CornerType cornerType) {
        mImageEntity.cornerType = cornerType;
        return this;
    }

    public ImageBuilder cropWidth(int cropWidth) {
        mImageEntity.cropWidth = cropWidth;
        return this;
    }

    public ImageBuilder cropHeight(int cropHeight) {
        mImageEntity.cropHeight = cropHeight;
        return this;
    }

    public ImageBuilder cropType(CropTransformation.CropType cropType) {
        mImageEntity.cropType = cropType;
        return this;
    }

    public ImageBuilder colorFilter(int colorFilter) {
        mImageEntity.colorFilter = colorFilter;
        return this;
    }

    public ImageBuilder diskCacheStrategy(DiskCacheStrategy diskCacheStrategy) {
        mImageEntity.diskCacheStrategy = diskCacheStrategy;
        return this;
    }

    public ImageBuilder transitionFactory(TransitionFactory transitionFactory) {
        mImageEntity.transitionFactory = transitionFactory;
        return this;
    }

    public void clearEntity() {
        mImageEntity = null;
        mImageEntity = new ImageEntity();
    }

    /**
     * 图片显示
     */
    public ImageBuilder into(ImageView imageView) {
        mImageEntity.imageView = imageView;
        ImageDirector.getInstance().loadImage();
        return this;
    }

    public Context getContext(){
        return mContext;
    }

    /**
     * 同步加载图片
     */
    public Object intoSyn() throws Exception {
        return ImageDirector.getInstance().loadImageSyn();
    }
}

package tv.guojiang.core.image.factory;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import java.util.ArrayList;
import java.util.List;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import tv.guojiang.core.image.annotation.ImageType;
import tv.guojiang.core.image.model.ImageConstants;
import tv.guojiang.core.image.model.ImageEntity;

/**
 * @author Elvis
 */

public class GlideFactory implements ImageFactory {

    @Override
    public void loadImage(@NonNull Context context, @NonNull final ImageEntity entity) {
        RequestManager requestManager = Glide.with(context);
        imageType(requestManager, entity.imageType);
        RequestBuilder requestBuilder = requestManager.load(entity.imageUrl);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = addOptions(requestOptions, entity);
        requestOptions.diskCacheStrategy(entity.diskCacheStrategy);
        TransitionOptions transitionOptions = new DrawableTransitionOptions();
        if (entity.transitionFactory != null) {
            // new DrawableCrossFadeFactory.Builder().build();
            transitionOptions.transition(entity.transitionFactory);
            requestBuilder.transition(transitionOptions);
        }

        requestBuilder.apply(requestOptions);
        requestBuilder.transition(DrawableTransitionOptions.withCrossFade());
        if (entity.imageLoadingListener == null) {
            if (entity.imageView == null) {
                requestBuilder.preload(entity.imageSize.getWidth(), entity.imageSize.getHeigth());
            } else {
                requestBuilder.into(entity.imageView);
            }
        } else {
            requestBuilder.into(new SimpleTarget<Drawable>(entity.imageSize.getWidth(),
                entity.imageSize.getHeigth()) {
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

    /**
     * 同步加载图片
     */
    public Object loadImageSyn(Context context, ImageEntity entity) throws Exception {
        RequestManager requestManager = Glide.with(context);
        imageType(requestManager, entity.imageType);
        RequestBuilder requestBuilder = requestManager.load(entity.imageUrl);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = addOptions(requestOptions, entity);
        requestOptions.diskCacheStrategy(entity.diskCacheStrategy);
        requestBuilder.apply(requestOptions);
        requestBuilder.transition(DrawableTransitionOptions.withCrossFade());
        return requestBuilder.into(entity.imageSize.getWidth(), entity.imageSize.getHeigth()).get();
    }

    @Override
    public void clear(@NonNull Context context, ImageView imageView) {
        Glide.with(context).clear(imageView);
    }

    @Override
    public void clearMemory(@NonNull Context context) {
        Glide.with(context).onLowMemory();
    }

    /**
     * 加载图片类型
     */
    private void imageType(RequestManager requestManager, @ImageType int imageType) {
        switch (imageType) {
            case ImageConstants.IMAGE_TYPE_BITMAP:
                requestManager.asBitmap();
                break;
            case ImageConstants.IMAGE_TYPE_FILE:
                requestManager.asFile();
                break;
            case ImageConstants.IMAGE_TYPE_GIF:
                requestManager.asGif();
                break;
            case ImageConstants.IMAGE_TYPE_DRAWABLE:
                requestManager.asDrawable();
            default:
                break;
        }
    }

    /**
     * 添加其余配置参数
     */
    private RequestOptions addOptions(RequestOptions options, ImageEntity entity) {
        // 失败图片
        if (entity.errorImage > 0) {
            options.error(entity.errorImage);
        } else {
            options.error(entity.errorDrawable);
        }
        // loading图片
        if (entity.loadingImage > 0) {
            options.placeholder(entity.loadingImage);
        } else {
            options.placeholder(entity.loadingDrawable);
        }
        // ScaleType
        if (ImageView.ScaleType.CENTER_CROP == entity.scaleType) {
            if (entity.imageTransfor.contains(ImageConstants.IMAGE_TRANSFOR_DEFAULT)) {
                options.optionalCenterCrop();
            } else {
                options.centerCrop();
            }
        } else if (ImageView.ScaleType.CENTER_INSIDE == entity.scaleType) {
            if (entity.imageTransfor.contains(ImageConstants.IMAGE_TRANSFOR_DEFAULT)) {
                options.optionalCenterInside();
            } else {
                options.centerInside();
            }
        } else if (ImageView.ScaleType.FIT_CENTER == entity.scaleType) {
            if (entity.imageTransfor.contains(ImageConstants.IMAGE_TRANSFOR_DEFAULT)) {
                options.optionalFitCenter();
            } else {
                options.fitCenter();
            }
        }
        // Transformation
        MultiTransformation multiTransformation;
        List<Transformation> transformations = new ArrayList<>();
        for (int transformation : entity.imageTransfor) {
            switch (transformation) {
                case ImageConstants.IMAGE_TRANSFOR_DEFAULT:
                    break;
                case ImageConstants.IMAGE_TRANSFOR_CROP:
                    transformations.add(new CropTransformation(entity.cropWidth, entity.cropHeight,
                        entity.cropType));
                    break;
                case ImageConstants.IMAGE_TRANSFOR_CROP_CIRCLE:
                    transformations.add(new CircleCrop());
                    break;
                case ImageConstants.IMAGE_TRANSFOR_CROP_SQUARE:
                    transformations.add(new CropSquareTransformation());
                    break;
                case ImageConstants.IMAGE_TRANSFOR_CROP_CORNER:
                    transformations
                        .add(new RoundedCornersTransformation(entity.radius, 0, entity.cornerType));
                    break;
                case ImageConstants.IMAGE_TRANSFOR_COLOR_FILTER:
                    transformations.add(new ColorFilterTransformation(entity.colorFilter));
                    break;
                case ImageConstants.IMAGE_TRANSFOR_MASK:
                    transformations.add(new MaskTransformation(entity.maskId));
                    break;
                case ImageConstants.IMAGE_TRANSFOR_BLUR:
                    transformations
                        .add(new BlurTransformation(entity.radius == 0 ? 25 : entity.radius));
                    break;
                case ImageConstants.IMAGE_TRANSFOR_GRAY_SCALE:
                    transformations.add(new GrayscaleTransformation());
                    break;
            }
        }
        if (transformations.size() > 0) {
            multiTransformation = new MultiTransformation(
                transformations.toArray(new Transformation[]{}));
            options.transform(multiTransformation);
        }
        return options;
    }
}

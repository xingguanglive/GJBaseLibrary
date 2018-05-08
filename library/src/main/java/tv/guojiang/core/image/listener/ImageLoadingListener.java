package tv.guojiang.core.image.listener;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2017/5/17.
 */

public interface ImageLoadingListener {

    void onLoadStarted(Drawable placeholder);

    void onLoadFailed(Drawable errorDrawable);

    void onLoadingComplete(Drawable resource);

    void onLoadCleared(Drawable placeholder);
}

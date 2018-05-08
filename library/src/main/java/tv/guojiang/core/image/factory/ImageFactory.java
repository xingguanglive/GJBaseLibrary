package tv.guojiang.core.image.factory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import tv.guojiang.core.image.model.ImageEntity;

/**
 * @author Elvis
 */

public interface ImageFactory {

    /**
     * 加载图片
     *
     * @param entity imageview不为空则显示，为空只加载
     */
    void loadImage(@NonNull Context context, ImageEntity entity);

    /**
     * 同步加载图片
     */
    Object loadImageSyn(@NonNull Context context, ImageEntity entity) throws Exception;

    /**
     * 清除内存
     */
    void clearMemory(@NonNull Context context);


    /**
     * 取消加载
     */
    void clear(@NonNull Context context, ImageView imageView);
}

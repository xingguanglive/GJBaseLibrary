package tv.guojiang.baselib.image.factory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import tv.guojiang.baselib.image.model.ImageEntity;

/**
 * @author Elvis
 * @date 20/11/2017
 * @description tv.guojiang.baselib.image
 */

public interface ImageFactory<T extends ImageEntity> {

	/**
	 * 加载图片
	 *
	 * @param context
	 * @param entity  imageview不为空则显示，为空只加载
	 */
	public void loadImage(@NonNull Context context, T entity);

	/**
	 * 清除内存
	 *
	 * @param context
	 */
	public void clearMemory(@NonNull Context context);

	/**
	 * 取消加载
	 *
	 * @param context
	 */
	public void clear(@NonNull Context context, ImageView imageView);
}

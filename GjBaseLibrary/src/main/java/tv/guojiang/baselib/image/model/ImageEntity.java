package tv.guojiang.baselib.image.model;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import tv.guojiang.baselib.image.listener.ImageLoadingListener;

/**
 * @author Elvis
 * @date 20/11/2017
 * @description tv.guojiang.baselib.image
 */

public class ImageEntity {

	/**
	 * 图片宽高
	 */
	public ImageSize imageSize;
	/**
	 * 加载失败图
	 */
	public int errorImage;
	/**
	 * 正在加载loading图
	 */
	public int loadingImage;
	/**
	 * 显示的图片地址
	 */
	public Object imageUrl;

	/**
	 * 绑定的控件
	 */
	public ImageView imageView;

	/**
	 * 加载监听事件
	 */
	public ImageLoadingListener imageLoadingListener;

	public ImageType imageType;

	public Drawable loadingDrawable;
	public Drawable errorDrawable;
	public int radius;

	public class ImageType{
		public int type;

		public static final int IMAGE_TYPE_DRAWABLE = 0;

		public static final int IMAGE_TYPE_BITMAP = 1;

		public static final int IMAGE_TYPE_GIF = 2;

		public static final int IMAGE_TYPE_FILE = 3;
	}
}

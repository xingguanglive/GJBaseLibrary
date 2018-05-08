package tv.guojiang.baselib.image.model;

/**
 * @author Elvis
 * @date 30/11/2017
 * @description tv.guojiang.baselib.image.model
 */

public class ImageConstants {
	// 显示的图片类型
	public static final int IMAGE_TYPE_DRAWABLE = 0;
	public static final int IMAGE_TYPE_BITMAP = 1;
	public static final int IMAGE_TYPE_GIF = 2;
	public static final int IMAGE_TYPE_FILE = 3;

	// 外部渲染、形状
	public static final int IMAGE_TRANSFOR_DEFAULT = 0;
	public static final int IMAGE_TRANSFOR_CROP = 1;
	public static final int IMAGE_TRANSFOR_CROP_CIRCLE = 2;
	public static final int IMAGE_TRANSFOR_CROP_SQUARE = 3;
	public static final int IMAGE_TRANSFOR_CROP_CORNER = 4;
	// color
	public static final int IMAGE_TRANSFOR_COLOR_FILTER = 5;
	public static final int IMAGE_TRANSFOR_GRAY_SCALE = 6;
	//blur
	public static final int IMAGE_TRANSFOR_BLUR = 7;
	// mask
	public static final int IMAGE_TRANSFOR_MASK = 8;
}

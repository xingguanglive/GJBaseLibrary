package tv.guojiang.baselib.image.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

/**
 * Created by Elvis on 2017/7/5.
 * @description:.9图转换为drawable
 */

public class NinePatchDrawable extends Drawable {
	private int resId;
	private Context context;

	public NinePatchDrawable(Context context, int resId) {
		if (resId <= 0) {
			throw new RuntimeException("NinePatchDrawable,resId is must!");
		}
		if (context == null) {
			throw new RuntimeException("NinePatchDrawable,context is not null");
		}
		this.resId = resId;
		this.context = context.getApplicationContext();
	}

	@Override
	public void setAlpha(int alpha) {

	}

	@Override
	public void draw(Canvas canvas) {
		android.graphics.drawable.NinePatchDrawable drawable = (android.graphics.drawable.NinePatchDrawable) context.getResources().getDrawable(this.resId);
		drawable.setBounds(getBounds());
		drawable.draw(canvas);
	}


	@Override
	public void setColorFilter(int color, PorterDuff.Mode mode) {
		super.setColorFilter(color, mode);
	}

	@Override
	public void setColorFilter(ColorFilter colorFilter) {
	}

	@Override
	public int getOpacity() {
		return PixelFormat.OPAQUE;
	}
}

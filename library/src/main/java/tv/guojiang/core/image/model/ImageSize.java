package tv.guojiang.core.image.model;

import com.bumptech.glide.request.target.Target;

/**
 * Created by Administrator on 2017/5/24.
 */

public class ImageSize {

    public static final int SIZE_ORIGINAL = Target.SIZE_ORIGINAL;
    int width;
    int heigth;

    public ImageSize(int width, int heigth) {
        this.width = width;
        this.heigth = heigth;
    }

    public int getWidth() {
        return width;
    }

    public int getHeigth() {
        return heigth;
    }
}

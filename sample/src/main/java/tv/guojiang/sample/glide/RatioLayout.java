package tv.guojiang.sample.glide;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import tv.guojiang.base.R;


/**
 * 自适应的宽高比的布局
 */
public class RatioLayout extends FrameLayout {

    public static final int RELATIVE_WIDTH = 0;

    public static final int RELATIVE_HEIGHT = 1;

    private float mRatio;

    private int mRelative = RELATIVE_WIDTH;

    public RatioLayout(Context context) {
        this(context, null);
    }

    public RatioLayout(Context context, AttributeSet set) {
        super(context, set);

        TypedArray ta = context.obtainStyledAttributes(set, R.styleable.RatioLayout);
        mRatio = ta.getFloat(R.styleable.RatioLayout_ratio, 0);
        mRelative = ta.getInt(R.styleable.RatioLayout_relative, RELATIVE_WIDTH);
        ta.recycle();
    }

    /**
     * 获取宽高比
     */
    public float getRatio() {
        return mRatio;
    }

    /**
     * 设置宽高比
     */
    public void setRatio(float ratio) {
        this.mRatio = ratio;
    }

    /**
     * 设置相对谁
     */
    public void setRelative(int relative) {
        if (relative == RELATIVE_HEIGHT) {
            this.mRelative = relative;
        } else {
            this.mRelative = RELATIVE_WIDTH;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // 获得宽度值
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightsMode = MeasureSpec.getMode(heightMeasureSpec);

        // 测量孩子
        if (widthMode == MeasureSpec.EXACTLY && mRatio != 0 && mRelative == RELATIVE_WIDTH) {
            // 获得孩子的宽度
            int width = widthSize - getPaddingLeft() - getPaddingRight();

            // 通过宽度计算高度
            int height = (int) (width / mRatio + 0.5f);

            // 期望孩子的宽度和高度
            measureChildren(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));

            // 设置自己的高度
            setMeasuredDimension(widthSize, height + getPaddingTop() + getPaddingBottom());
        } else if (heightsMode == MeasureSpec.EXACTLY
            && mRatio != 0
            && mRelative == RELATIVE_HEIGHT) {
            int height = heightSize - getPaddingTop() - getPaddingBottom();

            int width = (int) (height * mRatio + 0.5f);

            // 期望孩子的宽度和高度
            measureChildren(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));

            // 设置自己的高度
            setMeasuredDimension(width + getPaddingLeft() + getPaddingRight(), heightSize);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}

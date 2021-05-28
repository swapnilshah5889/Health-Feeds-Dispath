package com.fluvina.hummnew.Custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

import com.fluvina.hummnew.R;


public class MaxHeightRelativeLayout extends RelativeLayout {

    private int mMaxHeight;
//shortcut for search globally?
    public MaxHeightRelativeLayout(Context context) {
        super(context);
    }

    public MaxHeightRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaxHeightRelativeLayout, 0, 0);
        try {
            //mMaxHeight = a.getDimensionPixelSize(R.styleable.MaxHeightRelativeLayout_maxHeightDp, 0);
            mMaxHeight =  a.getDimensionPixelSize(R.styleable.MaxHeightRelativeLayout_maxHeightDp,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mMaxHeight, context.getResources().getDisplayMetrics()));
        } finally {
            a.recycle();
        }
    }

    public MaxHeightRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //float maxHeightPx = MobiHealthUserUitility.dpToPx(getContext(), maxHeightDp);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            heightSize = heightSize <= mMaxHeight ? heightSize
                    : mMaxHeight;
        }

        if (heightMode == MeasureSpec.UNSPECIFIED) {
            heightSize = heightSize <= mMaxHeight ? heightSize
                    : mMaxHeight;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = heightSize <= mMaxHeight ? heightSize
                    : mMaxHeight;
        }
        int maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
                heightMode);
        super.onMeasure(widthMeasureSpec, maxHeightMeasureSpec);
    }

    public void setMaxHeightDp(int maxHeightDp) {
        this.mMaxHeight = maxHeightDp;
        invalidate();
    }

}

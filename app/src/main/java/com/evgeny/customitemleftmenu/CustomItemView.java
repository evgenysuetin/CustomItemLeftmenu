package com.evgeny.customitemleftmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Evgeny on 10.02.2017.
 */

public class CustomItemView extends View {


    private boolean mHasStatus = false;

    private float mTextWidth = 0.0f;
    private float mRadius = 5f;

    private Paint mTextPaintWhite;
    private Paint mTextPaintBlack;
    private Paint mHighlightRechtangle;
    private Paint mNoHighlightRechtangle;

    private Path path = new Path();

    public CustomItemView(Context context) {
        super(context);
        init();

    }

    public CustomItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyAttr(context, attrs);
        init();
    }

    public CustomItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyAttr(context, attrs);
        init();
    }

    public CustomItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyAttr(context, attrs);
        init();
    }

    private void applyAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomItemView,
                0,0);
        try {
            mHasStatus = typedArray.getBoolean(R.styleable.CustomItemView_has_status, false);
            mTextWidth = typedArray.getDimension(R.styleable.CustomItemView_textWidth, 0.0f);
            mRadius = typedArray.getFloat(R.styleable.CustomItemView_radius, 5f);
        } finally {
            typedArray.recycle();
        }
    }

    private void init(){
        mTextPaintWhite = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaintWhite.setColor(Color.WHITE);
        mTextPaintWhite.setTextSize(50f);

        mTextPaintBlack = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaintBlack.setColor(Color.BLACK);
        mTextPaintBlack.setTextSize(50f);

        mHighlightRechtangle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHighlightRechtangle.setStyle(Paint.Style.FILL);
        mHighlightRechtangle.setColor(Color.CYAN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {/*
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        // Whatever the width ends up being, ask for a height that would let the pie
        // get as big as it can
        int minh = MeasureSpec.getSize(w) - (int)mTextWidth + getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(MeasureSpec.getSize(w) - (int)mTextWidth, heightMeasureSpec, 0);
*/
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRoundRect(canvas);
        drawText(canvas);


    }

    private void drawText(Canvas canvas) {
        //path.reset();
    }

    private void drawRoundRect(Canvas canvas) {
        drawRoundRect(canvas);
        path.reset();
        path.moveTo(mRadius + getPaddingLeft(), getPaddingTop());
        path.lineTo(getMeasuredWidth()- mRadius - getPaddingRight(), getPaddingTop());
        path.quadTo(getMeasuredWidth() - getPaddingRight(),
                getPaddingTop(),
                getMeasuredWidth() - getPaddingRight(),
                mRadius + getPaddingTop());

        path.lineTo(getMeasuredWidth() - getPaddingRight(),getMeasuredHeight()- mRadius - getPaddingBottom());
        path.quadTo(getMeasuredWidth() - getPaddingRight(),
                getMeasuredHeight() - getPaddingBottom(),
                getMeasuredWidth()- mRadius - getPaddingRight(),
                getMeasuredHeight() - getPaddingBottom());

        path.lineTo(mRadius + getPaddingLeft(),getMeasuredHeight() - getPaddingBottom());
        path.quadTo(getPaddingLeft(),
                getMeasuredHeight() - getPaddingBottom(),
                getPaddingLeft(),
                getMeasuredHeight()- mRadius - getPaddingBottom());

        path.lineTo(getPaddingLeft(), mRadius + getPaddingTop());
        path.quadTo(getPaddingLeft(),
                getPaddingTop(),
                mRadius + getPaddingLeft(),
                getPaddingTop());

        canvas.drawPath(path,mHighlightRechtangle);
    }
}

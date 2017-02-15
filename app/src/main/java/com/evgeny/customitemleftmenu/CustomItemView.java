package com.evgeny.customitemleftmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;


/**
 * Created by Evgeny on 10.02.2017.
 */

public class CustomItemView extends View {

    public static final int ONLINE = 0;
    public static final int OFFLINE = 1;
    public static final int AWAY = 2;
    private static final String TAG = "CustomItemView";
    private static final String DOTS = "...";

    private boolean mHasStatus = false;
    private int mStatus = OFFLINE;

    private float mTextWidth = 0.0f;
    private float mRadius = 5f;

    private float mTextRadius = 10f;

    private Paint mTextPaintWhite;
    private Paint mTextPaintBlack;


    private Paint mHighlightRechtangle;
    private int mHighlightBackground;

    private Paint mNoHighlightRechtangle;
    private int mNoHighlightBackground;

    private String mText = "TEST TEST TEST";
    private int mMention;

    private Path path = new Path();
    private Paint mStatusOfflinePaint;
    private Paint mStatusAwayPaint;
    private Paint mStatusOnlinePaint;

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
                0, 0);
        try {
            mHasStatus = typedArray.getBoolean(R.styleable.CustomItemView_has_status, false);
            mTextWidth = typedArray.getDimension(R.styleable.CustomItemView_textWidth, 0.0f);
            mHighlightBackground = typedArray.getColor(R.styleable.CustomItemView_hightlightBackground, Color.TRANSPARENT);
            mNoHighlightBackground = typedArray.getColor(R.styleable.CustomItemView_noHightlightBackground, Color.TRANSPARENT);
            mRadius = typedArray.getFloat(R.styleable.CustomItemView_radius, 5f);
        } finally {
            typedArray.recycle();
        }
    }

    private void init() {
        mTextPaintWhite = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaintWhite.setTextAlign(Paint.Align.LEFT);
        mTextPaintWhite.setColor(Color.WHITE);
        mTextPaintWhite.setTextSize(convertSpToPixels(mTextWidth, getContext()));

        mTextPaintBlack = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaintBlack.setTextAlign(Paint.Align.LEFT);
        mTextPaintBlack.setColor(Color.BLACK);
        mTextPaintBlack.setTextSize(convertSpToPixels(mTextWidth, getContext()));

        mHighlightRechtangle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHighlightRechtangle.setStyle(Paint.Style.FILL);
        mHighlightRechtangle.setColor(mHighlightBackground);

        mNoHighlightRechtangle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNoHighlightRechtangle.setStyle(Paint.Style.FILL);
        mNoHighlightRechtangle.setColor(mNoHighlightBackground);

        mStatusOfflinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStatusOfflinePaint.setStyle(Paint.Style.FILL);
        mStatusOfflinePaint.setColor(Color.GRAY);

        mStatusAwayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStatusAwayPaint.setStyle(Paint.Style.FILL);
        mStatusAwayPaint.setColor(Color.YELLOW);

        mStatusOnlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStatusOnlinePaint.setStyle(Paint.Style.FILL);
        mStatusOnlinePaint.setColor(Color.GREEN);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        // Whatever the width ends up being, ask for a height that would let the pie
        // get as big as it can
        int minh = MeasureSpec.getSize(w) - (int)mTextWidth + getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(MeasureSpec.getSize(w) - (int)mTextWidth, heightMeasureSpec, 0);*/

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        createRoundRectPath();
        buildPreDrawing();
    }

    Rect t_rect = new Rect();
    Rect m_rect = new Rect();
    float[] t_widths = new float[0];
    float[] m_widths = new float[0];
    String t_mention = "";
    int endIndex = -1;


    private void buildPreDrawing() {
        mTextPaintBlack.getTextBounds(DOTS, 0, DOTS.length(), textRect);
        int dots_width = textRect.width();

        int m_padding = 5;
        int c_width = getMeasuredWidth();
        int c_height = getMeasuredHeight();

        mTextPaintBlack.getTextBounds(mText, 0, mText.length(), t_rect);
        int t_width = t_rect.width();
        int t_height = t_rect.height();

        mTextPaintBlack.getTextBounds(t_mention, 0, t_mention.length(), m_rect);
        int m_width = m_rect.width();
        int m_height = m_rect.height();

        float max_t_width = c_width - m_padding * 5 - mTextRadius * 2 - m_width;
        float maxt_t_width_with_dots = max_t_width - dots_width;

        int s = 0;
        if (max_t_width < t_width) {
            for (int i = 0; i < t_widths.length; i++) {
                s += t_widths[i];
                if (s < maxt_t_width_with_dots) {
                    endIndex = i;
                } else {
                    break;
                }
            }
        } else {
            endIndex = -1;
        }
        int test = 1;

        if (endIndex != -1) {
            endIndex += DOTS.length();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: isSelected() = " + isSelected() + "text = " + getText());
        drawRoundRect(canvas);
        drawText(canvas);
        drawStatus(canvas);

    }

    private Rect textRect = new Rect();

    private void drawText(Canvas canvas) {
        canvas.getClipBounds(textRect);
        int cHeight = textRect.height();
        if (isSelected()) {
            mTextPaintBlack.getTextBounds(mText, 0, (endIndex != -1) ? endIndex : mText.length(), textRect);
        } else {
            try {
                mTextPaintWhite.getTextBounds(mText, 0, (endIndex != -1) ? endIndex : mText.length(), textRect);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        float x;
        float y;
        if (mHasStatus) {
            x = getPaddingLeft() * 4 + mRadius * 2;
        } else {
            x = textRect.left;
        }
        y = cHeight / 2f + textRect.height() / 2f - textRect.bottom;
        if (endIndex != -1) {
            canvas.drawText(mText.substring(0,endIndex-DOTS.length()) + DOTS,
                    0,
                    endIndex,
                    x,
                    y,
                    isSelected() ? mTextPaintBlack : mTextPaintWhite);
        } else {
            canvas.drawText(mText,
                    0,
                    mText.length(),
                    x,
                    y,
                    isSelected() ? mTextPaintBlack : mTextPaintWhite);
        }
        //path.reset();
    }

    private void drawRoundRect(Canvas canvas) {
        canvas.drawPath(path, isSelected() ? mHighlightRechtangle : mNoHighlightRechtangle);
    }

    private void createRoundRectPath() {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int measuredWidth = getMeasuredWidth();
        int paddingRight = getPaddingRight();
        int measuredHeight = getMeasuredHeight();
        int paddingBottom = getPaddingBottom();
        path.reset();
        path.moveTo(mRadius + paddingLeft, paddingTop);
        path.lineTo(measuredWidth - mRadius - paddingRight, paddingTop);
        path.quadTo(measuredWidth - paddingRight,
                paddingTop,
                measuredWidth - paddingRight,
                mRadius + paddingTop);

        path.lineTo(measuredWidth - paddingRight, measuredHeight - mRadius - paddingBottom);
        path.quadTo(measuredWidth - paddingRight,
                measuredHeight - paddingBottom,
                measuredWidth - mRadius - paddingRight,
                measuredHeight - paddingBottom);

        path.lineTo(mRadius + paddingLeft, measuredHeight - paddingBottom);
        path.quadTo(paddingLeft,
                measuredHeight - paddingBottom,
                paddingLeft,
                measuredHeight - mRadius - paddingBottom);

        path.lineTo(paddingLeft, mRadius + paddingTop);
        path.quadTo(paddingLeft,
                paddingTop,
                mRadius + paddingLeft,
                paddingTop);
    }

    private void drawStatus(Canvas canvas) {
        switch (mStatus) {
            case ONLINE:
                canvas.drawCircle(20, getMeasuredHeight() / 2, mTextRadius, mStatusOnlinePaint);
                break;
            case AWAY:
                canvas.drawCircle(20, getMeasuredHeight() / 2, mTextRadius, mStatusAwayPaint);
                break;
            default:
                canvas.drawCircle(20, getMeasuredHeight() / 2, mTextRadius, mStatusOfflinePaint);
                break;
        }
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
        t_widths = new float[mText.length()];
        mTextPaintBlack.getTextWidths(mText, t_widths);
        invalidate();
    }

    public int getMention() {
        return mMention;
    }

    public void setMention(int mention) {
        this.mMention = mention;
        t_mention = String.valueOf(mention);
        m_widths = new float[t_mention.length()];
        mTextPaintBlack.getTextWidths(t_mention, m_widths);
        invalidate();

    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        this.mStatus = status;
        invalidate();
    }

    public static int convertSpToPixels(float sp, Context context) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
        return px;
    }
}

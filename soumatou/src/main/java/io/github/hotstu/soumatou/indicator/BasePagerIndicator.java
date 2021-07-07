package io.github.hotstu.soumatou.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.github.hotstu.soumatou.ISoumatouAdapter;
import io.github.hotstu.soumatou.R;
import io.github.hotstu.soumatou.SoumatouIndicator;

public abstract class BasePagerIndicator extends SoumatouIndicator {


    /**
     * Indicator Height
     */
    private int mIndicatorItemHeight = 3 * 16;

    /**
     * Indicator width.
     */
    private int mIndicatorItemWidth = 3 * 4;
    /**
     * Padding between indicators.
     */
    private int mIndicatorItemGap = 8;

    private @ColorInt int colorActive = 0;

    private @ColorInt int colorInactive = 0;

    private int marginBottom = 0;

    /**
     * Some more natural animation interpolation
     */
    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    private final Paint mPaint = new Paint();

    public BasePagerIndicator(Context ctx) {
        this(ctx, R.style.soumatou_pager_indicator_style_default);
    }

    public BasePagerIndicator(Context ctx, @StyleRes int style) {
        TypedArray a = ctx.obtainStyledAttributes(style, R.styleable.soumatou_pager_indicator_style);
        mIndicatorItemHeight = a.getDimensionPixelSize(R.styleable.soumatou_pager_indicator_style_android_height, 32);
        mIndicatorItemWidth = a.getDimensionPixelSize(R.styleable.soumatou_pager_indicator_style_android_width, 32);
        mIndicatorItemGap = a.getDimensionPixelSize(R.styleable.soumatou_pager_indicator_style_soumatou_gap, 8);
        colorActive = a.getColor(R.styleable.soumatou_pager_indicator_style_soumatou_colorActive, 0xDE000000);
        colorInactive = a.getColor(R.styleable.soumatou_pager_indicator_style_soumatou_colorActive, 0x33000000);
        marginBottom = a.getDimensionPixelSize(R.styleable.soumatou_pager_indicator_style_android_layout_marginBottom, 0);
        a.recycle();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
    }

    protected abstract ShapeDrawDelegate getShapeDraw();

    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
        if (!(parent.getAdapter() instanceof ISoumatouAdapter)) {
            return;
        }
        ISoumatouAdapter adapter = (ISoumatouAdapter) parent.getAdapter();
        if (adapter == null) {
            return;
        }
        int itemCount = adapter.getBannerItemCount();
        if (itemCount <= 1) {
            return;
        }

        // center horizontally, calculate width and subtract half from center
        float totalLength = mIndicatorItemWidth * itemCount;
        float paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemGap;
        float indicatorTotalWidth = totalLength + paddingBetweenItems;
        float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2F;

        // center vertically in the allotted space
        float indicatorPosY = parent.getHeight() - mIndicatorItemHeight * .5f - marginBottom;

        drawInactiveIndicators(canvas, indicatorStartX, indicatorPosY, itemCount);

        // find active page (which should be highlighted)
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        if (layoutManager == null) {
            return;
        }
        int activePosition = layoutManager.findFirstVisibleItemPosition();
        if (activePosition == RecyclerView.NO_POSITION) {
            return;
        }

        // find offset of active page (if the user is scrolling)
        final View activeChild = layoutManager.findViewByPosition(activePosition);
        if (activeChild == null) {
            return;
        }
        int left = activeChild.getLeft();
        int width = activeChild.getWidth();
        int right = activeChild.getRight();

        // on swipe the active item will be positioned from [-width, 0]
        // interpolate offset for smooth animation
        float progress = mInterpolator.getInterpolation(left * -1 / (float) width);

        int highlightPosition = adapter.mapBannerItemPosition(activePosition);
        if (highlightPosition == itemCount - 1) {
            progress = 0;
        }
        drawHighlights(canvas, indicatorStartX, indicatorPosY, highlightPosition, progress);
    }

    private void drawInactiveIndicators(Canvas c, float indicatorStartX, float indicatorPosY, int itemCount) {
        mPaint.setColor(colorInactive);

        // width of item indicator including padding
        final float itemWidth = mIndicatorItemWidth + mIndicatorItemGap;

        float start = indicatorStartX;

        for (int i = 0; i < itemCount; i++) {
            getShapeDraw().drawShape(c, start, indicatorPosY, mIndicatorItemWidth, mIndicatorItemHeight, mPaint);
            start += itemWidth;
        }
    }

    private void drawHighlights(Canvas c, float indicatorStartX, float indicatorPosY,
                                int highlightPosition, float progress) {
        mPaint.setColor(colorActive);

        // width of item indicator including padding
        final float itemWidth = mIndicatorItemWidth + mIndicatorItemGap;

        if (progress == 0F) {
            // no swipe, draw a normal indicator
            float highlightStart = indicatorStartX + itemWidth * highlightPosition;
            getShapeDraw().drawShape(c, highlightStart, indicatorPosY, mIndicatorItemWidth, mIndicatorItemHeight, mPaint);

        } else {
            float highlightStart = indicatorStartX + itemWidth * highlightPosition;
            // calculate partial highlight
            float partialLength = mIndicatorItemWidth * progress + mIndicatorItemGap * progress;

            getShapeDraw().drawShape(c, highlightStart + partialLength, indicatorPosY, mIndicatorItemWidth, mIndicatorItemHeight, mPaint);
        }
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //outRect.bottom = mIndicatorHeight;
    }
}
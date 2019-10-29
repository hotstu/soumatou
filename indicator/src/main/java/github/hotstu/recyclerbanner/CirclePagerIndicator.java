package github.hotstu.recyclerbanner;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import github.hotstu.recyclerbanner.indicator.BaseBannerIndicator;

public class CirclePagerIndicator extends BaseBannerIndicator {
    private int colorActive = 0xDE000000;
    private int colorInactive = 0x33000000;

    private static final float DP = Resources.getSystem().getDisplayMetrics().density;

    /**
     * Height of the space the indicator takes up at the bottom of the view.
     */
    private final int mIndicatorHeight = (int) (DP * 16);

    /**
     * Indicator stroke width.
     */
    private final float mIndicatorStrokeWidth = DP * 4;

    /**
     * Indicator width.
     */
    private final float mIndicatorItemLength = DP * 4;
    /**
     * Padding between indicators.
     */
    private final float mIndicatorItemPadding = DP * 8;

    /**
     * Some more natural animation interpolation
     */
    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    private final Paint mPaint = new Paint();

    public CirclePagerIndicator(Context ctx) {
        this(ctx, R.style.rb_circle_pager_indicator_style_default);
    }

    public CirclePagerIndicator(Context ctx, @StyleRes int style) {
        TypedArray a = ctx.obtainStyledAttributes(style, R.styleable.rb_circle_pager_indictor_style);
        int dimensionPixelSize = a.getDimensionPixelSize(R.styleable.rb_circle_pager_indictor_style_strokeWidth, (int) (mIndicatorStrokeWidth));
        a.recycle();
        mPaint.setStrokeWidth(dimensionPixelSize);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
        IBannerAdapter adapter = (IBannerAdapter) parent.getAdapter();
        assert adapter != null;
        int itemCount = adapter.getBannerItemCount();

        // center horizontally, calculate width and subtract half from center
        float totalLength = mIndicatorItemLength * itemCount;
        float paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding;
        float indicatorTotalWidth = totalLength + paddingBetweenItems;
        float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2F;

        // center vertically in the allotted space
        float indicatorPosY = parent.getHeight() - mIndicatorHeight / 2F;

        drawInactiveIndicators(canvas, indicatorStartX, indicatorPosY, itemCount);

        // find active page (which should be highlighted)
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int activePosition = layoutManager.findFirstVisibleItemPosition();
        if (activePosition == RecyclerView.NO_POSITION) {
            return;
        }

        // find offset of active page (if the user is scrolling)
        final View activeChild = layoutManager.findViewByPosition(activePosition);
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
        final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

        float start = indicatorStartX;
        for (int i = 0; i < itemCount; i++) {

            c.drawCircle(start, indicatorPosY, mIndicatorItemLength / 2F, mPaint);

            start += itemWidth;
        }
    }

    private void drawHighlights(Canvas c, float indicatorStartX, float indicatorPosY,
                                int highlightPosition, float progress) {
        mPaint.setColor(colorActive);

        // width of item indicator including padding
        final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

        if (progress == 0F) {
            // no swipe, draw a normal indicator
            float highlightStart = indicatorStartX + itemWidth * highlightPosition;

            c.drawCircle(highlightStart, indicatorPosY, mIndicatorItemLength / 2F, mPaint);

        } else {
            float highlightStart = indicatorStartX + itemWidth * highlightPosition;
            // calculate partial highlight
            float partialLength = mIndicatorItemLength * progress + mIndicatorItemPadding * progress;

            c.drawCircle(highlightStart + partialLength, indicatorPosY, mIndicatorItemLength / 2F, mPaint);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //outRect.bottom = mIndicatorHeight;
    }
}
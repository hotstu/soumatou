package github.hotstu.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/27/19
 */
public class TextDrawable extends Drawable {
    private Paint mPaint;
    private String text;

    public TextDrawable(String text) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        this.text = text;

    }

    public TextDrawable(String text, Typeface font) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        this.text = text;
        mPaint.setTypeface(font);

    }

    public void setText(String text) {
        this.text = text;
        invalidateSelf();
    }

    public void setFont(Typeface font) {
        mPaint.setTypeface(font);
        invalidateSelf();
    }

    public void setColor(@ColorInt int color) {
        mPaint.setColor(color);
        invalidateSelf();
    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        mPaint.setTextSize(200);

        float xPos = (bounds.width() / 2f);
        float yPos = (int) ((bounds.height() / 2) - ((mPaint.descent() + mPaint.ascent()) / 2));

        canvas.save();
        canvas.drawText(text, xPos, yPos, mPaint);
        canvas.restore();
    }

    @Override
    public void setAlpha(int i) {
        invalidateSelf();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}

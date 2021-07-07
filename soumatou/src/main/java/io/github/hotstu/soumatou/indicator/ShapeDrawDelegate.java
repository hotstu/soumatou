package io.github.hotstu.soumatou.indicator;

import android.graphics.Canvas;
import android.graphics.Paint;

interface ShapeDrawDelegate {
    void drawShape(Canvas canvas, float centerX, float centerY, int width, int height, Paint paint);
}

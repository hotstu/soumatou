package io.github.hotstu.soumatou.indicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class CircleDraw implements ShapeDrawDelegate {
    private RectF rect = new RectF();

    @Override
    public void drawShape(Canvas canvas, float centerX, float centerY, int width, int height, Paint paint) {
        canvas.save();
        canvas.translate(centerX, centerY);
        rect.set(-width * .5f, -height * .5f, width * .5f, height * .5f);
        canvas.drawOval(rect, paint);
        canvas.restore();

    }
}

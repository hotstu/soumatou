package io.github.hotstu.soumatou.indicator;

import android.graphics.Canvas;
import android.graphics.Paint;

public class SquareDraw implements ShapeDrawDelegate {

    @Override
    public void drawShape(Canvas canvas, float centerX, float centerY, int width, int height, Paint paint) {
        canvas.save();
        canvas.translate(centerX, centerY);
        canvas.drawRect(-width * .5f, -height * .5f, width * .5f, height * .5f, paint);
        canvas.restore();

    }
}

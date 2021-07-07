package io.github.hotstu.soumatou.indicator;

import android.content.Context;

public class CirclePagerIndicator extends BasePagerIndicator {
    private ShapeDrawDelegate delegate = new CircleDraw();

    public CirclePagerIndicator(Context ctx) {
        super(ctx);
    }

    public CirclePagerIndicator(Context ctx, int style) {
        super(ctx, style);
    }

    @Override
    protected ShapeDrawDelegate getShapeDraw() {
        return delegate;
    }
}
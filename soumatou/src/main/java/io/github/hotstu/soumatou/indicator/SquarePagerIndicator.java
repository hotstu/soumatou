package io.github.hotstu.soumatou.indicator;

import android.content.Context;

public class SquarePagerIndicator extends BasePagerIndicator {
    private ShapeDrawDelegate delegate = new SquareDraw();

    public SquarePagerIndicator(Context ctx) {
        super(ctx);
    }

    public SquarePagerIndicator(Context ctx, int style) {
        super(ctx, style);
    }

    @Override
    protected ShapeDrawDelegate getShapeDraw() {
        return delegate;
    }
}
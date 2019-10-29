package github.hotstu.recyclerbanner;

import android.content.Context;
import android.util.DisplayMetrics;

import androidx.recyclerview.widget.LinearSmoothScroller;

public class SlowScroller extends LinearSmoothScroller {
    public SlowScroller(Context context) {
        super(context);
    }

    @Override
    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
        return 1f;
    }
}

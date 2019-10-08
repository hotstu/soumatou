package github.hotstu.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @since 9/30/19
 * <p>
 * Also don’t forget to set the android:background of the button to use the theme value ?android:selectableItemBackgroundBorderless to get the nice ripple effect on touch.
 */
public class MyCheckableImageView extends AppCompatImageView implements Checkable {

    private static final int[] CHECKED_STATE_SET = {
            //android.R.attr.state_checked
            R.attr.state_custom1
    };

    private boolean checked;

    public MyCheckableImageView(Context context) {
        super(context);
    }

    public MyCheckableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCheckableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
        refreshDrawableState();
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked());
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }
}

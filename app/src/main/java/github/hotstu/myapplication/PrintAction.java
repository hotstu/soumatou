package github.hotstu.myapplication;

import android.util.Log;
import android.view.MotionEvent;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/29/19
 */
public class PrintAction {
    static final String TAG = "PrintAction";

    public static void print(int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG, "ACTION_CANCEL");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "ACTION_MOVE");
                break;
            default:
                Log.e(TAG, "OTHER:" + action);
                break;
        }
    }
}

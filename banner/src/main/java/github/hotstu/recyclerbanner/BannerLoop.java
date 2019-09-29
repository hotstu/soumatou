package github.hotstu.recyclerbanner;

import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/29/19
 */
public class BannerLoop extends RecyclerView.ItemDecoration implements RecyclerView.OnItemTouchListener {
    RecyclerView mRecyclerView = null;
    int period = 5 * 1000;

    public BannerLoop() {
    }

    public BannerLoop(int period) {
        this.period = period;
    }

    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) {
        if (mRecyclerView == recyclerView) {
            return; // nothing to do
        }
        if (mRecyclerView != null) {
            destroyCallbacks();
        }
        mRecyclerView = recyclerView;
        if (mRecyclerView != null) {
            setupCallbacks();
        }
    }



    private void setupCallbacks() {
        mRecyclerView.addItemDecoration(this);
        mRecyclerView.addOnItemTouchListener(this);
        mRecyclerView.addOnItemTouchListener(this);
    }

    private void destroyCallbacks() {
        mRecyclerView.removeItemDecoration(this);
        mRecyclerView.removeOnItemTouchListener(this);
        stopPlay();
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        int action = e.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            stopPlay();
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            startPlay();
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public void stopPlay() {
        Log.d("BannerLoop", "stopPlay");
        mRecyclerView.removeCallbacks(task);
    }

    public void startPlay() {
        Log.d("BannerLoop", "startPlay");
        boolean b = mRecyclerView.postDelayed(task, period);
    }

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            Log.d("BannerLoop", "tick");
            LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            assert layoutManager != null;
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
            RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
            assert adapter != null;
            if (firstVisibleItemPosition + 1 < adapter.getItemCount()) {
                mRecyclerView.smoothScrollToPosition(firstVisibleItemPosition + 1);
            } else {
                mRecyclerView.scrollToPosition(0);
            }
            mRecyclerView.postDelayed(this, period);
        }
    };
}


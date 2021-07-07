package io.github.hotstu.soumatou;

import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/29/19
 */
public class Soumatou extends RecyclerView.ItemDecoration implements RecyclerView.OnItemTouchListener {
    RecyclerView mRecyclerView = null;
    final int period;
    final boolean reverse;

    PagerSnapHelper snapHelper = new PagerSnapHelper();
    private boolean isStarted = false;
    private final int FLAG_PAUSE_BY_ACTION = 0x01;

    private int flags = 0;

    public Soumatou() {
        this(5 * 1000);
    }

    public Soumatou(int period) {
        this(period, false);
    }

    public Soumatou(int period, boolean reverse) {
        this.period = period;
        this.reverse = reverse;
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
        snapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addItemDecoration(this);
        mRecyclerView.addOnItemTouchListener(this);

    }

    private void destroyCallbacks() {
        stopPlay();
        snapHelper.attachToRecyclerView(null);
        mRecyclerView.removeItemDecoration(this);
        mRecyclerView.removeOnItemTouchListener(this);
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        int action = e.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            if (isStarted) {
                flags |= FLAG_PAUSE_BY_ACTION;
                stopPlay();
            }
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            if ((flags & FLAG_PAUSE_BY_ACTION) > 0) {
                flags &= ~FLAG_PAUSE_BY_ACTION;
                startPlay();
            }

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
        isStarted = false;
        if (mRecyclerView != null) {
            mRecyclerView.removeCallbacks(task);
        }
    }

    public void startPlay() {
        Log.d("BannerLoop", "startPlay");
        isStarted = true;
        if (mRecyclerView != null) {
            mRecyclerView.postDelayed(task, period);
        }
    }

    public void moveNext() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        assert layoutManager != null;
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        assert adapter != null;
        if (!reverse) {
            if (firstVisibleItemPosition + 1 < adapter.getItemCount()) {
                mRecyclerView.smoothScrollToPosition(firstVisibleItemPosition + 1);
            } else {
                mRecyclerView.scrollToPosition(0);
            }
        } else {
            if (firstVisibleItemPosition - 1 < 0) {
                mRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
            } else {
                mRecyclerView.smoothScrollToPosition(firstVisibleItemPosition - 1);
            }
        }
    }

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            Log.d("BannerLoop", "tick");
            moveNext();
            mRecyclerView.postDelayed(this, period);
        }
    };
}


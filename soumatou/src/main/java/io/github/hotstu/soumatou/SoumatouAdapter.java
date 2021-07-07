package io.github.hotstu.soumatou;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;



/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/29/19
 */
public abstract class SoumatouAdapter<T extends RecyclerView.Adapter> extends RecyclerView.Adapter implements ISoumatouAdapter {
    final int logicalItemCount;
    protected final T delegate;

    public SoumatouAdapter(T dele, int logicalItemCount) {
        delegate = dele;
        this.logicalItemCount = logicalItemCount;
    }


    @Override
    public int getBannerItemCount() {
        return Math.min(delegate.getItemCount(), logicalItemCount);
    }

    @Override
    public int mapBannerItemPosition(int adapterItemPosition) {
        return adapterItemPosition % logicalItemCount;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        return delegate.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        delegate.onBindViewHolder(holder, mapBannerItemPosition(position));
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

}

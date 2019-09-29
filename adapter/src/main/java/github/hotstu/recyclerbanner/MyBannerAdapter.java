package github.hotstu.recyclerbanner;

import java.lang.reflect.Field;

import github.hotstu.naiue.widget.recycler.MOTypedRecyclerAdapter;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/29/19
 */
public class MyBannerAdapter extends MOTypedRecyclerAdapter implements IBannerAdapter {
    final int logicalItemCount;

    public MyBannerAdapter(int logicalItemCount) {
        super();
        try {
            Field mList = MOTypedRecyclerAdapter.class.getDeclaredField("mList");
            mList.setAccessible(true);
            mList.set(this, new BanerList());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        assert logicalItemCount != 0;
        this.logicalItemCount = logicalItemCount;
    }

    @Override
    public int getBannerItemCount() {
        return Math.min(getItemCount(), logicalItemCount);
    }

    @Override
    public int mapBannerItemPosition(int adapterItemPosition) {
        return adapterItemPosition % logicalItemCount;
    }
}

package github.hotstu.myapplication;

import github.hotstu.naiue.widget.recycler.MOTypedRecyclerAdapter;
import io.github.hotstu.soumatou.SoumatouAdapter;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/29/19
 */
public class MyBannerAdapter extends SoumatouAdapter<MOTypedRecyclerAdapter> {

    public MyBannerAdapter(MOTypedRecyclerAdapter dele, int logicalItemCount) {
        super(dele, logicalItemCount);
    }
}

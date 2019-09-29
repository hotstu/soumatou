package github.hotstu.recyclerbanner;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/29/19
 */
public class BanerList<T> extends ArrayList<T> {
    public BanerList(int initialCapacity) {
        super(initialCapacity);
    }

    public BanerList() {
    }

    public BanerList(@NonNull Collection<? extends T> c) {
        super(c);
    }

    @Override
    public int size() {
        if (super.size() == 0 || super.size() == 1) {
            return super.size();
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public T get(int index) {
        if (this.size() == 0 || this.size() == 1) {
            return super.get(index);
        }
        int i = index % super.size();
        return super.get(i);
    }


}

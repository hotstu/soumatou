package github.hotstu.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

import github.hotstu.naiue.widget.recycler.MOCommonViewHolder;
import github.hotstu.naiue.widget.recycler.MOTypedRecyclerAdapter;
import github.hotstu.recyclerbanner.BanerList;
import github.hotstu.recyclerbanner.BannerLoop;
import github.hotstu.recyclerbanner.CirclePagerIndicator;
import github.hotstu.recyclerbanner.MyBannerAdapter;
import github.hotstu.recyclerbanner.SlowScroller;
import github.hotstu.recyclerbanner.SomatoSnapHelper;

public class MainActivity extends AppCompatActivity {

    private BannerLoop play;
    private BannerLoop play2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView list = findViewById(R.id.list);
        LinearLayoutManager layout = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        list.setLayoutManager(layout);
        SomatoSnapHelper snapHelper = new SomatoSnapHelper();
        snapHelper.attachToRecyclerView(list);
        MyBannerAdapter adapter = new MyBannerAdapter(4);
        adapter.addDelegate(new MOTypedRecyclerAdapter.AdapterDelegate() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(MOTypedRecyclerAdapter adapter, ViewGroup parent) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                MOCommonViewHolder viewHolder = new MOCommonViewHolder(view);
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(MOTypedRecyclerAdapter adapter, RecyclerView.ViewHolder holder, Object data) {
                TextDrawable drawable = new TextDrawable((String) data);
                ((MOCommonViewHolder) holder).getImageView(R.id.user_avatar_image).setImageDrawable(drawable);
            }

            @Override
            public boolean isDelegateOf(Class<?> clazz, Object item, int position) {
                return true;
            }
        });
        adapter.addItems(new BanerList<>(Arrays.asList("1", "2", "3", "4")));
        list.setAdapter(adapter);
        list.scrollToPosition(0 + 4 * 1000);
        list.addItemDecoration(new CirclePagerIndicator(this));
        play = new BannerLoop(1000, true);
        play.attachToRecyclerView(list);
        //
        RecyclerView list2 = findViewById(R.id.list2);
        LinearLayoutManager layout2 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                SlowScroller linearSmoothScroller = new SlowScroller(recyclerView.getContext());
                linearSmoothScroller.setTargetPosition(position);
                startSmoothScroll(linearSmoothScroller);
            }
        };
        list2.setLayoutManager(layout2);
        SomatoSnapHelper snapHelper2 = new SomatoSnapHelper();
        snapHelper2.attachToRecyclerView(list2);
        MyBannerAdapter adapter2 = new MyBannerAdapter(4);
        adapter2.addDelegate(new MOTypedRecyclerAdapter.AdapterDelegate() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(MOTypedRecyclerAdapter adapter, ViewGroup parent) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item2, parent, false);
                MOCommonViewHolder viewHolder = new MOCommonViewHolder(view);
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(MOTypedRecyclerAdapter adapter, RecyclerView.ViewHolder holder, Object data) {
                ((MOCommonViewHolder) holder).setText(android.R.id.text1, ((String) data));
            }

            @Override
            public boolean isDelegateOf(Class<?> clazz, Object item, int position) {
                return true;
            }
        });
        adapter2.addItems(new BanerList<>(Arrays.asList("床前明月光", "疑是地上霜", "举头望明月", "我是郭德纲")));
        list2.setAdapter(adapter2);
        list2.scrollToPosition(0);
        play2 = new BannerLoop(3000, false);
        play2.attachToRecyclerView(list2);
        //
        ImageView image = findViewById(R.id.image);
        image.setOnClickListener(v -> {
            ((Checkable) v).toggle();
            if (((Checkable) v).isChecked()) {
                play.startPlay();
                play2.startPlay();
            } else {
                play.stopPlay();
                play2.stopPlay();
            }
        });
    }

}

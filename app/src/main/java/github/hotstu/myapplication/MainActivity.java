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
import io.github.hotstu.soumatou.indicator.CirclePagerIndicator;
import io.github.hotstu.soumatou.SlowScroller;
import io.github.hotstu.soumatou.Soumatou;
import io.github.hotstu.soumatou.indicator.SquarePagerIndicator;

public class MainActivity extends AppCompatActivity {

    private Soumatou play;
    private Soumatou play2;
    private Soumatou play3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView list = findViewById(R.id.list);
        LinearLayoutManager layout = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        list.setLayoutManager(layout);
        MOTypedRecyclerAdapter typedRecyclerAdapter = new MOTypedRecyclerAdapter();
        MyBannerAdapter adapter = new MyBannerAdapter(typedRecyclerAdapter, 4);
        typedRecyclerAdapter.addDelegate(new MOTypedRecyclerAdapter.AdapterDelegate() {
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
        typedRecyclerAdapter.addItems(Arrays.asList("1", "2", "3", "4"));
        list.setAdapter(adapter);
        list.scrollToPosition(0 + 4 * 1000);
        list.addItemDecoration(new CirclePagerIndicator(this));
        play = new Soumatou(1000, true);
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
        MOTypedRecyclerAdapter typedRecyclerAdapter2 = new MOTypedRecyclerAdapter();

        MyBannerAdapter adapter2 = new MyBannerAdapter(typedRecyclerAdapter2, 4);
        typedRecyclerAdapter2.addDelegate(new MOTypedRecyclerAdapter.AdapterDelegate() {
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
        typedRecyclerAdapter2.addItems(Arrays.asList("床前明月光", "疑是地上霜", "举头望明月", "我是郭德纲"));
        list2.setAdapter(adapter2);
        list2.scrollToPosition(0);
        play2 = new Soumatou(3000, false);
        play2.attachToRecyclerView(list2);
        //
        RecyclerView list3 = findViewById(R.id.list3);
        LinearLayoutManager layout3 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        list3.setLayoutManager(layout3);
        MOTypedRecyclerAdapter typedRecyclerAdapter3 = new MOTypedRecyclerAdapter();
        MyBannerAdapter adapter3 = new MyBannerAdapter(typedRecyclerAdapter3, 8);
        typedRecyclerAdapter3.addDelegate(new MOTypedRecyclerAdapter.AdapterDelegate() {
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
        typedRecyclerAdapter3.addItems(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8"));
        list3.setAdapter(adapter3);
        list3.addItemDecoration(new SquarePagerIndicator(this));
        play3 = new Soumatou(500, false);
        play3.attachToRecyclerView(list3);
        //
        ImageView image = findViewById(R.id.image);
        image.setOnClickListener(v -> {
            ((Checkable) v).toggle();
            if (((Checkable) v).isChecked()) {
                play.startPlay();
                play2.startPlay();
                play3.startPlay();
            } else {
                play.stopPlay();
                play2.stopPlay();
                play3.stopPlay();
            }
        });
    }

}

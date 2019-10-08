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
import github.hotstu.recyclerbanner.SomatoSnapHelper;

public class MainActivity extends AppCompatActivity {

    private BannerLoop play;

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
        play = new BannerLoop(1000);
        play.attachToRecyclerView(list);


//        AnimatedVectorDrawableCompat drawable = AnimatedVectorDrawableCompat.create(this, R.drawable.animate_shape);
        ImageView image = findViewById(R.id.image);
//        image.setImageDrawable(drawable);
        image.setOnClickListener(v -> {
            ((Checkable) v).toggle();
        });
//        VectorDrawable v = null;


    }

    @Override
    protected void onResume() {
        super.onResume();
        play.startPlay();
    }

    @Override
    protected void onPause() {
        play.stopPlay();
        super.onPause();
    }
}

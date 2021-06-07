package com.example.view_pager2_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private final int FIRST_POSITION=0;
    private final int SLIDE_MARGIN = 400;
    private final int ONE_SLIDE_TIME_DELAY = 3000;
    List<SliderItem> sliderItems = new ArrayList<>();
    CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
    private Handler sliderHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViewPagerWidget();


        fillTheImageList();


        giveTheSliderAdapterToTheViewPagerWidget(viewPager2);


        stylingTheSlider(compositePageTransformer);


        reinitializeTheSliderOnUserChangePageAction(viewPager2);
    }


    public void initializeViewPagerWidget(){
        viewPager2 = findViewById(R.id.viewPagerImageSlider);
//        viewPager2.setClipToPadding(false);
//        viewPager2.setClipChildren(false);
//        viewPager2.getChildAt(FIRST_POSITION).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }


    public void fillTheImageList(){
        sliderItems.add(new SliderItem(R.drawable.pic1));
        sliderItems.add(new SliderItem(R.drawable.pic2));
        sliderItems.add(new SliderItem(R.drawable.pic3));
    }

    public void giveTheSliderAdapterToTheViewPagerWidget(ViewPager2 viewPager){
        viewPager.setAdapter(new SliderAdapter(sliderItems,viewPager));
    }


    public void stylingTheSlider(CompositePageTransformer compositePageTransformer){
        compositePageTransformer.addTransformer(new MarginPageTransformer(SLIDE_MARGIN));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull @NotNull View page, float position) {
                float r = 1 -Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
    }

    public void reinitializeTheSliderOnUserChangePageAction(ViewPager2 viewPager){
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable,ONE_SLIDE_TIME_DELAY);
            }
        });
    }


    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable,ONE_SLIDE_TIME_DELAY);
    }
}
package com.praxello.tailorsmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.praxello.tailorsmart.adapter.MyCustomPagerAdapter;

import butterknife.BindView;


public class SliderActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.ivNext)
    ImageView ivNext;
    int[] images = {R.drawable.f1, R.drawable.f2, R.drawable.f3, R.drawable.f4};
    MyCustomPagerAdapter myCustomPagerAdapter;
    int currentPos = 0;
    boolean isFromMain;
    @BindView(R.id.tvGetStarted)
    TextView tvGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFromMain = getIntent().getBooleanExtra("isFromMain", false);
        myCustomPagerAdapter = new MyCustomPagerAdapter(mContext, images);
        viewPager.setAdapter(myCustomPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPos = position;
                if (currentPos == 3 && !isFromMain) {
                    tvGetStarted.setVisibility(View.VISIBLE);
                    ivNext.setVisibility(View.GONE);
                } else {
                    ivNext.setVisibility(View.VISIBLE);
                    tvGetStarted.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ivNext.setOnClickListener(view -> {
            if (currentPos == 3) {
                if (!isFromMain) {
                    startActivity(new Intent(mContext, LoginActivity.class));
                }
                finish();
            } else {
                viewPager.setCurrentItem(currentPos + 1);
            }
        });
        tvGetStarted.setOnClickListener(view -> {
            if (currentPos == 3) {
                if (!isFromMain) {
                    startActivity(new Intent(mContext, LoginActivity.class));
                }
                finish();
            } else {
                viewPager.setCurrentItem(currentPos + 1);
            }
        });
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_slider;
    }

}

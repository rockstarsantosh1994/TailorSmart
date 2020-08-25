package com.praxello.tailorsmart.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.request.RequestOptions;
import com.praxello.tailorsmart.GlideApp;
import com.praxello.tailorsmart.R;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;


public class HomeBgPagerAdapter extends PagerAdapter {
    private final int widthPixels;
    private final float scale;
    Context mContext;
    LayoutInflater mLayoutInflater;
    int[] list;

    public HomeBgPagerAdapter(Context mContext, int[] list) {
        this.mContext = mContext;
        this.list = list;
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        widthPixels = displayMetrics.widthPixels;
        scale = displayMetrics.density;
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.viewpager_bg, container, false);
        ImageView ivBg = itemView.findViewById(R.id.ivBg);
        new Handler(Looper.getMainLooper()).post(() -> GlideApp.with(mContext).load(list[position])
                .thumbnail(0.1f).apply(RequestOptions.bitmapTransform(new BlurTransformation(5)))
                .into(ivBg));
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
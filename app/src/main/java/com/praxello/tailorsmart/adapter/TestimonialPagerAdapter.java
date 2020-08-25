package com.praxello.tailorsmart.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.praxello.tailorsmart.GlideApp;
import com.praxello.tailorsmart.R;
import com.praxello.tailorsmart.model.Testimonial;

import java.util.List;


public class TestimonialPagerAdapter extends PagerAdapter {
    private final int widthPixels;
    private final float scale;
    Context mContext;
    LayoutInflater mLayoutInflater;
    List<Testimonial> list;

    public TestimonialPagerAdapter(Context mContext, List<Testimonial> list) {
        this.mContext = mContext;
        this.list = list;
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        widthPixels = displayMetrics.widthPixels;
        scale = displayMetrics.density;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.viewpager_testimonial, container, false);
        ImageView ivProfile = itemView.findViewById(R.id.ivProfile);
        TextView tvTitle = itemView.findViewById(R.id.tvTitle);
        TextView tvWordsBy = itemView.findViewById(R.id.tvWordsBy);
        Testimonial testimonial = list.get(position);
        GlideApp.with(mContext).load(testimonial.getPhoto()).into(ivProfile);
        tvTitle.setText(testimonial.getTitle());
        tvWordsBy.setText("- " + testimonial.getWordsBy());
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
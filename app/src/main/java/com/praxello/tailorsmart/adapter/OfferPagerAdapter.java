package com.praxello.tailorsmart.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.praxello.tailorsmart.GlideApp;
import com.praxello.tailorsmart.MainActivity;
import com.praxello.tailorsmart.ProductListActivity;
import com.praxello.tailorsmart.R;
import com.praxello.tailorsmart.model.Banner;
import com.praxello.tailorsmart.model.Offer;
import com.praxello.tailorsmart.utils.Utils;

import java.util.List;


public class OfferPagerAdapter extends PagerAdapter {
    private final int widthPixels;
    private final float scale;
    Context mContext;
    LayoutInflater mLayoutInflater;
    List<Offer> offers;

    public OfferPagerAdapter(Context mContext, List<Offer> offers) {
        this.mContext = mContext;
        this.offers = offers;
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        widthPixels = displayMetrics.widthPixels;
        scale = displayMetrics.density;
    }

    @Override
    public int getCount() {
        return offers.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.row_offer, container, false);
        ImageView ivOffer = itemView.findViewById(R.id.ivOffer);
        TextView tvOffer = itemView.findViewById(R.id.tvOffer);
        Offer offer = offers.get(position);
        GlideApp.with(mContext).load("http://103.127.146.25/~tailors/Tailorsmart/mobileimages/category/"
                + offer.getId() + ".jpg").thumbnail(0.1f).into(ivOffer);
        itemView.setOnClickListener(view -> {
            mContext.startActivity(new Intent(mContext, ProductListActivity.class)
                    .putExtra("categoryId", offer.getId())
                    .putExtra("data", ((MainActivity) mContext).data));
        });
        if (!TextUtils.isEmpty(offer.getTitle())) {
            tvOffer.setText(offer.getTitle());
            tvOffer.setVisibility(View.VISIBLE);
        } else {
            tvOffer.setVisibility(View.GONE);
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
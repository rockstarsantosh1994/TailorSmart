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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.praxello.tailorsmart.R;
import com.praxello.tailorsmart.model.Banner;
import com.praxello.tailorsmart.utils.Utils;

import java.util.List;


public class BannerPagerAdapter extends PagerAdapter {
    private final int widthPixels;
    private final float scale;
    Context mContext;
    LayoutInflater mLayoutInflater;
    List<Banner> banners;

    public BannerPagerAdapter(Context mContext, List<Banner> banners) {
        this.mContext = mContext;
        this.banners = banners;
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        widthPixels = displayMetrics.widthPixels;
        scale = displayMetrics.density;
    }

    @Override
    public int getCount() {
        return banners.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.viewpager_image, container, false);
        ImageView iv_banner = itemView.findViewById(R.id.iv_banner);
        View vwOverlay = itemView.findViewById(R.id.vwOverlay);
        ImageView ivPlay = itemView.findViewById(R.id.ivPlay);
        TextView tvAdName = itemView.findViewById(R.id.tvAdName);
        Banner banner = banners.get(position);
        if (banner.getMediaType() != null) {
            if (banner.getMediaType().equals("0")) {
                if (banner.getAdId() != null && !TextUtils.isEmpty(banner.getAdId())) {
                    Glide.with(mContext).load("https://praxello.com/tailorsmart/mobileimages/ads/" + banner.getAdId() + ".jpg").into(iv_banner);
                    iv_banner.setOnClickListener(v -> {
                        if (banner.getWebsitelink() != null && !TextUtils.isEmpty(banner.getWebsitelink())) {
                            Utils.openBrowser(mContext, banner.getWebsitelink());
                        }
                    });
                }
                vwOverlay.setVisibility(View.GONE);
                ivPlay.setVisibility(View.GONE);
            } else {
                vwOverlay.setVisibility(View.VISIBLE);
                ivPlay.setVisibility(View.VISIBLE);
                Glide.with(mContext).load("http://img.youtube.com/vi/" + banner.getWebsitelink() + "/0.jpg").into(iv_banner);
                iv_banner.setOnClickListener(v -> {
                    String url = banner.getWebsitelink();
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + url));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        // youtube is not installed.Will be opened in other available apps
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/watch?v=" + url));
                        mContext.startActivity(i);
                    }
                });
            }
        } else {
            vwOverlay.setVisibility(View.GONE);
            ivPlay.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(banner.getTitle())) {
            tvAdName.setText(banner.getTitle());
            tvAdName.setVisibility(View.VISIBLE);
        } else {
            tvAdName.setVisibility(View.GONE);
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
package com.praxello.tailorsmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.praxello.tailorsmart.GlideApp;
import com.praxello.tailorsmart.MainActivity;
import com.praxello.tailorsmart.ProductListActivity;
import com.praxello.tailorsmart.R;
import com.praxello.tailorsmart.model.Offer;
import com.praxello.tailorsmart.model.Product;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.RecyclerViewHolder> {
    private Context mContext;
    private ArrayList<Offer> list;

    public OfferAdapter(Context mContext, ArrayList<Offer> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_offer, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Offer obj = list.get(position);
        GlideApp.with(mContext).load("http://103.127.146.25/~tailors/Tailorsmart/mobileimages/category/"
                + obj.getId() + ".jpg").thumbnail(0.1f).into(holder.ivOffer);
        holder.itemView.setOnClickListener(view -> {
            mContext.startActivity(new Intent(mContext, ProductListActivity.class)
                    .putExtra("categoryId", obj.getId())
                    .putExtra("data", ((MainActivity) mContext).data));
        });
        if (!TextUtils.isEmpty(obj.getTitle())) {
            holder.tvOffer.setText(obj.getTitle());
            holder.tvOffer.setVisibility(View.VISIBLE);
        } else {
            holder.tvOffer.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivOffer)
        ImageView ivOffer;
        @BindView(R.id.tvOffer)
        TextView tvOffer;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
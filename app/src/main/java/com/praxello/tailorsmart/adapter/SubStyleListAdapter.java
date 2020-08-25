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

import com.praxello.tailorsmart.App;
import com.praxello.tailorsmart.GlideApp;
import com.praxello.tailorsmart.ProductDetailsActivity;
import com.praxello.tailorsmart.ProductListActivity;
import com.praxello.tailorsmart.R;
import com.praxello.tailorsmart.SubStyleListActivity;
import com.praxello.tailorsmart.model.Product;
import com.praxello.tailorsmart.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubStyleListAdapter extends RecyclerView.Adapter<SubStyleListAdapter.RecyclerViewHolder> {
    private Context mContext;
    private ArrayList<Product> list;
    private final App app;
//    private final String selectedCurrency;
//    private final String selectedCurrencyMultiplier;

    public SubStyleListAdapter(Context mContext, ArrayList<Product> list) {
        this.mContext = mContext;
        this.list = list;
        app = (App) mContext.getApplicationContext();
//        selectedCurrency = app.getPreferences().getSelectedCurrency();
//        selectedCurrencyMultiplier = app.getPreferences().getSelectedCurrencyMultiplier();
//        for (int i = 0; i < list.size(); i++) {
//            double price = Double.parseDouble(list.get(i).getPrice()) * Double.parseDouble(selectedCurrencyMultiplier);
//            list.get(i).setPrice(String.valueOf(price));
//        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Product obj = list.get(position);
        holder.tvTitle.setText(obj.getSubStyleTitle());
        if (!TextUtils.isEmpty(obj.getProductSubTitle()) && obj.getIsGroup().equals("0")) {
            holder.tvSubTitle.setText(obj.getProductSubTitle());
            holder.tvSubTitle.setVisibility(View.VISIBLE);
        } else {
            holder.tvSubTitle.setVisibility(View.GONE);
        }
//        String price = obj.getPrice();
//        if (!TextUtils.isEmpty(obj.getIsPriceVariable()) && obj.getIsPriceVariable().equals("1"))
//            price = price + Utils.fromHtml("<sup><small>*</small></sup>");
//        holder.tvPrice.setText("\u20B9 " + price);
        holder.tvPrice.setVisibility(View.GONE);
        GlideApp.with(mContext).load("http://103.127.146.5/~tailor/Tailorsmart/mobileimages/parent/"
                + obj.getParentId() + ".jpg").thumbnail(0.1f).into(holder.ivProduct);
//        holder.tvDetails.setText(obj.getStyleTitle() + "   ||   " + obj.getSubStyleTitle() + "   ||   " + obj.getCategoryTitle() + "  || " + obj
//                .getSequenceNo() + " || " + obj.getIsPriceVariable());
        holder.itemView.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(obj.getIsGroup())) {
                if (obj.getIsGroup().equals("0")) {
                    mContext.startActivity(new Intent(mContext, ProductDetailsActivity.class)
                            .putExtra("data", ((SubStyleListActivity) mContext).data)
                            .putExtra("product", obj));
                } else {
                    mContext.startActivity(new Intent(mContext, ProductListActivity.class)
                            .putExtra("parentId", obj.getParentId())
                            .putExtra("data", ((SubStyleListActivity) mContext).data));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivProduct)
        ImageView ivProduct;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.tvSubTitle)
        TextView tvSubTitle;
        @BindView(R.id.tvDetails)
        TextView tvDetails;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
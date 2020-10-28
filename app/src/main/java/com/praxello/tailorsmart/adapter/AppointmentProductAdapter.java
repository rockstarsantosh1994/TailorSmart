package com.praxello.tailorsmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.praxello.tailorsmart.App;
import com.praxello.tailorsmart.AppointmentFabricListActivity;
import com.praxello.tailorsmart.GlideApp;
import com.praxello.tailorsmart.R;
import com.praxello.tailorsmart.model.Product;
import com.praxello.tailorsmart.model.SelectedItem;
import com.praxello.tailorsmart.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppointmentProductAdapter extends RecyclerView.Adapter<AppointmentProductAdapter.RecyclerViewHolder> {
    private final String selectedCurrency;
    private final String selectedCurrencyMultiplier;
    App app;
    private Context mContext;
    private List<SelectedItem> list;

    public AppointmentProductAdapter(Context mContext, List<SelectedItem> list) {
        this.mContext = mContext;
        this.list = list;
        app = (App) mContext.getApplicationContext();
        selectedCurrency = app.getPreferences().getSelectedCurrency();
        selectedCurrencyMultiplier = app.getPreferences().getSelectedCurrencyMultiplier();
        for (int i = 0; i < list.size(); i++) {
            double price = Double.parseDouble(list.get(i).getProduct().getPrice()) * Double.parseDouble(selectedCurrencyMultiplier);
            list.get(i).getProduct().setCalculatedPrice(String.valueOf(price));
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_wishlist, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        SelectedItem selectedItem = list.get(position);
        Product obj = selectedItem.getProduct();
        holder.tvProduct.setText(obj.getProductTitle());
        if (Double.parseDouble(obj.getCalculatedPrice()) == 0) {
            holder.tvPrice.setVisibility(View.GONE);
        } else {
            holder.tvPrice.setText(selectedCurrency + " " + Utils.format2Dec(Double.parseDouble(obj.getCalculatedPrice())));
            holder.tvPrice.setVisibility(View.VISIBLE);
        }
        GlideApp.with(mContext).load("http://103.127.146.25/~tailors/Tailorsmart/mobileimages/product/"
                + obj.getProductId() + ".jpg").into(holder.ivProduct);
        holder.rvFabrics.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        if (obj.getFabricList() != null && obj.getFabricList().size() > 0) {
            holder.rvFabrics.setAdapter(new FabricPhotoAdapter(mContext, obj.getFabricList(), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext, AppointmentFabricListActivity.class)
                            .putExtra("selectedItem", selectedItem));
                }
            }));
        } else {
            holder.rvFabrics.setAdapter(null);
        }
        if (selectedItem.getFabrics() != null && selectedItem.getFabrics().size() > 0) {
            holder.tvViewFabrics.setVisibility(View.GONE);
            holder.tvViewFabrics.setOnClickListener(view ->
                    mContext.startActivity(new Intent(mContext, AppointmentFabricListActivity.class)
                            .putExtra("selectedItem", selectedItem)));
            holder.itemView.setOnClickListener(view ->
                    mContext.startActivity(new Intent(mContext, AppointmentFabricListActivity.class)
                            .putExtra("selectedItem", selectedItem)));
        } else {
            holder.tvViewFabrics.setVisibility(View.GONE);
        }
        holder.ivDelete.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivProduct)
        ImageView ivProduct;
        @BindView(R.id.tvProduct)
        TextView tvProduct;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.rvFabrics)
        RecyclerView rvFabrics;
        @BindView(R.id.tvViewFabrics)
        TextView tvViewFabrics;
        @BindView(R.id.ivDelete)
        ImageView ivDelete;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
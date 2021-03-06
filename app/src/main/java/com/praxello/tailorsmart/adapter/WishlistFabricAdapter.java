package com.praxello.tailorsmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.praxello.tailorsmart.App;
import com.praxello.tailorsmart.FullScreenImageActivity;
import com.praxello.tailorsmart.GlideApp;
import com.praxello.tailorsmart.R;
import com.praxello.tailorsmart.WishListActivity;
import com.praxello.tailorsmart.WishListFabricListActivity;
import com.praxello.tailorsmart.model.Fabric;
import com.praxello.tailorsmart.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WishlistFabricAdapter extends RecyclerView.Adapter<WishlistFabricAdapter.RecyclerViewHolder> {
    private Context mContext;
    private ArrayList<Fabric> list;
    App app;
    private final String selectedCurrency;
    private final String selectedCurrencyMultiplier;

    public WishlistFabricAdapter(Context mContext, ArrayList<Fabric> list) {
        this.mContext = mContext;
        this.list = list;
        app = (App) mContext.getApplicationContext();
        selectedCurrency = app.getPreferences().getSelectedCurrency();
        selectedCurrencyMultiplier = app.getPreferences().getSelectedCurrencyMultiplier();
        for (int i = 0; i < list.size(); i++) {
            double price = Double.parseDouble(list.get(i).getMappedFabricPrice()) * Double.parseDouble(selectedCurrencyMultiplier);
            list.get(i).setFabricCalculatedPrice(String.valueOf(price));
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fabric, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Fabric obj = list.get(position);
        holder.tvFabricName.setText(obj.getFabricTitle());
        holder.tvDetails.setText(obj.getFabricDetails());
        if (Double.parseDouble(obj.getFabricCalculatedPrice()) == 0) {
            holder.tvPrice.setVisibility(View.GONE);
        } else {
            holder.tvPrice.setText(selectedCurrency + " " + Utils.format2Dec(Double.parseDouble(obj.getFabricCalculatedPrice())));
            holder.tvPrice.setVisibility(View.VISIBLE);
        }
        holder.cbSelect.setVisibility(View.GONE);
        holder.ivDelete.setVisibility(View.VISIBLE);
        GlideApp.with(mContext).load("http://103.127.146.25/~tailors/Tailorsmart/mobileimages/fabric/"
                + obj.getSkuNo() + ".jpg").into(holder.ivFabric);
        holder.ivFabric.setOnClickListener(view -> {
            mContext.startActivity(new Intent(mContext, FullScreenImageActivity.class)
                    .putExtra("title", obj.getFabricTitle())
                    .putExtra("urlPhotoClick", "http://103.127.146.25/~tailors/Tailorsmart/mobileimages/fabric/"
                            + obj.getSkuNo() + ".jpg"));
        });
        if (((WishListFabricListActivity) mContext).isShowDeleteBtn) {
            holder.ivDelete.setVisibility(View.VISIBLE);
        } else {
            holder.ivDelete.setVisibility(View.GONE);
        }
        holder.ivDelete.setOnClickListener(view -> {
            if (list.size() == 1) {
                Utils.showDialog(mContext, "Product must have at least one frabric added", false, (dialog, which) -> dialog.dismiss());
                return;
            }
            list.remove(list.get(holder.getAdapterPosition()));
            notifyItemRemoved(holder.getAdapterPosition());
            ((WishListFabricListActivity) mContext).product.setFabricList(list);
            app.getPreferences().updateWishlist(((WishListFabricListActivity) mContext).product);
            ((WishListFabricListActivity) mContext).toolbarTitle.setText("Fabrics(" + list.size() + ")");
            if (WishListActivity.activity != null && !WishListActivity.activity.isFinishing()) {
                WishListActivity.activity.loadWishlistData();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public ArrayList<Fabric> getList() {
        return list;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivFabric)
        ImageView ivFabric;
        @BindView(R.id.tvFabricName)
        TextView tvFabricName;
        @BindView(R.id.tvDetails)
        TextView tvDetails;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.cbSelect)
        AppCompatCheckBox cbSelect;
        @BindView(R.id.ivDelete)
        ImageView ivDelete;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
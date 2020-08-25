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
import com.praxello.tailorsmart.model.Fabric;
import com.praxello.tailorsmart.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FabricAdapter extends RecyclerView.Adapter<FabricAdapter.RecyclerViewHolder> {
    private Context mContext;
    private ArrayList<Fabric> list;
    List<Fabric> list_search = new ArrayList<>();
    //    public int selected = -1;
    App app;
    private final String selectedCurrency;
    private final String selectedCurrencyMultiplier;

    public FabricAdapter(Context mContext, ArrayList<Fabric> list) {
        this.mContext = mContext;
        list_search.addAll(list);
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
//        holder.tvDetails.setText(obj.getFabricDetails());
        holder.tvDetails.setText(obj.getColorName() + " | " + obj.getFabricBrand());
        if (Double.parseDouble(obj.getFabricCalculatedPrice()) == 0) {
            holder.tvPrice.setVisibility(View.GONE);
        } else {
            holder.tvPrice.setText(selectedCurrency + " " + Utils.format2Dec(Double.parseDouble(obj.getFabricCalculatedPrice())));
            holder.tvPrice.setVisibility(View.VISIBLE);
        }
        holder.cbSelect.setOnCheckedChangeListener(null);
        holder.cbSelect.setChecked(obj.isChecked());
        GlideApp.with(mContext).load("http://103.127.146.5/~tailor/Tailorsmart/mobileimages/fabric/"
                + obj.getSkuNo() + ".jpg").into(holder.ivFabric);
        holder.ivFabric.setOnClickListener(view -> {
            mContext.startActivity(new Intent(mContext, FullScreenImageActivity.class)
                    .putExtra("title", obj.getFabricTitle())
                    .putExtra("urlPhotoClick", "http://103.127.146.5/~tailor/Tailorsmart/mobileimages/fabric/"
                            + obj.getSkuNo() + ".jpg"));
        });
        holder.cbSelect.setOnCheckedChangeListener((compoundButton, b) -> {
            Fabric fabric = list.get(holder.getAdapterPosition());
            for (int i = 0; i < list_search.size(); i++) {
                if (list_search.get(i).getFabricId().equals(fabric.getFabricId())) {
                    list_search.get(i).setChecked(compoundButton.isChecked());
                    break;
                }
            }
            obj.setChecked(compoundButton.isChecked());
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

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if (charText.length() == 0) {
            list.addAll(list_search);
        } else {
            for (int i = 0; i < list_search.size(); i++) {
                Fabric g = list_search.get(i);
                if (g.getFabricTitle().toLowerCase().contains(charText)) {
                    list.add(list_search.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }
}
package com.praxello.tailorsmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.praxello.tailorsmart.App;
import com.praxello.tailorsmart.GlideApp;
import com.praxello.tailorsmart.R;
import com.praxello.tailorsmart.model.FabricOrderItem;
import com.praxello.tailorsmart.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FabricOrderAdapter extends RecyclerView.Adapter<FabricOrderAdapter.RecyclerViewHolder> {
    private Context mContext;
    private List<FabricOrderItem> list;
    List<FabricOrderItem> list_search = new ArrayList<>();
    App app;
    private final String selectedCurrency;

    public FabricOrderAdapter(Context mContext, List<FabricOrderItem> list) {
        this.mContext = mContext;
        list_search.addAll(list);
        this.list = list;
        app = (App) mContext.getApplicationContext();
        selectedCurrency = app.getPreferences().getSelectedCurrency();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fabric, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        FabricOrderItem obj = list.get(position);
        holder.tvFabricName.setText(obj.getFabricTitle());
//        holder.tvDetails.setText(obj.getFabricDetails());
        holder.tvDetails.setText(obj.getColorName() + " | " + obj.getFabricBrand());
        holder.tvPrice.setText(selectedCurrency + " " + Utils.format2Dec(Double.parseDouble(obj.getFabricPrice())));
        holder.tvPrice.setVisibility(View.VISIBLE);
        holder.cbSelect.setVisibility(View.GONE);
        GlideApp.with(mContext).load("http://103.127.146.25/~tailors/Tailorsmart/mobileimages/fabric/"
                + obj.getSkuNo() + ".jpg").into(holder.ivFabric);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<FabricOrderItem> getList() {
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
}
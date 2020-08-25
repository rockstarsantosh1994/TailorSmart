package com.praxello.tailorsmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.praxello.tailorsmart.App;
import com.praxello.tailorsmart.GlideApp;
import com.praxello.tailorsmart.OrderDetailsActivity;
import com.praxello.tailorsmart.OrderItemDetailsActivity;
import com.praxello.tailorsmart.R;
import com.praxello.tailorsmart.model.OrderItem;
import com.praxello.tailorsmart.model.ProductOrderItem;
import com.praxello.tailorsmart.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.RecyclerViewHolder> {
    private List<OrderItem> list;
    Context mContext;
    private final String selectedCurrency;

    public OrderDetailAdapter(Context mContext, List<OrderItem> list) {
        this.list = list;
        this.mContext = mContext;
        App app = (App) mContext.getApplicationContext();
        selectedCurrency = app.getPreferences().getSelectedCurrency();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_wishlist, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        OrderItem orderItem = list.get(position);
        ProductOrderItem productOrderItem = orderItem.getProductOrderItem();
        holder.tvProduct.setText(productOrderItem.getProductTitle());
        if (Double.parseDouble(productOrderItem.getOrderItemPrice()) == 0) {
            holder.tvPrice.setVisibility(View.GONE);
        } else {
            holder.tvPrice.setText(selectedCurrency + " " + Utils.format2Dec(Double.parseDouble(productOrderItem.getOrderItemPrice())));
            holder.tvPrice.setVisibility(View.VISIBLE);
        }
        GlideApp.with(mContext).load("http://103.127.146.5/~tailor/Tailorsmart/mobileimages/product/"
                + productOrderItem.getProductId() + ".jpg").into(holder.ivProduct);
        if (orderItem.getFabrics() != null || orderItem.getMeasurements() != null || orderItem.getStyles() != null) {
            holder.tvViewFabrics.setVisibility(View.VISIBLE);
            holder.tvViewFabrics.setText("View Details");
            holder.tvViewFabrics.setOnClickListener(view ->
                    mContext.startActivity(new Intent(mContext, OrderItemDetailsActivity.class)
                            .putExtra("orderItem", orderItem)));
            holder.itemView.setOnClickListener(view ->
                    mContext.startActivity(new Intent(mContext, OrderItemDetailsActivity.class)
                            .putExtra("orderItem", orderItem)));
        } else {
            holder.tvViewFabrics.setVisibility(View.GONE);
        }
        holder.ivDelete.setVisibility(View.GONE);
        holder.cbSelect.setVisibility(View.VISIBLE);
        holder.cbSelect.setOnCheckedChangeListener(null);
        holder.cbSelect.setChecked(productOrderItem.isChecked());
        holder.cbSelect.setOnCheckedChangeListener((compoundButton, b) -> {
            productOrderItem.setChecked(compoundButton.isChecked());
        });
        holder.llAlterDelete.setVisibility(View.VISIBLE);
        holder.ivAlter.setOnClickListener(view -> {
            if (mContext instanceof OrderDetailsActivity) {
                new MaterialDialog.Builder(mContext)
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .title("Alternation Request")
                        .content("Enter comments for Tailor")
                        .autoDismiss(false)
                        .positiveText("Submit")
                        .input("", "", (dialog, input) -> {
                            String val = input.toString();
                            if (TextUtils.isEmpty(val)) {
                                Utils.showLongToast(mContext, "Enter comments for Tailor");
                            } else {
                                ((OrderDetailsActivity) mContext).requestforalteration(holder.getAdapterPosition(), val);
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
        holder.ivDeleteOrderItem.setOnClickListener(view -> {
            if (mContext instanceof OrderDetailsActivity) {
                Utils.showDialog(mContext, "Are sure want to delete this?", true, (dialog, which) -> {
                    ((OrderDetailsActivity) mContext).deleteorderitem(holder.getAdapterPosition());
                });
            }
        });
        holder.tvDelivery.setText("Delivery: " + Utils.ymdTodmy(Utils.checkForNull(productOrderItem.getDeliveryDate())));
        holder.tvDelivery.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return list != null && list.size() > 0 ? list.size() : 0;
    }

    public List<OrderItem> getList() {
        return list;
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
        @BindView(R.id.tvDelivery)
        TextView tvDelivery;
        @BindView(R.id.ivDelete)
        ImageView ivDelete;
        @BindView(R.id.llAlterDelete)
        LinearLayout llAlterDelete;
        @BindView(R.id.ivAlter)
        ImageView ivAlter;
        @BindView(R.id.ivDeleteOrderItem)
        ImageView ivDeleteOrderItem;
        @BindView(R.id.cbSelect)
        AppCompatCheckBox cbSelect;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
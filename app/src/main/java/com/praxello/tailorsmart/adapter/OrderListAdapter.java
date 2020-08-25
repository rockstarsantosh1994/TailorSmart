package com.praxello.tailorsmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.praxello.tailorsmart.App;
import com.praxello.tailorsmart.OrderDetailsActivity;
import com.praxello.tailorsmart.OrdersActivity;
import com.praxello.tailorsmart.PaymentsActivity;
import com.praxello.tailorsmart.R;
import com.praxello.tailorsmart.model.Order;
import com.praxello.tailorsmart.model.OrderDetails;
import com.praxello.tailorsmart.utils.Utils;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.RecyclerViewHolder> {
    private List<Order> tempList;
    Context mContext;
    private final App app;

    public OrderListAdapter(Context mContext, List<Order> list) {
        this.tempList = list;
        this.mContext = mContext;
        app = (App) mContext.getApplicationContext();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Order order = tempList.get(position);
        OrderDetails orderDetails = order.getOrderDetails();
        holder.tvTotal.setText(orderDetails.getCurrency() + " " + orderDetails.getAmount());
        holder.tvDateTime.setText(Utils.ymdTodMy(orderDetails.getPurchaseDateTime()));
        holder.tvOrderID.setText(orderDetails.getOrderId());
        if (!TextUtils.isEmpty(orderDetails.getOrderStatus())) {
            switch (orderDetails.getOrderStatus()) {
                case "0":
                    holder.tvTransactionStatus.setText("Not completed");
                    holder.tvTransactionStatus.setTextColor(holder.colorPrimary);
                    break;
                case "1":
                    holder.tvTransactionStatus.setText("Confirmed");
                    holder.tvTransactionStatus.setTextColor(holder.colorPrimary);
                    break;
                case "2":
                    holder.tvTransactionStatus.setText("Processing");
                    holder.tvTransactionStatus.setTextColor(holder.colorPrimary);
                    break;
                case "3":
                    holder.tvTransactionStatus.setText("Sent for Trial");
                    holder.tvTransactionStatus.setTextColor(holder.colorPrimary);
                    break;
                case "4":
                    holder.tvTransactionStatus.setText("Completed");
                    holder.tvTransactionStatus.setTextColor(holder.colorPrimary);
                    break;
                case "5":
                    holder.tvTransactionStatus.setText("Cancelled");
                    holder.tvTransactionStatus.setTextColor(holder.colorPrimary);
                    break;
                case "6":
                    holder.tvTransactionStatus.setText("For Alteration");
                    holder.tvTransactionStatus.setTextColor(holder.colorPrimary);
                    break;
            }
        } else {
            holder.tvTransactionStatus.setText("");
        }
        holder.itemView.setOnClickListener(view -> {
            mContext.startActivity(new Intent(mContext, OrderDetailsActivity.class)
                    .putExtra("order", tempList.get(holder.getAdapterPosition())));
        });
//        if (!TextUtils.isEmpty(orderDetails.getIsSucceed()) && !TextUtils.isEmpty(orderDetails.getIsConfirmed())) {
//            if (orderDetails.getIsSucceed().equals("1")) {
//                holder.btnMakePayment.setVisibility(View.GONE);
//            } else if (orderDetails.getIsSucceed().equals("0") && orderDetails.getIsSucceed().equals("1")) {
//
//            } else {
//                holder.btnMakePayment.setVisibility(View.GONE);
//            }
//        } else {
//            holder.btnMakePayment.setVisibility(View.GONE);
//        }
        holder.btnMakePayment.setVisibility(View.GONE);
//        holder.btnMakePayment.setOnClickListener(view -> {
//            ((OrdersActivity) mContext).orderToPass = order;
//            ((OrdersActivity) mContext).startWebViewActivity();
//        });
        holder.btnViewDetails.setOnClickListener(view -> {
            mContext.startActivity(new Intent(mContext, OrderDetailsActivity.class)
                    .putExtra("order", tempList.get(holder.getAdapterPosition())));
        });
        if (order.getIsSucceedPaymentList().size() == 0)
            holder.btnPayments.setText("Payments");
        else
            holder.btnPayments.setText("Payments(" + order.getIsSucceedPaymentList().size() + ")");
        holder.btnPayments.setOnClickListener(view -> {
            mContext.startActivity(new Intent(mContext, PaymentsActivity.class)
                    .putExtra("order", tempList.get(holder.getAdapterPosition())));
        });
        if (!TextUtils.isEmpty(orderDetails.getIsConfirmed())) {
            if (orderDetails.getIsConfirmed().equals("1")) {
                holder.tvOrderStatus.setVisibility(View.VISIBLE);
                holder.tvOrderStatus.setText("Order Confirmed");
            } else {
                holder.tvOrderStatus.setVisibility(View.GONE);
            }
        } else {
            holder.tvOrderStatus.setVisibility(View.GONE);
        }
    }

    public List<Order> getOrderList() {
        return tempList;
    }

    @Override
    public int getItemCount() {
        return tempList != null && tempList.size() > 0 ? tempList.size() : 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_orderID)
        TextView tvOrderID;
        @BindView(R.id.tv_dateTime)
        TextView tvDateTime;
        @BindView(R.id.tv_total)
        TextView tvTotal;
        @BindView(R.id.tv_transactionStatus)
        TextView tvTransactionStatus;
        @BindView(R.id.tvOrderStatus)
        TextView tvOrderStatus;
        @BindView(R.id.btnMakePayment)
        Button btnMakePayment;
        @BindView(R.id.btnPayments)
        Button btnPayments;
        @BindView(R.id.btnViewDetails)
        Button btnViewDetails;
        @BindColor(R.color.red)
        int red;
        @BindColor(R.color.colorPrimary)
        int colorPrimary;
        @BindColor(R.color.black)
        int black;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
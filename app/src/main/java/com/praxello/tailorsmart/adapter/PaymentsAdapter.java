package com.praxello.tailorsmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.praxello.tailorsmart.App;
import com.praxello.tailorsmart.PaymentsActivity;
import com.praxello.tailorsmart.R;
import com.praxello.tailorsmart.model.Payment;
import com.praxello.tailorsmart.utils.Utils;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.RecyclerViewHolder> {
    private List<Payment> tempList;
    Context mContext;
    private final App app;

    public PaymentsAdapter(Context mContext, List<Payment> list) {
        this.tempList = list;
        this.mContext = mContext;
        app = (App) mContext.getApplicationContext();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_payment, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Payment payment = tempList.get(position);
        holder.tvTotal.setText(payment.getCurrency() + " " + payment.getAmount());
        holder.tvMode.setText(payment.getPaymentMode());
        holder.tvPaymentType.setText(payment.getPaymentType());
        holder.tvDate.setText(Utils.ymdToedmy(payment.getPaymentDateTime()));
        if (payment.getIsSuceed().equals("0")) {
            holder.btnMakePayment.setVisibility(View.VISIBLE);
            holder.ivDone.setVisibility(View.GONE);
        } else {
            holder.btnMakePayment.setVisibility(View.GONE);
            holder.ivDone.setVisibility(View.VISIBLE);
        }
        holder.btnMakePayment.setOnClickListener(view -> {
            ((PaymentsActivity) mContext).paymentToPass = payment;
            ((PaymentsActivity) mContext).startWebViewActivity();
        });
    }

    @Override
    public int getItemCount() {
        return tempList != null && tempList.size() > 0 ? tempList.size() : 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvPaymentType)
        TextView tvPaymentType;
        @BindView(R.id.tvMode)
        TextView tvMode;
        @BindView(R.id.tvTotal)
        TextView tvTotal;
        @BindView(R.id.btnMakePayment)
        Button btnMakePayment;
        @BindView(R.id.ivDone)
        ImageView ivDone;
        @BindView(R.id.tvDate)
        TextView tvDate;
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
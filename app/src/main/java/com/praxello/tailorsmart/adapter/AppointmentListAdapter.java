package com.praxello.tailorsmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.praxello.tailorsmart.App;
import com.praxello.tailorsmart.AppointmentDetailsActivity;
import com.praxello.tailorsmart.AppointmentsActivity;
import com.praxello.tailorsmart.R;
import com.praxello.tailorsmart.model.Appointment;
import com.praxello.tailorsmart.model.AppointmentDetails;
import com.praxello.tailorsmart.utils.Utils;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.RecyclerViewHolder> {
    private List<Appointment> tempList;
    Context mContext;
    private final App app;

    public AppointmentListAdapter(Context mContext, List<Appointment> list) {
        this.tempList = list;
        this.mContext = mContext;
        app = (App) mContext.getApplicationContext();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_appointment, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Appointment appointment = tempList.get(position);
        AppointmentDetails appointmentDetails = appointment.getAppointmentDetails();
        if (appointmentDetails != null) {
            holder.tvSlotTime.setText(appointmentDetails.getSlotTime());
            if (!TextUtils.isEmpty(appointmentDetails.getEmployeename())) {
                holder.tvEmployeeName.setText(appointmentDetails.getEmployeename());
            } else {
                holder.tvEmployeeName.setText("Not Assigned");
            }
            holder.tvDateTime.setText(Utils.ymdToedmy(appointmentDetails.getAppointmentDate()));
            if (!TextUtils.isEmpty(appointmentDetails.getAppointmentStatus())) {
                switch (appointmentDetails.getAppointmentStatus()) {
                    case "0":
                        holder.tvStatus.setText("Processing");
                        holder.ivMore.setVisibility(View.VISIBLE);
                        break;
                    case "1":
                        holder.tvStatus.setText("Confirmed");
                        holder.ivMore.setVisibility(View.GONE);
                        break;
                    case "2":
                        holder.tvStatus.setText("Cancelled");
                        holder.ivMore.setVisibility(View.VISIBLE);
                        break;
                    case "3":
                        holder.tvStatus.setText("Withdrawn");
                        holder.ivMore.setVisibility(View.VISIBLE);
                        break;
                    default:
                        holder.tvStatus.setText("Unknown");
                        holder.ivMore.setVisibility(View.VISIBLE);
                        break;
                }
                holder.tvStatus.setVisibility(View.VISIBLE);
            } else {
                holder.tvStatus.setVisibility(View.GONE);
                holder.tvStatus.setText("");
            }
        } else {
            holder.tvSlotTime.setText("");
            holder.tvEmployeeName.setText("");
            holder.tvDateTime.setText("");
            holder.tvStatus.setText("");
        }
        holder.ivMore.setOnClickListener(view -> {
            ((AppointmentsActivity) mContext).showOptionDialog(tempList.get(holder.getAdapterPosition()));
        });
        holder.itemView.setOnClickListener(view -> {
            mContext.startActivity(new Intent(mContext, AppointmentDetailsActivity.class)
                    .putExtra("appointment", tempList.get(holder.getAdapterPosition())));
        });
    }

    public List<Appointment> getOrderList() {
        return tempList;
    }

    @Override
    public int getItemCount() {
        return tempList != null && tempList.size() > 0 ? tempList.size() : 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvSlotTime)
        TextView tvSlotTime;
        @BindView(R.id.tv_dateTime)
        TextView tvDateTime;
        @BindView(R.id.tvEmployeeName)
        TextView tvEmployeeName;
        @BindView(R.id.tvStatus)
        TextView tvStatus;
        @BindView(R.id.llContent)
        LinearLayout llContent;
        @BindView(R.id.ivMore)
        ImageView ivMore;
        @BindView(R.id.llEmployee)
        LinearLayout llEmployee;
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
package com.praxello.tailorsmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.praxello.tailorsmart.R;
import com.praxello.tailorsmart.model.Measurement;
import com.praxello.tailorsmart.model.OrderDetails;
import com.praxello.tailorsmart.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeasurementListAdapter extends RecyclerView.Adapter<MeasurementListAdapter.RecyclerViewHolder> {
    private List<Measurement> tempList;
    Context mContext;

    public MeasurementListAdapter(Context mContext, List<Measurement> list) {
        this.tempList = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_measurement_list, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Measurement obj = tempList.get(position);
        holder.tvTitle.setText(obj.getItemTitle());
        holder.tvValue.setText(obj.getValue());
    }

    @Override
    public int getItemCount() {
        return tempList != null && tempList.size() > 0 ? tempList.size() : 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvValue)
        TextView tvValue;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
package com.praxello.tailorsmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.praxello.tailorsmart.R;
import com.praxello.tailorsmart.model.Measurement;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.RecyclerViewHolder> {
    private Context mContext;
    private List<Measurement> list;

    public MeasurementAdapter(Context mContext, List<Measurement> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_measurement_list, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Measurement obj = list.get(position);
        holder.tvTitle.setText(obj.getItemTitle());
        holder.tvValue.setText(obj.getValue());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvValue)
        TextView tvValue;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
package com.praxello.tailorsmart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.praxello.tailorsmart.R;
import com.praxello.tailorsmart.model.ColorObj;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FabricColorFilterAdapter extends RecyclerView.Adapter<FabricColorFilterAdapter.RecyclerViewHolder> {
    private Context mContext;
    List<ColorObj> filterList;

    public FabricColorFilterAdapter(Context mContext, List<ColorObj> filterList, String selectedfilter) {
        this.mContext = mContext;
        Collections.sort(filterList, (obj1, obj2) -> {
            // ## Ascending order
            return obj1.getTitle().compareToIgnoreCase(obj2.getTitle()); // To compare string values
        });
        if (selectedfilter != null && !TextUtils.isEmpty(selectedfilter)) {
            for (int i = 0; i < filterList.size(); i++) {
                if (selectedfilter.contains(filterList.get(i).getHex())) {
                    filterList.get(i).setChecked(true);
                }
            }
        }
        this.filterList = filterList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_color_filter, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        ColorObj filter = filterList.get(position);
        if (filter != null) {
            String colorString;
            if (!filter.getHex().contains("#"))
                colorString = "#" + filter.getHex();
            else colorString = filter.getHex();
            int color = Color.parseColor(colorString);
            holder.ivColor.setColorFilter(color);
            holder.cbFilter.setChecked(filter.isChecked());
            holder.cbFilter.setText(filter.getTitle());
            holder.cbFilter.setOnCheckedChangeListener((compoundButton, b) -> {
                filter.setChecked(compoundButton.isChecked());
            });
        }
    }

    public List<ColorObj> getStyleFilterList() {
        return filterList;
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cb_filter)
        CheckBox cbFilter;
        @BindView(R.id.ivColor)
        ImageView ivColor;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
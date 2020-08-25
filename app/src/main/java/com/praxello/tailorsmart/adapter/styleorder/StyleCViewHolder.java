package com.praxello.tailorsmart.adapter.styleorder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.praxello.tailorsmart.R;
import com.praxello.tailorsmart.model.StyleHeader;
import com.praxello.tailorsmart.model.StyleOrderItem;
import com.praxello.tailorsmart.widget.expandablerecyclerview.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StyleCViewHolder extends ChildViewHolder {
    View view;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvValue)
    TextView tvValue;
    Context mContext;

    public StyleCViewHolder(@NonNull View itemView, Context mContext) {
        super(itemView);
        this.mContext = mContext;
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull final StyleOrderItem obj, StyleHeader styleHeader) {
        tvTitle.setText(obj.getStitchSubStyleTitle() + " : ");
        tvValue.setText(obj.getValue());
    }

    public View getView() {
        return view;
    }
}
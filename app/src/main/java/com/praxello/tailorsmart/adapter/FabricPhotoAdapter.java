package com.praxello.tailorsmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.praxello.tailorsmart.GlideApp;
import com.praxello.tailorsmart.R;
import com.praxello.tailorsmart.model.Fabric;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FabricPhotoAdapter extends RecyclerView.Adapter<FabricPhotoAdapter.RecyclerViewHolder> {
    private Context mContext;
    private ArrayList<Fabric> list;
    View.OnClickListener onClickListener;

    public FabricPhotoAdapter(Context mContext, ArrayList<Fabric> list, View.OnClickListener onClickListener) {
        this.mContext = mContext;
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fabric_photo, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Fabric obj = list.get(position);
        GlideApp.with(mContext).load("http://103.127.146.5/~tailor/Tailorsmart/mobileimages/fabric/"
                + obj.getSkuNo() + ".jpg").into(holder.ivFabric);
        holder.itemView.setOnClickListener(view -> {
            onClickListener.onClick(view);
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

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
package com.praxello.tailorsmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.praxello.tailorsmart.GlideApp;
import com.praxello.tailorsmart.MainActivity;
import com.praxello.tailorsmart.ProductListActivity;
import com.praxello.tailorsmart.R;
import com.praxello.tailorsmart.SubStyleListActivity;
import com.praxello.tailorsmart.model.Category;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.RecyclerViewHolder> {
    private Context mContext;
    private ArrayList<Category> list;

    public CategoryAdapter(Context mContext, ArrayList<Category> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Category obj = list.get(position);
        GlideApp.with(mContext).load("http://103.127.146.25/~tailors/Tailorsmart/mobileimages/style/"
                + obj.getId() + ".jpg").thumbnail(0.1f).into(holder.ivCategory);
        holder.itemView.setOnClickListener(view -> {
//            if (obj.getId().equals("1")) {
//                mContext.startActivity(new Intent(mContext, ProductListActivity.class)
//                        .putExtra("categoryId", obj.getId())
//                        .putExtra("data", ((MainActivity) mContext).data));
//            } else {
                mContext.startActivity(new Intent(mContext, SubStyleListActivity.class)
                        .putExtra("styleId", obj.getId())
                        .putExtra("title", obj.getTitle())
                        .putExtra("data", ((MainActivity) mContext).data));
//            }
        });
        if (!TextUtils.isEmpty(obj.getTitle())) {
            holder.tvTitle.setText(obj.getTitle());
            holder.tvTitle.setVisibility(View.VISIBLE);
        } else {
            holder.tvTitle.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivCategory)
        ImageView ivCategory;
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
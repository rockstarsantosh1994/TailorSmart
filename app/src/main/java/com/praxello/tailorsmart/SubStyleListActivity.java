package com.praxello.tailorsmart;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.praxello.tailorsmart.adapter.SubStyleListAdapter;
import com.praxello.tailorsmart.model.AllData;
import com.praxello.tailorsmart.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;

public class SubStyleListActivity extends BaseActivity {
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    public AllData data;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvError)
    TextView tvError;
    public String selectedStyle = "";
    public static SubStyleListActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        toolbarTitle.setText("Products");
        if (!TextUtils.isEmpty(getIntent().getStringExtra("styleId")))
            selectedStyle = getIntent().getStringExtra("styleId");
        if (!TextUtils.isEmpty(getIntent().getStringExtra("title")))
            toolbarTitle.setText(getIntent().getStringExtra("title"));
        data = getIntent().getParcelableExtra("data");

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        setProductList();
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_product_list;
    }

    public void setProductList() {
        if (data != null && data.getData() != null && data.getData().size() > 0) {
            ArrayList<Product> list = new ArrayList<>();

            Set<String> subStyleIdSet = new HashSet<>();
            for (int i = 0; i < data.getData().size(); i++) {
                subStyleIdSet.add(data.getData().get(i).getSubStyleId());
            }

            if (!TextUtils.isEmpty(selectedStyle)) {
                for (int i = 0; i < data.getData().size(); i++) {
                    Product product = data.getData().get(i);
                    if (subStyleIdSet.size() > 0 && subStyleIdSet.contains(product.getSubStyleId()) && selectedStyle.contains(data.getData().get(i).getStyleId())) {
//                        if (product.getIsGroup().equals("1")) {
//                            product.setProductTitle(product.getSubStyleTitle());
//                            product.setProductSubTitle("");
//                        }
                        list.add(product);
                        subStyleIdSet.remove(product.getSubStyleId());
                    }
                }
            }
            if (list.size() > 0) {
                Collections.sort(list, (obj1, obj2) -> {
                    // ## Ascending order
                    return obj1.sequenceNo.compareToIgnoreCase(obj2.sequenceNo); // To compare string values
                    // return Integer.valueOf(obj1.empId).compareTo(Integer.valueOf(obj2.empId)); // To compare integer values

                    // ## Descending order
                    // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                    // return Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
                });
//                toolbarTitle.setText("Products(" + list.size() + ")");
                recyclerView.setAdapter(null);
                recyclerView.setAdapter(new SubStyleListAdapter(mContext, list));
                recyclerView.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.GONE);
            } else {
//                toolbarTitle.setText("Products");
                tvError.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        } else {
//            toolbarTitle.setText("Products");
            tvError.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

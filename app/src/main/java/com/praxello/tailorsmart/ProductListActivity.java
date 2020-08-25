package com.praxello.tailorsmart;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.praxello.tailorsmart.adapter.ProductAdapter;
import com.praxello.tailorsmart.fragment.FilterProductFragment;
import com.praxello.tailorsmart.model.AllData;
import com.praxello.tailorsmart.model.Product;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;

public class ProductListActivity extends BaseActivity {
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    public AllData data;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvError)
    TextView tvError;
    public String selectedStyle = "", selectedSubStyle = "", selectedCategory = "", parentId = "", selectedPrice = "";
    public static ProductListActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        toolbarTitle.setText("Products");
        if (!TextUtils.isEmpty(getIntent().getStringExtra("categoryId")))
            selectedCategory = getIntent().getStringExtra("categoryId");
        if (!TextUtils.isEmpty(getIntent().getStringExtra("styleId")))
            selectedStyle = getIntent().getStringExtra("styleId");
        if (!TextUtils.isEmpty(getIntent().getStringExtra("parentId")))
            parentId = getIntent().getStringExtra("parentId");
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

            if (TextUtils.isEmpty(selectedStyle) && TextUtils.isEmpty(selectedSubStyle) && TextUtils.isEmpty(selectedCategory)) {
                for (int i = 0; i < data.getData().size(); i++) {
                    if (!TextUtils.isEmpty(parentId) && parentId.equals(data.getData().get(i).getParentId())) {
                        list.add(data.getData().get(i));
                    }
                }
            } else {
                if (!TextUtils.isEmpty(selectedStyle)) {
                    if (!TextUtils.isEmpty(selectedSubStyle)) {
                        for (int i = 0; i < data.getData().size(); i++) {
                            Product product = data.getData().get(i);
                            if (selectedStyle.contains(product.getStyleId()) &&
                                    selectedSubStyle.contains(product.getSubStyleId())) {
                                if (!TextUtils.isEmpty(parentId) && parentId.equals(product.getParentId())) {
                                    list.add(data.getData().get(i));
                                } else {
                                    list.add(data.getData().get(i));
                                }
                            }
                        }
                    } else {
                        for (int i = 0; i < data.getData().size(); i++) {
                            if (selectedStyle.contains(data.getData().get(i).getStyleId()))
                                if (!TextUtils.isEmpty(parentId) && parentId.equals(data.getData().get(i).getParentId())) {
                                    list.add(data.getData().get(i));
                                } else {
                                    list.add(data.getData().get(i));
                                }
                        }
                    }
                } else if (!TextUtils.isEmpty(selectedSubStyle)) {
                    for (int i = 0; i < data.getData().size(); i++) {
                        if (selectedSubStyle.contains(data.getData().get(i).getSubStyleId()))
                            if (!TextUtils.isEmpty(parentId) && parentId.equals(data.getData().get(i).getParentId())) {
                                list.add(data.getData().get(i));
                            } else {
                                list.add(data.getData().get(i));
                            }
                    }
                }
                if (!TextUtils.isEmpty(selectedCategory)) {
                    for (int i = 0; i < data.getData().size(); i++) {
                        if (selectedCategory.contains(data.getData().get(i).getCategoryId())) {
                            boolean isProdAdded = false;
                            for (int j = 0; j < list.size(); j++) {
                                if (data.getData().get(i).getProductId().equals(list.get(j).getProductId())) {
                                    isProdAdded = true;
                                    break;
                                }
                            }
                            if (!isProdAdded) {
                                if (!TextUtils.isEmpty(parentId) && parentId.equals(data.getData().get(i).getParentId())) {
                                    list.add(data.getData().get(i));
                                } else {
                                    list.add(data.getData().get(i));
                                }
                            }
                        }
                    }
                }
            }
            if (list.size() > 0) {
                if (!TextUtils.isEmpty(selectedPrice)) {
                    if (selectedPrice.equals("L2H")) {
                        Collections.sort(list, (obj1, obj2) -> {
                            // ## Ascending order
                            return Double.valueOf(obj1.price).compareTo(Double.valueOf(obj2.price)); // To compare integer values

                            // ## Descending order
                            // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                            // return Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
                        });
                    } else {
                        Collections.sort(list, (obj1, obj2) -> {
                            // ## Ascending order
//                            return Double.valueOf(obj1.price).compareTo(Double.valueOf(obj2.price)); // To compare integer values

                            // ## Descending order
                            // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                            return Double.valueOf(obj2.price).compareTo(Double.valueOf(obj1.price)); // To compare integer values
                        });
                    }
                } else {
                    Collections.sort(list, (obj1, obj2) -> {
                        // ## Ascending order
                        return obj1.sequenceNo.compareToIgnoreCase(obj2.sequenceNo); // To compare string values
                        // return Integer.valueOf(obj1.empId).compareTo(Integer.valueOf(obj2.empId)); // To compare integer values

                        // ## Descending order
                        // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                        // return Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
                    });
                }
                toolbarTitle.setText("Products(" + list.size() + ")");
                recyclerView.setAdapter(null);
                recyclerView.setAdapter(new ProductAdapter(mContext, list));
                recyclerView.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.GONE);
            } else {
                toolbarTitle.setText("Products");
                tvError.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        } else {
            toolbarTitle.setText("Products");
            tvError.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter: {
                if (data != null) {
                    boolean isPriceOnly = false;
                    if (!TextUtils.isEmpty(parentId)) isPriceOnly = true;
                    FilterProductFragment filterProductFragment = new FilterProductFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("data", data);
                    bundle.putBoolean("isPriceOnly", isPriceOnly);
                    filterProductFragment.setArguments(bundle);
                    filterProductFragment.show(getSupportFragmentManager(), "FilterProductFragment");
                }
                return true;
            }
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

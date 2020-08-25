package com.praxello.tailorsmart;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.praxello.tailorsmart.adapter.WishlistFabricAdapter;
import com.praxello.tailorsmart.model.Product;

import butterknife.BindView;

public class WishListFabricListActivity extends BaseActivity {
    @BindView(R.id.toolbarTitle)
    public TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    public Product product;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvError)
    TextView tvError;
    @BindView(R.id.btnTakeAppointment)
    Button btnTakeAppointment;
    public boolean isShowDeleteBtn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = getIntent().getParcelableExtra("product");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        toolbarTitle.setText("Fabrics");

        isShowDeleteBtn = getIntent().getBooleanExtra("isShowDeleteBtn", false);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        btnTakeAppointment.setVisibility(View.GONE);

        if (product != null && product.getFabricList() != null && product.getFabricList().size() > 0) {
            toolbarTitle.setText("Fabrics(" + product.getFabricList().size() + ")");
            recyclerView.setAdapter(new WishlistFabricAdapter(mContext, product.getFabricList()));
            recyclerView.setVisibility(View.VISIBLE);
            tvError.setVisibility(View.GONE);
        } else {
            toolbarTitle.setText("Fabrics");
            tvError.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_wishlist;
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

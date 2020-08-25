package com.praxello.tailorsmart;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.praxello.tailorsmart.adapter.FabricOrderAdapter;
import com.praxello.tailorsmart.adapter.MeasurementAdapter;
import com.praxello.tailorsmart.adapter.styleorder.StyleExpandableAdapter;
import com.praxello.tailorsmart.model.FabricOrderItem;
import com.praxello.tailorsmart.model.OrderItem;
import com.praxello.tailorsmart.model.StyleHeader;
import com.praxello.tailorsmart.model.StyleOrderItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;

public class OrderItemDetailsActivity extends BaseActivity {
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.tabLayout)
    TabLayout tabs;
    OrderItem orderItem;
    private int tabPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        toolbarTitle.setText("Order Item Details");
        orderItem = getIntent().getParcelableExtra("orderItem");
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        tabs.setVisibility(View.VISIBLE);
        tabs.addTab(tabs.newTab().setText("Fabrics"), true);
        tabs.addTab(tabs.newTab().setText("Measurements"));
        tabs.addTab(tabs.newTab().setText("Styles"));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
                loadData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
                loadData();
            }
        });
        loadData();
    }

    private void loadData() {
        switch (tabPosition) {
            case 0:
                if (orderItem.getFabrics() != null && orderItem.getFabrics().size() > 0) {
                    List<FabricOrderItem> fabrics = orderItem.getFabrics();
                    FabricOrderAdapter adapter = new FabricOrderAdapter(mContext, fabrics);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    tvError.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    tvError.setText("No Records available.");
                    tvError.setVisibility(View.VISIBLE);
                }
                break;
            case 1:
                if (orderItem.getMeasurements() != null && orderItem.getMeasurements().size() > 0) {
                    recyclerView.setAdapter(new MeasurementAdapter(mContext, orderItem.getMeasurements()));
                    recyclerView.setVisibility(View.VISIBLE);
                    tvError.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    tvError.setText("No Records available.");
                    tvError.setVisibility(View.VISIBLE);
                }
                break;
            case 2:
                if (orderItem != null && orderItem.getStyles() != null && orderItem.getStyles().size() > 0) {
                    List<StyleHeader> list = new ArrayList<>();
                    Set<String> categorySet = new HashSet<>();
                    for (int i = 0; i < orderItem.getStyles().size(); i++) {
                        categorySet.add(orderItem.getStyles().get(i).getStitchStyleTitle());
                    }
                    List<String> categories = new ArrayList<>(categorySet);
                    for (int i = 0; i < categories.size(); i++) {
                        String catName = categories.get(i);
                        StyleHeader styleHeader = new StyleHeader();
                        styleHeader.setTitle(catName);
                        List<StyleOrderItem> styleItemList = new ArrayList<>();
                        for (int j = 0; j < orderItem.getStyles().size(); j++) {
                            if (orderItem.getStyles().get(j).getStitchStyleTitle().equalsIgnoreCase(catName)) {
                                styleItemList.add(orderItem.getStyles().get(j));
                            }
                        }
                        styleHeader.setStyleOrderItemList(styleItemList);
                        list.add(styleHeader);
                    }
                    recyclerView.setAdapter(new StyleExpandableAdapter(mContext, list));
                    recyclerView.setVisibility(View.VISIBLE);
                    tvError.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    tvError.setText("No Records available.");
                    tvError.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_order_item_details;
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

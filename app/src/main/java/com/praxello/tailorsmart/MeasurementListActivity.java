package com.praxello.tailorsmart;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.praxello.tailorsmart.adapter.MeasurementListAdapter;
import com.praxello.tailorsmart.api.ApiRequestHelper;
import com.praxello.tailorsmart.model.MeasurementData;
import com.praxello.tailorsmart.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class MeasurementListActivity extends BaseActivity {
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    public MeasurementData data;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvError)
    TextView tvError;
    public static MeasurementListActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        toolbarTitle.setText("My Measurements");

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mymeasurements();
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_orders;
    }

    public void mymeasurements() {
        if (cd.isConnectingToInternet()) {
            if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
            Map<String, String> map = new HashMap<>();
            map.put("customerid", app.getPreferences().getLoggedInUser().getData().getCustomerId());
            callList.add(app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().mymeasurements(map), new ApiRequestHelper.OnRequestComplete() {
                @Override
                public void onSuccess(Object object) {
                    if (progressBar != null) progressBar.setVisibility(View.GONE);
                    data = (MeasurementData) object;
                    if (data != null && data.getResponsecode() == 200) {
                        if (data.getData() != null && data.getData().size() > 0) {
                            if (recyclerView != null) {
                                recyclerView.setAdapter(null);
                                recyclerView.setAdapter(new MeasurementListAdapter(mContext, data.getData()));
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                            if (tvError != null) tvError.setVisibility(View.GONE);
                        } else {
                            if (tvError != null) tvError.setVisibility(View.VISIBLE);
                            if (recyclerView != null) recyclerView.setVisibility(View.GONE);
                        }
                    } else {
                        if (tvError != null) tvError.setVisibility(View.VISIBLE);
                        if (recyclerView != null) recyclerView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(String apiResponse) {
                    if (progressBar != null) progressBar.setVisibility(View.GONE);
                    if (tvError != null) tvError.setVisibility(View.VISIBLE);
                    if (recyclerView != null) recyclerView.setVisibility(View.GONE);
                }
            }));
        } else {
            if (progressBar != null) progressBar.setVisibility(View.GONE);
            if (tvError != null) tvError.setVisibility(View.VISIBLE);
            if (recyclerView != null) recyclerView.setVisibility(View.GONE);
            if (mContext != null) Utils.alert_dialog(mContext);
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

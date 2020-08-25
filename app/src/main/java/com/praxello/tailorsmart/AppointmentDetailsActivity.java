package com.praxello.tailorsmart;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.praxello.tailorsmart.adapter.AppointmentProductAdapter;
import com.praxello.tailorsmart.model.AllData;
import com.praxello.tailorsmart.model.Appointment;

import butterknife.BindView;

public class AppointmentDetailsActivity extends BaseActivity {
    @BindView(R.id.toolbarTitle)
    public
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
    public static AppointmentDetailsActivity activity;
    Appointment appointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        toolbarTitle.setText("Appointment Details");
        appointment = getIntent().getParcelableExtra("appointment");

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        loadDetails();
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_wishlist;
    }

    public void loadDetails() {
        if (appointment != null && appointment.getSelectedItems().size() > 0) {
            recyclerView.setAdapter(new AppointmentProductAdapter(mContext, appointment.getSelectedItems()));
            recyclerView.setVisibility(View.VISIBLE);
            tvError.setVisibility(View.GONE);
        } else {
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

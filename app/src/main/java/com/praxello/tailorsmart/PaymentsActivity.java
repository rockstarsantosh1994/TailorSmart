package com.praxello.tailorsmart;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.praxello.tailorsmart.adapter.PaymentsAdapter;
import com.praxello.tailorsmart.model.Data;
import com.praxello.tailorsmart.model.Order;
import com.praxello.tailorsmart.model.OrderData;
import com.praxello.tailorsmart.model.Payment;
import com.praxello.tailorsmart.utility.AvenuesParams;

import java.math.BigDecimal;

import butterknife.BindView;

public class PaymentsActivity extends BaseActivity {
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    public OrderData data;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvError)
    TextView tvError;
    public static PaymentsActivity activity;
    private String transactionId = "";
    public BigDecimal finalTotal = new BigDecimal(0);
    public Payment paymentToPass;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        order = getIntent().getParcelableExtra("order");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        if (order.getPaymentList() != null && order.getPaymentList().size() > 0)
            toolbarTitle.setText("Payments(" + order.getPaymentList().size() + ")");
        else
            toolbarTitle.setText("Payments");

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        if (order.getPaymentList() != null && order.getPaymentList().size() > 0) {
            recyclerView.setAdapter(null);
            recyclerView.setAdapter(new PaymentsAdapter(mContext, order.getPaymentList()));
            recyclerView.setVisibility(View.VISIBLE);
            tvError.setVisibility(View.GONE);
        } else {
            toolbarTitle.setText("Payments");
            tvError.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_orders;
    }

    public void startWebViewActivity() {
        Intent intent = new Intent(mContext, CCAvenueWebViewActivity.class);
        intent.putExtra(AvenuesParams.ACCESS_CODE, BuildConfig.ACCESS_CODE);
        intent.putExtra(AvenuesParams.MERCHANT_ID, BuildConfig.MERCHANT_ID);
//        Random random = new Random();
//        int m = random.nextInt(9999 - 1000) + 1000;
        intent.putExtra(AvenuesParams.ORDER_ID, paymentToPass.getPaymentId());
        intent.putExtra(AvenuesParams.CURRENCY, paymentToPass.getCurrency());
//        intent.putExtra(AvenuesParams.AMOUNT, finalTotal.doubleValue() + "");
        intent.putExtra(AvenuesParams.AMOUNT, paymentToPass.getAmount());
        intent.putExtra(AvenuesParams.REDIRECT_URL, BuildConfig.REDIRECT_URL);
        intent.putExtra(AvenuesParams.CANCEL_URL, BuildConfig.CANCEL_URL);
        intent.putExtra(AvenuesParams.RSA_KEY_URL, BuildConfig.RSA_KEY_URL);
        intent.putExtra(AvenuesParams.PROMO_CODE, "");
        if (app.getPreferences().getLoggedInUser().getData() != null) {
            Data data = app.getPreferences().getLoggedInUser().getData();
            intent.putExtra(AvenuesParams.BILLING_COUNTRY, "India");
            intent.putExtra(AvenuesParams.BILLING_STATE, "Maharashtra");
//            if (data.getCity() != null)
//                intent.putExtra(AvenuesParams.BILLING_CITY, data.getCity());
            intent.putExtra(AvenuesParams.BILLING_CITY, "Pune");
//            if (data.getZipcode() != null)
//                intent.putExtra(AvenuesParams.BILLING_ZIP, data.getZipcode());
            intent.putExtra(AvenuesParams.BILLING_ZIP, "411001");
            intent.putExtra(AvenuesParams.BILLING_NAME, getName(data));
            if (data.getAddress() != null)
                intent.putExtra(AvenuesParams.BILLING_ADDRESS, data.getAddress());
            if (data.getMobile() != null)
                intent.putExtra(AvenuesParams.BILLING_TEL, data.getMobile());
            if (data.getEmail() != null)
                intent.putExtra(AvenuesParams.BILLING_EMAIL, data.getEmail());
            intent.putExtra(AvenuesParams.DELIVERY_NAME, getName(data));
            if (data.getAddress() != null)
                intent.putExtra(AvenuesParams.DELIVERY_ADDRESS, data.getAddress());
//            if (data.getCity() != null)
//                intent.putExtra(AvenuesParams.DELIVERY_CITY, data.getCity());
            intent.putExtra(AvenuesParams.DELIVERY_CITY, "Pune");
            intent.putExtra(AvenuesParams.DELIVERY_STATE, "Maharashtra");
//            if (data.getZipcode() != null)
//                intent.putExtra(AvenuesParams.DELIVERY_ZIP, data.getZipcode());
            intent.putExtra(AvenuesParams.DELIVERY_ZIP, "411001");
            intent.putExtra(AvenuesParams.DELIVERY_COUNTRY, "India");
            if (data.getMobile() != null)
                intent.putExtra(AvenuesParams.DELIVERY_TEL, data.getMobile());
        }
//        }
        intent.putExtra(AvenuesParams.BILLING_NOTES, "");
        startActivityForResult(intent, 121);
    }

    private String getName(Data data) {
        String name = "";
        if (!TextUtils.isEmpty(data.getFirstName())) {
            name = data.getFirstName();
        }
        if (!TextUtils.isEmpty(data.getLastName())) {
            name = name + " " + data.getLastName();
        }
        return name;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 121 && resultCode == RESULT_OK) {
                String status = data.getStringExtra("status");
                if (status.equalsIgnoreCase("Transaction Declined!")) {
                    new MaterialDialog.Builder(mContext)
                            .content(status)
                            .cancelable(false)
                            .positiveText("Retry")
                            .negativeText("Cancel")
                            .autoDismiss(false)
                            .onPositive((dialog, which) -> startWebViewActivity())
                            .onNegative((dialog, which) -> {
                                dialog.dismiss();
                                startActivity(new Intent(mContext, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            }).show();
                } else if (status.equalsIgnoreCase("Transaction Successful!")) {
                    new MaterialDialog.Builder(mContext)
                            .content("Thank you for your order. Your request has been submitted for approval.")
                            .cancelable(false)
                            .positiveText("Ok")
                            .autoDismiss(false)
                            .onPositive((dialog, which) -> {
                                dialog.dismiss();
                                startActivity(new Intent(mContext, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            }).show();
                } else if (status.equalsIgnoreCase("Transaction Cancelled!")) {
                    new MaterialDialog.Builder(mContext)
                            .content(status)
                            .positiveText("Ok")
                            .cancelable(false)
                            .autoDismiss(false)
                            .onPositive((dialog, which) -> {
                                dialog.dismiss();
                                startActivity(new Intent(mContext, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            }).show();
                } else {
                    new MaterialDialog.Builder(mContext)
                            .content(status)
                            .positiveText("Ok")
                            .autoDismiss(false)
                            .cancelable(false)
                            .onPositive((dialog, which) -> {
                                dialog.dismiss();
                                startActivity(new Intent(mContext, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            }).show();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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

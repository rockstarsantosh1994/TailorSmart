package com.praxello.tailorsmart;

import android.content.Intent;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.praxello.tailorsmart.adapter.OrderListAdapter;
import com.praxello.tailorsmart.api.ApiRequestHelper;
import com.praxello.tailorsmart.model.Data;
import com.praxello.tailorsmart.model.Order;
import com.praxello.tailorsmart.model.OrderData;
import com.praxello.tailorsmart.utility.AvenuesParams;
import com.praxello.tailorsmart.utils.Utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class OrdersActivity extends BaseActivity {
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
    public static OrdersActivity activity;
    private String transactionId = "";
    public BigDecimal finalTotal = new BigDecimal(0);
    public Order orderToPass;
    private String selectedCurrency;
    private String selectedCurrencyMultiplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        selectedCurrency = app.getPreferences().getSelectedCurrency();
        selectedCurrencyMultiplier = app.getPreferences().getSelectedCurrencyMultiplier();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        toolbarTitle.setText("Orders");

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        setProductList();
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_orders;
    }

    public void setProductList() {
        if (cd.isConnectingToInternet()) {
            progressBar.setVisibility(View.VISIBLE);
            Map<String, String> map = new HashMap<>();
//            map.put("customerid", "1");
            map.put("customerid", app.getPreferences().getLoggedInUser().getData().getCustomerId());
            callList.add(app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().customerorders(map), new ApiRequestHelper.OnRequestComplete() {
                @Override
                public void onSuccess(Object object) {
                    progressBar.setVisibility(View.GONE);
//                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing())
//                        swipeRefreshLayout.setRefreshing(false);
                    data = (OrderData) object;
                    if (data != null && data.getResponsecode() == 200) {
                        if (data.getData() != null && data.getData().size() > 0) {
                            toolbarTitle.setText("Orders(" + data.getData().size() + ")");
                            recyclerView.setAdapter(null);
                            recyclerView.setAdapter(new OrderListAdapter(mContext, data.getData()));
                            recyclerView.setVisibility(View.VISIBLE);
                            tvError.setVisibility(View.GONE);
                        } else {
                            toolbarTitle.setText("Orders");
                            tvError.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    } else {
                        toolbarTitle.setText("Orders");
                        tvError.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(String apiResponse) {
//                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing())
//                        swipeRefreshLayout.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                    toolbarTitle.setText("Orders");
                    tvError.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }));
        } else {
//            if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing())
//                swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
            toolbarTitle.setText("Orders");
            tvError.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            if (mContext != null) Utils.alert_dialog(mContext);
        }
    }

//    public void takeappointment() {
//        Map<String, String> map = new HashMap<>();
//        map.put("customerid", app.getPreferences().getLoggedInUser().getData().getCustomerId());
//        map.put("service", packageObj.getTitle());
//        map.put("carid", carId);
//        if (cd.isConnectingToInternet()) {
//            app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().takeappointment(map), new ApiRequestHelper.OnRequestComplete() {
//                @Override
//                public void onSuccess(Object object) {
//                }
//
//                @Override
//                public void onFailure(String apiResponse) {
//                }
//            });
//        }
//    }

//    public void startWebViewActivity() {
//        Intent intent = new Intent(mContext, CCAvenueWebViewActivity.class);
//        intent.putExtra(AvenuesParams.ACCESS_CODE, BuildConfig.ACCESS_CODE);
//        intent.putExtra(AvenuesParams.MERCHANT_ID, BuildConfig.MERCHANT_ID);
////        Random random = new Random();
////        int m = random.nextInt(9999 - 1000) + 1000;
//        intent.putExtra(AvenuesParams.ORDER_ID, orderToPass.getOrderDetails().getOrderId());
//        intent.putExtra(AvenuesParams.CURRENCY, selectedCurrency);
////        intent.putExtra(AvenuesParams.AMOUNT, finalTotal.doubleValue() + "");
//        intent.putExtra(AvenuesParams.AMOUNT, orderToPass.getOrderDetails().getAmount());
//        intent.putExtra(AvenuesParams.REDIRECT_URL, BuildConfig.REDIRECT_URL);
//        intent.putExtra(AvenuesParams.CANCEL_URL, BuildConfig.CANCEL_URL);
//        intent.putExtra(AvenuesParams.RSA_KEY_URL, BuildConfig.RSA_KEY_URL);
//        intent.putExtra(AvenuesParams.PROMO_CODE, orderToPass.getOrderDetails().getPromoCode());
//        if (app.getPreferences().getLoggedInUser().getData() != null) {
//            Data data = app.getPreferences().getLoggedInUser().getData();
//            intent.putExtra(AvenuesParams.BILLING_COUNTRY, "India");
//            intent.putExtra(AvenuesParams.BILLING_STATE, "Maharashtra");
////            if (data.getCity() != null)
////                intent.putExtra(AvenuesParams.BILLING_CITY, data.getCity());
//            intent.putExtra(AvenuesParams.BILLING_CITY, "Pune");
////            if (data.getZipcode() != null)
////                intent.putExtra(AvenuesParams.BILLING_ZIP, data.getZipcode());
//            intent.putExtra(AvenuesParams.BILLING_ZIP, "411001");
//            intent.putExtra(AvenuesParams.BILLING_NAME, getName(data));
//            if (data.getAddress() != null)
//                intent.putExtra(AvenuesParams.BILLING_ADDRESS, data.getAddress());
//            if (data.getMobile() != null)
//                intent.putExtra(AvenuesParams.BILLING_TEL, data.getMobile());
//            if (data.getEmail() != null)
//                intent.putExtra(AvenuesParams.BILLING_EMAIL, data.getEmail());
//            intent.putExtra(AvenuesParams.DELIVERY_NAME, getName(data));
//            if (data.getAddress() != null)
//                intent.putExtra(AvenuesParams.DELIVERY_ADDRESS, data.getAddress());
////            if (data.getCity() != null)
////                intent.putExtra(AvenuesParams.DELIVERY_CITY, data.getCity());
//            intent.putExtra(AvenuesParams.DELIVERY_CITY, "Pune");
//            intent.putExtra(AvenuesParams.DELIVERY_STATE, "Maharashtra");
////            if (data.getZipcode() != null)
////                intent.putExtra(AvenuesParams.DELIVERY_ZIP, data.getZipcode());
//            intent.putExtra(AvenuesParams.DELIVERY_ZIP, "411001");
//            intent.putExtra(AvenuesParams.DELIVERY_COUNTRY, "India");
//            if (data.getMobile() != null)
//                intent.putExtra(AvenuesParams.DELIVERY_TEL, data.getMobile());
//        }
////        }
//        intent.putExtra(AvenuesParams.BILLING_NOTES, "");
//        startActivityForResult(intent, 121);
//    }

//    private String getName(Data data) {
//        String name = "";
//        if (!TextUtils.isEmpty(data.getFirstName())) {
//            name = data.getFirstName();
//        }
//        if (!TextUtils.isEmpty(data.getLastName())) {
//            name = name + " " + data.getLastName();
//        }
//        return name;
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        try {
//            super.onActivityResult(requestCode, resultCode, data);
//            if (requestCode == 121 && resultCode == RESULT_OK) {
//                String status = data.getStringExtra("status");
//                if (status.equalsIgnoreCase("Transaction Declined!")) {
//                    new MaterialDialog.Builder(mContext)
//                            .content(status)
//                            .cancelable(false)
//                            .positiveText("Retry")
//                            .negativeText("Cancel")
//                            .autoDismiss(false)
//                            .onPositive((dialog, which) -> startWebViewActivity())
//                            .onNegative((dialog, which) -> {
//                                dialog.dismiss();
//                                startActivity(new Intent(mContext, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                            }).show();
//                } else if (status.equalsIgnoreCase("Transaction Successful!")) {
//                    new MaterialDialog.Builder(mContext)
//                            .content("Thank you for your order. Your request has been submitted for approval.")
//                            .cancelable(false)
//                            .positiveText("Ok")
//                            .autoDismiss(false)
//                            .onPositive((dialog, which) -> {
//                                dialog.dismiss();
//                                startActivity(new Intent(mContext, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                            }).show();
//                } else if (status.equalsIgnoreCase("Transaction Cancelled!")) {
//                    new MaterialDialog.Builder(mContext)
//                            .content(status)
//                            .positiveText("Ok")
//                            .cancelable(false)
//                            .autoDismiss(false)
//                            .onPositive((dialog, which) -> {
//                                dialog.dismiss();
//                                startActivity(new Intent(mContext, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                            }).show();
//                } else {
//                    new MaterialDialog.Builder(mContext)
//                            .content(status)
//                            .positiveText("Ok")
//                            .autoDismiss(false)
//                            .cancelable(false)
//                            .onPositive((dialog, which) -> {
//                                dialog.dismiss();
//                                startActivity(new Intent(mContext, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                            }).show();
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rate_us, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_rate_us: {
                startActivity(new Intent(mContext, FeedbackActivity.class));
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

package com.praxello.tailorsmart;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.praxello.tailorsmart.adapter.OrderDetailAdapter;
import com.praxello.tailorsmart.api.ApiRequestHelper;
import com.praxello.tailorsmart.model.Order;
import com.praxello.tailorsmart.model.OrderItem;
import com.praxello.tailorsmart.model.ProductOrderItem;
import com.praxello.tailorsmart.model.UserData;
import com.praxello.tailorsmart.model.UserResponse;
import com.praxello.tailorsmart.utils.Utils;
import com.praxello.tailorsmart.widget.materialprogress.CustomProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class OrderDetailsActivity extends BaseActivity {
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_error)
    TextView tvError;
    Order order;
    @BindView(R.id.tv_orderID)
    TextView tvOrderID;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    private String selectedCurrency;
    private String selectedCurrencyMultiplier;
    private OrderDetailAdapter orderDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        toolbarTitle.setText("Order Details");
        order = getIntent().getParcelableExtra("order");
        selectedCurrency = app.getPreferences().getSelectedCurrency();
        selectedCurrencyMultiplier = app.getPreferences().getSelectedCurrencyMultiplier();
        tvOrderID.setText(order.getOrderDetails().getOrderId());
        tvTotal.setText("Total : " + order.getOrderDetails().getCurrency() + " " + order.getOrderDetails().getAmount());
        if (order.getOrderItems() != null && order.getOrderItems().size() > 0) {
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            orderDetailAdapter = new OrderDetailAdapter(mContext, order.getOrderItems());
            recyclerView.setAdapter(orderDetailAdapter);
            recyclerView.setVisibility(View.VISIBLE);
            tvError.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_order_details;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reorder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reorder: {
                if (orderDetailAdapter != null) {
                    List<OrderItem> orderItems = orderDetailAdapter.getList();
                    if (orderItems != null && orderItems.size() > 0) {
                        ArrayList<OrderItem> selOrderItemList = new ArrayList<>();
                        for (int i = 0; i < orderItems.size(); i++) {
                            if (orderItems.get(i).getProductOrderItem().isChecked()) {
                                selOrderItemList.add(orderItems.get(i));
                            }
                        }
                        if (selOrderItemList.size() == 0) {
                            Utils.showLongToast(mContext, "Please Select Item to Reorder");
                        } else {
//                            praxello.com/tailorsmart/admin/reorderproduct.php
//                            postdata:{"customerid":"19","orderitemids":[{"orderitemid":"1"},{"orderitemid":"2"}]}
                            JSONObject postdata = new JSONObject();
                            try {
                                postdata.put("customerid", app.getPreferences().getLoggedInUser().getData().getCustomerId());
                                JSONArray orderitemids = new JSONArray();
                                for (int i = 0; i < selOrderItemList.size(); i++) {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("orderitemid", selOrderItemList.get(i).getProductOrderItem().getOrderItemId());
                                    orderitemids.put(jsonObject);
                                }
                                postdata.put("orderitemids", orderitemids);
                                Log.e("postdata", postdata.toString());
                                Map<String, String> map = new HashMap<>();
                                map.put("postdata", postdata.toString());
                                reorderproduct(map);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
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

    private void reorderproduct(Map<String, String> map) {
        if (cd.isConnectingToInternet()) {
            CustomProgressDialog pd = new CustomProgressDialog(mContext);
            pd.show();
            app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().reorderproduct(map), new ApiRequestHelper.OnRequestComplete() {
                @Override
                public void onSuccess(Object object) {
                    if (pd.isShowing()) pd.dismiss();
                    UserData userData = (UserData) object;
                    if (userData != null) {
                        if (userData.getResponsecode() == 200) {
                            if (userData.getMessage() != null && !TextUtils.isEmpty(userData.getMessage()))
                                Utils.showShortToast(mContext, userData.getMessage());
                        } else {
                            if (userData.getMessage() != null && !TextUtils.isEmpty(userData.getMessage()))
                                Utils.showLongToast(mContext, userData.getMessage());
                        }
                    } else {
                        Utils.showLongToast(mContext, Utils.UNPROPER_RESPONSE);
                    }
                }

                @Override
                public void onFailure(String apiResponse) {
                    if (pd.isShowing()) pd.dismiss();
                    Utils.showLongToast(mContext, apiResponse);
                }
            });
        } else {
            Utils.alert_dialog(mContext);
        }
    }

    public void requestforalteration(int adapterPosition, String remarks) {
        if (cd.isConnectingToInternet()) {
            CustomProgressDialog pd = new CustomProgressDialog(mContext);
            pd.show();
            Map<String, String> map = new HashMap<>();
            OrderItem orderItem = orderDetailAdapter.getList().get(adapterPosition);
            ProductOrderItem productOrderItem = orderItem.getProductOrderItem();
            map.put("orderitemid", "" + productOrderItem.getOrderItemId());
            map.put("remarks", "" + remarks);
            map.put("orderid", "" + productOrderItem.getOrderId());
            app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().requestforalteration(map), new ApiRequestHelper.OnRequestComplete() {
                @Override
                public void onSuccess(Object object) {
                    if (pd.isShowing()) pd.dismiss();
                    UserResponse userData = (UserResponse) object;
                    if (userData != null) {
                        if (userData.getResponsecode() == 200) {
                            if (userData.getMessage() != null && !TextUtils.isEmpty(userData.getMessage()))
                                Utils.showShortToast(mContext, userData.getMessage());
                        } else {
                            if (userData.getMessage() != null && !TextUtils.isEmpty(userData.getMessage()))
                                Utils.showLongToast(mContext, userData.getMessage());
                        }
                    } else {
                        Utils.showLongToast(mContext, Utils.UNPROPER_RESPONSE);
                    }
                }

                @Override
                public void onFailure(String apiResponse) {
                    if (pd.isShowing()) pd.dismiss();
                    Utils.showLongToast(mContext, apiResponse);
                }
            });
        } else {
            Utils.alert_dialog(mContext);
        }
    }

    public void deleteorderitem(int position) {
        if (cd.isConnectingToInternet()) {
            CustomProgressDialog pd = new CustomProgressDialog(mContext);
            pd.show();
            Map<String, String> map = new HashMap<>();
            OrderItem orderItem = orderDetailAdapter.getList().get(position);
            ProductOrderItem productOrderItem = orderItem.getProductOrderItem();
            map.put("orderitemid", "" + productOrderItem.getOrderItemId());
            map.put("price", "" + productOrderItem.getOrderItemPrice());
            map.put("orderid", "" + productOrderItem.getOrderId());
            app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().deleteorderitem(map), new ApiRequestHelper.OnRequestComplete() {
                @Override
                public void onSuccess(Object object) {
                    if (pd.isShowing()) pd.dismiss();
                    UserResponse userData = (UserResponse) object;
                    if (userData != null) {
                        if (userData.getResponsecode() == 200) {
                            if (userData.getMessage() != null && !TextUtils.isEmpty(userData.getMessage()))
                                Utils.showShortToast(mContext, userData.getMessage());
                            orderDetailAdapter.getList().remove(position);
                            orderDetailAdapter.notifyDataSetChanged();
                            if (OrdersActivity.activity != null && !OrdersActivity.activity.isFinishing()) {
                                OrdersActivity.activity.setProductList();
                            }
                        } else {
                            if (userData.getMessage() != null && !TextUtils.isEmpty(userData.getMessage()))
                                Utils.showLongToast(mContext, userData.getMessage());
                        }
                    } else {
                        Utils.showLongToast(mContext, Utils.UNPROPER_RESPONSE);
                    }
                }

                @Override
                public void onFailure(String apiResponse) {
                    if (pd.isShowing()) pd.dismiss();
                    Utils.showLongToast(mContext, apiResponse);
                }
            });
        } else {
            Utils.alert_dialog(mContext);
        }
    }
}

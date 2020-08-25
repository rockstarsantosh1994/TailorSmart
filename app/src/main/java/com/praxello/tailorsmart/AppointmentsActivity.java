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
import com.praxello.tailorsmart.adapter.AppointmentListAdapter;
import com.praxello.tailorsmart.adapter.OrderListAdapter;
import com.praxello.tailorsmart.api.ApiRequestHelper;
import com.praxello.tailorsmart.model.Appointment;
import com.praxello.tailorsmart.model.AppointmentData;
import com.praxello.tailorsmart.model.Holiday;
import com.praxello.tailorsmart.model.Product;
import com.praxello.tailorsmart.model.SelectedItem;
import com.praxello.tailorsmart.model.UserResponse;
import com.praxello.tailorsmart.utils.Utils;
import com.praxello.tailorsmart.widget.materialprogress.CustomProgressDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class AppointmentsActivity extends BaseActivity {
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    public AppointmentData data;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvError)
    TextView tvError;
    public static AppointmentsActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        toolbarTitle.setText("Appointments");
        tvError.setText("You have no appointments right now , why don't you look at our new collection");
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        getappointment();
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_appointments;
    }

    public void getappointment() {
        if (cd.isConnectingToInternet()) {
            progressBar.setVisibility(View.VISIBLE);
            Map<String, String> map = new HashMap<>();
//            map.put("customerid", "1");
            map.put("customerid", app.getPreferences().getLoggedInUser().getData().getCustomerId());
            callList.add(app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().getappointment(map), new ApiRequestHelper.OnRequestComplete() {
                @Override
                public void onSuccess(Object object) {
                    progressBar.setVisibility(View.GONE);
//                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing())
//                        swipeRefreshLayout.setRefreshing(false);
                    data = (AppointmentData) object;
                    if (data != null && data.getResponsecode() == 200) {
                        if (data.getData() != null && data.getData().size() > 0) {
                            toolbarTitle.setText("Appointments(" + data.getData().size() + ")");
                            recyclerView.setAdapter(null);
                            recyclerView.setAdapter(new AppointmentListAdapter(mContext, data.getData()));
                            recyclerView.setVisibility(View.VISIBLE);
                            tvError.setVisibility(View.GONE);
                        } else {
                            toolbarTitle.setText("Appointments");
                            tvError.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    } else {
                        toolbarTitle.setText("Appointments");
                        tvError.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(String apiResponse) {
//                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing())
//                        swipeRefreshLayout.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                    toolbarTitle.setText("Appointments");
                    tvError.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }));
        } else {
//            if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing())
//                swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
            toolbarTitle.setText("Appointments");
            tvError.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            if (mContext != null) Utils.alert_dialog(mContext);
        }
    }

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

    public void showOptionDialog(Appointment appointment) {
        new MaterialDialog.Builder(mContext)
                .items("Cancel")
                .itemsCallback((dialog, view1, which, text) -> {
                    switch (which) {
//                        case 0:
//                            updateWishlist(appointment);
//                            break;
                        case 0: {
//                            rescheduleAppointment(appointment);
//                            break;
//                        }
//                        case 1: {
                            Map<String, String> map = new HashMap<>();
                            map.put("slotid", appointment.getAppointmentDetails().getSlotId());
                            map.put("customerid", app.getPreferences().getLoggedInUser().getData().getCustomerId());
//                            map.put("customerid", "1");
                            map.put("appointmentid", appointment.getAppointmentDetails().getAppointmentId());
                            map.put("visitdate", appointment.getAppointmentDetails().getAppointmentDate());
                            map.put("productids", "");
                            map.put("cancel", "3");
                            map.put("fabricids", "");
                            updateappointment(map);
                            break;
                        }
                    }
                    dialog.dismiss();
                })
                .show();
    }

    private void rescheduleAppointment(Appointment appointment) {
        List<String> productIdList = new ArrayList<>();
        String allfabricIds = "";
        for (int i = 0; i < appointment.getSelectedItems().size(); i++) {
            SelectedItem selectedItem = appointment.getSelectedItems().get(i);
            Product product = selectedItem.getProduct();
            productIdList.add(product.getProductId());
            List<String> fabricIdList = new ArrayList<>();
            for (int j = 0; j < selectedItem.getFabrics().size(); j++) {
                fabricIdList.add(selectedItem.getFabrics().get(j).getFabricId());
            }
            String fabricIds = fabricIdList.toString().replace("[", "").replace("]", "");
            if (TextUtils.isEmpty(allfabricIds))
                allfabricIds = fabricIds;
            else
                allfabricIds = allfabricIds + ";" + fabricIds;
        }
        String productIds = productIdList.toString().replace("[", "").replace("]", "");


        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH, 3);
        String finalAllfabricIds = allfabricIds;
        DatePickerDialog dpd = DatePickerDialog.newInstance((view1, year, monthOfYear, dayOfMonth) -> {
            String date = "" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            String selectedDate = year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth);
            if (data.getHoliday() != null && data.getHoliday().size() > 0) {
                for (int i = 0; i < data.getHoliday().size(); i++) {
                    Holiday holiday = data.getHoliday().get(i);
                    if (holiday.getSkipDate().equals(selectedDate)) {
                        Utils.showDialog(mContext, holiday.getSkipDate() + " is holiday(" + holiday.getHolidayTitle() + ")", false, (dialog, which) -> dialog.dismiss());
                        return;
                    }
                }
            }
            if (data.getSlots() != null && data.getSlots().size() > 0) {
                List<String> slotTimes = new ArrayList<>();
                for (int i = 0; i < data.getSlots().size(); i++) {
                    slotTimes.add(data.getSlots().get(i).getSlotTime());
                }
                MaterialDialog dialog = new MaterialDialog.Builder(this)
                        .title("Select Time Slot").items(slotTimes)
                        .itemsCallbackSingleChoice(-1, (dialog1, itemView, which, text) -> {
                            String slotId = data.getSlots().get(which).getSlotId();
                            Map<String, String> map = new HashMap<>();
                            map.put("slotid", slotId);
                            map.put("customerid", app.getPreferences().getLoggedInUser().getData().getCustomerId());
//                            map.put("customerid", "1");
                            map.put("appointmentid", appointment.getAppointmentDetails().getAppointmentId());
                            map.put("visitdate", selectedDate);
                            map.put("productids", productIds);
                            map.put("cancel", "0");
                            map.put("fabricids", finalAllfabricIds);
                            updateappointment(map);
                            return false;
                        })
                        .build();

                dialog.show();
            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.setMinDate(now);
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
    }

    private void updateWishlist(Appointment appointment) {
        List<String> productIdList = new ArrayList<>();
        String allfabricIds = "";
        for (int i = 0; i < appointment.getSelectedItems().size(); i++) {
            Product product = appointment.getSelectedItems().get(i).getProduct();
            productIdList.add(product.getProductId());
            List<String> fabricIdList = new ArrayList<>();
            for (int j = 0; j < product.getFabricList().size(); j++) {
                fabricIdList.add(product.getFabricList().get(j).getFabricId());
            }
            String fabricIds = fabricIdList.toString().replace("[", "").replace("]", "");
            if (TextUtils.isEmpty(allfabricIds))
                allfabricIds = fabricIds;
            else
                allfabricIds = allfabricIds + ";" + fabricIds;
        }
        String productIds = productIdList.toString().replace("[", "").replace("]", "");


        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH, 1);
        String finalAllfabricIds = allfabricIds;
        DatePickerDialog dpd = DatePickerDialog.newInstance((view1, year, monthOfYear, dayOfMonth) -> {
            String date = "" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            String selectedDate = year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth);
            if (data.getHoliday() != null && data.getHoliday().size() > 0) {
                for (int i = 0; i < data.getHoliday().size(); i++) {
                    Holiday holiday = data.getHoliday().get(i);
                    if (holiday.getSkipDate().equals(selectedDate)) {
                        Utils.showDialog(mContext, holiday.getSkipDate() + " is holiday(" + holiday.getHolidayTitle() + ")", false, (dialog, which) -> dialog.dismiss());
                        return;
                    }
                }
            }
            if (data.getSlots() != null && data.getSlots().size() > 0) {
                List<String> slotTimes = new ArrayList<>();
                for (int i = 0; i < data.getSlots().size(); i++) {
                    slotTimes.add(data.getSlots().get(i).getSlotTime());
                }
                MaterialDialog dialog = new MaterialDialog.Builder(this)
                        .title("Select Time Slot").items(slotTimes)
                        .itemsCallbackSingleChoice(-1, (dialog1, itemView, which, text) -> {
                            String slotId = data.getSlots().get(which).getSlotId();
                            Map<String, String> map = new HashMap<>();
                            map.put("slotid", slotId);
                            map.put("customerid", app.getPreferences().getLoggedInUser().getData().getCustomerId());
//                            map.put("customerid", "1");
                            map.put("appointmentid", appointment.getAppointmentDetails().getAppointmentId());
                            map.put("visitdate", selectedDate);
                            map.put("productids", productIds);
                            map.put("cancel", "0");
                            map.put("fabricids", finalAllfabricIds);
                            updateappointment(map);
                            return false;
                        })
                        .build();

                dialog.show();
            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.setMinDate(now);
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
    }

    private void updateappointment(Map<String, String> map) {
        if (cd.isConnectingToInternet()) {
            CustomProgressDialog pd = new CustomProgressDialog(mContext);
            pd.show();
            app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().updateappointment(map), new ApiRequestHelper.OnRequestComplete() {
                @Override
                public void onSuccess(Object object) {
                    if (pd.isShowing()) pd.dismiss();
                    UserResponse userData = (UserResponse) object;
                    if (userData != null) {
                        if (userData.getResponsecode() == 200) {
                            if (userData.getMessage() != null && !TextUtils.isEmpty(userData.getMessage()))
                                Utils.showShortToast(mContext, userData.getMessage());
                            getappointment();
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

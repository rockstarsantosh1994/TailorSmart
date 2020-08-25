package com.praxello.tailorsmart;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.praxello.tailorsmart.adapter.WishlistAdapter;
import com.praxello.tailorsmart.api.ApiRequestHelper;
import com.praxello.tailorsmart.model.AllData;
import com.praxello.tailorsmart.model.Holiday;
import com.praxello.tailorsmart.model.Product;
import com.praxello.tailorsmart.model.Slot;
import com.praxello.tailorsmart.model.UserResponse;
import com.praxello.tailorsmart.model.VacancyData;
import com.praxello.tailorsmart.utils.Utils;
import com.praxello.tailorsmart.widget.materialprogress.CustomProgressDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class WishListActivity extends BaseActivity {
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
    public static WishListActivity activity;
    @BindView(R.id.btnTakeAppointment)
    Button btnTakeAppointment;
    private List<Holiday> holidayList;
    private List<Slot> slots;
    private String selectedDate;
    private List<Product> wishlistData;
    private VacancyData vacancyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        toolbarTitle.setText("Wishlist");
        data = getIntent().getParcelableExtra("data");

        if (data != null) {
            if (data.getHoliday() != null && data.getHoliday().size() > 0) {
                holidayList = data.getHoliday();
            }
            if (data.getSlots() != null && data.getSlots().size() > 0) {
                slots = data.getSlots();
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        loadWishlistData();
        getvacancy();
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_wishlist;
    }

    public void loadWishlistData() {
        wishlistData = app.getPreferences().getWishlistData();
        if (wishlistData != null && wishlistData.size() > 0) {
            toolbarTitle.setText("Wishlist(" + wishlistData.size() + ")");
            recyclerView.setAdapter(new WishlistAdapter(mContext, wishlistData));
            recyclerView.setVisibility(View.VISIBLE);
            tvError.setVisibility(View.GONE);
            btnTakeAppointment.setVisibility(View.VISIBLE);
        } else {
            toolbarTitle.setText("Wishlist");
            tvError.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            btnTakeAppointment.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btnTakeAppointment)
    public void onViewClicked() {
        if (app.getPreferences().isLoggedInUser()) {
            if (app.getPreferences().getLoggedInUser().getData() != null) {
                if (TextUtils.isEmpty(app.getPreferences().getLoggedInUser().getData().getCity())) {
                    startActivity(new Intent(mContext, EditProfileActivity.class)
                            .putExtra("isCompleteProfile", true));
                    return;
                }
            }
            List<String> productIdList = new ArrayList<>();
            String allfabricIds = "";
            for (int i = 0; i < wishlistData.size(); i++) {
                Product product = wishlistData.get(i);
                productIdList.add(product.getProductId());
                List<String> fabricIdList = new ArrayList<>();
                if (product.getFabricList() != null && product.getFabricList().size() > 0) {
                    for (int j = 0; j < product.getFabricList().size(); j++) {
                        fabricIdList.add(product.getFabricList().get(j).getFabricId());
                    }
                }
                String fabricIds = fabricIdList.toString().replace("[", "").replace("]", "");
                if (TextUtils.isEmpty(allfabricIds)) {
                    if (!TextUtils.isEmpty(fabricIds)) {
                        allfabricIds = fabricIds + ";";
                    } else {
                        allfabricIds = ";";
                    }
                } else
                    allfabricIds = allfabricIds + fabricIds + ";";
            }
            String productIds = productIdList.toString().replace("[", "").replace("]", "");


            Calendar now = Calendar.getInstance();
            if (now.get(Calendar.HOUR_OF_DAY) < 21) { // Check if before 9 PM
                now.add(Calendar.DAY_OF_MONTH, 1);
            } else {
                now.add(Calendar.DAY_OF_MONTH, 2);
            }
            String finalAllfabricIds = allfabricIds;
            DatePickerDialog dpd = DatePickerDialog.newInstance((view1, year, monthOfYear, dayOfMonth) -> {
                String date = "" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                selectedDate = year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth);
                if (holidayList != null && holidayList.size() > 0) {
                    for (int i = 0; i < holidayList.size(); i++) {
                        Holiday holiday = holidayList.get(i);
                        if (holiday.getSkipDate().equals(selectedDate)) {
                            Utils.showDialog(mContext, holiday.getSkipDate() + " is holiday(" + holiday.getHolidayTitle() + ")", false, (dialog, which) -> dialog.dismiss());
                            return;
                        }
                    }
                }
                if (slots != null && slots.size() > 0) {
                    List<String> allottedSlotIds = new ArrayList<>();
                    if (vacancyData != null && vacancyData.getData() != null && vacancyData.getData().size() > 0) {
                        for (int i = 0; i < vacancyData.getData().size(); i++) {
                            if (vacancyData.getData().get(i).getAppointmentDate().equals(selectedDate)) {
                                for (int j = 0; j < slots.size(); j++) {
                                    if (slots.get(j).getSlotId().equals(vacancyData.getData().get(i).getSlotId())) {
                                        if (!TextUtils.isEmpty(vacancyData.getData().get(i).getTotal()) &&
                                                Integer.parseInt(vacancyData.getData().get(i).getTotal()) >= 2) {
                                            allottedSlotIds.add(vacancyData.getData().get(i).getSlotId());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    List<String> slotTimes = new ArrayList<>();
                    List<Integer> allottedSlotTimeIndices = new ArrayList<>();
                    for (int i = 0; i < slots.size(); i++) {
                        slotTimes.add(slots.get(i).getSlotTime());
                        if (allottedSlotIds.contains(slots.get(i).getSlotId())) {
                            allottedSlotTimeIndices.add(i);
                        }
                    }
                    Integer[] disabledSlotIndices = toArray(allottedSlotTimeIndices);

                    MaterialDialog dialog = new MaterialDialog.Builder(this)
                            .title("Select Time Slot")
                            .items(slotTimes)
                            .itemsDisabledIndices(disabledSlotIndices)
                            .itemsCallbackSingleChoice(-1, (dialog1, itemView, which, text) -> {
                                String slotId = slots.get(which).getSlotId();
                                Map<String, String> map = new HashMap<>();
                                map.put("slotid", slotId);
                                map.put("customerid", app.getPreferences().getLoggedInUser().getData().getCustomerId());
                                map.put("appointmentdate", selectedDate);
                                map.put("productids", productIds);
                                map.put("fabricids", finalAllfabricIds);
                                String totalAppointments = app.getPreferences().getLoggedInUser().getTotalAppointments();
                                if (!TextUtils.isEmpty(totalAppointments) && Integer.parseInt(totalAppointments) > 0) {
                                    new MaterialDialog.Builder(mContext)
                                            .content("Do you wish to invite our Master for taking measurement?")
                                            .cancelable(false)
                                            .positiveText("Yes")
                                            .negativeText("No")
                                            .autoDismiss(false)
                                            .onPositive((d, w) -> {
                                                d.dismiss();
                                                map.put("measurementneeded", "1");
                                                takeappointment(map);
                                            }).onNegative((dialog2, which1) -> {
                                                dialog2.dismiss();
                                                new MaterialDialog.Builder(mContext)
                                                        .title("You Selected 'No'")
                                                        .content("We will use your previous order measurements for this order")
                                                        .cancelable(false)
                                                        .positiveText("Ok")
                                                        .autoDismiss(false)
                                                        .onPositive((d, w) -> {
                                                            d.dismiss();
                                                            map.put("measurementneeded", "0");
                                                            takeappointment(map);
                                                        }).show();
                                            }).show();
                                } else {
                                    takeappointment(map);
                                }
                                return false;
                            })
                            .build();

                    dialog.show();
                }
            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            dpd.setMinDate(now);
            Calendar max_date_c = Calendar.getInstance();
            max_date_c.add(Calendar.MONTH, 11);
            for (Calendar loopdate = now; now.before(max_date_c); now.add(Calendar.DATE, 1), loopdate = now) {
                int dayOfWeek = loopdate.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek == Calendar.MONDAY) {
                    Calendar[] disabledDays = new Calendar[1];
                    disabledDays[0] = loopdate;
                    dpd.setDisabledDays(disabledDays);
                }
            }
            dpd.show(getSupportFragmentManager(), "Datepickerdialog");
        } else {
            if (LoginActivity.loginActivity != null && !LoginActivity.loginActivity.isFinishing())
                LoginActivity.loginActivity.finish();
            Utils.showLongToast(mContext, "Please Create Account and login to take appointment");
            startActivity(new Intent(mContext, LoginActivity.class)
                    .putExtra("isFromWishlist", true));
        }
    }

    Integer[] toArray(List<Integer> list) {
        Integer[] ret = new Integer[list.size()];
        int i = 0;
        for (Iterator<Integer> it = list.iterator();
             it.hasNext();
             ret[i++] = it.next())
            ;
        return ret;
    }

    private void takeappointment(Map<String, String> map) {
        if (cd.isConnectingToInternet()) {
            CustomProgressDialog pd = new CustomProgressDialog(mContext);
            pd.show();
            app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().takeappointment(map), new ApiRequestHelper.OnRequestComplete() {
                @Override
                public void onSuccess(Object object) {
                    if (pd.isShowing()) pd.dismiss();
                    UserResponse userData = (UserResponse) object;
                    if (userData != null) {
                        if (userData.getResponsecode() == 200) {
                            if (userData.getMessage() != null && !TextUtils.isEmpty(userData.getMessage()))
                                Utils.showShortToast(mContext, userData.getMessage());
                            app.getPreferences().setWishlistData(null);
                            finish();
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

    public void getvacancy() {
        if (cd.isConnectingToInternet()) {
            callList.add(app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().getvacancy(), new ApiRequestHelper.OnRequestComplete() {
                @Override
                public void onSuccess(Object object) {
                    vacancyData = (VacancyData) object;
                    if (vacancyData != null && vacancyData.getResponsecode() == 200) {
                        if (vacancyData.getData() != null && vacancyData.getData().size() > 0) {

                        }
                    }
                }

                @Override
                public void onFailure(String apiResponse) {
                }
            }));
        } else {
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

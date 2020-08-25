package com.praxello.tailorsmart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.praxello.tailorsmart.api.ApiRequestHelper;
import com.praxello.tailorsmart.fragment.FilterProductFragment;
import com.praxello.tailorsmart.model.Data;
import com.praxello.tailorsmart.model.UserData;
import com.praxello.tailorsmart.utils.Utils;
import com.praxello.tailorsmart.widget.materialprogress.CustomProgressDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider;

import static io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider.REQUEST_CHECK_SETTINGS;

public class EditProfileActivity extends BaseActivity {
    String[] cities = {"Pune", "Mumbai"};
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_firstName)
    MaterialEditText etFirstName;
    @BindView(R.id.et_lastName)
    MaterialEditText etLastName;
    @BindView(R.id.et_mobile)
    MaterialEditText etMobile;
    @BindView(R.id.et_email)
    MaterialEditText etEmail;
    @BindView(R.id.et_password)
    MaterialEditText etPassword;
    @BindView(R.id.et_state)
    MaterialEditText etState;
    @BindView(R.id.et_city)
    MaterialEditText etCity;
    @BindView(R.id.et_address)
    MaterialEditText etAddress;
    //    @BindView(R.id.etLandmark)
//    MaterialEditText etLandmark;
//    private List<Landmark> landmarkList;
    boolean isCompleteProfile = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCompleteProfile = getIntent().getBooleanExtra("isCompleteProfile", false);
//        getservicearea();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        toolbarTitle.setText("My Profile");
        if (isCompleteProfile) {
            Utils.showDialog(mContext, "Please complete profile", false, (dialog, which) -> dialog.dismiss());
        }
        if (app.getPreferences().getLoggedInUser().getData() != null) {
            Data data = app.getPreferences().getLoggedInUser().getData();
            etFirstName.setText(data.getFirstName());
            etLastName.setText(data.getLastName());
            etEmail.setText(data.getEmail());
            etMobile.setText(data.getMobile());
            etPassword.setText(data.getPassword());
            etAddress.setText(data.getAddress());
            etCity.setText(data.getCity());
//            if (TextUtils.isEmpty(data.getCity()))
//                etCity.setText("Pune");
//            etLandmark.setText(data.getLandmark());
        }
//        etLandmark.setOnClickListener(view -> {
//            if (landmarkList != null && landmarkList.size() > 0) {
//                List<String> list = new ArrayList<>();
//                for (Landmark landmark : landmarkList) {
//                    list.add(landmark.getAreaName());
//                }
//                if (list.size() > 0)
//                    new MaterialDialog.Builder(mContext).items(list)
//                            .itemsCallback((dialog, itemView, position, text) -> etLandmark.setText(text.toString())).show();
//            }
//        });
        if (Build.VERSION.SDK_INT >= 23) {   //Android M Or Over
            ask_permissions();
        } else {
            saveSmartLocation();
        }
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_edit_profile;
    }

    @OnClick({R.id.btn_register, R.id.et_city})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_city: {
                if (cities != null && cities.length > 0) {
                    new MaterialDialog.Builder(mContext)
                            .title("Select City")
                            .items(cities)
                            .itemsCallback((dialog, view1, which, text) -> etCity.setText(text.toString()))
                            .show();
                }
                break;
            }

            case R.id.btn_register: {
                String firstName = etFirstName.getText().toString().trim();
                String lastName = etLastName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String mobile = etMobile.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
//                String landmark = etLandmark.getText().toString().trim();
                String city = etCity.getText().toString().trim();
                if (TextUtils.isEmpty(firstName)) {
                    etFirstName.setError("Enter First Name");
                    etFirstName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(lastName)) {
                    etLastName.setError("Enter Last Name");
                    etLastName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(mobile)) {
                    etMobile.setError("Enter Mobile Number");
                    etMobile.requestFocus();
                    return;
                }
                if (mobile.length() < 10) {
                    etMobile.setError("Mobile Number must be 10 digits");
                    etMobile.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    etEmail.setError("Enter Email");
                    etEmail.requestFocus();
                    return;
                }
                if (!Utils.isValidEmail(email)) {
                    etEmail.setError("Enter Valid Email");
                    etEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Enter password");
                    etPassword.requestFocus();
                    return;
                }
                if (password.length() < 5) {
                    etPassword.setError("Password must be at least 5 characters of length");
                    etPassword.requestFocus();
                    return;
                }
//                if (TextUtils.isEmpty(landmark)) {
//                    etLandmark.setError("Enter Landmark");
//                    etLandmark.requestFocus();
//                    return;
//                }
                if (TextUtils.isEmpty(city)) {
                    etAddress.setError("Select City");
                    etAddress.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    etAddress.setError("Enter Address");
                    etAddress.requestFocus();
                    return;
                }
                if (address.length() < 5) {
                    etAddress.setError("Address must be minimum 5 characters length");
                    etAddress.requestFocus();
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("fname", firstName);
                map.put("lname", lastName);
                map.put("mobile", mobile);
                map.put("email", email);
                map.put("address", address);
                map.put("city", city);
                map.put("latitude", app.getPreferences().getLat());
                map.put("longitude", app.getPreferences().getLng());
                map.put("state", "Maharashtra");
                map.put("country", "India");
                map.put("password", password);
//                map.put("landmark", landmark);
                if (cd.isConnectingToInternet()) {
                    CustomProgressDialog pd = new CustomProgressDialog(mContext);
                    pd.show();
                    app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().updatecustomer(map), new ApiRequestHelper.OnRequestComplete() {
                        @Override
                        public void onSuccess(Object object) {
                            if (pd.isShowing()) pd.dismiss();
                            UserData userData = (UserData) object;
                            if (userData != null) {
                                if (userData.getResponsecode() == 200) {
                                    if (userData.getMessage() != null && !TextUtils.isEmpty(userData.getMessage()))
                                        Utils.showShortToast(mContext, userData.getMessage());
                                    app.getPreferences().setLoggedInUser(userData);
                                    if (MainActivity.activity != null && !MainActivity.activity.isFinishing()) {
                                        MainActivity.activity.setSelectedCurrency();
                                    }
                                    if (isCompleteProfile) {
                                        if (WishListActivity.activity != null && !WishListActivity.activity.isFinishing()) {
                                            WishListActivity.activity.loadWishlistData();
                                        }
                                        if (ProductListActivity.activity != null && !ProductListActivity.activity.isFinishing()) {
                                            ProductListActivity.activity.setProductList();
                                        }
                                        if (SubStyleListActivity.activity != null && !SubStyleListActivity.activity.isFinishing()) {
                                            SubStyleListActivity.activity.setProductList();
                                        }
                                        if (ProductDetailsActivity.activity != null && !ProductDetailsActivity.activity.isFinishing()) {
                                            ProductDetailsActivity.activity.setPriceUponSelectedCurrency();
                                            ProductDetailsActivity.activity.setFabricAdapter();
                                        }
                                    }
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
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CHECK_SETTINGS) {
            saveSmartLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    displayLocationSettingsRequest(BaseActivity.this);
                    saveSmartLocation();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
//permission is denied (and never ask again is  checked)
                    //shouldShowRequestPermissionRationale will return false
//                    Toast.makeText(SplashActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
//                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//                        saveSmartLocation();
//                    } else {
//                        ask_permissions();
//                    }
                }
                return;
            }
        }
    }

    public void saveSmartLocation() {
        LocationGooglePlayServicesProvider provider = new LocationGooglePlayServicesProvider();
        provider.setCheckLocationSettings(true);
        SmartLocation smartLocation = new SmartLocation.Builder(mContext).logging(true).build();
        smartLocation.location(provider).oneFix().start(location -> {
            if (location != null) {
//                Log.e("in", "startGettingLocation loc " + location.getLatitude() + "||" + location.getLongitude());
                app.getPreferences().setLat("" + location.getLatitude());
                app.getPreferences().setLng("" + location.getLongitude());
                getAddressFromGeocoder(location.getLatitude(), location.getLongitude());
            } else {
//                Log.e("else", "location 0.0");
                getAddressFromGeocoder(Double.parseDouble(app.getPreferences().getLat()), Double.parseDouble(app.getPreferences().getLng()));
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void getAddressFromGeocoder(double latitude, double longitude) {
        try {
            new AsyncTask<Void, Integer, List<Address>>() {
                @Override
                protected List<Address> doInBackground(Void... voids) {
                    List<Address> results = null;
                    Geocoder coder = new Geocoder(mContext, Locale.ENGLISH);
                    try {
                        results = coder.getFromLocation(latitude, longitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return results;
                }

                @Override
                protected void onPostExecute(List<Address> addresses) {
                    if (addresses != null && addresses.size() > 0) {
                        String address = addresses.get(0).getAddressLine(0);
                        if (etAddress != null && TextUtils.isEmpty(app.getPreferences().getLoggedInUser().getData().getAddress()) &&
                                TextUtils.isEmpty(etAddress.getText().toString()))
                            etAddress.setText(address);
                    }
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void getservicearea() {
//        if (cd.isConnectingToInternet()) {
//            callList.add(app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().getservicearea(), new ApiRequestHelper.OnRequestComplete() {
//                @Override
//                public void onSuccess(Object object) {
//                    LandmarkData data = (LandmarkData) object;
//                    if (data != null && data.getResponsecode() == 200 && data.getData() != null && data.getData().size() > 0) {
//                        landmarkList = data.getData();
//                    }
//                }
//
//                @Override
//                public void onFailure(String apiResponse) {
//                    if (!TextUtils.isEmpty(apiResponse))
//                        Utils.showLongToast(mContext, apiResponse);
//                }
//            }));
//        } else {
//            Utils.alert_dialog(mContext);
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_measurement, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_measurement: {
                startActivity(new Intent(mContext, MeasurementListActivity.class));
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

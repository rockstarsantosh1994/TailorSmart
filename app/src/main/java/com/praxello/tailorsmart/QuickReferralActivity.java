package com.praxello.tailorsmart;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.praxello.tailorsmart.api.ApiRequestHelper;
import com.praxello.tailorsmart.model.UserResponse;
import com.praxello.tailorsmart.utils.Utils;
import com.praxello.tailorsmart.widget.materialprogress.CustomProgressDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class QuickReferralActivity extends BaseActivity {
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etTailorName)
    EditText etTailorName;
    @BindView(R.id.etAddress)
    EditText etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        toolbarTitle.setText("Quick Referral");
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_quick_referral;
    }

    @OnClick({R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit: {
                String strTailorName = etTailorName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String strAddress = etAddress.getText().toString().trim();
                if (TextUtils.isEmpty(strTailorName)) {
                    etTailorName.setError("Enter Tailor/Master Name");
                    etTailorName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    etEmail.setError("Enter Email Id/Mobile");
                    etEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(strAddress)) {
                    etAddress.setError("Enter Address");
                    etAddress.requestFocus();
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("customername", app.getPreferences().getLoggedInUser().getData().getFirstName() + " " + app.getPreferences().getLoggedInUser().getData().getLastName());
                map.put("mobile", app.getPreferences().getLoggedInUser().getData().getMobile());
                map.put("customeremail", app.getPreferences().getLoggedInUser().getData().getEmail());
                map.put("tailorname", strTailorName);
                map.put("tailormobile", email);
                map.put("tailoraddress", strAddress);
                refermaster(map);
                break;
            }
        }
    }

    private void refermaster(Map<String, String> map) {
        if (cd.isConnectingToInternet()) {
            CustomProgressDialog pd = new CustomProgressDialog(mContext);
            pd.show();
            app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().refermaster(map), new ApiRequestHelper.OnRequestComplete() {
                @Override
                public void onSuccess(Object object) {
                    if (pd.isShowing()) pd.dismiss();
                    UserResponse response = (UserResponse) object;
                    if (response != null) {
                        if (response.getResponsecode() == 200) {
                            if (response.getMessage() != null && !TextUtils.isEmpty(response.getMessage()))
                                Utils.showShortToast(mContext, response.getMessage());
                            finish();
                        } else {
                            if (response.getMessage() != null && !TextUtils.isEmpty(response.getMessage()))
                                Utils.showLongToast(mContext, response.getMessage());
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

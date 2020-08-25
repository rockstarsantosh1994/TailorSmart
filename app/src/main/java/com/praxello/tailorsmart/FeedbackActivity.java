package com.praxello.tailorsmart;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import com.praxello.tailorsmart.api.ApiRequestHelper;
import com.praxello.tailorsmart.model.UserData;
import com.praxello.tailorsmart.utils.Utils;
import com.praxello.tailorsmart.widget.materialprogress.CustomProgressDialog;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends BaseActivity {
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etFeedback)
    MaterialEditText etFeedback;
    @BindView(R.id.crb1)
    ScaleRatingBar crb1;
    @BindView(R.id.crb2)
    ScaleRatingBar crb2;
    @BindView(R.id.crb3)
    ScaleRatingBar crb3;
    @BindView(R.id.crb4)
    ScaleRatingBar crb4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        toolbarTitle.setText("Feedback");
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_feedback;
    }

    @OnClick({R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                String feedback = etFeedback.getText().toString().trim();
                if (TextUtils.isEmpty(feedback)) {
                    etFeedback.setError("Please Write your feedback");
                    etFeedback.requestFocus();
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("customerid", app.getPreferences().getLoggedInUser().getData().getCustomerId());
                map.put("rate1", "" + crb1.getRating());
                map.put("rate2", "" + crb2.getRating());
                map.put("rate3", "" + crb3.getRating());
                map.put("rate4", "" + crb4.getRating());
                map.put("feedback", feedback);
                savefeedback(map);
                break;
        }
    }

    private void savefeedback(Map<String, String> map) {
        if (cd.isConnectingToInternet()) {
            CustomProgressDialog pd = new CustomProgressDialog(mContext);
            pd.show();
            app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().savefeedback(map), new ApiRequestHelper.OnRequestComplete() {
                @Override
                public void onSuccess(Object object) {
                    if (pd.isShowing()) pd.dismiss();
                    UserData userData = (UserData) object;
                    if (userData != null) {
                        if (userData.getResponsecode() == 200) {
                            if (userData.getMessage() != null && !TextUtils.isEmpty(userData.getMessage()))
                                Utils.showShortToast(mContext, userData.getMessage());
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

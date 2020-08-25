package com.praxello.tailorsmart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.praxello.tailorsmart.api.ApiRequestHelper;
import com.praxello.tailorsmart.model.UserResponse;
import com.praxello.tailorsmart.utils.Utils;
import com.praxello.tailorsmart.widget.materialprogress.CustomProgressDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ContactUsActivity extends BaseActivity {
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etSubject)
    MaterialEditText etSubject;
    @BindView(R.id.etMessage)
    MaterialEditText etMessage;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.tvEmail1)
    TextView tvEmail1;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvAddress)
    TextView tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        toolbarTitle.setText("Contact Us");
        tvEmail1.setPaintFlags(tvEmail1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvEmail1.setOnClickListener(view -> sendEmail(tvEmail1.getText().toString(), "", ""));

        SpannableString ss1 = new SpannableString("Cell No.: +91 7887888666, +91 9225525565");
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                callPhone("+91 7887888666");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss1.setSpan(clickableSpan1, 10, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                callPhone("+91 9225525565");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss1.setSpan(clickableSpan2, 26, 40, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPhone.setMovementMethod(LinkMovementMethod.getInstance());
        tvPhone.setText(ss1);
//        tvPhone.setOnClickListener(view -> callPhone("+91 7887888666"));

        tvAddress.setOnClickListener(view -> {
            String uri = "http://maps.google.co.in/maps?q=" + tvAddress.getText().toString();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
        });
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_contact_us;
    }

    @OnClick({R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                String subject = etSubject.getText().toString().trim();
                String message = etMessage.getText().toString().trim();
                if (TextUtils.isEmpty(subject)) {
                    etSubject.setError("Enter Subject");
                    etSubject.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(message)) {
                    etMessage.setError("Enter Message");
                    etMessage.requestFocus();
                    return;
                }
//                Map<String, String> map = new HashMap<>();
//                map.put("customerid", app.getPreferences().getLoggedInUser().getData().getCustomerId());
//                map.put("subject", subject);
//                map.put("message", message);
//                savefeedback(map);
                sendEmail("joy@tailorsmart.in", subject, message);
//                contactus(subject, message);
                break;
        }
    }

    public void contactus(String subject, String message) {
        Map<String, String> map = new HashMap<>();
        map.put("customerid", app.getPreferences().getLoggedInUser().getData().getCustomerId());
        map.put("subject", subject);
        map.put("message", message);
        if (cd.isConnectingToInternet()) {
            CustomProgressDialog cpd = new CustomProgressDialog(mContext);
            cpd.show();
            app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().contactus(map), new ApiRequestHelper.OnRequestComplete() {
                @Override
                public void onSuccess(Object object) {
                    if (cpd != null && cpd.isShowing()) cpd.dismiss();
                    UserResponse userData = (UserResponse) object;
                    if (userData != null) {
                        if (userData.getResponsecode() == 200) {
                            if (userData.getMessage() != null && !TextUtils.isEmpty(userData.getMessage()))
                                Utils.showShortToast(mContext, userData.getMessage());
                            etMessage.setText("");
                            etSubject.setText("");
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
                    if (cpd != null && cpd.isShowing()) cpd.dismiss();
                }
            });
        } else {
            Utils.alert_dialog(mContext);
        }
    }

    private void sendEmail(String email, String subject, String text) {
        List<Intent> targetedShareIntents = new ArrayList<Intent>();
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, text);
//        startActivityForResult(Intent.createChooser(emailIntent, "Send email..."), 1);
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(emailIntent, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                Intent targetedShare = new Intent(Intent.ACTION_SEND);
//                targetedShare.setType("text/plain"); // put here your mime type
                targetedShare.setPackage(info.activityInfo.packageName.toLowerCase());
                targetedShareIntents.add(targetedShare);
            }
            // Then show the ACTION_PICK_ACTIVITY to let the user select it
            Intent intentPick = new Intent();
            intentPick.setAction(Intent.ACTION_PICK_ACTIVITY);
            // Set the title of the dialog
            intentPick.putExtra(Intent.EXTRA_TITLE, "");
            intentPick.putExtra(Intent.EXTRA_INTENT, emailIntent);
            intentPick.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray());
            // Call StartActivityForResult so we can get the app name selected by the user
            startActivityForResult(intentPick, 111);
        }
    }

    public void callPhone(String phoneNo) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        } else {
            final MaterialDialog ok = new MaterialDialog.Builder(mContext)
                    .content("Do you want to call this phone number?")
                    .positiveText("Yes")
                    .negativeText("No")
                    .show();
            ok.getActionButton(DialogAction.POSITIVE).setOnClickListener(view -> {
                ok.dismiss();
                mContext.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNo)));
            });
            ok.getActionButton(DialogAction.NEGATIVE).setOnClickListener(view -> ok.dismiss());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            if (data != null && data.getComponent() != null && !TextUtils.isEmpty(data.getComponent().flattenToShortString())) {
                String appName = data.getComponent().flattenToShortString();
                // Now you know the app being picked.
                // data is a copy of your launchIntent with this important extra info added.

                // Start the selected activity
                startActivity(data);
                etMessage.setText("");
                etSubject.setText("");
            }
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

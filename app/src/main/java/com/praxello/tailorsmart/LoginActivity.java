package com.praxello.tailorsmart;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.praxello.tailorsmart.api.ApiRequestHelper;
import com.praxello.tailorsmart.model.UserData;
import com.praxello.tailorsmart.utils.Utils;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.etUserName)
    MaterialEditText etEmail;
    @BindView(R.id.etPassword)
    MaterialEditText etPassword;
    public static LoginActivity loginActivity;
    @BindView(R.id.sign_in_button)
    SignInButton signInButton;
    @BindView(R.id.ivLoginBg)
    ImageView ivLoginBg;
    @BindView(R.id.tv_forgetPassword)
    TextView tvForgetPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btn_login_as_a_guest)
    AppCompatButton btnLoginAsGuest;
    @BindView(R.id.tv_signup)
    TextView tvSignup;
    @BindView(R.id.ivImage)
    ImageView ivImage;
    @BindView(R.id.vwOverlay)
    View vwOverlay;
    @BindView(R.id.ivPlay)
    ImageView ivPlay;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 101;
    boolean isFromWishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivity = this;
        isFromWishlist = getIntent().getBooleanExtra("isFromWishlist", false);
        if (app.getPreferences().isLoggedInUser()) {
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
            return;
        }
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // 297688624392-iss9190o702g1ne9b4n8rco5of1cj69f.apps.googleusercontent.com
        // DHpLJrla1xSBe_-yj4eIvn9q
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult ->
                app.getPreferences().setToken(instanceIdResult.getToken()));

//        new Handler(Looper.getMainLooper()).post(() -> GlideApp.with(mContext).load(R.drawable.newlogin4)
//                .thumbnail(0.1f).apply(RequestOptions.bitmapTransform(new BlurTransformation(10)))
//                .into(ivLoginBg));
        GlideApp.with(mContext).load(R.drawable.newlogin)
                .thumbnail(0.1f)
                .into(ivLoginBg);

        String video = "pJvHCZ9Qvvs";
        Glide.with(mContext).load("http://img.youtube.com/vi/" + video + "/0.jpg").into(ivImage);
        ivImage.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + video));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // youtube is not installed.Will be opened in other available apps
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/watch?v=" + video));
                mContext.startActivity(i);
            }
        });
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.btnLogin, R.id.btn_login_as_a_guest, R.id.sign_in_button, R.id.tv_forgetPassword, R.id.tv_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login_as_a_guest: {
                startActivity(new Intent(LoginActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
            }
            case R.id.tv_forgetPassword: {
                startActivity(new Intent(mContext, ForgotPasswordActivity.class));

                break;
            }
            case R.id.tv_signup: {
                startActivity(new Intent(mContext, RegisterActivity.class));
                break;
            }
            case R.id.sign_in_button: {
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(mContext);
                if (acct != null) {
                    mGoogleSignInClient.signOut()
                            .addOnCompleteListener(this, task -> {
                                startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
                            })
                            .addOnFailureListener(this, Throwable::printStackTrace);
                } else {
                    startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
                }
                break;
            }
            case R.id.btnLogin:
                final String email = etEmail.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    etEmail.setError("Enter User Name");
                    etEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Enter Password");
                    etPassword.requestFocus();
                    return;
                }
                Map<String, String> params = new HashMap<>();
                params.put("usrname", email);
                params.put("passwrd", password);
                if (!TextUtils.isEmpty(app.getPreferences().getToken()))
                    params.put("deviceid", app.getPreferences().getToken());
                params.put("devicetype", "android");
                params.put("uuid", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                params.put("model", Utils.getDeviceName());
                params.put("imei", Utils.getDeviceIMEI(mContext));
                login(params);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                Log.e("g sign details", personName + "||" + personGivenName + "||" + personFamilyName + "||" +
                        personEmail + "||" + personId + "||" + (personPhoto != null ? personPhoto.toString() : ""));
                Map<String, String> params = new HashMap<String, String>();
                if (personEmail != null)
                    params.put("email", personEmail);
                if (personGivenName != null)
                    params.put("fname", personGivenName);
                if (personFamilyName != null)
                    params.put("lname", personFamilyName);
                params.put("date_birth", "");
                params.put("mobile", "");
                params.put("landline", "");
                params.put("city", "");
                params.put("state", "");
                params.put("address", "");
                params.put("ismale", "");
                params.put("password", "");
                params.put("issocial", "1");
                customersignup(params);
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("ApiException", "signInResult:failed code=" + e.getStatusCode());
            e.printStackTrace();
        }
    }

    private void login(Map<String, String> params) {
        if (cd.isConnectingToInternet()) {
            final ProgressDialog pd = new ProgressDialog(mContext);
            pd.setTitle("Loading...");
            pd.show();
            app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().login(params), new ApiRequestHelper.OnRequestComplete() {
                @Override
                public void onSuccess(Object object) {
                    pd.dismiss();
                    UserData response = (UserData) object;
                    if (response != null) {
                        if (response.getResponsecode() == 200) {
                            if (response.getMessage() != null && !TextUtils.isEmpty(response.getMessage()))
                                Utils.showLongToast(mContext, response.getMessage());
                            if (response.getData() != null) {
                                regPushNotification(response);
//                                app.getPreferences().setLoggedInUser(response);
//                                startActivity(new Intent(mContext, MainActivity.class));
//                                finish();
                            }
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
                    pd.dismiss();
                    if (!TextUtils.isEmpty(apiResponse))
                        Utils.showLongToast(mContext, apiResponse);
                }
            });
        } else {
            Utils.alert_dialog(mContext);
        }
    }

    private void customersignup(Map<String, String> params) {
        if (cd.isConnectingToInternet()) {
            final ProgressDialog pd = new ProgressDialog(mContext);
            pd.setTitle("Loading...");
            pd.show();
            app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().customersignup(params), new ApiRequestHelper.OnRequestComplete() {
                @Override
                public void onSuccess(Object object) {
                    pd.dismiss();
                    UserData response = (UserData) object;
                    if (response != null) {
                        if (response.getResponsecode() == 200) {
                            if (response.getMessage() != null && !TextUtils.isEmpty(response.getMessage()))
                                Utils.showLongToast(mContext, response.getMessage());
                            if (response.getData() != null) {
                                regPushNotification(response);
//                                app.getPreferences().setLoggedInUser(response);
//                                startActivity(new Intent(mContext, MainActivity.class));
//                                finish();
                            }
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
                    pd.dismiss();
                    if (!TextUtils.isEmpty(apiResponse))
                        Utils.showLongToast(mContext, apiResponse);
                }
            });
        } else {
            Utils.alert_dialog(mContext);
        }
    }

    public void regPushNotification(final UserData response) {
        Map<String, String> params = new HashMap<>();
        params.put("userid", response.getData().getCustomerId() + "");
        if (!TextUtils.isEmpty(app.getPreferences().getToken()))
            params.put("deviceid", app.getPreferences().getToken());
        params.put("ostype", "Android");
        params.put("appversion", "1");
        if (cd.isConnectingToInternet()) {
            final ProgressDialog cpd = new ProgressDialog(mContext);
            cpd.show();
            app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().registerpushnotification(params), new ApiRequestHelper.OnRequestComplete() {
                @Override
                public void onSuccess(Object object) {
                    if (cpd.isShowing())
                        cpd.dismiss();
                    app.getPreferences().setLoggedInUser(response);
                    if (!isFromWishlist)
                        startActivity(new Intent(mContext, MainActivity.class));
                       finish();
                }

                @Override
                public void onFailure(String apiResponse) {
                    if (cpd.isShowing())
                        cpd.dismiss();
                    Utils.showLongToast(mContext, apiResponse);
                }
            });
        } else {
            Utils.alert_dialog(mContext);
        }
    }
}

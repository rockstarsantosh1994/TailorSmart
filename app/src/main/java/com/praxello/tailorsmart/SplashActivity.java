package com.praxello.tailorsmart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_handler();
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_splash;
    }

    private void set_handler() {
        int SPLASH_TIME_OUT = 2000;
        new Handler().postDelayed(this::startNextActivity, SPLASH_TIME_OUT);
    }

    private void startNextActivity() {
        if (LoginActivity.loginActivity != null && !LoginActivity.loginActivity.isFinishing()) {
            LoginActivity.loginActivity.finish();
        }
        if (app.getPreferences().isLoggedInUser()){
            startActivity(new Intent(SplashActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }else {
            startActivity(new Intent(SplashActivity.this, SliderActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
        finish();
    }
}

package com.praxello.tailorsmart;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.praxello.tailorsmart.utils.ConnectionDetector;
import retrofit2.Call;

import java.util.ArrayList;
import java.util.List;

abstract class BaseActivity extends AppCompatActivity {
    public App app;
    public Context mContext;
    public ConnectionDetector cd;
    private Unbinder unbinder;
    public List<Call> callList = new ArrayList<Call>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());
        unbinder = ButterKnife.bind(this);
        app = (App) getApplication();
        mContext = this;
        cd = new ConnectionDetector(mContext);
//        if (Build.VERSION.SDK_INT >= 23) {   //Android M Or Over
//            ask_permissions();
//        }
    }

    protected abstract int getActivityLayout();

    public void ask_permissions() {
        ActivityCompat.requestPermissions(BaseActivity.this,
                new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE
                },
                1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (callList.size() > 0) {
            for (Call call : callList) {
                if (call != null && call.isExecuted())
                    call.cancel();
            }
        }
    }
}

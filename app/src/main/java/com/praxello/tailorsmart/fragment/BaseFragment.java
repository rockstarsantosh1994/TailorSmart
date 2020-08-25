package com.praxello.tailorsmart.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.praxello.tailorsmart.App;
import com.praxello.tailorsmart.utils.ConnectionDetector;
import retrofit2.Call;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFragment extends Fragment {
    private Unbinder unbinder;
    public Context mContext;
    public App app;
    public ConnectionDetector cd;
    public List<Call> callList = new ArrayList<Call>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        app = (App) getActivity().getApplication();
        cd = new ConnectionDetector(mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(getActivityLayout(), container, false);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) unbinder.unbind();
        if (callList.size() > 0) {
            for (Call call : callList) {
                if (call != null && call.isExecuted())
                    call.cancel();
            }
        }
    }

    protected abstract int getActivityLayout();
}
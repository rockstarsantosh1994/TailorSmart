package com.praxello.tailorsmart;


import android.app.Application;
import androidx.appcompat.app.AppCompatDelegate;
import com.facebook.stetho.Stetho;
import com.praxello.tailorsmart.api.ApiRequestHelper;
import com.praxello.tailorsmart.preferences.Preferences;

public class App extends Application {
    private ApiRequestHelper apiRequestHelper;
    private Preferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        doInit();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        if (BuildConfig.DEBUG) {
            Stetho.initialize(Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    //.enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                    .build());
        }
    }

    private void doInit() {
        this.preferences = new Preferences(this);
        this.apiRequestHelper = ApiRequestHelper.init(this);
    }

    public synchronized ApiRequestHelper getApiRequestHelper() {
        return apiRequestHelper;
    }

    public synchronized Preferences getPreferences() {
        return preferences;
    }
}

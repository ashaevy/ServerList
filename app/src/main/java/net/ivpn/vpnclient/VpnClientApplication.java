package net.ivpn.vpnclient;

import android.app.Application;

import net.ivpn.vpnclient.di.AppComponent;
import net.ivpn.vpnclient.di.AppModule;
import net.ivpn.vpnclient.di.DaggerAppComponent;

/**
 * Created by ashaevy on 30.05.17.
 */

public class VpnClientApplication extends Application {
    private AppComponent appComponent;

    @Override public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}

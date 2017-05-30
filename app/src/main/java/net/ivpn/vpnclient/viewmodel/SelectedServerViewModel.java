package net.ivpn.vpnclient.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Intent;

import net.ivpn.vpnclient.VpnClientApplication;
import net.ivpn.vpnclient.model.AppDatabase;
import net.ivpn.vpnclient.model.ServerItem;
import net.ivpn.vpnclient.model.ServersListService;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ashaevy on 30.05.17.
 */

public class SelectedServerViewModel extends AndroidViewModel {

    private LiveData<ServerItem> selectedServer;
    @Inject
    AppDatabase mDb;

    public SelectedServerViewModel(Application application) {
        super(application);
        ((VpnClientApplication) getApplication()).getAppComponent().inject(this);

        subscribeToDbChanges();
    }

    private void subscribeToDbChanges() {
        selectedServer = mDb.serversListModel().loadSelectedData();
    }

    public LiveData<ServerItem> getSelectedServer() {
        return selectedServer;
    }

    public void requestDataFromServer() {
        Intent serviceIntent = new Intent(this.getApplication(), ServersListService.class);
        serviceIntent.setAction(ServersListService.REQUEST_SERVERS_ACTION);
        getApplication().startService(serviceIntent);
    }

}

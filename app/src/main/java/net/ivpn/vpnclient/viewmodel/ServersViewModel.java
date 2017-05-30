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

public class ServersViewModel extends AndroidViewModel {

    private LiveData<List<ServerItem>> serverItemListLiveData;
    @Inject
    AppDatabase mDb;

    public ServersViewModel(Application application) {
        super(application);
        ((VpnClientApplication) getApplication()).getAppComponent().inject(this);

        subscribeToDbChanges();
    }

    private void subscribeToDbChanges() {
        serverItemListLiveData = mDb.serversListModel().loadServers();
    }

    public LiveData<List<ServerItem>> getServerItemListLiveData() {
        return serverItemListLiveData;
    }

    public void setSelectedServer(ServerItem serverItem) {
        int id = serverItem.getId();
        Intent serviceIntent = new Intent(this.getApplication(), ServersListService.class);
        serviceIntent.setAction(ServersListService.UPDATE_SELECTED_ACTION);
        serviceIntent.putExtra(ServersListService.SELECTED_SERVER_ID_KEY, id);
        getApplication().startService(serviceIntent);
    }
}

package net.ivpn.vpnclient.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import net.ivpn.vpnclient.VpnClientApplication;
import net.ivpn.vpnclient.model.AppDatabase;
import net.ivpn.vpnclient.model.ServerItem;

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

}

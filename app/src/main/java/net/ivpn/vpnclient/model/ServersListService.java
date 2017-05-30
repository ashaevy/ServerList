package net.ivpn.vpnclient.model;

import android.app.IntentService;
import android.content.Intent;

import net.ivpn.vpnclient.VpnClientApplication;
import net.ivpn.vpnclient.api.IVPNService;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

public class ServersListService extends IntentService {

    public static String REQUEST_SERVERS_ACTION = ServersListService.class.getName() + ".REQUEST_SERVERS_ACTION";
    public static String UPDATE_SELECTED_ACTION = ServersListService.class.getName() + ".UPDATE_SELECTED_ACTION";
    public static String SELECTED_SERVER_ID_KEY = "selected_server_id";

    @Inject IVPNService ivpnService;
    @Inject AppDatabase db;

    public ServersListService() {
        super("ServersListService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((VpnClientApplication) getApplication()).getAppComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            if (REQUEST_SERVERS_ACTION.equals(intent.getAction())) {
                try {
                    requestServersList();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (UPDATE_SELECTED_ACTION.equals(intent.getAction())) {
                updateSelectedServer(intent.getIntExtra(SELECTED_SERVER_ID_KEY, 0));
            }
        }
    }

    private void requestServersList() throws java.io.IOException {
        ServerItem selectedServer = db.serversListModel().loadSelected();
        List<ServerItem> serversList = ivpnService.getServers().execute().body();
        db.serversListModel().deleteAll();
        db.serverIpsListModel().deleteAll();
        boolean shouldUpdateSelectedServer = true;
        for (ServerItem si : serversList) {
            if (shouldUpdateSelectedServer) {
                if (selectedServer == null) {
                    // if no selected server - select first
                    si.setSelected(true);
                    shouldUpdateSelectedServer = false;
                } else {
                    if (selectedServer.getGateway().equals(si.getGateway())) {
                        si.setSelected(true);
                        shouldUpdateSelectedServer = true;
                    }
                }
            }
            long serverId = db.serversListModel().insertServerItem(si);
            List<String> ipAddresses = si.getIpAddresses();
            for (String ipAddressString: ipAddresses) {
                ServerIp serverIp = new ServerIp();
                serverIp.setIp(ipAddressString);
                serverIp.setServerId((int) serverId);
                db.serverIpsListModel().insertServerIp(serverIp);
            }
        }
    }

    public void updateSelectedServer(int id) {
        ServerItem oldSelectedServer = db.serversListModel().loadSelected();
        ServerItem selectedServer = db.serversListModel().loadWithId(id);
        db.beginTransaction();
        try {
            oldSelectedServer.setSelected(false);
            selectedServer.setSelected(true);
            db.serversListModel().updateServer(oldSelectedServer);
            db.serversListModel().updateServer(selectedServer);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

}

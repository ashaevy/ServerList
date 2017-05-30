package net.ivpn.vpnclient.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by ashaevy on 30.05.17.
 */

@Database(entities = { ServerItem.class, ServerIp.class }, version = 1) public abstract class AppDatabase
        extends RoomDatabase {
    public abstract ServersListDao serversListModel();
    public abstract ServerIpsListDao serverIpsListModel();
}

package net.ivpn.vpnclient.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by ashaevy on 30.05.17.
 */
@Dao
public interface ServerIpsListDao {
    @Query("select * from ServerIp WHERE ServerIp.id = :serverId")
    List<ServerIp> loadIps(int serverId);

    @Query("DELETE FROM ServerIp") void deleteAll();

    @Insert(onConflict = REPLACE) void insertServerIp(ServerIp serverIp);
}

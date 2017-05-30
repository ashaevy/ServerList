package net.ivpn.vpnclient.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by ashaevy on 30.05.17.
 */

@Dao
public interface ServersListDao {
    @Query("select * from ServerItem WHERE ServerItem.id = :id") ServerItem loadWithId(int id);

    @Query("select * from ServerItem WHERE ServerItem.selected = 1") ServerItem loadSelected();

    @Query("select * from ServerItem WHERE ServerItem.selected = 1") LiveData<ServerItem> loadSelectedData();

    @Query("select * from ServerItem") LiveData<List<ServerItem>> loadServers();

    @Query("DELETE FROM ServerItem") void deleteAll();

    @Insert(onConflict = REPLACE) long insertServerItem(ServerItem serverItem);

    @Update(onConflict = REPLACE) void updateServer(ServerItem serverItem);
}

package net.ivpn.vpnclient.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ashaevy on 30.05.17.
 */

@Entity
public class ServerIp {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String ip;

    @ColumnInfo(name = "server_id")
    @ForeignKey(entity = ServerItem.class, parentColumns = "id", childColumns = "server_id")
    int serverId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }
}

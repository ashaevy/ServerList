package net.ivpn.vpnclient.api;

import net.ivpn.vpnclient.model.ServerItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ashaevy on 30.05.17.
 */

public interface IVPNService {
    @GET("servers.json")
    Call<List<ServerItem>> getServers();
}

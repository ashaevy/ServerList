package net.ivpn.vpnclient;

import net.ivpn.vpnclient.model.ServerItem;

/**
 * Created by ashaevy on 31.05.17.
 */

public class StringUtils {
    public static String formatServerName(ServerItem serverItem) {
        return serverItem.getCity() + ", " + serverItem.getCountryCode();
    }
}

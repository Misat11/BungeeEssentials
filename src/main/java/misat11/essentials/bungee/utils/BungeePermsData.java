package misat11.essentials.bungee.utils;

import net.alpenblock.bungeeperms.BungeePerms;
import net.alpenblock.bungeeperms.Group;
import net.alpenblock.bungeeperms.PermissionsManager;
import net.alpenblock.bungeeperms.User;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeePermsData {
	public static boolean isAvailable() {
        return ProxyServer.getInstance().getPluginManager().getPlugin("BungeePerms") != null;
    }

    public static String getPrefix(ProxiedPlayer player) {
        if (isAvailable()) {
            BungeePerms bungeePerms = BungeePerms.getInstance();
            if (bungeePerms != null) {
                PermissionsManager pm = bungeePerms.getPermissionsManager();
                if (pm != null) {
                    User user = pm.getUser(player.getName());
                    if (user != null) {
                        return user.buildPrefix();
                    }
                }
            }
        }
        return "";
    }

    public static String getSuffix(ProxiedPlayer player) {
        if (isAvailable()) {
            BungeePerms bungeePerms = BungeePerms.getInstance();
            if (bungeePerms != null) {
                PermissionsManager pm = bungeePerms.getPermissionsManager();
                if (pm != null) {
                    User user = pm.getUser(player.getName());
                    if (user != null) {
                        return user.buildSuffix();
                    }
                }
            }
        }
        return "";
    }

    public static String getGroup(ProxiedPlayer player) {
        if (isAvailable()) {
            BungeePerms bungeePerms = BungeePerms.getInstance();
            if (bungeePerms != null) {
                PermissionsManager pm = bungeePerms.getPermissionsManager();
                if (pm != null) {
                    User user = pm.getUser(player.getName());
                    if (user != null) {
                        Group mainGroup = pm.getMainGroup(user);
                        if (mainGroup != null) {
                            return mainGroup.getName();
                        }
                    }
                }
            }
        }
        return "";
    }
}

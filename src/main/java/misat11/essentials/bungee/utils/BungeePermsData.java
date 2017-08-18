package misat11.essentials.bungee.utils;

import net.alpenblock.bungeeperms.BungeePerms;
import net.alpenblock.bungeeperms.Group;
import net.alpenblock.bungeeperms.PermissionsManager;
import net.alpenblock.bungeeperms.User;
import net.md_5.bungee.api.ProxyServer; 

public class BungeePermsData {
	public static boolean isAvailable() {
        return ProxyServer.getInstance().getPluginManager().getPlugin("BungeePerms") != null;
    }

    public static String getPrefix(String player) {
        if (isAvailable()) {
            BungeePerms bungeePerms = BungeePerms.getInstance();
            if (bungeePerms != null) {
                PermissionsManager pm = bungeePerms.getPermissionsManager();
                if (pm != null) {
                    User user = pm.getUser(player);
                    if (user != null) {
                        return user.buildPrefix();
                    }
                }
            }
        }
        return "";
    }

    public static String getSuffix(String player) {
        if (isAvailable()) {
            BungeePerms bungeePerms = BungeePerms.getInstance();
            if (bungeePerms != null) {
                PermissionsManager pm = bungeePerms.getPermissionsManager();
                if (pm != null) {
                    User user = pm.getUser(player);
                    if (user != null) {
                        return user.buildSuffix();
                    }
                }
            }
        }
        return "";
    }

    public static String getGroup(String player) {
        if (isAvailable()) {
            BungeePerms bungeePerms = BungeePerms.getInstance();
            if (bungeePerms != null) {
                PermissionsManager pm = bungeePerms.getPermissionsManager();
                if (pm != null) {
                    User user = pm.getUser(player);
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

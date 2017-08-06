package misat11.essentials.bungee.utils;

import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.Contexts;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.User;
import me.lucko.luckperms.api.caching.MetaData;

import java.util.Optional;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class LuckPermsData {
	public static boolean isAvailible(){
        return ProxyServer.getInstance().getPluginManager().getPlugin("LuckPerms") != null;
	}
	
	private static LuckPermsApi getAPI() {
		Optional<LuckPermsApi> provider = LuckPerms.getApiSafe();
		if (provider.isPresent()) {
			return provider.get();
		}
		return null;
	}

	public static String getPrefix(ProxiedPlayer player) {
		if(!isAvailible()) return "";
		User user = getAPI().getUserSafe(player.getName()).orElse(null);
		if (user == null) {
		    return "";
		}

		Contexts contexts = getAPI().getContextForUser(user).orElse(null);
		if (contexts == null) {
		    return "";
		}

		MetaData metaData = user.getCachedData().getMetaData(contexts);

		return metaData.getPrefix();
	}

	public static String getSuffix(ProxiedPlayer player) {
		if(!isAvailible()) return "";
		User user = getAPI().getUserSafe(player.getName()).orElse(null);
		if (user == null) {
		    return "";
		}

		Contexts contexts = getAPI().getContextForUser(user).orElse(null);
		if (contexts == null) {
		    return "";
		}

		MetaData metaData = user.getCachedData().getMetaData(contexts);

		return metaData.getSuffix();
	} 
	 
    public static String getPrimaryGroup(ProxiedPlayer player) {
		if(!isAvailible()) return "";
		User user = getAPI().getUserSafe(player.getName()).orElse(null);
		if (user == null) {
		    return "";
		}

        return user.getPrimaryGroup();
    }
}

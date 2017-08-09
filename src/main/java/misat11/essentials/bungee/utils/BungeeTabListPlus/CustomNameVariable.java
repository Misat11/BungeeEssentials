package misat11.essentials.bungee.utils.BungeeTabListPlus;

import codecrafter47.bungeetablistplus.api.bungee.BungeeTabListPlusAPI;
import codecrafter47.bungeetablistplus.api.bungee.Variable;

import misat11.essentials.bungee.UserConfig;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class CustomNameVariable {
	public static void hookToBungeeTabListPlus(Plugin plugin) {
		if (ProxyServer.getInstance().getPluginManager().getPlugin("BungeeTabListPlus") != null) {
			BungeeTabListPlusAPI.registerVariable(plugin, new Variable("bungee_essentials_custom_name") {

				@Override
				public String getReplacement(ProxiedPlayer player) {
					UserConfig userConfig = UserConfig.getPlayer(player.getName());
					if (userConfig != null) {
						return userConfig.getCustomname();
					}
					return player.getName();
				}

			});
		}
	}
}
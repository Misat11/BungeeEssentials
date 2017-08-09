package misat11.essentials.bungee.utils.GlobalTabList;

import java.util.regex.Matcher;

import de.codecrafter47.globaltablist.GlobalTablist;
import de.codecrafter47.globaltablist.Placeholder;
import misat11.essentials.bungee.UserConfig;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class CustomNamePlaceholder {
	public static void hookToGlobalTabList(Plugin plugin) {
		if (plugin.getProxy().getPluginManager().getPlugin("GlobalTablist") != null) {
			GlobalTablist.getAPI().registerPlaceholder(plugin, new Placeholder("bungee_essentials_custom_name") {

				@Override
				public String getReplacement(ProxiedPlayer player, Matcher arg1) {
					UserConfig userConfig = UserConfig.getPlayer(player.getName());
					if (userConfig != null) {
						return userConfig.getCustomname();
					}
					return player.getName();
				}

				@Override
				protected void onActivate() {
					// It's this needed? :)
				}

				@Override
				protected void onDeactivate() {
					// It's this needed? :)
				}

			});
		}
	}
}

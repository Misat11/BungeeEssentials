package misat11.essentials.bukkit;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.ess3.api.IEssentials;

import misat11.essentials.Constants;
import misat11.essentials.bukkit.listeners.Bridge;

public class BukkitPlugin extends JavaPlugin {
	private static BukkitPlugin instance;

	public void onEnable() {
		instance = this;
		getServer().getMessenger().registerOutgoingPluginChannel(this, Constants.channel_name);
		getServer().getMessenger().registerIncomingPluginChannel(this, Constants.channel_name, new Bridge());
	}

	public void onDisable() {

	}

	public static boolean isOriginalEssentialsAvailable() {
		final PluginManager pluginManager = instance.getServer().getPluginManager();
		if (pluginManager.getPlugin("Essentials") != null) {
			final IEssentials ess = (IEssentials) pluginManager.getPlugin("Essentials");
			if (ess.isEnabled())
				return true;
		}
		return false;
	}

	public static IEssentials getEssentials() {
		if (isOriginalEssentialsAvailable())
			return (IEssentials) instance.getServer().getPluginManager().getPlugin("Essentials");
		return null;
	}
}

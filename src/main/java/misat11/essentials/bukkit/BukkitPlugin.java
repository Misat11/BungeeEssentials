package misat11.essentials.bukkit;
 
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.ess3.api.IEssentials;

import misat11.essentials.Constants;
import misat11.essentials.bukkit.listeners.Bridge;

public class BukkitPlugin extends JavaPlugin {
	private static BukkitPlugin instance;
	private static boolean isSpigot;

	public void onEnable() {
		isSpigot = getIsSpigot();
		instance = this;
		getServer().getMessenger().registerOutgoingPluginChannel(this, Constants.channel_name);
		getServer().getMessenger().registerIncomingPluginChannel(this, Constants.channel_name, new Bridge());
		getLogger().info("********************");
		getLogger().info("* BungeeEssentials *");
		getLogger().info("*   BukkitBridge   *");
		getLogger().info("*    by Misat11    *");
		getLogger().info("*                  *");
		if (Constants.version.length() == 10) {
			getLogger().info("*    V" + Constants.version + "   *");
		} else {
			getLogger().info("*      V" + Constants.version + "      *");
		}
		getLogger().info("*                  *");
		if (Constants.snapshot == true) {
			getLogger().info("* SNAPSHOT VERSION *");
		} else {
			getLogger().info("*  STABLE VERSION  *");
		}

		if (isSpigot == false) {
			getLogger().info("*                  *");
			getLogger().info("*     WARNING:     *");
			getLogger().info("* You aren't using *");
			getLogger().info("*      Spigot      *");
			getLogger().info("*                  *");
			getLogger().info("* Please download! *");
			getLogger().info("*   spigotmc.org   *");
		}

		getLogger().info("*                  *");
		getLogger().info("********************");
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

	private boolean getIsSpigot() {
		try {
			Package spigotPackage = Package.getPackage("org.spigotmc");
			return (spigotPackage != null);
		} catch (Exception e) {
			return false;
		}
	}
}

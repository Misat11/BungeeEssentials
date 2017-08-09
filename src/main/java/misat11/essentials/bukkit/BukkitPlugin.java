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
		getLogger().info("§a********************");
		getLogger().info("§a* BungeeEssentials *");
		getLogger().info("§a*   BukkitBridge   *");
		getLogger().info("§a*    by Misat11    *");
		getLogger().info("§a*                  *");
		if (Constants.version.length() == 10) {
			getLogger().info("§a*    V" + Constants.version + "   *");
		} else {
			getLogger().info("§a*      V" + Constants.version + "      *");
		}
		getLogger().info("§a*                  *");
		if (Constants.snapshot == true) {
			getLogger().info("§a* SNAPSHOT VERSION *");
		} else {
			getLogger().info("§a*  STABLE VERSION  *");
		}

		if (isSpigot == false) {
			getLogger().info("§a*                  *");
			getLogger().info("§c*     WARNING:     *");
			getLogger().info("§c* You aren't using *");
			getLogger().info("§c*      Spigot      *");
			getLogger().info("§c*                  *");
			getLogger().info("§c* Please download! *");
			getLogger().info("§c*   spigotmc.org   *");
		}

		getLogger().info("§a*                  *");
		getLogger().info("§a********************");
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

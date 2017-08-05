package misat11.essentials.bungee;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class UserConfig {

	private ProxiedPlayer player;
	private String customname = null;
	private Configuration config;

	private boolean init = false;

	private static HashMap<ProxiedPlayer, UserConfig> userConfigs = new HashMap<ProxiedPlayer, UserConfig>();

	public UserConfig(String username) {
		this(ProxyServer.getInstance().getPlayer(username));
	}

	public UserConfig(ProxiedPlayer player) {
		this.player = player;
		userConfigs.put(player, this);
	}

	public String getName() {
		return player.getName();
	}

	public String getCustomname() {
		return this.getCustomname(true);
	}

	public String getCustomname(boolean wprefix) {
		if (customname != null) {
			return (wprefix == true ? BungeeEssentials.getConfig().getString("customnameprefix") : "") + customname;
		} else {
			return player.getName();
		}
	}

	private File getDataFolder() {
		return new File(BungeeEssentials.getInstance().getDataFolder().toString() + "/players");
	}

	public void configInit() {
		if (init)
			return;

		if (!getDataFolder().exists())
			getDataFolder().mkdir();

		File file = new File(getDataFolder(), player.getName().toLowerCase() + ".yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			config = ConfigurationProvider.getProvider(YamlConfiguration.class)
					.load(new File(getDataFolder(), player.getName().toLowerCase() + ".yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (config.contains("customname")) {
			customname = config.getString("customnick");
		}

		init = true;
	}

	public void configReload() {
		init = false;
		customname = null;
		configInit();
	}

	public void setCustomname(String customname) {
		this.customname = customname;
		try {
			config.set("customname", customname);
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config,
					new File(getDataFolder(), player.getName().toLowerCase() + ".yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static UserConfig getPlayer(String username) {
		return getPlayer(ProxyServer.getInstance().getPlayer(username));
	}

	public static UserConfig getPlayer(ProxiedPlayer player) {
		if (userConfigs.containsKey(player)) {
			return userConfigs.get(player);
		} else {
			return null;
		}
	}
}

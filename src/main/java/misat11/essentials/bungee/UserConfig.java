package misat11.essentials.bungee;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class UserConfig {

	private String playername;
	private Configuration config;

	private boolean init = false;

	private static HashMap<String, UserConfig> userConfigs = new HashMap<String, UserConfig>();

	public static void registerPlayer(ProxiedPlayer player) {
		registerPlayer(player.getName());
	}

	public static void registerPlayer(String username) {
		if (!userConfigs.containsKey(username.toLowerCase())) {
			UserConfig user = new UserConfig();
			user.setName(username);
			userConfigs.put(username.toLowerCase(), user);
			user.configInit();
		} else {
			userConfigs.get(username.toLowerCase()).setName(username);
		}
	}

	private UserConfig() {

	}

	public String getName() {
		return playername;
	}

	private void setName(String username) {
		this.playername = username;
	}

	public String getCustomname() {
		return this.getCustomname(true);
	}

	public void sendMail(ProxiedPlayer sender, String mail) {
		if (!config.contains("mails"))
			config.set("mails", new ArrayList<HashMap>());
		HashMap<String, String> save = new HashMap<String, String>();
		save.put("sender", sender.getName());
		save.put("message", mail);
		List list = config.getList("mails");
		list.add(save);
		config.set("mails", list);
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config,
					new File(getDataFolder(), playername.toLowerCase() + ".yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List getMails() {
		return config.contains("mails") ? config.getList("mails") : null;
	}

	public void clearMails() {
		config.set("mails", null);
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config,
					new File(getDataFolder(), playername.toLowerCase() + ".yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getCustomname(boolean wprefix) {
		if (config.contains("customname")) {
			return (wprefix == true ? BungeeEssentials.getConfig().getString("customnameprefix") : "") + config.getString("customname");
		} else {
			return playername;
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

		File file = new File(getDataFolder(), playername.toLowerCase() + ".yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			config = ConfigurationProvider.getProvider(YamlConfiguration.class)
					.load(new File(getDataFolder(), playername.toLowerCase() + ".yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		init = true;
	}

	public void configReload() {
		init = false; 
		configInit();
	}

	public void setCustomname(String customname) { 
		try {
			config.set("customname", customname);
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config,
					new File(getDataFolder(), playername.toLowerCase() + ".yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static UserConfig getPlayer(String username) {
		username = username.toLowerCase();
		if (userConfigs.containsKey(username)) {
			return userConfigs.get(username);
		} else {
			return null;
		}
	}

	public static UserConfig getPlayer(ProxiedPlayer player) {
		return getPlayer(player.getName());
	}
}

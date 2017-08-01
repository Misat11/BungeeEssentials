package misat11.essentials.bungee;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import misat11.essentials.bungee.commands.MsgCommand;
import misat11.essentials.bungee.listeners.ChatListener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class BungeeEssentials extends Plugin {
	public static String version = "0.0.2";
	public static boolean snapshot = true;
	private static BungeeEssentials instance;
	private static Configuration config;

	@Override
	public void onEnable() {
		instance = this;

		/* Configs */
		if (!getDataFolder().exists())
			getDataFolder().mkdir();

		File file = new File(getDataFolder(), "config.yml");

		if (!file.exists()) {
			try {
				InputStream in = getResourceAsStream("config.yml");
				Files.copy(in, file.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			config = ConfigurationProvider.getProvider(YamlConfiguration.class)
					.load(new File(getDataFolder(), "config.yml"));
			if (!config.contains("chat"))
				config.set("chat", "[%server%] %name% >> %chat%");
			if (!config.contains("msg"))
				config.set("msg", "%sender_name% >> %receiver_name%: %chat%");
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config,
					new File(getDataFolder(), "config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		/* Listeners */
		getProxy().getPluginManager().registerListener(this, new ChatListener());

		/* Commands */
		getProxy().getPluginManager().registerCommand(this, new MsgCommand());

		getLogger().info("********************");
		getLogger().info("*   Bungee Perms   *");
		getLogger().info("*    by Misat11    *");
		getLogger().info("*                  *");
		if (version.length() == 10) {
			getLogger().info("*                  *");
			getLogger().info("*    V" + version + "   *");
		} else {
			getLogger().info("*      V" + version + "      *");
		}
		getLogger().info("*                  *");
		if (snapshot == true) {
			getLogger().info("* SNAPSHOT VERSION *");
		} else {
			getLogger().info("*  STABLE VERSION  *");
		}
		getLogger().info("*                  *");
		getLogger().info("*                  *");
		getLogger().info("********************");
	}

	@Override
	public void onDisable() {
		getLogger().info("********************");
		getLogger().info("* Thanks for using *");
		getLogger().info("*   Bungee Perms   *");
		getLogger().info("********************");
	}

	public static BungeeEssentials getInstance() {
		return instance;
	}

	public static Configuration getConfig() {
		return config;
	}

}

package misat11.essentials.bungee;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import misat11.essentials.bungee.commands.MailCommand;
import misat11.essentials.bungee.commands.MsgCommand;
import misat11.essentials.bungee.commands.NickCommand;
import misat11.essentials.bungee.listeners.ChatListener; 
import misat11.essentials.bungee.listeners.PlayerJoinListener;
import misat11.essentials.bungee.listeners.PlayerLeaveListener;
import misat11.essentials.bungee.listeners.PlayerSwitchServerListener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class BungeeEssentials extends Plugin {
	public static String version = "0.0.5";
	public static boolean snapshot = true;
	private static BungeeEssentials instance;
	private static Configuration config;

	@Override
	public void onEnable() {
		if (Float.parseFloat(System.getProperty("java.class.version")) < 52.0) {
			getLogger().severe("§cBungeeEssentials requires Java 8 or above. Please download and install it!");
			getLogger().severe("Disabling plugin!");
			return;
		}

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
				config.set("chat", "[%server%] %customname% >> %chat%");
			if (!config.contains("msg"))
				config.set("msg", "%sender_customname% >> %receiver_customname%: %chat%");
			if (!config.contains("customnameprefix"))
				config.set("customnameprefix", "~");
			if (!config.contains("join-msg"))
				config.set("join-msg", "%customname%§a joined to proxy.");
			if (!config.contains("leave-msg"))
				config.set("leave-msg", "%customname% §cleaved from proxy.");
			if (!config.contains("change-server-msg"))
				config.set("change-server-msg", "%customname% §bchanged server to %after%§b.");
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config,
					new File(getDataFolder(), "config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		/* Listeners */
		getProxy().getPluginManager().registerListener(this, new ChatListener());
		getProxy().getPluginManager().registerListener(this, new PlayerJoinListener());
		getProxy().getPluginManager().registerListener(this, new PlayerLeaveListener());
		getProxy().getPluginManager().registerListener(this, new PlayerSwitchServerListener());

		/* Commands */
		getProxy().getPluginManager().registerCommand(this, new MsgCommand());
		getProxy().getPluginManager().registerCommand(this, new NickCommand());
		getProxy().getPluginManager().registerCommand(this, new MailCommand());

		getLogger().info("§a********************");
		getLogger().info("§a* BungeeEssentials *");
		getLogger().info("§a*    by Misat11    *");
		getLogger().info("§a*                  *");
		if (version.length() == 10) { 
			getLogger().info("§a*    V" + version + "   *");
		} else {
			getLogger().info("§a*      V" + version + "      *");
		}
		getLogger().info("§a*                  *");
		if (snapshot == true) {
			getLogger().info("§a* SNAPSHOT VERSION *");
		} else {
			getLogger().info("§a*  STABLE VERSION  *");
		}
		getLogger().info("§a*                  *"); 
		getLogger().info("§a********************");

		File folder = new File(getDataFolder().toString() + "/players");
		if (folder.exists()) {
			File[] listOfFiles = folder.listFiles();
			if (listOfFiles.length > 0) {
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {
						UserConfig.registerPlayer(listOfFiles[i].getName().replaceAll(".yml", ""));
					}
				}
			}
		}
	}

	@Override
	public void onDisable() {
		getLogger().info("§a********************");
		getLogger().info("§a* Thanks for using *");
		getLogger().info("§a* BungeeEssentials *");
		getLogger().info("§a********************");
	}

	public static BungeeEssentials getInstance() {
		return instance;
	}

	public static Configuration getConfig() {
		return config;
	}

}

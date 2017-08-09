package misat11.essentials.bungee;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
  
import misat11.essentials.Constants;
import misat11.essentials.api.APlayerPlaceholderProcessor;
import misat11.essentials.api.BungeeEssentialsApi;
import misat11.essentials.bungee.commands.MailCommand;
import misat11.essentials.bungee.commands.MeCommand;
import misat11.essentials.bungee.commands.MsgCommand;
import misat11.essentials.bungee.commands.NickCommand;
import misat11.essentials.bungee.commands.ServerShortcutCommand;
import misat11.essentials.bungee.listeners.ChatListener;
import misat11.essentials.bungee.listeners.PlayerJoinListener;
import misat11.essentials.bungee.listeners.PlayerLeaveListener;
import misat11.essentials.bungee.listeners.PlayerSwitchServerListener;
import misat11.essentials.bungee.utils.BungeeTabListPlus.CustomNameVariable;
import misat11.essentials.bungee.utils.GlobalTabList.CustomNamePlaceholder;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class BungeeEssentials extends Plugin implements BungeeEssentialsApi { 
	private static BungeeEssentials instance;
	private static Configuration config;
	private HashMap<String, ServerShortcutCommand> registeredServerShortcuts = new HashMap<String, ServerShortcutCommand>();
	private List<APlayerPlaceholderProcessor> player_placeholder_processors = new ArrayList<APlayerPlaceholderProcessor>();
	
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
				config.set("change-server-msg", "%customname% §bchanged server to %server%§b.");
			if (!config.contains("me-msg"))
				config.set("me-msg", "§d* §f%customname% §dsays %chat%");
			if (!config.contains("server-shortcuts"))
				config.set("server-shortcuts", new ArrayList());
			if (!config.contains("regex"))
				config.set("regex", new ArrayList());
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
		getProxy().getPluginManager().registerCommand(this, new MeCommand());

		List<Map<String, String>> list = (List<Map<String, String>>) config.getList("server-shortcuts");
		if (list != null) {
			list.forEach(new Consumer<Map<String, String>>() {
				public void accept(Map<String, String> t) {
					if (!registeredServerShortcuts.containsKey(t.get("command").toString())) {
						ServerShortcutCommand command = new ServerShortcutCommand(t.get("command").toString(),
								t.get("server").toString());
						getProxy().getPluginManager().registerCommand(instance, command);
						registeredServerShortcuts.put(t.get("command").toString(), command);
					}
				}
			});
		}

		getLogger().info("§a********************");
		getLogger().info("§a* BungeeEssentials *");
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
		
		if(getProxy().getPluginManager().getPlugin("BungeeTabListPlus") != null){
			CustomNameVariable.hookToBungeeTabListPlus(this);
		}
		
		if(getProxy().getPluginManager().getPlugin("GlobalTablist") != null){
			CustomNamePlaceholder.hookToGlobalTabList(this);
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

	@Override
	public String getCustomNick(String playername) {
		return UserConfig.getPlayer(playername) != null ? UserConfig.getPlayer(playername).getCustomname() : playername;
	}

	@Override
	public void regsterPlayerPlaceholderProcessor(APlayerPlaceholderProcessor processor) {
		player_placeholder_processors.add(processor);
	} 
	
	public List<APlayerPlaceholderProcessor> getPlayerPlaceholderProcessors(){
		return player_placeholder_processors;
	}

}

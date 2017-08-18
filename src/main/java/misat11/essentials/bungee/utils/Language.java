package misat11.essentials.bungee.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import misat11.essentials.bungee.BungeeEssentials;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Language {

	private static List<String> list = Arrays.asList("en", "cs", "hu");
	private static String base_langcode = "en";

	private static Language instance;

	public static Language loadLanguage() {
		Configuration config = BungeeEssentials.getConfig();
		String lang = config.getString("lang");
		if (!list.contains(lang)) {
			try {
				config.set("lang", base_langcode);
				ConfigurationProvider.getProvider(YamlConfiguration.class).save(config,
						new File(BungeeEssentials.getInstance().getDataFolder(), "config.yml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Language language = new Language();
		language.langcode = lang;
		if (!language.langcode.equals(base_langcode)) {
			InputStream in = BungeeEssentials.getInstance()
					.getResourceAsStream("messages_" + base_langcode + ".yml");
			language.config_baseLanguage = ConfigurationProvider.getProvider(YamlConfiguration.class).load(in);
		}
		InputStream in = BungeeEssentials.getInstance().getResourceAsStream("messages_" + language.langcode + ".yml");
		language.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(in);
		instance = language;
		BungeeEssentials.getInstance().getLogger().info(
				"Successfully loaded messages for BungeeEssentials! Language: " + language.translateMsg("lang_name"));
		return language;
	}

	public static String translate(String base) {
		return instance.translateMsg(base);
	}

	private String langcode;
	private Configuration config;
	private Configuration config_baseLanguage;

	private Language() {

	}

	public String translateMsg(String base) {
		if (config.contains(base)) {
			return config.getString(base);
		} else if (config_baseLanguage != null) {
			if (config_baseLanguage.contains(base)) {
				return config_baseLanguage.getString(base);
			}
		}
		return "§cLanguage error: Not found (" + base + ")";
	}

}

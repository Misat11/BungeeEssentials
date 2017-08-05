package misat11.essentials.api;

import misat11.essentials.bungee.UserConfig;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeEssentialsApi {
	public static String getCustomNick(String playername){
		return UserConfig.getPlayer(playername).getCustomname();
	}
	
	public static String getCustomNick(ProxiedPlayer player){
		return UserConfig.getPlayer(player).getCustomname();
	}
}

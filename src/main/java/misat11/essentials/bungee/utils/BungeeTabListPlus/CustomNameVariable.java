package misat11.essentials.bungee.utils.BungeeTabListPlus; 

import codecrafter47.bungeetablistplus.api.bungee.Variable;

import misat11.essentials.bungee.UserConfig;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class CustomNameVariable extends Variable {
    public CustomNameVariable() { 
        super("bungee_essentials_custom_name");
    } 

	@Override
	public String getReplacement(ProxiedPlayer player) {
		UserConfig userConfig = UserConfig.getPlayer(player.getName());
		if(userConfig != null){
			return userConfig.getCustomname();
		}
		return player.getName();
	}
}
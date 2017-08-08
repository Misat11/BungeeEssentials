package misat11.essentials.api; 

import misat11.essentials.bungee.BungeeEssentials;
import net.md_5.bungee.api.connection.ProxiedPlayer; 

public interface BungeeEssentialsApi { 
	public static BungeeEssentialsApi getInstance(){
		return BungeeEssentials.getInstance();
	}  

	public default String getCustomNick(ProxiedPlayer player){
		return getCustomNick(player.getName());
	}
	
	public String getCustomNick(String playername);  
	
	public void regsterPlayerPlaceholderProcessor(APlayerPlaceholderProcessor processor);
}

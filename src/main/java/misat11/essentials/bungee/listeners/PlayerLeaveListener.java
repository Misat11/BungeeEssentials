package misat11.essentials.bungee.listeners;

import misat11.essentials.bungee.BungeeEssentials; 
import misat11.essentials.bungee.utils.Placeholders;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent; 
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerLeaveListener implements Listener {
	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent e){  
    	BaseComponent[] message  = Placeholders.replace(BungeeEssentials.getConfig().getString("leave-msg"), 
    			Placeholders.getPlayerPlaceholders(e.getPlayer(), null));  
		ProxyServer.getInstance().broadcast(message);
	}
}

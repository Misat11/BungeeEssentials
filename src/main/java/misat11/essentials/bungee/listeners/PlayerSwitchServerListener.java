package misat11.essentials.bungee.listeners;

import misat11.essentials.bungee.Bridge;
import misat11.essentials.bungee.BungeeEssentials; 
import misat11.essentials.bungee.utils.Placeholders;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent; 
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerSwitchServerListener implements Listener {
	@EventHandler
	public void onServerSwitch(ServerSwitchEvent e){
    	BaseComponent[] message  = Placeholders.replace(BungeeEssentials.getConfig().getString("change-server-msg"), 
    			Placeholders.getPlayerPlaceholders(e.getPlayer(), null));  
		ProxyServer.getInstance().broadcast(message);
		Bridge.sendCustomName(e.getPlayer());
	}

}

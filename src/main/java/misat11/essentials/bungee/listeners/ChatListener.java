package misat11.essentials.bungee.listeners; 

import misat11.essentials.bungee.BungeeEssentials; 
import misat11.essentials.bungee.utils.Placeholder;
import misat11.essentials.bungee.utils.Placeholders;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent; 
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {
 
	@EventHandler
	public void onChat(ChatEvent e) { 
        if (e.isCancelled()) return;
        if (e.isCommand()) return;
		ProxiedPlayer player = (ProxiedPlayer) e.getSender();
		BaseComponent[] message = Placeholders.replace(BungeeEssentials.getConfig().getString("chat"),
				new Placeholder("chat", e.getMessage()),
				Placeholders.getPlayerPlaceholders(player, null)); 
		ProxyServer.getInstance().broadcast(message);
		e.setCancelled(true);
	}
}

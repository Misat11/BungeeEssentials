package misat11.essentials.bungee.listeners;

import misat11.essentials.bungee.BungeeEssentials;
import misat11.essentials.bungee.UserConfig;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerLeaveListener implements Listener {
	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent e){ 
		BaseComponent[] message = TextComponent.fromLegacyText(BungeeEssentials.getConfig().getString("leave-msg") 
				.replace("%name%", e.getPlayer().getName())
				.replace("%displayname%", e.getPlayer().getDisplayName())
				.replace("%customname%", UserConfig.getPlayer(e.getPlayer()).getCustomname()) 
				.replaceAll("&", "§")
				);
		ProxyServer.getInstance().broadcast(message);
	}
}

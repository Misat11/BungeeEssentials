package misat11.essentials.bungee.listeners; 

import misat11.essentials.bungee.BungeeEssentials;
import misat11.essentials.bungee.UserConfig;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
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
		BaseComponent[] message = TextComponent.fromLegacyText(BungeeEssentials.getConfig().getString("chat")
				.replace("%chat%", e.getMessage())
				.replace("%name%", player.getName())
				.replace("%displayname%", player.getDisplayName())
				.replace("%customname%", UserConfig.getPlayer(player).getCustomname())
				.replace("%server%", player.getServer().getInfo().getName())
				.replace("%server_motd%", player.getServer().getInfo().getMotd())
				.replaceAll("&", "§")
				);
		ProxyServer.getInstance().broadcast(message);
		e.setCancelled(true);
	}
}

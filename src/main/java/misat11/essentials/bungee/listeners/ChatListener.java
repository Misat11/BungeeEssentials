package misat11.essentials.bungee.listeners;

import misat11.essentials.bungee.BungeeEssentials;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;

public class ChatListener implements Listener {

	public void onChat(ChatEvent e) {
		ProxiedPlayer player = (ProxiedPlayer) e.getSender();
		BaseComponent[] message = TextComponent.fromLegacyText(BungeeEssentials.getConfig().getString("chat")
				.replace("%chat%", e.getMessage())
				.replace("%name%", player.getName())
				.replace("%displayname%", player.getDisplayName())
				.replace("%server%", player.getServer().getInfo().getName())
				.replace("%server_motd%", player.getServer().getInfo().getMotd())
				);
		player.sendMessage(message);
		e.setCancelled(true);
	}
}

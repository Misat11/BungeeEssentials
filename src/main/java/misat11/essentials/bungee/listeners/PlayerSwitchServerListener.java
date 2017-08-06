package misat11.essentials.bungee.listeners;

import misat11.essentials.bungee.BungeeEssentials;
import misat11.essentials.bungee.UserConfig;
import misat11.essentials.bungee.utils.BungeePermsData;
import misat11.essentials.bungee.utils.LuckPermsData;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerSwitchServerListener implements Listener {
	@EventHandler
	public void onServerSwitch(ServerSwitchEvent e){
		BaseComponent[] message = TextComponent.fromLegacyText(BungeeEssentials.getConfig().getString("change-server-msg") 
				.replace("%name%", e.getPlayer().getName())
				.replace("%displayname%", e.getPlayer().getDisplayName())
				.replace("%customname%", UserConfig.getPlayer(e.getPlayer()).getCustomname()) 
				.replace("%after%", e.getPlayer().getServer().getInfo().getName())
				.replace("%after_motd%", e.getPlayer().getServer().getInfo().getMotd())
				.replace("%BungeePerms_prefix%", BungeePermsData.getPrefix(e.getPlayer()))
				.replace("%BungeePerms_suffix%", BungeePermsData.getSuffix(e.getPlayer()))
				.replace("%BungeePerms_group%", BungeePermsData.getGroup(e.getPlayer()))
				.replace("%LuckPerms_prefix%", LuckPermsData.getPrefix(e.getPlayer()))
				.replace("%LuckPerms_suffix%", LuckPermsData.getSuffix(e.getPlayer())) 
				.replace("%LuckPerms_group%", LuckPermsData.getPrimaryGroup(e.getPlayer())) 
				.replaceAll("&", "§")
				); 
		ProxyServer.getInstance().broadcast(message);
	}

}

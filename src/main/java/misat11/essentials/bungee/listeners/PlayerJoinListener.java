package misat11.essentials.bungee.listeners;
 
import java.util.List;
import java.util.function.Consumer;

import misat11.essentials.bungee.BungeeEssentials;
import misat11.essentials.bungee.UserConfig;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPreLogin(final PreLoginEvent e){
    	final String newname = e.getConnection().getName().toLowerCase();
    	ProxyServer.getInstance().getPlayers().forEach(new Consumer<ProxiedPlayer>(){ 
			public void accept(ProxiedPlayer p) {  
				if(p.getName().toLowerCase().equals(newname)){
					e.getConnection().disconnect(new TextComponent("Your name was already used by another user."));
				}
			}
    		
    	});
    }
    
    @EventHandler
    public void onPostLogin(PostLoginEvent e) {  
    	UserConfig.registerPlayer(e.getPlayer()); 
    	
		BaseComponent[] message = TextComponent.fromLegacyText(BungeeEssentials.getConfig().getString("join-msg") 
				.replace("%name%", e.getPlayer().getName())
				.replace("%displayname%", e.getPlayer().getDisplayName())
				.replace("%customname%", UserConfig.getPlayer(e.getPlayer()).getCustomname()) 
				.replaceAll("&", "§")
				);
		ProxyServer.getInstance().broadcast(message);
		
		List mails = UserConfig.getPlayer(e.getPlayer()).getMails();
		if(mails != null){
			e.getPlayer().sendMessage(new TextComponent("You have mails. Please read it by §7/mail read §fand clear by §7/mail clear"));
		}
    }
}

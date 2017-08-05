package misat11.essentials.bungee.listeners;
 
import misat11.essentials.bungee.UserConfig;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerJoinLisetner implements Listener {
    @EventHandler
    public void onPostLogin(PostLoginEvent e) {
    	UserConfig config = new UserConfig(e.getPlayer());
    	config.configInit();
    }
}

package misat11.essentials.bungee;
 
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeTimer implements Runnable {

	@Override
	public void run() { 
		for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
			UserConfig user = UserConfig.getPlayer(player);
			user.checkMuteTimeout(System.currentTimeMillis());
		}
	}

}

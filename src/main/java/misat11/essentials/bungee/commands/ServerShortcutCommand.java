package misat11.essentials.bungee.commands; 

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ServerShortcutCommand extends Command {

	private String server;
	
	public ServerShortcutCommand(String name, String server) {
		super(name);
		this.server = server;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer){
			if (sender.hasPermission("essentials.servershortcut")) {
				if(((ProxiedPlayer) sender).getServer().getInfo().getName().equalsIgnoreCase(server)){
					sender.sendMessage(new TextComponent("You are already on this server."));
				} else {
					ServerInfo info = ProxyServer.getInstance().getServerInfo(server);
					if(info == null){
						sender.sendMessage(new TextComponent("Server name in config is invalid. Please contact admins of server about this."));
					} else {
						((ProxiedPlayer) sender).connect(info);
					}
				}
			} else {
				sender.sendMessage(new TextComponent("You haven't permissions!"));
			}
			
		} else {
			sender.sendMessage(new TextComponent("Cannot be used from console!"));
		}
	}

}

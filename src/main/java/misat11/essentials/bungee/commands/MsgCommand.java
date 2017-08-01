package misat11.essentials.bungee.commands;

import misat11.essentials.bungee.BungeeEssentials;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MsgCommand extends Command {

	public MsgCommand() {
		super("msg");
	}

	@Override
	public void execute(CommandSender sender, String[] args) { 
		if (sender instanceof ProxiedPlayer) {
			if (args.length >= 2) {
				if (ProxyServer.getInstance().getPlayer(args[0]) != null) {
					String message = String.join(" ", args).replace(args[0] + " ", "");
					ProxiedPlayer receiver = ProxyServer.getInstance().getPlayer(args[0]);
					BaseComponent[] sended_message = TextComponent.fromLegacyText(BungeeEssentials.getConfig()
							.getString("msg").replace("%chat%", message).replace("%sender_name%", sender.getName())
							.replace("%sender_displayname%", ((ProxiedPlayer) sender).getDisplayName())
							.replace("%sender_server%", ((ProxiedPlayer) sender).getServer().getInfo().getName())
							.replace("%sender_server_motd%", ((ProxiedPlayer) sender).getServer().getInfo().getMotd())
							.replace("%receiver_name%", receiver.getName())
							.replace("%receiver_displayname%", receiver.getDisplayName())
							.replace("%receiver_server%", receiver.getServer().getInfo().getName())
							.replace("%receiver_server_motd%", receiver.getServer().getInfo().getMotd())
							.replaceAll("&", "§")
							);
					sender.sendMessage(sended_message);
					receiver.sendMessage(sended_message);
				} else {
					sender.sendMessage(new TextComponent("Player not be online."));
				}
			} else {
				sender.sendMessage(new TextComponent("Wrong usage! Use /msg <player> <message>"));
			}
		} else {
			sender.sendMessage(new TextComponent("Cannot be used from console!"));
		}
	}

}

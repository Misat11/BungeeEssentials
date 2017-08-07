package misat11.essentials.bungee.commands;

import misat11.essentials.bungee.BungeeEssentials; 
import misat11.essentials.bungee.utils.Placeholder;
import misat11.essentials.bungee.utils.Placeholders;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MsgCommand extends Command {

	public MsgCommand() {
		super("msg", null, "tell", "message", "w", "whisper");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			if (!sender.hasPermission("essentials.msg")) {
				sender.sendMessage(new TextComponent("You haven't permissions!"));
				return;
			}
			if (args.length >= 2) {
				if (ProxyServer.getInstance().getPlayer(args[0]) != null) {
					String message = String.join(" ", args).replace(args[0] + " ", "");
					ProxiedPlayer receiver = ProxyServer.getInstance().getPlayer(args[0]);
					if(receiver == null){
						sender.sendMessage(new TextComponent("Player is not online."));
						return;
					}
					BaseComponent[] sended_message = Placeholders.replace(BungeeEssentials.getConfig().getString("msg"),
							new Placeholder("chat", message),
							Placeholders.getPlayerPlaceholders((ProxiedPlayer) sender, "sender"),
							Placeholders.getPlayerPlaceholders(receiver, "receiver"));  
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

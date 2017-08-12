package misat11.essentials.bungee.commands;

import misat11.essentials.bungee.BungeeEssentials;
import misat11.essentials.bungee.utils.Language;
import misat11.essentials.bungee.utils.Placeholder;
import misat11.essentials.bungee.utils.Placeholders;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent; 
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MeCommand extends Command {

	public MeCommand() {
		super("me", null, "action", "describe");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			if (sender.hasPermission("essentials.me")) {
				if (args.length >= 1) {
					BaseComponent[] message = Placeholders.replace(BungeeEssentials.getConfig().getString("me-msg"),
							new Placeholder("chat", String.join(" ", args)),
							Placeholders.getPlayerPlaceholders((ProxiedPlayer) sender, null)); 
					ProxyServer.getInstance().broadcast(message);
				} else {
					sender.sendMessage(Placeholders.replace(Language.translate("me.usage")));
				}
			} else {
				sender.sendMessage(Placeholders.replace(Language.translate("nopermissions")));
			}
		} else {
			sender.sendMessage(Placeholders.replace(Language.translate("consoleuse")));
		}

	}

}

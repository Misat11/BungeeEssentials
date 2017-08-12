package misat11.essentials.bungee.commands;

import misat11.essentials.bungee.Bridge;
import misat11.essentials.bungee.UserConfig;
import misat11.essentials.bungee.utils.Language;
import misat11.essentials.bungee.utils.Placeholder;
import misat11.essentials.bungee.utils.Placeholders;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer; 
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class NickCommand extends Command {

	public NickCommand() {
		super("nick");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!sender.hasPermission("essentials.nick")) {
			sender.sendMessage(Placeholders.replace(Language.translate("nopermissions")));
			return;
		}
		if (args.length == 1) {
			if (sender instanceof ProxiedPlayer) {
				UserConfig config = UserConfig.getPlayer((ProxiedPlayer) sender);
				if (args[0].equalsIgnoreCase("off")) {
					config.setCustomname(null);
					sender.sendMessage(Placeholders.replace(Language.translate("nick.reseted")));
					Bridge.sendCustomName((ProxiedPlayer) sender);
				} else {
					config.setCustomname(args[0]);
					sender.sendMessage(
							Placeholders.replace(Language.translate("nick.changed"), new Placeholder("nick", args[0])));
					Bridge.sendCustomName((ProxiedPlayer) sender);
				}
			} else {
				sender.sendMessage(Placeholders.replace(Language.translate("consoleuse")));
			}
		} else if (args.length == 2) {
			if (!sender.hasPermission("essentials.nick.others")) {
				sender.sendMessage(Placeholders.replace(Language.translate("nopermissions")));
			} else {
				UserConfig config = UserConfig.getPlayer(args[0]);
				if (config != null) {
					if (args[1].equalsIgnoreCase("off")) {
						config.setCustomname(null);
						sender.sendMessage(Placeholders.replace(Language.translate("nick.other.reseted"),
								new Placeholder("player", args[0])));
						if (ProxyServer.getInstance().getPlayer(args[0]) != null) {
							Bridge.sendCustomName(ProxyServer.getInstance().getPlayer(args[0]));
						}
					} else {
						config.setCustomname(args[1]);
						sender.sendMessage(Placeholders.replace(Language.translate("nick.other.reseted"),
								new Placeholder("player", args[0]), new Placeholder("nick", args[1])));
						if (ProxyServer.getInstance().getPlayer(args[0]) != null) {
							Bridge.sendCustomName(ProxyServer.getInstance().getPlayer(args[0]));
						}
					}
				} else {
					sender.sendMessage(Placeholders.replace(Language.translate("nick.other.notfound")));
				}
			}
		} else {
			sender.sendMessage(Placeholders.replace(Language.translate("nick.usage")));
		}
	}

}

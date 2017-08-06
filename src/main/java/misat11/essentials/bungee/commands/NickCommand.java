package misat11.essentials.bungee.commands;

import misat11.essentials.bungee.UserConfig;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class NickCommand extends Command {

	public NickCommand() {
		super("nick");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!sender.hasPermission("essentials.nick")) {
			sender.sendMessage(new TextComponent("You haven't permissions!"));
			return;
		}
		if (args.length == 1) {
			if (sender instanceof ProxiedPlayer) {
				UserConfig config = UserConfig.getPlayer((ProxiedPlayer) sender);
				if (args[0].equalsIgnoreCase("off")) {
					config.setCustomname(null);
					sender.sendMessage(new TextComponent("Your nick was reseted"));
				} else {
					config.setCustomname(args[0]);
					sender.sendMessage(new TextComponent("Your nick was changed to " + args[0].replaceAll("&", "§")));
				}
			} else {
				sender.sendMessage(new TextComponent("Cannot be used from console!"));
			}
		} else if (args.length == 2) {
			if (!sender.hasPermission("essentials.nick.others")) {
				sender.sendMessage(new TextComponent("You haven't permissions!"));
			} else {
				UserConfig config = UserConfig.getPlayer(args[0]);
				if (config != null) {
					if (args[1].equalsIgnoreCase("off")) {
						config.setCustomname(null);
						sender.sendMessage(new TextComponent("Custom nick of " + args[0] + " was reseted"));
					} else {
						config.setCustomname(args[1]);
						sender.sendMessage(new TextComponent("Custom nick of " + args[0] + " was changed to " + args[1].replaceAll("&", "§")));
					}
				} else {
					sender.sendMessage(new TextComponent("Player is offline or not exists!"));
				}
			}
		} else {
			sender.sendMessage(new TextComponent("Wrong usage! Use /nick <newnick> or /nick <player> <nick>"));
		}
	}

}

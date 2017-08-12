package misat11.essentials.bungee.commands;

import java.util.List;
import java.util.Map;

import misat11.essentials.bungee.UserConfig;
import misat11.essentials.bungee.utils.Language;
import misat11.essentials.bungee.utils.Placeholder;
import misat11.essentials.bungee.utils.Placeholders;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer; 
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MailCommand extends Command {

	public MailCommand() {
		super("mail", null, "email");
	}

	@Override
	public void execute(final CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			if (sender.hasPermission("essentials.mail")) {
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("read")) {
						List mails = UserConfig.getPlayer((ProxiedPlayer) sender).getMails();
						if (mails == null) {
							sender.sendMessage(Placeholders.replace(Language.translate("mail.nomails")));
							return;
						}
						sender.sendMessage(Placeholders.replace(Language.translate("mail.header")));
						sender.sendMessage(Placeholders.replace(Language.translate("mail.clearinfo")));
						for (Object obj : mails) {
							if (obj instanceof Map) {
								Map t = (Map) obj;
								sender.sendMessage(Placeholders.replace(Language.translate("mail.email"),
										new Placeholder("player",
												UserConfig.getPlayer(t.get("sender").toString()).getCustomname()),
										new Placeholder("message", t.get("message").toString())));
							}
						}
						sender.sendMessage(Placeholders.replace(Language.translate("mail.footer")));
						return;
					}
					if (args[0].equalsIgnoreCase("clear")) {
						UserConfig.getPlayer((ProxiedPlayer) sender).clearMails();
						sender.sendMessage(Placeholders.replace(Language.translate("mail.cleared")));
						return;
					}
				}
				if (args.length >= 3) {
					if (args[0].equalsIgnoreCase("send")) {
						if (!sender.hasPermission("essentials.mail.send")) {
							sender.sendMessage(Placeholders.replace(Language.translate("nopermissions")));
							return;
						}
						if (UserConfig.getPlayer(args[1]) != null) {
							String message = String.join(" ", args).replace("send " + args[1] + " ", "");
							UserConfig.getPlayer(args[1]).sendMail((ProxiedPlayer) sender, message);
							ProxiedPlayer receiver = ProxyServer.getInstance().getPlayer(args[1]);
							if (receiver != null) {
								receiver.sendMessage(Placeholders.replace(Language.translate("mail.new")));
							}
							sender.sendMessage(
									Placeholders.replace(Language.translate("mail.sendedto"), new Placeholder("player",
											UserConfig.getPlayer(args[1]).getCustomname())));
						} else {
							sender.sendMessage(Placeholders.replace(Language.translate("mail.never_connected"),
									new Placeholder("player", args[1])));
						}
						return;
					}
				}

				sender.sendMessage(Placeholders.replace(Language.translate("mail.usage")));
			} else {
				sender.sendMessage(Placeholders.replace(Language.translate("nopermissions")));
			}
		} else {
			sender.sendMessage(Placeholders.replace(Language.translate("consoleuse")));
		}

	}

}

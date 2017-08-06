package misat11.essentials.bungee.commands;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import misat11.essentials.bungee.UserConfig;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
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
						List mails = UserConfig.getPlayer((ProxiedPlayer)sender).getMails();
						if(mails == null){
							sender.sendMessage(new TextComponent("You haven't any mail."));
							return;
						}
						sender.sendMessage(new TextComponent("---------- [Mails] ----------"));
						sender.sendMessage(new TextComponent("Use /mail clear for clear your mails"));
						mails.forEach(new Consumer<HashMap>() {  
							public void accept(HashMap t) { 
								sender.sendMessage(new TextComponent("["+t.get("sender")+"] "+t.get("message").toString()
								.replaceAll("&", "§")));
							} 
						});
						sender.sendMessage(new TextComponent("-----------------------------"));
						return;
					}
					if (args[0].equalsIgnoreCase("clear")) {
						UserConfig.getPlayer((ProxiedPlayer) sender).clearMails();
						sender.sendMessage(new TextComponent("Your mails were cleared!"));
						return;
					}
				}
				if (args.length >= 3) {
					if (args[0].equalsIgnoreCase("send")) {
						if (!sender.hasPermission("essentials.mail.send")) {
							sender.sendMessage(new TextComponent("You haven't permissions to do this."));
							return;
						}
						if (UserConfig.getPlayer(args[1]) != null) {
							String message = String.join(" ", args).replace("send " + args[1] + " ", "");
							UserConfig.getPlayer(args[1]).sendMail((ProxiedPlayer) sender, message);
							ProxiedPlayer receiver = ProxyServer.getInstance().getPlayer(args[1]);
							if (receiver != null) {
								receiver.sendMessage(
										new TextComponent("You have new mail. Please read it by /mail read!"));
							}
							sender.sendMessage(new TextComponent("Your email was sended to player " + args[1]));
						} else {
							sender.sendMessage(
									new TextComponent("Player " + args[1] + " is never connected to this server."));
						}
						return;
					}
				}

				sender.sendMessage(new TextComponent("Wrong usage! Use /mail [read|clear|send [to] [message]]"));
			} else {
				sender.sendMessage(new TextComponent("You haven't permissions!"));
			}
		} else {
			sender.sendMessage(new TextComponent("Cannot be used from console!"));
		}

	}

}

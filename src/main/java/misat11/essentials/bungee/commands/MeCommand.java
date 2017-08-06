package misat11.essentials.bungee.commands;

import misat11.essentials.bungee.BungeeEssentials;
import misat11.essentials.bungee.UserConfig;
import misat11.essentials.bungee.utils.BungeePermsData;
import misat11.essentials.bungee.utils.LuckPermsData;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
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
					BaseComponent[] message = TextComponent.fromLegacyText(BungeeEssentials.getConfig()
							.getString("me-msg").replace("%chat%", String.join(" ", args)).replace("%sender_name%", sender.getName())
							.replace("%displayname%", ((ProxiedPlayer) sender).getDisplayName())
							.replace("%server%", ((ProxiedPlayer) sender).getServer().getInfo().getName())
							.replace("%server_motd%", ((ProxiedPlayer) sender).getServer().getInfo().getMotd())
							.replace("%customname%",
									UserConfig.getPlayer((ProxiedPlayer) sender).getCustomname())
							.replace("%BungeePerms_prefix%", BungeePermsData.getPrefix((ProxiedPlayer) sender))
							.replace("%BungeePerms_suffix%", BungeePermsData.getSuffix((ProxiedPlayer) sender))
							.replace("%BungeePerms_group%", BungeePermsData.getGroup((ProxiedPlayer) sender))
							.replace("%LuckPerms_prefix%", LuckPermsData.getPrefix((ProxiedPlayer) sender))
							.replace("%LuckPerms_suffix%", LuckPermsData.getSuffix((ProxiedPlayer) sender)) 
							.replace("%LuckPerms_group%", LuckPermsData.getPrimaryGroup((ProxiedPlayer) sender)) 
							.replaceAll("&", "§"));
					ProxyServer.getInstance().broadcast(message);
				} else {
					sender.sendMessage(new TextComponent("Wrong usage! Use /me <message>"));
				}
			} else {
				sender.sendMessage(new TextComponent("You haven't permissions!"));
			}
		} else {
			sender.sendMessage(new TextComponent("Cannot be used from console!"));
		}

	}

}

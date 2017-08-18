package misat11.essentials.bungee.commands;

import misat11.essentials.bungee.UserConfig;
import misat11.essentials.bungee.utils.Language;
import misat11.essentials.bungee.utils.Placeholder;
import misat11.essentials.bungee.utils.Placeholders;
import misat11.essentials.utils.DateUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MuteCommand extends Command {

	public MuteCommand() {
		super("mute");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			if (!sender.hasPermission("essentials.mute")) {
				sender.sendMessage(Placeholders.replace(Language.translate("nopermissions")));
				return;
			}

			if (args.length >= 1) {

				if (ProxyServer.getInstance().getPlayer(args[0]) != null) {
					UserConfig user = UserConfig.getPlayer(args[0]);

					if (user.isAuthorized("essentials.mute.exempt")) {
						sender.sendMessage(Placeholders.replace(Language.translate("mute.muteExempt")));
						return;
					}

					long muteTimestamp = 0;

					if (args.length > 1) {
						try {
							final String time = getFinalArg(args, 1);
							muteTimestamp = DateUtil.parseDateDiff(time, true);
							user.setMuted(true);
						} catch (Exception e) {
							user.setMuted(!user.getMuted());
						}
					} else {
						user.setMuted(!user.getMuted());
					}
					user.setMuteTimeout(muteTimestamp);
					boolean muted = user.getMuted();
					String muteTime = DateUtil.formatDateDiff(muteTimestamp);

					if (muted) {
						if (muteTimestamp > 0) {
							sender.sendMessage(Placeholders.replace(Language.translate("mute.mutedFor"),
									new Placeholder("player", user.getCustomname()),
									new Placeholder("time", muteTime)));
							user.sendMessage(Placeholders.replace(Language.translate("mute.youMutedFor"),
									new Placeholder("time", muteTime)));
						} else {
							sender.sendMessage(Placeholders.replace(Language.translate("mute.muted"),
									new Placeholder("player", user.getCustomname())));
							user.sendMessage(Placeholders.replace(Language.translate("mute.youMuted")));
						}
					} else {
						sender.sendMessage(Placeholders.replace(Language.translate("mute.unmuted"),
								new Placeholder("player", user.getCustomname())));
						user.sendMessage(Placeholders.replace(Language.translate("mute.youUnmuted")));
					}
				} else {
					sender.sendMessage(Placeholders.replace(Language.translate("mute.notonline"), new Placeholder("player", args[0])));
				}

			} else {

				sender.sendMessage(Placeholders.replace(Language.translate("mute.usage")));
			}
		} else {
			sender.sendMessage(Placeholders.replace(Language.translate("consoleuse")));
		}

	}

	public static String getFinalArg(final String[] args, final int start) {
		final StringBuilder bldr = new StringBuilder();
		for (int i = start; i < args.length; i++) {
			if (i != start) {
				bldr.append(" ");
			}
			bldr.append(args[i]);
		}
		return bldr.toString();
	}

}

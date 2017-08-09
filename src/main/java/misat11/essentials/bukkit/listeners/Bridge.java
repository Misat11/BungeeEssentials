package misat11.essentials.bukkit.listeners;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import misat11.essentials.Constants;
import misat11.essentials.bukkit.BukkitPlugin;

public class Bridge implements PluginMessageListener {

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
		if (!channel.equals(Constants.channel_name)) {
			return;
		}
		ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
		String subchannel = in.readUTF();
		if (subchannel.equals("CustomName")) {
			String nick = in.readUTF();
			if (!nick.equalsIgnoreCase(player.getName())) {
				player.setDisplayName("~" + nick); 
				if (BukkitPlugin.isOriginalEssentialsAvailable()) {
					BukkitPlugin.getEssentials().getOfflineUser(player.getName()).setNickname(nick.replaceAll("&", "§"));
				} 
			}
		}

	}

}

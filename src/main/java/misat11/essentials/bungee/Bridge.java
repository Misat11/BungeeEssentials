package misat11.essentials.bungee;

import java.util.logging.Level;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import misat11.essentials.Constants;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Bridge { 
	public static void sendToBukkit(ProxiedPlayer player, String actionName, String args){ 
        try {
        	  ByteArrayDataOutput out = ByteStreams.newDataOutput();
        	  out.writeUTF(actionName);
        	  out.writeUTF(args); 
            player.getServer().sendData(Constants.channel_name, out.toByteArray());
        } catch (Throwable th){
            BungeeEssentials.getInstance().getLogger().log(Level.SEVERE, "Failed to execute task for player " + player.getName());
        }
	}
	
	public static void sendCustomName(ProxiedPlayer player){
		sendToBukkit(player, "CustomName", UserConfig.getPlayer(player).getCustomname(false));
	}
}

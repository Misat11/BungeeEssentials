package misat11.essentials.api; 

import java.util.List;

import misat11.essentials.bungee.utils.Placeholder;
import net.md_5.bungee.api.plugin.Plugin;

public abstract class APlayerPlaceholderProcessor {
	
	private Plugin plugin;
	
	private List<IPlaceholder> list;
	private String prefix;
	
	public APlayerPlaceholderProcessor(Plugin plugin){
		this.plugin = plugin;
	}
	
	public void process(List<IPlaceholder> list, String playername, String prefix){
		this.list = list;
		this.prefix = prefix;
		run(playername);
		this.list = null;
		this.prefix = null;
	}
	
	public abstract void run(String playername);
	
	protected void newplaceholder(String variable, String replace){
		list.add(new Placeholder(prefix+plugin.getDescription().getName()+"_"+variable, replace));
	}
	
	protected void newplaceholder(IPlaceholder placeholder){
		list.add(placeholder);
	}
}

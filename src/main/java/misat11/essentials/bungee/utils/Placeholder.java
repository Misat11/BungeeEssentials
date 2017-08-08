package misat11.essentials.bungee.utils;

import misat11.essentials.api.IPlaceholder;

public class Placeholder implements IPlaceholder {
	
	private String base;
	private String replace; 
	
	public Placeholder(String base, String replace){
		this.base = base;
		this.replace = replace; 
	}
	
	public String baseString(){
		return base;
	}
	public String replace(){
		return replace;
	}
	 
}

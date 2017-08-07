package misat11.essentials.bungee.utils;

public class Placeholder {
	
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

package src;

import java.util.HashMap;

public class NameDecorator {
	private HashMap<String, String> types = new HashMap<String, String>();
	private String type;
	
	public NameDecorator(String type){
		this.type = type;
		this.types.put("", "");
		this.types.put("singleton", "\\n\\<\\<Singleton\\>\\>");
	}
	
	public String getType(){
		return this.types.get(this.type);
	}
}

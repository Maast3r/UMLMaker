package src;

import java.util.HashMap;

public class NameDecorator extends TypeDetector{
	private HashMap<String, String> types = new HashMap<String, String>();
	
	public NameDecorator(String type, boolean[] flags){
		super(flags);
		this.types.put("", "");
		this.types.put("singleton", "\\n\\<\\<Singleton\\>\\>");
	}
	
	public NameDecorator(boolean[] flags){
		super(flags);
		this.types.put("", "");
		this.types.put("singleton", "\\n\\<\\<Singleton\\>\\>");
	}
	
	public String getType(){
		return this.types.get(super.getType());
	}
}

package src;

import java.util.HashMap;

public class NameDecorator extends TypeDetector{
	private HashMap<String, String> types = new HashMap<String, String>();
	private String type;
	private TypeDetector typeDec;
	
	public NameDecorator(String type, boolean[] flags){
		super(flags);
		this.type = type;
		this.types.put("", "");
		this.types.put("singleton", "\\n\\<\\<Singleton\\>\\>");
	}
	
	public NameDecorator(TypeDetector t, boolean[] flags){
		super(flags);
		this.typeDec = t;
		this.types.put("", "");
		this.types.put("singleton", "\\n\\<\\<Singleton\\>\\>");
	}
	
	public String getType(){
		return this.types.get(this.typeDec.getType());
	}
}

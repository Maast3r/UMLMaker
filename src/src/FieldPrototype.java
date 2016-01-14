package src;

public class FieldPrototype {
	public String symbol;
	public String name;
	public String type;
	
	public FieldPrototype(String symbol, String name, String type) {
		this.symbol = symbol;
		this.name = name;
		this.type = type;
	}
	
	public String prepareUML(){
		return symbol + name + ": "+ type + " \\l";
	}

}
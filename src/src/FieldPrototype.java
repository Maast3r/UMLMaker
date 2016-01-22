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
//		System.out.println("FIELD PREP : " + this.symbol + this.name + ": " + this.type + " \\l");
		return this.symbol + this.name + ": "+ this.type + " \\l";
	}
	
	public boolean getIsStaticAndSame(String whateverYouWant){
		return this.symbol.contains("static") && this.type.equals(whateverYouWant);
	}

}
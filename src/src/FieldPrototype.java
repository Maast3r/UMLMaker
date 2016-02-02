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
		return this.symbol + this.name + ": "+ this.type + " \\l";
	}
	
	public boolean getSingleton(String whateverYouWant, String superName){
		return this.symbol.contains("static") && this.type.equals(whateverYouWant);
	}
	
	public boolean checkDecorator(String superName){
		return this.type.equals(superName);
	}
	
	public boolean checkAdapter(){
		return true;
	}

}
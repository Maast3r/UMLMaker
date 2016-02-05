package src;

public class FieldPrototype {
	public String symbol;
	public String name;
	public String type;
	public String sig;
	public String listType;
	
	public FieldPrototype(String symbol, String name, String type, String sig, String listType) {
		this.symbol = symbol;
		this.name = name;
		this.type = type;
		this.sig = sig;
		this.listType = listType;
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
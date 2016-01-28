package src;

public class DecoratorDetector extends AbstractDetector{
	public NoahsArk ark;
	
	public DecoratorDetector(NoahsArk ark){
		super(ark);
		this.ark = ark;
	}

	@Override
	public String getType(String cName) {
		if(this.ark.getListOfClass().containsKey(cName)){
			ClassPrototype c = this.ark.getBoat().get(cName);
			if(!c.type.equals("")) return c.type;
		}
		return "";
	}
}

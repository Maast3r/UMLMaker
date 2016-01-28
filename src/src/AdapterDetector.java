package src;

public class AdapterDetector extends AbstractDetector{
	public NoahsArk ark;
	
	public AdapterDetector(NoahsArk ark){
		super(ark);
		this.ark = ark;
	}
	
	@Override
	public String getType(String cName) {
		if(this.ark.getListOfClass().containsKey(cName)){
			ClassPrototype c = this.ark.getBoat().get(cName);
			return c.type;
		}
		return "";
	}
}

package src;

public abstract class AbstractDetector {
	public NoahsArk ark;
	
	public AbstractDetector(NoahsArk ark){
		this.ark = ark;
	}
	
	public abstract String getType(String cName);
}

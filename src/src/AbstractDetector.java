package src;

import java.util.HashSet;

public abstract class AbstractDetector {
	public NoahsArk ark;
	
	public AbstractDetector(NoahsArk ark){
		this.ark = ark;
	}
	
	public abstract HashSet<String> getType(String cName);
}

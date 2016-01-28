package src;

import java.util.ArrayList;

public class TypeDetector {
	public String cName;
	public NoahsArk ark;
	public ArrayList<AbstractDetector> detectors = new ArrayList<AbstractDetector>();
	
	public TypeDetector(String cName, NoahsArk ark){
		this.cName = cName;
		this.ark = ark;
		this.detectors.add(new SingletonDetector(this.ark));
		this.detectors.add(new DecoratorDetector(this.ark));
		this.detectors.add(new AdapterDetector(this.ark));
	}
	
	public String getType(){
		for(AbstractDetector ad : this.detectors){
			if(!ad.getType(this.cName).equals("")) {
				return ad.getType(this.cName);
			}
		}
		return "";
	}
}

package src;

import java.util.ArrayList;
import java.util.HashSet;

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
	
	public HashSet<String> getType(){
		HashSet<String> result = new HashSet<String>();
		for(AbstractDetector ad : this.detectors){
//			if(!ad.getType(this.cName).equals("")) {
				result.addAll(ad.getType(this.cName));
//			}
		}
//		System.out.println(result);
		return result;
	}
}

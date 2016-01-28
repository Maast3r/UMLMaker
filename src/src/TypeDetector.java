package src;

import java.util.Arrays;

public class TypeDetector {
	public boolean[] flags;
	
	public TypeDetector(boolean[] flags){
		this.flags = flags;
	}
	
	public String getType(){
		if(flags[0] && flags[1]) return "singleton";
		if(flags[2]) return "decorator";
		return "a";
	}
}

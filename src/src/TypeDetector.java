package src;

public class TypeDetector {
	private boolean[] flags;
	
	public TypeDetector(boolean[] flags){
		this.flags = flags;
	}
	
	public String getType(){
		if(flags[0] == true && flags[1] == true) return "singleton";
		return "";
	}
}

package lab51;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

public class ArraylistAdapter {
	private ArrayList<String> in;
	
	public ArraylistAdapter(ArrayList<String> in){
		this.in = in;
	}
	
	public Enumeration<String> vectorToArrayList(){
		Vector<String> temp = new Vector<String>();
		for(String s : this.in){
			temp.addElement(s);
		}
		return temp.elements();
	}
}

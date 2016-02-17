package pls;

import java.util.ArrayList;

public class Herp {
	public String q = "fives";
	public ArrayList<Integer> test = new ArrayList<Integer>();
	
	
	public ArrayList<Integer> get(){
		for(int i = 0; i<5; i++){
			this.test.add(i);
		}
		this.test = new ArrayList<Integer>();
		return this.test;
	}
}

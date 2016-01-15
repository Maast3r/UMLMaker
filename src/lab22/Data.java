package lab22;

public class Data {
	private int mask;
	public String test;
	private char[] content;

	public Data(int mask, char[] content) {
		this.mask = mask;
		this.content = content;
	}
	
	public int getMask() {
		this.test = "test";
		DumbClass dumb = new DumbClass(this.test);
		DumbClass dum2 = new DumbClass(this.test);
		this.test = dumb.getTest();
		this.test = dum2.getTest();
		return mask;
	}
	
	public char[] getContent() {
		return content;
	}
}

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
		return mask;
	}
	
	public char[] getContent() {
		return content;
	}
}

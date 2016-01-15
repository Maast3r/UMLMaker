package lab22;

public class Data {
	private int mask;
	private char[] content;

	public Data(int mask, char[] content) {
		this.mask = mask;
		this.content = content;
	}
	
	public int getMask() {
		return mask;
	}
	
	public char[] getContent() {
		return content;
	}
}

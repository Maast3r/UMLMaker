package lab22;

public class AviEncoder implements IReceiver {
	public static final char MASK = 0x11;
	public static final int THRESHOLD = 2048;
	private StringBuffer buffer;
	
	public AviEncoder() {
		buffer = new StringBuffer();
	}

	public void received(Object source, Data d) {
		char[] content = d.getContent();
		
		for(int i = 0; i < content.length; ++i) {
			char first = (char)(content[i] << MASK);
			char second = (char)(content[i] >> MASK);
			content[i] = (char)(first ^ second);
		}
		
		buffer.append(content);
		
		if(DataLine.TOTAL_BYTES > THRESHOLD) {
			DataLine s = (DataLine)source;
			Data transformed = new Data(MASK, content);
			for(IReceiver r : s.getReceivers()) {
				r.received(this, transformed);
			}
		}
	}
}

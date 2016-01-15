package lab22;

public class MpegEncoder implements IReceiver {
	public static final char MASK = 0x01;
	public static final int THRESHOLD = 1024;
	private StringBuffer buffer;
	
	public MpegEncoder() {
		buffer = new StringBuffer();
	}
	
	public void test(){
		String test = "test";
	}
	
	public String test2(){
		return "java sucks";
	}

	public void received(Object source, Data d) {
		char[] content = d.getContent();
		Util.transform(MASK, content);
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

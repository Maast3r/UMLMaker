package lab22;

import java.util.ArrayList;
import java.util.List;

public class DataLine {
	public static int TOTAL_BYTES = 0;
	
	private String id;
	private StringBuffer buffer;
	private List<IReceiver> receivers;
		
	public DataLine(String id) {
		this.id = new String(id);
		this.buffer = new StringBuffer();
		receivers = new ArrayList<IReceiver>();
	}
	
	public String getId() {
		return id;
	}
	
	public int take(char[] buffer) {
		this.buffer.append(buffer);
		Data d = new Data(0, buffer);
		int i = d.getMask();
		for(IReceiver r : receivers) {
			r.received(this, d);
		}
		TOTAL_BYTES += buffer.length;
		return buffer.length;
	}
	
	public List<IReceiver> getReceivers() {
		return this.receivers;
	}
}

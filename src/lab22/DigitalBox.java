package lab22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DigitalBox implements Runnable {
	public static final int THRESHOLD = AviEncoder.THRESHOLD + MpegEncoder.THRESHOLD;
	private int serialNumber;
	DataLine line;
	
	public DigitalBox(int serialNumber) {
		this.serialNumber = serialNumber;
		line = new DataLine("" + this.serialNumber);
		AviEncoder aviEncoder = new AviEncoder();
		MpegEncoder mpegEncoder = new MpegEncoder();
		line.getReceivers().add(aviEncoder);
		line.getReceivers().add(mpegEncoder);
	}
	
	public void run() {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(System.in));
			char[] buffer = new char[THRESHOLD];
			while ((in.read(buffer)) != -1) {
				line.take(buffer);
				buffer = new char[THRESHOLD];
			}
		}
		catch (IOException e) {
			Util.processDataException(in, "IOException reading raw data. ", e);
		}
	}
}
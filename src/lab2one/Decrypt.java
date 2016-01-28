package lab2one;

import java.io.IOException;
import java.io.InputStream;

public class Decrypt extends InputStream {
	public InputStream in;
	private int count = 0;
	
	public Decrypt(InputStream in){
		this.in = in;
	}

	public int read() throws IOException {
		// TODO Auto-generated method stub
		SubstitutionCipher s = new SubstitutionCipher();
		int read = in.read();
		if(read == -1){
			return -1;
		} else {
			this.count++;
			return (int) s.decrypt((char)read);
		}
	}
}

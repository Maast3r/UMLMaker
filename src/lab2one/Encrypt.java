package lab2one;

import java.io.IOException;

public class Encrypt extends OutputStream {
	public OutputStream out;
	
	public Encrypt(OutputStream out){
		this.out = out;
	}

	@Override
	public void write(int arg0) throws IOException {
		// TODO Auto-generated method stub
		SubstitutionCipher s = new SubstitutionCipher();
		out.write(s.encrypt((char) arg0));
	}

}

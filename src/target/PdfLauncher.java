package target;

import java.io.IOException;

public class PdfLauncher implements FileLauncher, Observer{

	@Override
	public void launch(String filename, AppLauncher app) {
		// TODO Auto-generated method stub
		String command = "explorer";
		String arg = filename;
		
		ProcessBuilder processBuilder = new ProcessBuilder(command, arg);
		
		// Start and add the process to the processes list
		try {
			Process process = processBuilder.start();
			app.getProcesses().add(process);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(String filename) {
		// TODO Auto-generated method stub
//		String reverse = "";
//		for(int i=filename.length()-1; i>=0; i--){
//			reverse += filename.charAt(i);
//		}
//		System.out.println(reverse);
	}
	

}

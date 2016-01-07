package target;

import java.io.IOException;

public class HtmlBehavior implements FileBehavior, Observer{

	@Override
	public void handleDirectoryEvent(String filename) {
		// TODO Auto-generated method stub
		// Run the application if support is available
		ProcessBuilder processBuilder = null;
		String command = "explorer";
		String arg = filename;
		try {
			System.out.format("Launching %s ...%n", command);
			processBuilder = new ProcessBuilder(command, arg);
			
			// Start and add the process to the processes list
			Process process = processBuilder.start();
			//this.processes.add(process);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}

}

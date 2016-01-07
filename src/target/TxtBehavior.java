package target;

import java.io.IOException;

public class TxtBehavior implements FileBehavior, Observer{
	private String extension;
	@Override
	public void handleDirectoryEvent(String filename) {
		// TODO Auto-generated method stub
		// Run the application if support is available
		ProcessBuilder processBuilder = null;
		String command = "notepad";
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
		String reversed = (new StringBuilder(filename)).reverse().toString();
		System.out.println("Reversed filename: " + reversed);
		
	}
	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}

}

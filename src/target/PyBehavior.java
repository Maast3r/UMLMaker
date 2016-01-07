package target;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PyBehavior implements FileBehavior, Observer {

	@Override
	public void handleDirectoryEvent(String filename) {
		// TODO Auto-generated method stub
		// Run the application if support is available
		ProcessBuilder processBuilder = null;
		String command = "python";
		String arg = filename;
		try {
			System.out.format("Launching python file %s ...%n", command);
			processBuilder = new ProcessBuilder(command, arg);

			// Start and add the process to the processes list
			Process process = processBuilder.start();
			// this.processes.add(process);
		} catch (IOException e) {
			e.printStackTrace();
		}
		File f = new File(filename);
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);

			// Construct BufferedReader from InputStreamReader
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}

			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub

	}

}

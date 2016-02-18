package src;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class ConfigurationManager {
	private static ConfigurationManager singleton;
	public HashMap<String, Boolean> configuration;
	Properties defaultProps;
	
	public static ConfigurationManager getInstance( ) {
	   if(singleton == null){
		   singleton = new ConfigurationManager();
	   }
      return singleton;
   }
	
	public HashMap<String, Boolean> getCurrentConfiguration(){
		return configuration;
		
	}
	
	public void setDefaultConfiguration(String filepath) throws IOException{
		System.out.println(filepath);
		defaultProps = new Properties();
		FileInputStream in = new FileInputStream(filepath);
		defaultProps.load(in);
		in.close();
	}
}

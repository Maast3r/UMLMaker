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
	
	public static ConfigurationManager getInstance() {
	   if(singleton == null){
		   singleton = new ConfigurationManager();
		   singleton.configuration = new HashMap<String, Boolean>();
	   }
      return singleton;
   }
	
	public HashMap<String, Boolean> getDrawConfiguration(){
		return configuration;
	}
	
	public boolean setDefaultConfiguration(String filepath) throws IOException{
		System.out.println(filepath);
		defaultProps = new Properties();
		FileInputStream in = new FileInputStream(filepath);
		defaultProps.load(in);
		in.close();
		return true;
	}
	
	public void setDrawConfiguration(HashMap<String,Boolean> toDraw){
		configuration = toDraw;
	}
	
	public void initDrawConfiguration(HashMap<String, String> seenClasses){
		for(String s: seenClasses.keySet()){
			configuration.put(s, true);
		}
	}
	
	
}

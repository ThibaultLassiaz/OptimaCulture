package optimaculture.backend.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import optimaculture.backend.services.MqttImportService;

public class PropertiesService {
	
	private static Properties prop;

	private static PropertiesService instance = null;
	
	private static Logger logger = Logger.getLogger(PropertiesService.class);
	
	private PropertiesService() {
		prop = new Properties();
		try {
			instantiatePropertyFiles();
		} catch (IOException e) {
			logger.warn("Une erreur c'est produite lors de l'initilisation des propriétés");
			e.printStackTrace();
		}
	}
	
	public static PropertiesService getInstance() {
		if(instance == null) {
			instance =  new PropertiesService();
		}
		return instance;
	}
	
	private void instantiatePropertyFiles() throws IOException {
		String propFileName = "config.properties";
		
		InputStream inputStream = new FileInputStream("config.properties");
		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
	}
	
	public String getProperty(String propName) {
		return prop.getProperty(propName);
	}
}

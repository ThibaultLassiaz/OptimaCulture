package optimaculture.backend.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * Service d'accès aux propriétés java
 *
 */
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
		InputStream inputStream = new FileInputStream("config.properties");
		prop.load(inputStream);
	}
	
	public String getProperty(String propName) {
		return prop.getProperty(propName);
	}
}

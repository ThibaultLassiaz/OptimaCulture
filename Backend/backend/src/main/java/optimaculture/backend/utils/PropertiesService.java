package optimaculture.backend.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.eclipse.paho.client.mqttv3.MqttClient;

import optimaculture.backend.services.MqttImportService;

public class PropertiesService {
	
	private static Properties prop;

	private static PropertiesService instance = null;
	
	private PropertiesService() {
		prop = new Properties();
		try {
			instantiatePropertyFiles();
		} catch (IOException e) {
			System.out.println("Une erreur c'est produite lors de l'initilisation des propriétés");
			e.printStackTrace();
		}
	}
	
	public static PropertiesService getInstance() {
		if(instance == null) {
			instance =  new PropertiesService();
		}
		return instance;
	}
	
	private static void instantiatePropertyFiles() throws IOException {
		String propFileName = "config.properties";
		
		InputStream inputStream = new FileInputStream(new File("C:\\IOT\\iot\\Backend\\backend\\resources\\config\\config.properties"));
		if (inputStream != null) {
			prop.load(inputStream);
			final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
		} else {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
	}
	
	public static String getProperty(String propName) {
		//TODO : faire le vrai raitement
		//return prop.getProperty(propName);
		return "localhost";
	}
}

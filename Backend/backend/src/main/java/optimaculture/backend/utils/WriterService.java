package optimaculture.backend.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriterService {

	/**
	 * Ecrit dans un fichier temporaire les données json
	 * Une entête et un saut de ligne indispensables pour elatic
	 * @param json
	 * @throws IOException
	 */
	public static void writeData(String json, String type) throws IOException {
		File temp = File.createTempFile( PropertiesService.getInstance().getProperty("outputFileName"), ".json");
		BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
		bw.write(getJsonHeader(type));
		bw.write(String.format("%n"));
		bw.write(json);
		bw.write(String.format("%n"));
        bw.close();
 	}
	
	/**
	 * Choisi le header en fonction du type
	 * @param type
	 * @return
	 */
	private static String getJsonHeader(String type) {
		String header = "";
		switch (type) {
			case "LUM": header = PropertiesService.getInstance().getProperty("jsonHeaderLum");			
				break;
			case "PRES": header = PropertiesService.getInstance().getProperty("jsonHeaderPres");			
				break;
			case "HUM": header = PropertiesService.getInstance().getProperty("jsonHeaderHum");			
				break;
			case "COLOR": header = PropertiesService.getInstance().getProperty("jsonHeaderColor");			
				break;
			default:
				break;
		}
		System.out.println("head :"+header);
		return header;
	}
}

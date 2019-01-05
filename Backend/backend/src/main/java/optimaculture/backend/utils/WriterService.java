package optimaculture.backend.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriterService {

	/**
	 * ecrit dans un fichier temporaire les données json avec une entête et un saut de ligne indispensables pour elatic
	 * @param json
	 * @throws IOException
	 */
	public static void writeData(String json) throws IOException {
		File temp = File.createTempFile( PropertiesService.getInstance().getProperty("outputFileName"), ".json");
		BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
		bw.write(PropertiesService.getInstance().getProperty("jsonHeader"));
		bw.write(String.format("%n"));
		bw.write(json);
		bw.write(String.format("%n"));
        bw.close();
 	}
}

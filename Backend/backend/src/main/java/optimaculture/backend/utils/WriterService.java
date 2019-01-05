package optimaculture.backend.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriterService {

	public static void writeData(String json) throws IOException {
		File temp = File.createTempFile( PropertiesService.getInstance().getProperty("outputFileName"), ".json");
		BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
		bw.write(json);
        bw.close();
 	}
}

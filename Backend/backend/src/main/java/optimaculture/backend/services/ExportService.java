package optimaculture.backend.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import optimaculture.backend.utils.PropertiesService;

public class ExportService extends TimerTask {
	
	private static ExportService instance = null;
	
	public ExportService() {
		
	}
	
	public static ExportService getInstance() {
		if(instance == null) {
			instance = new ExportService();
		}
		return instance;
	}

	@Override
	public void run() {
		List<File> fileList = getFiles();
		if(fileList!= null && fileList.size() > 0) {
		pushToElastic(fileList);
			//TODO activer ça quand le code de push marche
			//removeFiles(fileList);
		}
	}
	
	/**
	 * 
	 * @return la liste des fichiers à push
	 */
	private List<File> getFiles() {
		File tempFiledirectory = new File(System.getProperty("java.io.tmpdir"));
		List<File> fileList = new ArrayList<File>();
		
		for (final File fileEntry : tempFiledirectory.listFiles()) {
	        if (!fileEntry.isDirectory() && fileEntry.getName().startsWith(PropertiesService.getInstance().getProperty("outputFileName"))) {
	            fileList.add(fileEntry);
	        }
	    }
		return null;
	}	
	
	private void pushToElastic(List<File> fileList) {
		//TODO faire le code curl ici
	}
	
	private void removeFiles(List<File> fileList) {
		for (final File fileEntry : fileList) {
			try {
				fileEntry.delete();
			}catch(Exception e) {}
	    }
	}
}

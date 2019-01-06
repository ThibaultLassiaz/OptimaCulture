package optimaculture.backend.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import optimaculture.backend.utils.PropertiesService;

/**
 * 
 * Service d'export des données vers Elastic
 *
 */
public class ExportService extends TimerTask {
	
	private static Logger logger = Logger.getLogger(ExportService.class);

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
			try {
				Thread.sleep((long)1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			removeFiles(fileList);
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
		return fileList;
	}	
	
	/**
	 * Lance une commande cURL pour pousser les fichiers 
	 * @param fileList
	 */
	private void pushToElastic(List<File> fileList) {
		for (final File fileEntry : fileList) {
			String[] curlCommand = buildCurlCommand(fileEntry);
			
			ProcessBuilder processBuilder = new ProcessBuilder(curlCommand); 
	        try
	        {
	        	processBuilder.start();
	        }
	        catch (IOException e)
	        {   
	        	logger.warn("Erreur lors de l'envoie de la requete cURL");
	            e.printStackTrace();
	        }
	    }
	}
	
	private void removeFiles(List<File> fileList) {
		for (final File fileEntry : fileList) {
			try {
				fileEntry.delete();
			}catch(Exception e) {}
	    }
	}
	
	/**
	 * 
	 * @param fileEntry
	 * @return la commande cURL permettant de pousser les fichiers vers ELastic
	 */
	private String[] buildCurlCommand(File fileEntry) {
		
		String username=PropertiesService.getInstance().getProperty("elasticUserName");
	    String password=PropertiesService.getInstance().getProperty("elasticPassword");
	    String file=fileEntry.getPath().replaceAll("\\\\", "/");
	    String server = PropertiesService.getInstance().getProperty("elasticServer");
	    String[] command = {"curl","-XPUT" ,"https://"+username+":"+password+"@"+server+"/_bulk","-H","\"Content-Type: application/json\"","--data-binary","@"+file};
	    
	    return command;
	}
}

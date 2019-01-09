package optimaculture.backend.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import optimaculture.backend.utils.PropertiesService;

/**
 * 
 * Modèle des données reçues depuis le broker mqtt
 *
 */
public class DataModel {
	
	private String id;
	private String type;
	private String date;
	private String value;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public DataModel(String data) throws Exception {
		super();
		String[] dataTab = data.split(PropertiesService.getInstance().getProperty("dataSeparator"));
		if(dataTab.length == 3) {
			this.id = dataTab[0];
			this.type = dataTab[1].toUpperCase();
			if(!isValidType(type)) {
				throw new Exception("Type de données invalide : "+type);
			}
			
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Date today = Calendar.getInstance().getTime();        
			this.date = df.format(today);
			
			this.value = dataTab[2];
		}else {
			throw new Exception("Données manquantes");
		}
	}
	
	private boolean isValidType(String type) {
		for(SensorType sensorType : SensorType.values()) {
			if(sensorType.name().equals(type)) {
				return true;
			}
		}		
		return false;
	}
}

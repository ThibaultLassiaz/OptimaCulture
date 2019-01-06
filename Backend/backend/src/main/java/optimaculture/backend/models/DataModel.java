package optimaculture.backend.models;

import java.util.Date;

/**
 * 
 * Modèle des données reçues depuis le broker mqtt
 *
 */
public class DataModel {
	
	private String id;
	private String type;
	private Date date;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public DataModel(String data) {
		super();
		//TODO utiliser les properties quand le service marche :)
		String[] dataTab = data.split(";");
		
		this.id = dataTab[0];
		this.type = dataTab[1];
		this.date = new Date();
		this.value = dataTab[2];
	}
}

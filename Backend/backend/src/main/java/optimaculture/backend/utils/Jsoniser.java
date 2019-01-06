package optimaculture.backend.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import optimaculture.backend.models.DataModel;

public class Jsoniser {

	/**
	 * 
	 * @param data
	 * @return un json créer à partir du modèle de données
	 */
	public static String jsonise(DataModel data) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		return gson.toJson(data);
	}
}

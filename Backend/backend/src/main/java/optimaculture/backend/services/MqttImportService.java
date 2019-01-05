package optimaculture.backend.services;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import optimaculture.backend.models.DataModel;
import optimaculture.backend.utils.Jsoniser;
import optimaculture.backend.utils.WriterService;

/**
 * 
 * Service de contact au broker mqtt
 *
 */
public class MqttImportService {
	
	private static Logger logger = Logger.getLogger(MqttImportService.class);
	
	private MqttClient client = null;

	private static MqttImportService instance = null;
	
	private String broker;
	
	private String topic;
	
	private MqttImportService() {
		//TODO utiliser le service de properties quand il marche
		broker = "tcp://localhost:1883";
		topic = "test";
		
		instatiateService();
		try {
			dataTreatment();
		} catch (MqttSecurityException e) {
			logger.warn("Une erreur de sécurité c'est produite : ");
			e.printStackTrace();
		} catch (MqttException e) {
			logger.warn("Une erreur c'est produite lors de l'accès au serveur Mqtt : ");
			e.printStackTrace();
		}
	}
	
	public static MqttImportService getInstance() {
		if(instance == null) {
			instance =  new MqttImportService();
		}
		return instance;
	}
	
	/**
	 * Instancie le client Mqtt
	 */
	private void instatiateService() {
		try {
			String publisherId = UUID.randomUUID().toString();
			MemoryPersistence persistence = new MemoryPersistence();
			client = new MqttClient(broker, publisherId.toString(),persistence);
		} 
		catch (MqttException e) {
			e.printStackTrace();
			logger.warn("Une erreur c'est produite lors de l'initilisation du client");
		}
	}	
	
	/**
	 * S'abonne et recoit les données du broker
	 * @throws MqttSecurityException
	 * @throws MqttException
	 */
	private void dataTreatment() throws MqttSecurityException, MqttException {
	    MqttConnectOptions connOpts = new MqttConnectOptions();
	    connOpts.setCleanSession(true);
	    
		client.setCallback(new MqttCallback() {
		      public void connectionLost(Throwable cause) {}

		      public void messageArrived(String topic, MqttMessage message) throws Exception {
		    	  logger.info("Message collecté : " + message.toString());
		    	  
		    	  //Créer un object java stockant la donnée
		    	  DataModel data = new DataModel(message.toString());

		    	  //Transforme l'ojet en json
		    	  String json = Jsoniser.jsonise(data);

		    	  //Enregistre le json dans un fichier
		    	  WriterService.writeData(json);
		      }
		      public void deliveryComplete(IMqttDeliveryToken token) {}
	    });
		
		try {
			logger.info("Connection au broker: " + broker);
			client.connect(connOpts);
			logger.info("Connecté");
		}catch (Exception e){
			logger.info("Reconnection au broker: " + broker);
			client.reconnect();
			logger.info("Connecté");
		}
	    client.subscribe(topic);
	}
}

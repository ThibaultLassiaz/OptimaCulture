package optimaculture.backend;

import optimaculture.backend.services.MqttImportService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	MqttImportService.getInstance();
    }
}

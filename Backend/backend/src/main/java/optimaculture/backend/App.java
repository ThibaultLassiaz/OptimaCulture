package optimaculture.backend;

import optimaculture.backend.services.MqttImportService;

public class App 
{
    public static void main( String[] args )
    {
    	MqttImportService.getInstance();
    }
}

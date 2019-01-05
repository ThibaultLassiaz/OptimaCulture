package optimaculture.backend;

import java.util.Timer;

import optimaculture.backend.services.ExportService;
import optimaculture.backend.services.MqttImportService;

public class App 
{
    public static void main( String[] args )
    {
        Timer timer = new Timer();
    	timer.scheduleAtFixedRate(ExportService.getInstance(), 0, 10000);
    	MqttImportService.getInstance();
    }
}

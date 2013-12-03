package honeyLIP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class HoneyLIPThreads{
	
	
	
	public void startModule(Module module){
		
		
		
		new Thread(module).start();
		
		
	}

	
}

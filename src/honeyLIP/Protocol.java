package honeyLIP;

import java.util.Vector;

public interface Protocol {

	
	String whoTalksFirst();
		
	Vector<String> processInput(String messageFromClient);
	
	int getPort();
	
    String toString();
    
    boolean isConnectionOver();
}

package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Job implements Runnable {
    private ServerSocket server ; 
    private ClientHandler ch;
    
    
    public Job(ServerSocket server,ClientHandler ch) {
		super();
		this.server = server ;
		this.ch = ch ; 
		}



	public int getJobPriority() {
		return 0;
	}

 

	@Override
    public void run() {
    
        try {
            // System.out.println("dsdsdsd");
			Socket aClient=server.accept(); 
			ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
		//	aClient.getInputStream().close();
		//	aClient.getOutputStream().close();
			aClient.close();
        	
		} catch (IOException e) {
			// TODO Auto-generated catch block
		 	e.printStackTrace();
		} // to simulate actual execution time
    }
 
    // standard setters and getters
}
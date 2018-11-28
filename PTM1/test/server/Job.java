package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.stream.Collectors;

public class Job implements Runnable {
    //private ServerSocket server ; 
    private ClientHandler ch;
    private Socket aClient; 
    
    
    public Job(Socket aClient,ClientHandler ch) {
		super();
		this.aClient = aClient ;
		this.ch = ch ; 
		}



	public int getJobPriority()  {
		try {
			String result = new BufferedReader(new InputStreamReader(aClient.getInputStream()))
					  .lines().collect(Collectors.joining("\n"));
			 return result.length();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 return 0;
	}

 

	@Override
    public void run() {
    
        try {
	 //		Socket aClient=server.accept(); 
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
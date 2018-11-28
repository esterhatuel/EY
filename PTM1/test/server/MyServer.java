
package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MyServer implements Server{
	
	///DONT PRINT ANYTHING IN THAT CLASS!!!!!!!!!!!!
	private final int port;
	private ClientHandler ch;
	private volatile boolean stop;
	
	public MyServer(int port) {
		this.port=port;
		stop=false;
	}
	
	private void runServer() throws IOException, InterruptedException{
		ServerSocket server=new ServerSocket(port);
		//ExecutorService executor = Executors. newFixedThreadPool (2);
		

			while(!stop){
				
					
					PriorityJobScheduler pjs = new PriorityJobScheduler(
						      2,2) ; 
					pjs.scheduleJob(new Job(server,ch));
                    
			
			}
		server.wait(1000 );
	}
			
	@Override
	public void start(ClientHandler clientHandler) {
		this.ch=clientHandler;
		new Thread(()->{
			try {
				runServer();
			} catch (Exception e) {
				
			}
		}).start(); 
	}

	@Override
	public void stop() {
	   stop=true;
	}

}
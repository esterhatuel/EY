package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MyServer implements Server{
	
	///DONT PRINT ANYTHING IN THAT CLASS!!!!!!!!!!!!
	private final int port;
	private ClientHandler ch;
	private int M ; 
	private volatile boolean stop;
	
	public MyServer(int port,int M) {
		super();
		this.port=port;
		stop=false;
		this.M = M ; 
	}
	
	private void runServer() throws IOException, InterruptedException{
	ServerSocket server=new ServerSocket(port);
		
			while(!stop){	
				Socket aClient =  server.accept();
				ExecutorService priorityJobScheduler = Executors.newSingleThreadExecutor();
	             priorityJobScheduler.execute(() ->{
					PriorityJobScheduler pjs = new PriorityJobScheduler(M,1000) ; 
					pjs.scheduleJob(new Job(aClient,ch));
	              });
            
			}
	Thread.sleep(port);
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
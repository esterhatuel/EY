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
	PriorityJobScheduler pjs = new PriorityJobScheduler(M,1000) ; 


			while(!stop){
				System.out.println("test");
				Socket aClient =  server.accept();
				ExecutorService priorityJobScheduler = Executors.newSingleThreadExecutor();
	             priorityJobScheduler.execute(() ->{
					pjs.scheduleJob(new Job(aClient,ch));
	              });
            
			}
	server.close();
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

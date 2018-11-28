package server;

public class Runner {
    // run your server here
    static Server s;
    public static void runServer(int port, int M){
        s=new MyServer(port,M);
        s.start(new MyClientHandler());
    }
    // stop your server here
    public static void stopServer(){
        s.stop();
    }
    public static void main(String[] args) {
        int port=6400;
        int M = 2; 
        runServer(port,M);
        //stopServer();
        System.out.println("done");
    }
}

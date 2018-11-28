package server;

import java.io.*;
import java.util.*;

import Searchable.*;
import algorithm.*;
import classes.*;
import interfaces.*;

public class MyClientHandler implements ClientHandler {
   CacheManager<Solution<PipeGameBoard>> cacheManager=new MyCacheManager<PipeGameBoard>();
   Searcher<PipeGameBoard> searcher=new DFS<PipeGameBoard>();
   Solver<PipeGameBoard> solver=new MySolver<>(searcher);
   @Override
   public void handleClient(InputStream infromClient, OutputStream outtoClient) {
      BufferedReader clientInput=new BufferedReader(
           new InputStreamReader(infromClient));
          System.out.println("alive");
      
      
      StringWriter stringWriter=new StringWriter();
      PrintWriter outToServer=new PrintWriter(stringWriter);
      PrintWriter outToClient=new PrintWriter(outtoClient);
      try {

         // correspond according to a well-defined protocol 
         readInputsAndSend(clientInput, outToServer, "done");
         String textfromclient = stringWriter.getBuffer().toString();
        System.out.println(textfromclient);
         PipeGameBoard pipeGameMap = new PipeGameBoard(textfromclient);
         //long time=System.currentTimeMillis();
        // System.out.println(pipeGameMap.toString());
        // System.out.println("start solution");
         PipeGameBoard pipeGameBoard = getsolution(pipeGameMap);
        // System.out.println("got solution");
         System.out.println(pipeGameBoard.toString());
         Collection<RotateMove> rotateMoves = RotateMove.getRotatesMoves(pipeGameMap,
               pipeGameBoard);///may case problem see RotateMove above function getRotatesMoves
         for (RotateMove rotateMove : rotateMoves) {
            //System.out.println("RotateMove: "+rotateMove.toString());
            outToClient.println(rotateMove.toString());
            outToClient.flush();
         }
         outToClient.println("done");
         outToClient.flush();
         //System.out.println("Done After : "+((System.currentTimeMillis()-time)/1000)+"."+((System.currentTimeMillis()-time)%1000)+" sec");
         clientInput.close();

      }catch (Exception e) {
         e.printStackTrace();
      } 
      
      finally {
         outToServer.flush();
         outToServer.close();
         outToClient.close();
      }
   }
   
   private void readInputsAndSend(BufferedReader in, PrintWriter out,String exitStr)
   {
      try { 
         String line;
         while(!(line=in.readLine()).equals(exitStr))
         {
		   System.out.println(line);

            out.println(line);
            out.flush(); 
         } 
      } 
      catch (IOException e) 
      { 
         e.printStackTrace();
      } 
   } 

   @SuppressWarnings({ "unchecked" })
   public PipeGameBoard getsolution(PipeGameBoard pipeGameMap){
      Solution<PipeGameBoard> solution=null;
      PipeGameBoard gameMap=null;
      boolean flag=false;
      try {
         if(cacheManager.isFileExist(pipeGameMap.hashMap().toString()))
         {
            //System.out.println("Solution Exist in Cache Manager");
            solution=cacheManager.loadFile(pipeGameMap.hashMap().toString());
            //System.out.println("Solution Loaded from Cache Manager");
            gameMap=((State<PipeGameBoard>)(solution.getStates().toArray()[(solution.getStates().size()-1)])).getState();
            flag=true;
            if(!gameMap.isSolevd())
            {
               //System.out.println("Error: Solution Loaded from Cache Manager is Corrapt");
               flag=false;
            }
         }
         else
         {
           // System.out.println("Solution Not Exist in Cache Manager");
         }
      }
      catch (Exception e) {
         flag=false;
         //System.out.println("Error: Solution Not Loaded from Cache Manager");
      }
      if(!flag)
      {
         
         solution = solver.solve(new PipeGameSearchable(pipeGameMap));
         //System.out.println("Problem Solved using the Solver");
         //System.out.println(solution!=null);
         ArrayList<State<PipeGameBoard>> states= solution.getArrayListStates();
         State<PipeGameBoard> state=states.get(states.size()-1);
         gameMap=state.getState();
         try {
            Collection<State<PipeGameBoard>> collection=new ArrayList<State<PipeGameBoard>>();
            collection.add(state);
            Solution<PipeGameBoard> solutiononlyone=new Solution<PipeGameBoard>(collection);
            cacheManager.saveFile(pipeGameMap.hashMap().toString(),solutiononlyone);
            //System.out.println("Solution Saved in Cache Manager");
         } catch (Exception e) {
            //System.out.println("Error: Solution Not Saved in Cache Manager");
            //e.printStackTrace();
         }
         //System.out.println(solution.getStates().size());
         
      }  
      return gameMap;
   }

}

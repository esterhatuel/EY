package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import classes.PipeGameBoard;
import classes.Solution;

public class MyCacheManager<T> implements CacheManager<Solution<T>>{

	   @Override
	   public boolean isFileExist(String fileName) {
	      Solution<T> solution=null;
	      try {
	         solution=loadFile(fileName);
	      } catch (Exception e) {
	         //e.printStackTrace();
	         return false;
	      }
	      
	      if(solution!=null&&
	            solution.getStates()!=null&&
	            solution.getStates().size()!=0)
	      {
	         //System.out.println("size: "+solution.getStates().size());
	         return true;
	      }
	      return false;
	   }

	   @SuppressWarnings("unchecked")
	   @Override
	   public Solution<T> loadFile(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
	      ObjectInputStream in=new ObjectInputStream(new FileInputStream(fileName+".txt"));
	      Solution<T> solution=(Solution<T>) in.readObject();
	      in.close();
	      return solution;
	   }

	   @Override
	   public void saveFile(String fileName, Solution<T> file) throws IOException{    
	      ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(fileName+".txt"));
	      out.writeObject(file);
	      out.flush();
	      out.close();   
	   }
	
}

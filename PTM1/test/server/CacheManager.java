package server;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface CacheManager<T> {
	
	public void saveFile(String filename,T file) throws IOException;
	public  T loadFile(String filename) throws FileNotFoundException, IOException, ClassNotFoundException; 
	public boolean isFileExist(String filename);

}

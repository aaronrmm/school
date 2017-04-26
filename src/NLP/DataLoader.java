package NLP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

//this class loads the files from NLPdata, reads each sentence and randomly puts it into one of two lists with .3 and .7 probability
public class DataLoader {

	static final int MAX_LINES = 6000;
	static ArrayList<String> training = new ArrayList<String>();
	static ArrayList<String> testing = new ArrayList<String>();
	public static void Load(){
		FileReader fReader = null;
		BufferedReader bReader = null;
		try {

			Path dir = Files.createDirectories(Paths.get("NLPdata"));
			DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
			    for (Path file: stream) {
			    	int lines = 0;
			        System.out.println(file.getFileName());
			        fReader = new FileReader(file.toString());
			        bReader = new BufferedReader(fReader);
			        while(lines<MAX_LINES/3){
			        	String line = bReader.readLine();
			        	lines++;
			        	if(line==null)
			        		break;
			        	if(Math.random()>.3)
			        		training.add(line);
			        	else
			        		testing.add(line);
			        }
			        	
			    }
		} catch (IOException | DirectoryIteratorException x) {
		    // IOException can never be thrown by the iteration.
		    // In this snippet, it can only be thrown by newDirectoryStream.
		    System.err.println(x);
		}finally{
			if(bReader!=null)
				try {
					bReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(fReader!=null)
				try {
					fReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}

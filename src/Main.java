import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	public static void main(String[]args){
		File file = new File("TMSInput.txt");
		BufferedReader reader = null;
		FileWriter writer = null;
		TMS tms = new TMS();
		try {
			 reader = new BufferedReader(new FileReader(file));
			 String line = reader.readLine();
			 while(line!=null){
				 String[]split = line.split(":");
				 if(split[0].equals("Tell"))
					 tms.add_sentence(split[1]);
				 else if (split[0].equals("Retract"))
					 tms.retract_sentence(split[1]);
				 else
					 new IOException("invalid input: "+line).printStackTrace();
				 
				 
				 line = reader.readLine();
			 }
			 System.out.println(tms.getState());
			 writer = new FileWriter(new File("TMSOutput.txt"));
			 writer.write(tms.getState());
			 writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{if(reader!=null)
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}}
	}
}

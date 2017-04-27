package NLP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Output {

	String dir_name;
	
	BufferedWriter treeWriter = null;
	BufferedWriter raw_training_writer = null;
	BufferedWriter raw_test_writer = null;
	BufferedWriter clean_training_writer = null;
	BufferedWriter clean_test_writer = null;
	BufferedWriter results_writer = null;
	BufferedWriter incorrect_writer = null;
	
	public Output(String dir_name){
		this.dir_name = dir_name;
		createDirectory();
	}
	
	public void createDirectory(){
		new File(dir_name).mkdir();
		try {
			String tree_filepath = dir_name+"\\tree.txt";
			new File(tree_filepath);
			FileWriter treeFWriter = new FileWriter(tree_filepath);
			treeWriter = new BufferedWriter(treeFWriter);

			String raw_training_filepath = dir_name+"\\raw_training.txt";
			new File(raw_training_filepath);
			FileWriter rawtrainFWriter = new FileWriter(raw_training_filepath);
			raw_training_writer = new BufferedWriter(rawtrainFWriter);
			
			String raw_test_filepath = dir_name+"\\raw_test.txt";
			new File(raw_test_filepath);
			FileWriter raw_testFWriter = new FileWriter(raw_test_filepath);
			raw_test_writer = new BufferedWriter(raw_testFWriter);
			
			String clean_training_filepath = dir_name+"\\clean_training.txt";
			new File(clean_training_filepath);
			FileWriter cleantrainFWriter = new FileWriter(clean_training_filepath);
			clean_training_writer = new BufferedWriter(cleantrainFWriter);

			String clean_test_filepath = dir_name+"\\clean_test.txt";
			new File(clean_test_filepath);
			FileWriter clean_testFWriter = new FileWriter(clean_test_filepath);
			clean_test_writer = new BufferedWriter(clean_testFWriter);

			String result_filepath = dir_name+"\\results.txt";
			new File(result_filepath);
			FileWriter resultFWriter = new FileWriter(result_filepath);
			results_writer = new BufferedWriter(resultFWriter);
			
			String incorrect_filepath = dir_name+"\\incorrect.txt";
			new File(incorrect_filepath);
			FileWriter incorrectFWriter = new FileWriter(incorrect_filepath);
			incorrect_writer = new BufferedWriter(incorrectFWriter);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void outputTree(String tree){
		try {
			treeWriter.write(tree);
			treeWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void outputRawTrainingSentence(List<String> raw_training){
		for(String sentence:raw_training){
			try{
				raw_training_writer.write(sentence+"\n");
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public void outputRawTestSentence(List<String> sentences){
		for(String sentence:sentences){
			try{
				raw_test_writer.write(sentence+"\n");
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public void outputcleanTrainingSentence(List<String> clean_training){
		for(String sentence:clean_training){
			try{
				clean_training_writer.write(sentence+"\n");
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public void outputcleanTestSentence(List<String> sentences){
		for(String sentence:sentences){
			try{
				clean_test_writer.write(sentence+"\n");
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public void outputToResults(String string){
		try{
			results_writer.write(string+"\n");
			results_writer.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void outputIncorrectSentence(String string){
		try{
			incorrect_writer.write(string+"\n");
			incorrect_writer.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}

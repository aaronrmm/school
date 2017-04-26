package tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.TextDirectoryLoader;

public class LoaderTest {

	public static void main(String[]args){

		TextDirectoryLoader loader = new TextDirectoryLoader();
		try {
			loader.setDirectory(new File("NLPdata"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Attribute>attributes = new ArrayList<Attribute>();
		ArrayList<String> classes = new ArrayList<String>();
		classes.add("0"); classes.add("1");
		attributes.add(new Attribute("sentence", new ArrayList<String>()));
		attributes.add(new Attribute("Association", classes));
		Instances instances = new Instances("Sentences", attributes, 10000);
		instances.setClassIndex(1);
		while(true){
			try{
				Instance instance = loader.getNextInstance(instances);
				System.out.println(instance);
				if (instance==null)
					break;
				else{
					instances.add(instance);
				}
			}catch(Exception ex){
				ex.printStackTrace();
				break;
			}
		}
		for(Instance instance : instances){
			System.out.println(instance.toString());
		}
	}
}

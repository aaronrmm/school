package NLP;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import weka.classifiers.trees.Id3;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class Main {
	
	static WordVectorGenerator wvg = new WordVectorGenerator(1,100000);
	static Attribute content_attr = new Attribute("content",(ArrayList<String>)null);
	static Attribute class_attr;
    static ArrayList<String> classVal = new ArrayList<String>();
	public static void main(String[]args){
		try {
			System.setOut(new PrintStream("output"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		DataLoader.Load();
		ArrayList<String> training_data = DataLoader.training;
		
		//read each sentence and tokenize
		
		ArrayList<Attribute> atts = new ArrayList<Attribute>(2);
        classVal.add("negative");
        classVal.add("positive");
        atts.add(content_attr);
        class_attr = new Attribute("classVal",classVal);
        atts.add(class_attr);
		
        Instances training_set = new Instances("TestInstances",atts,0);

        ArrayList<String> training_strings = new ArrayList<String>();
        for(int i=0;i <training_data.size() && i<30000;i++){
        	String sentence = training_data.get(i);
        	training_set.add(sentenceToInstance(sentence));
        	training_strings.add(sentence);
        }

		System.out.println("CREATING WORD VECTOR FROM TRAINING DATA");
		Instances output= wvg.getWordVectors(training_set);
		output.setClassIndex(0);
		
		try {
			System.out.println("CREATING ID3");
			Id3 id3 = new Id3();
			id3.buildClassifier(output);
			System.out.println(id3);
			System.out.println("TESTING ID3");
			double total = 0;
			double false_positives=0;
			double false_negatives=0;
			double true_positives=0;
			double true_negatives=0;
			for(String test: DataLoader.testing){
				total++;
				Instance wordVector = wvg.getWordVector(test);
				double classification = id3.classifyInstance(wordVector);
				if(getClassValue(test) == classification){
					if(classification==1)
						true_positives++;
					else
						true_negatives++;
				}
				else{
					if(classification==1)
						false_positives++;
					else
						false_negatives++;
				}
					System.out.println("incorrect"+getClassValue(test)+"!="+classification+":"+test);
			}
			System.out.println("true positives: "+true_positives/total);
			System.out.println("false positives: "+false_positives/total);
			System.out.println("true negatives: "+true_negatives/total);
			System.out.println("false negatives: "+false_negatives/total);
			System.out.println("accuracy: "+(true_positives+true_negatives)/total);
			String insert = "that was good I'd love more great time\t1";
			Instance vector = wvg.getWordVector(insert);
			System.out.println(id3.classifyInstance(vector));
			System.out.println(id3.classifyInstance(wvg.getWordVector("awful terrible bad wait line gross vomit in caterpiller in\t0")));
			System.out.println(vector);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//
		
		
		
		
	}
	
	static Instance sentenceToInstance(String sentence){
    	String[]split = sentence.split("\t");
        double[] instanceValue1 = new double[2];
        instanceValue1[0] = content_attr.addStringValue(split[0]);
        instanceValue1[1] = getClassValue(sentence);
        Instance instance = new DenseInstance(1.0, instanceValue1);
        return instance;
	}
	
	static int getClassValue(String sentence){
    	String[]split = sentence.split("\t");
        return Integer.parseInt(split[1]);
		
	}
	
}

package NLP;

import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class SentenceToWekaInstance {

	static Attribute content_attr = null;
	static Attribute class_attr = null;
    static ArrayList<String> classVal = null;
	static ArrayList<Attribute> atts = null;
    
    public static void initiate_format(){
    	classVal = new ArrayList<String>();
    	content_attr = new Attribute("content",(ArrayList<String>)null);
		atts = new ArrayList<Attribute>(2);
        classVal.add("negative");
        classVal.add("positive");
        atts.add(content_attr);
        class_attr = new Attribute("classVal",classVal);
        atts.add(class_attr);
    }
    
	public static Instances sentencesToInstances(ArrayList<String> sentences){
		if(atts==null)initiate_format();
	    Instances training_set = new Instances("TestInstances",atts,0);
	
	    ArrayList<String> training_strings = new ArrayList<String>();
	    for(int i=0;i <sentences.size() && i<30000;i++){
	    	String sentence = sentences.get(i);
	    	training_set.add(sentenceToInstance(sentence));
	    	training_strings.add(sentence);
	    }
	    return training_set;
	}
	
	static Instance sentenceToInstance(String sentence){
		if(atts==null)initiate_format();
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

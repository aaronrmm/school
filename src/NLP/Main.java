package NLP;

import java.util.ArrayList;

import weka.classifiers.trees.Id3;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.tokenizers.NGramTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToBinary;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class Main {
	static StringToWordVector wv;
	static NumericToBinary n2b;
	static Attribute content_attr = new Attribute("content",(ArrayList<String>)null);
	static Attribute class_attr;
    static ArrayList<String> classVal = new ArrayList<String>();
	public static void main(String[]args){
		DataLoader.Load();
		ArrayList<String> training_data = DataLoader.training;
		
		//read each sentence and tokenize
		
		ArrayList<Attribute> atts = new ArrayList<Attribute>(2);
        classVal.add("class0");
        classVal.add("class1");
        atts.add(content_attr);
        class_attr = new Attribute("classVal",classVal);
        atts.add(class_attr);
		
        Instances training_set = new Instances("TestInstances",atts,0);

        for(int i=0;i <training_data.size() && i<300;i++){
        	String sentence = training_data.get(i);
        	training_set.add(sentenceToInstance(sentence));
        }

		
		Instances output= getWordVectors(training_set);
		output.setClassIndex(0);
		
		try {
			Id3 id3 = new Id3();
			id3.buildClassifier(output);
			double total = 0;
			double correct = 0;
			for(String test: DataLoader.testing){
				total++;
				Instance wordVector = getWordVector(test);
				double classification = id3.classifyInstance(wordVector);
				if(getClassValue(test) == classification){
					System.out.println("correct");
					correct++;
				}
				else
					System.out.println("incorrect");
			}
			System.out.println(correct/total);
			
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
	
	
	static Instances getWordVectors(Instances sentences){
		if(wv==null){
			wv = new StringToWordVector(100000);
			NGramTokenizer tokenizer = new NGramTokenizer();
			tokenizer.setNGramMaxSize(2);
			tokenizer.setNGramMinSize(2);
			tokenizer.setDelimiters(" ");
			wv.setTokenizer(tokenizer);
			
			wv.setAttributeIndicesArray(new int[]{0});
			try {
				wv.setInputFormat(sentences);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		Instances output=null;
		try {
			output = Filter.useFilter(sentences, wv);
			//while(wv.batchFinished())
			//output.add(wv.output());
			n2b = new NumericToBinary();
			n2b.setInputFormat(output);
			output = Filter.useFilter(output, n2b);
			output.setClassIndex(0);
			
			return output;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	static Instance getWordVector(String sentence){
		//manually generate matching instance for sentence
		Instances format = wv.getOutputFormat();
		format.setClassIndex(0);
		double[] dInstance= new double[format.numAttributes()];
		dInstance[0] = Integer.parseInt(sentence.split("\t")[1]);
		for(int a = 1; a < format.numAttributes(); a ++){
			if(sentence.contains(format.attribute(a).name())){
				dInstance[a]=1;
			}
		}
		Instance instance = new DenseInstance(1, dInstance);
		format.add(instance);
		try {
			//format = Filter.useFilter(format, n2b);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return format.firstInstance();
	}
}

package NLP;

import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.tokenizers.NGramTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToBinary;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class WordVectorGenerator {
	StringToWordVector wv;
	NumericToBinary n2b;
	int n_of_ngrams;
	int max_ngrams;
	
	
	public WordVectorGenerator(int n_of_ngrams, int max_ngrams) {
		this.n_of_ngrams = n_of_ngrams;
		this.max_ngrams = max_ngrams;
	}

	Instances getWordVectors(Instances sentences){
		if(wv==null){
			wv = new StringToWordVector(max_ngrams);
			NGramTokenizer tokenizer = new NGramTokenizer();
			tokenizer.setNGramMaxSize(n_of_ngrams);
			tokenizer.setNGramMinSize(n_of_ngrams);
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
			System.out.println("Genenerated word vectors of size "+(output.numAttributes()-1));
			//while(wv.batchFinished())
			//output.add(wv.output());
			//n2b = new NumericToBinary();
			//n2b.setInputFormat(output);
			//output = Filter.useFilter(output, n2b);
			//output.setClassIndex(0);
			
			
			ArrayList<Attribute> list = new ArrayList<Attribute>();
			ArrayList<String> binary_values = new ArrayList<String>();
			binary_values.add("0"); binary_values.add("1");
			for(int i=0;i<output.numAttributes();i++){
				if(output.attribute(i).isNumeric())
					list.add(new Attribute(output.attribute(i).name(), binary_values));
				else
					list.add(output.attribute(i));
			}
			Instances binary_format = new Instances("wordvectors", list, 10000);
			for(Instance instance: output)
				binary_format.add(instance);
			
			
			return binary_format;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	Instance getWordVector(String sentence){
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
	
	public String getStats(){
		return (wv.getOutputFormat().numAttributes()-1)+" ngrams generated";
	}
}

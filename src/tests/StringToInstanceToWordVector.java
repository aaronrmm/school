package tests;

import java.io.File;
import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.tokenizers.NGramTokenizer;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class StringToInstanceToWordVector {
	
	public static Attribute content = new Attribute("content",(ArrayList<String>)null);
    public static void main(String[] args) {
        ArrayList<Attribute> atts = new ArrayList<Attribute>(2);
        ArrayList<String> classVal = new ArrayList<String>();
        classVal.add("A");
        classVal.add("B");
        atts.add(content);
        atts.add(new Attribute("@@class@@",classVal));
		
        Instances dataRaw = new Instances("TestInstances",atts,0);

        String[] sentences = new String[]{"test me","test me","one two three four", "test test"," me me me test me","butter time"};
        for(String sentence : sentences){
            double[] instanceValue1 = new double[dataRaw.numAttributes()];
            instanceValue1[0] = dataRaw.attribute(0).addStringValue(sentence);
            instanceValue1[1] = (int)(Math.random()*2);
            dataRaw.add(new DenseInstance(1.0, instanceValue1));
        }

        System.out.println("After adding second instance");
        System.out.println("--------------------------");
        System.out.println(dataRaw);
        System.out.println("--------------------------");

		StringToWordVector wv = new StringToWordVector(10000);
		NGramTokenizer tokenizer = new NGramTokenizer();
		tokenizer.setNGramMaxSize(2);
		tokenizer.setNGramMinSize(2);
		tokenizer.setDelimiters(" ");
		wv.setTokenizer(tokenizer);
		wv.setDictionaryFileToSaveTo(new File("mydict"));
		
		wv.setAttributeIndicesArray(new int[]{0});
		try {
			wv.setInputFormat(dataRaw);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(Instance instance : dataRaw){

			try {
				wv.input(instance);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(instance);
		}
		try {
			wv.batchFinished();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("output");
		while(wv.outputPeek()!=null){
			Instance nwes = wv.output();
			System.out.println(content.value(2));
			nwes.stringValue(content);
			System.out.println(nwes+nwes.stringValue(content));
		}
		System.out.println("output");

    }

}

package NLP;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import weka.classifiers.trees.Id3;
import weka.core.Instance;
import weka.core.Instances;

public class Main {
	
	final static int N_OF_NGRAMS = 1;
	final static int MAX_NGRAM_COUNT = 1000000;
	final static boolean FLATTEN_UPPERCASE = true;
	final static boolean STRIP_SUFFIXES = true;
	final static boolean STRIP_PUNCTUATION = true;
	final static boolean STRIP_STOPWORDS = true;
	final static String CONSOLE_OUTPUT_FILENAME = null;
	
	//Parameters
	static WordVectorGenerator wvg = new WordVectorGenerator(N_OF_NGRAMS, MAX_NGRAM_COUNT);
	
	
	
	

    
    
	public static void main(String[]args){
		//Set output to file "output"
		if(CONSOLE_OUTPUT_FILENAME!=null)
			try {
				System.setOut(new PrintStream(CONSOLE_OUTPUT_FILENAME));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		
		//Load data
		DataLoader dataLoader = new DataLoader(Integer.MAX_VALUE, 0.3);
		ArrayList<String> training_data = dataLoader.getTrainingData();
		ArrayList<String> test_data = dataLoader.getTestingData();
		

		//Clean data
		training_data = SentenceCleaner.cleanSentences(training_data,FLATTEN_UPPERCASE, STRIP_PUNCTUATION, STRIP_STOPWORDS, STRIP_SUFFIXES );
		test_data = SentenceCleaner.cleanSentences(test_data ,FLATTEN_UPPERCASE, STRIP_PUNCTUATION, STRIP_STOPWORDS, STRIP_SUFFIXES );
		

		//convert to weka instances
		Instances training_instances = SentenceToWekaInstance.sentencesToInstances(training_data);


		//convert instances to word vectors and produce ngram list
		System.out.println("CREATING WORD VECTOR FROM TRAINING DATA");
		Instances output= wvg.getWordVectors(training_instances);
		output.setClassIndex(0);
		
		
		//run ID3 on word vectors
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
			for(String test: test_data){
				total++;
				Instance wordVector = wvg.getWordVector(test);
				double classification = id3.classifyInstance(wordVector);
				if(SentenceToWekaInstance.getClassValue(test) == classification){
					if(classification==1)
						true_positives++;
					else
						true_negatives++;
				}
				else{
					System.out.println("incorrect"+SentenceToWekaInstance.getClassValue(test)+"!="+classification+":"+test);
					if(classification==1)
						false_positives++;
					else
						false_negatives++;
				}
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
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//
		
		
		
		
	}
}

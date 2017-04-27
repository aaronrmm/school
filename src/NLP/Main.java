package NLP;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import weka.classifiers.trees.Id3;
import weka.core.Instance;
import weka.core.Instances;

public class Main {
	
	//parameters
	final static int N_OF_NGRAMS = 3;//unigrams, bigrams, trigrams, etc.
	final static int MAX_NGRAM_COUNT = Integer.MAX_VALUE;//the maximum number of bigrams to store for categorization
	final static int LINES_TO_LOAD = Integer.MAX_VALUE;//the maximum number of sentences of data to load
	final static double TEST_DATA_PERPORTION = 0.3;//what percent of the data is used as test data
	final static boolean FLATTEN_UPPERCASE = false;//clean sentences by changing all letters to lowercase
	final static boolean STRIP_SUFFIXES = false;//remove suffixes like "ing", "es", "er", "ed", etc.
	final static boolean STRIP_PUNCTUATION = false;//remove periods and other punctuation
	final static boolean STRIP_STOPWORDS = false;//remove common irrelevant words like "the" and "and"
	final static String CONSOLE_OUTPUT_FILENAME = null;//if System.out.println is to print to a file. null to print to console
	final static String OUTPUT_DIRECTORY_NAME = "trigram_MAX_dirty";//the name of the directory to save all results
	
	//Parameters
	static WordVectorGenerator wvg = new WordVectorGenerator(N_OF_NGRAMS, MAX_NGRAM_COUNT);
	static Output output = new Output(OUTPUT_DIRECTORY_NAME);
	
	
	
	

    
    
	public static void main(String[]args){
		//Set destination file of standard out
		if(CONSOLE_OUTPUT_FILENAME!=null)
			try {
				System.setOut(new PrintStream(CONSOLE_OUTPUT_FILENAME));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		
		//Load data
		DataLoader dataLoader = new DataLoader(LINES_TO_LOAD, TEST_DATA_PERPORTION);
		ArrayList<String> training_data = dataLoader.getTrainingData();
		output.outputRawTrainingSentence(training_data);
		ArrayList<String> test_data = dataLoader.getTestingData();
		output.outputRawTestSentence(test_data);
		

		//Clean data
		training_data = SentenceCleaner.cleanSentences(training_data,FLATTEN_UPPERCASE, STRIP_PUNCTUATION, STRIP_STOPWORDS, STRIP_SUFFIXES );
		output.outputcleanTrainingSentence(training_data);
		test_data = SentenceCleaner.cleanSentences(test_data ,FLATTEN_UPPERCASE, STRIP_PUNCTUATION, STRIP_STOPWORDS, STRIP_SUFFIXES );
		output.outputcleanTestSentence(test_data);
		

		//convert to weka instances
		Instances training_instances = SentenceToWekaInstance.sentencesToInstances(training_data);


		//convert instances to word vectors and produce ngram list
		System.out.println("CREATING WORD VECTOR FROM TRAINING DATA");
		Instances wordvectors= wvg.getWordVectors(training_instances);
		wordvectors.setClassIndex(0);
		output.outputToResults(wvg.getStats());
		
		
		//run ID3 on word vectors
		try {
			System.out.println("CREATING ID3");
			Id3 id3 = new Id3();
			id3.buildClassifier(wordvectors);
			output.outputTree(id3.toString());
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
					if(classification==1){
						System.out.println("false+: "+SentenceToWekaInstance.getClassValue(test)+"!="+classification+":"+test);
						output.outputIncorrectSentence("false+: "+SentenceToWekaInstance.getClassValue(test)+"!="+classification+":"+test);
						false_positives++;
					}
					else{
						System.out.println("false-: "+SentenceToWekaInstance.getClassValue(test)+"!="+classification+":"+test);
						output.outputIncorrectSentence("false-: "+SentenceToWekaInstance.getClassValue(test)+"!="+classification+":"+test);
						false_negatives++;
					}
				}
			}
			double precision = true_positives/(true_positives+false_positives);
			double recall = true_positives/(true_positives+false_negatives);
			System.out.println("true positives: "+true_positives/total);
			System.out.println("false positives: "+false_positives/total);
			System.out.println("true negatives: "+true_negatives/total);
			System.out.println("false negatives: "+false_negatives/total);
			System.out.println("accuracy: "+(true_positives+true_negatives)/total);
			System.out.println("precision: "+precision);
			System.out.println("recall: "+ recall);
			System.out.println("F1-factor: "+(2*recall*precision/(precision+recall)));


			output.outputToResults("true positives: "+true_positives/total);
			output.outputToResults("false positives: "+false_positives/total);
			output.outputToResults("true negatives: "+true_negatives/total);
			output.outputToResults("false negatives: "+false_negatives/total);
			output.outputToResults("accuracy: "+(true_positives+true_negatives)/total);
			output.outputToResults("precision: "+precision);
			output.outputToResults("recall: "+ recall);
			output.outputToResults("F1-factor: "+(2*recall*precision/(precision+recall)));
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//
		
		
		
		
	}
}

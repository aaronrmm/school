package NLP;

import java.util.ArrayList;

public class SentenceCleaner {
	
	static final String[] postfixes_to_trim = new String[]{"ing","es","s","er","ers","ed","\\'s","s\\'","\\'ve","\\'nt","ly","y"};
	static final String[] punctuation_to_trim = new String[]{"\\.","\\,","\\:","\\;","\\!","\\(","\\)","\\?"};
	static final String[] stopwords = new String[]{"i","is","the","this","then","than","if","me","you","in","to","and",
			"my","we","at","be","do","from","had","of","a","as","an","i\\'ve","for","out","was","were","so","i'm"};
	
	public static ArrayList<String> cleanSentences(ArrayList<String> sentences, boolean flatten, boolean strip_punctuation, boolean strip_stopwords, boolean strip_postfixes){
		ArrayList<String> cleaned_sentences = new ArrayList<String>();
		for(String sentence : sentences){
			cleaned_sentences.add(cleanSentence(sentence, flatten, strip_punctuation, strip_stopwords, strip_postfixes));
		}
		return cleaned_sentences;
	}
	
	
	private static String cleanSentence(String sentence, boolean flatten, boolean strip_punctuation,
			boolean strip_stopwords, boolean strip_postfixes) {
		if(flatten)
			sentence = sentence.toLowerCase();
		if(strip_punctuation)
			sentence = strip_punctuation(sentence);
		if(strip_stopwords)
			sentence = strip_stopwords(sentence);
		if(strip_postfixes)
			sentence = stripPostfixes(sentence);
		return sentence;
	}


	public static String stripPostfixes(String sentence){
		StringBuilder builder = new StringBuilder();
		String[] words = sentence.split(" ");
		for(int i=0;i<words.length-1;i++){
			String word = words[i];
			for(int p = 0; p < postfixes_to_trim.length; p++){
				if(word.endsWith(postfixes_to_trim[p])){
					word = word.substring(0, word.length()-postfixes_to_trim[p].length());
					break;
				}
			}
			builder.append(word+" ");
		}
		builder.append(words[words.length-1]);//add back classval
		return builder.toString();
	}
	
	public static String strip_punctuation(String sentence){
		for (int i = 0; i< punctuation_to_trim.length; i++){
			sentence = sentence.replaceAll(punctuation_to_trim[i], " ");
		}
		return sentence;
	}

	public static String strip_stopwords(String sentence){
		StringBuilder builder = new StringBuilder();
		String[] split = sentence.split(" ");
		for(int i=0;i<split.length;i++){
			boolean acceptable = true;
			for(int sw=0; sw<stopwords.length; sw++){
				if(split[i].equals(stopwords[sw])){
					acceptable = false;
					break;
				}
			}
			if (acceptable)
				builder.append(split[i]+" ");
		}
		return builder.toString();
		
	}
}

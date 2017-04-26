package NLP;

import java.util.ArrayList;

public class SentenceCleaner {
	
	static String[] postfixes_to_trim = new String[]{"ing","es","s","er","ers","ed","\\'s","s\\'"};
	static String[] punctuation_to_trim = new String[]{"\\.","\\,","\\:","\\;","\\!","\\(","\\)","\\?"};
	
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
	
	public static ArrayList<String> stripPostfixes(ArrayList<String> sentences){
		ArrayList<String> stripped_strings = new ArrayList<String>();
		for(String sentence : sentences)
			stripped_strings.add(stripPostfixes(sentence));
		return stripped_strings;
	}
	
	public static String strip_punctuation(String sentence){
		for (int i = 0; i< punctuation_to_trim.length; i++){
			sentence = sentence.replaceAll(punctuation_to_trim[i], " ");
		}
		return sentence;
	}
	
	public static ArrayList<String> strip_punctuation(ArrayList<String> sentences){
		ArrayList<String> stripped_strings = new ArrayList<String>();
		for(String sentence : sentences)
			stripped_strings.add(strip_punctuation(sentence));
		return stripped_strings;
	}

	public static ArrayList<String> flatten_uppercase(ArrayList<String> sentences) {
		ArrayList<String>flattened_strings = new ArrayList<String>();
		for(String string: sentences)
			flattened_strings.add(string.toLowerCase());
		return flattened_strings;
	}
}

package NLP;

public class PostfixStripper {
	
	public static String[] postfixes_to_trim = new String[]{"ing","es","s","er","ers","ings","ed"};
	public static String stripPostfixes(String sentence){
		StringBuilder builder = new StringBuilder();
		String[] words = sentence.split(" ");
		for(int i=0;i<words.length;i++){
			String word = words[i];
			for(int p = 0; p < postfixes_to_trim.length; p++){
				if(word.endsWith(postfixes_to_trim[p])){
					word = word.substring(0, word.length()-postfixes_to_trim[p].length());
					break;
				}
			}
			builder.append(word);
		}
		return builder.toString();
	}
}

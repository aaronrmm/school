
public class Test {

	public static void main(String[] args) {
		TMS tms = new TMS();
		
		
		tms.addSentence("A+B->Q");
		System.out.println(tms.getState());

		tms.addSentence("A");
		tms.addSentence("B");
		
		System.out.println(tms.getState());
		
	}

}

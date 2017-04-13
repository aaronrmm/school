
public class Test {

	public static void main(String[] args) {
		TMS tms = new TMS();
		
		
		tms.addSentence("C*A+B->Q");
		System.out.println(tms.getState());

		tms.addSentence("A");
		tms.addSentence("B");
		tms.addSentence("C");
		
		System.out.println(tms.getState());
		
		tms.retract_sentence("A");
		System.out.println(tms.getState());
		
	}

}

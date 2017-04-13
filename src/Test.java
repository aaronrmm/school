
public class Test {

	public static void main(String[] args) {
		TMS tms = new TMS();
		
		
		tms.add_sentence("-C*A+B->Q");
		System.out.println(tms.getState());

		tms.add_sentence("A");
		tms.add_sentence("B");
		tms.add_sentence("-C");
		tms.add_sentence("D");
		
		System.out.println(tms.getState());

		tms.retract_sentence("-C*A+B->Q");
		tms.retract_sentence("A");
		tms.retract_sentence("C");
		System.out.println(tms.getState());
		
	}

}

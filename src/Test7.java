

public class Test7 {

	public static void main(String[] args) {
		TMS tms = new TMS();
		
		
		tms.add_sentence("A->B");
		tms.add_sentence("A");
		tms.add_sentence("B");
		tms.retract_sentence("A");
		System.out.println(tms.getState());
	}

}

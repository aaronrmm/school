

public class Test6 {

	public static void main(String[] args) {
		TMS tms = new TMS();
		
		
		tms.add_sentence("A");
		tms.add_sentence("-A");
		tms.add_sentence("A");
		tms.retract_sentence("A");
		System.out.println(tms.getState());
	}

}

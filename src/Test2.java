
public class Test2 {

	public static void main(String[] args) {
		TMS tms = new TMS();
		
		
		tms.add_sentence("A->B");
		System.out.println(tms.getState());
		tms.add_sentence("A");
		System.out.println(tms.getState());
		tms.add_sentence("A+-C->D");
		System.out.println(tms.getState());
		tms.add_sentence("-C");
		System.out.println(tms.getState());
		tms.retract_sentence("A");
		System.out.println(tms.getState());
		
	}

}

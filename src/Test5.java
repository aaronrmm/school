
public class Test5 {

	public static void main(String[] args) {
		TMS tms = new TMS();
		
		
		tms.add_sentence("A->C");
		tms.add_sentence("B->C");
		tms.add_sentence("B");
		tms.add_sentence("A");
		System.out.println(tms.getState());
	}

}






public class Test8 {

	public static void main(String[] args) {
		TMS tms = new TMS();
		
		
		tms.add_sentence("A->B");
		tms.add_sentence("A->-B");
		tms.add_sentence("A");
		System.out.println(tms.getState());
	}

}


public class Test4 {

	public static void main(String[] args) {
		TMS tms = new TMS();
		
		
		tms.add_sentence("V1+V2->I1");
		tms.add_sentence("I1->V1");
		tms.add_sentence("V1");
		tms.retract_sentence("V1");
		tms.add_sentence("I1->V1->V3");
		System.out.println(tms.getState());
	}

}

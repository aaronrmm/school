

public class Test3 {

	public static void main(String[] args) {
		TMS tms = new TMS();
		
		
		tms.add_sentence("A+B->E");
		tms.add_sentence("A");
		tms.add_sentence("C");
		tms.add_sentence("C*D->G");
		tms.retract_sentence("A");
		tms.add_sentence("B");
		tms.add_sentence("-F*H->M");
		tms.add_sentence("E->J");
		tms.retract_sentence("C");
		tms.add_sentence("H");
		tms.add_sentence("-F");
		tms.retract_sentence("-F");
		tms.add_sentence("M*B+U->L");
		tms.retract_sentence("A+B->E");
		tms.add_sentence("M");
		System.out.println(tms.getState());
		/*
		A+B->E
		C*D->G
		B
		E
		-F*H->M
		H
		M*B+U->L
		M
		L
		*/
	}

}

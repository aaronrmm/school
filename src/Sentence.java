import java.util.ArrayList;

public abstract class Sentence {
	public boolean in_effect = false;
	public ArrayList<Justification> justifications = new ArrayList<Justification>();
	
	public void add_justification(Justification implication){
		justifications.add(implication);
	}
	
	public boolean foundTrue(){
		return true;
	}
	
	public String toString(){
		return "UNEXTENDED toString() Method";
	}
}

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Sentence {
	public boolean in_effect = false;
	public HashMap<String, Justification> justifications = new HashMap<String, Justification>();
	
	public boolean foundTrue(){
		return true;
	}
	
	public boolean addJustification(Justification j){
		if(justifications.containsKey(j.toString())){
			return true;
		}
		justifications.put(j.toString(), j);
		return false;
	}
	
	public String toString(){
		return "UNEXTENDED toString() Method";
	}
}

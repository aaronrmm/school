import java.util.ArrayList;

public class Justification {

	public Implication implication;
	public ArrayList<Variable> required_variables;
	
	public Justification(ArrayList<Variable> required_variables, Implication implication){
		this.required_variables = required_variables;
		this.implication = implication;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for(Variable v: required_variables){
			sb.append(v.name);
		}
		sb.append(",");
		sb.append(implication.toString());
		sb.append("}");
		return sb.toString();
	}
}

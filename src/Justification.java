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
		for(int i=0;i<required_variables.size()-1;i++){
			sb.append(required_variables.get(i).name+",");
		}
		sb.append(required_variables.get(required_variables.size()-1).name);
		sb.append(",");
		sb.append(implication.toString());
		sb.append("}");
		return sb.toString();
	}
}

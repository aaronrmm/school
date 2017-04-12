
public class Implication extends Sentence{
	public Requirement[] requirements;
	public Variable result;

	public Implication(Variable result, Requirement... requirements){
		this.requirements = requirements;
		this.result = result;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i< requirements.length-1; i++){
			sb.append(requirements[i]);
			sb.append("*");
		}
		
		sb.append(requirements[requirements.length-1]);
		
		sb.append("->");
		sb.append(result.name);
		return sb.toString();
	}
}


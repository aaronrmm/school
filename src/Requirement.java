
public class Requirement {
	public Requirement(Variable[] disjuncts){
		this.disjuncts = disjuncts;
	}
	
	public Variable[] disjuncts;
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<disjuncts.length-1;i++){
			sb.append(disjuncts[i].name+"+");
		}
		sb.append(disjuncts[disjuncts.length-1].name);
		return sb.toString();
	}
}

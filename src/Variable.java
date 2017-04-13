
public class Variable extends Sentence{
	
	public String name;
	
	public Variable(String name){
		this.name = name;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		if(this.justifications.size()>0){
			sb.append(":");
			for(Justification justification: this.justifications.values()){
				sb.append(justification.toString());
			}
		}
		return sb.toString();
	}
}


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
			int i=0;
			for(Justification justification: this.justifications.values()){
				if(i>0) sb.append(",");
				sb.append(justification.toString());
				i++;
			}
		}
		return sb.toString();
	}
}

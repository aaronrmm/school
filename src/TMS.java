import java.util.ArrayList;
import java.util.HashMap;

public class TMS {
	
	HashMap<String,Variable> variables = new HashMap<String,Variable>();
	HashMap<String, Implication> implications = new HashMap<String, Implication>();
	
	public Variable getVariable(String name){
		Variable got = variables.get(name);
		if (got == null){
			got = new Variable(name);
			variables.put(name, got);
		}
		return got;
	}
	
	@SuppressWarnings("unchecked")
	public void tryImplication(Implication implication){
		boolean implication_fulfilled = true;
		@SuppressWarnings("rawtypes")
		ArrayList[] permutations = new ArrayList[implication.requirements.length];
		for(int i=0;i<implication.requirements.length;i++){
			permutations[i] = new ArrayList<Variable>();
			boolean no_req_found = true;
			for(Variable disjunct: implication.requirements[i].disjuncts){
				if(disjunct.in_effect){
					no_req_found = false;
					permutations[i].add(disjunct);
				}
			}
			if (no_req_found){
				implication_fulfilled = false;
				break;
			}
		}
		if (implication_fulfilled){
			implication.result.in_effect = true;
			ArrayList<ArrayList<Object>> justifiaction_bags = new RecursivePacker().pack(permutations);
			for (ArrayList<Object> bag : justifiaction_bags){
				Justification j = new Justification(new ArrayList<Variable>(), implication);
				for(Object o : bag){
					j.required_variables.add((Variable)o);
				}
				implication.result.addJustification(j);
			}
		}
	}
	
	
	public void retract_sentence(String string){
		Variable variable = variables.get(string);
		if(variable!=null){
			retractVariable(variable);
			return;
		}
		else{
			Implication implication = implications.get(string);
			if(implication!=null){
				retractImplication(implication);
				return;
			}
			
		}
		System.out.println("Sentence does not exist in knowledge base.");
	}
	
	public void retractImplication(Implication implication){
		System.out.println("Retracting "+implication);
		implication.in_effect = false;
		ArrayList<Justification> toRemove = new ArrayList<Justification>();
		for(Justification justification: implication.result.justifications.values()){
			if (justification.implication == implication)
				toRemove.add(justification);
		}
		for(Justification justification: toRemove){
			implication.result.justifications.remove(justification.toString());
		}
		if(implication.result.justifications.size()==0)
			implication.result.in_effect = false;
	}
	
	public void retractVariable(Variable variable){
		System.out.println("Retracting "+variable);
		variable.in_effect = false;
		for(Variable sentence: variables.values()){
			ArrayList<Justification> toRemove = new ArrayList<Justification>();
			for(Justification justification: sentence.justifications.values()){
				if(justification.required_variables.contains(variable))
					toRemove.add(justification);
			}
			for(Justification justification: toRemove){
				sentence.justifications.remove(justification.toString());
				if(sentence.justifications.size()==0){
					retractVariable(sentence);
					
				}
			}
		}
		for(Implication implication: implications.values()){
			ArrayList<Justification> toRemove = new ArrayList<Justification>();
			for(Justification justification: implication.justifications.values()){
				if(justification.required_variables.contains(variable))
					toRemove.add(justification);
			}
			for(Justification justification: toRemove){
				implication.justifications.remove(justification.toString());
				if(implication.justifications.size()==0){
					retractImplication(implication);
				}
			}
		}
	}
	
	public String getState(){
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("*****************");
		sb.append("\n");
		for(Sentence sentence: variables.values()){
			if (sentence.in_effect){
				sb.append("\n");
				sb.append(sentence);
			}
		}
		for(Sentence sentence: implications.values()){
			if (sentence.in_effect){
				sb.append("\n");
				sb.append(sentence);
			}
		}
		return sb.toString();
	}

	public void addSentence(String string) {
		String[] split = string.split("->");
		if (split.length==1){//variable
			Variable variable = getVariable(split[0]);
			variable.in_effect = true;
			for(Implication implication: implications.values())
				tryImplication(implication);
			
		}
		else if (split.length==2){//implication
			//split up requirement
			String[]requirementsS = split[0].split("\\*");
			Requirement[] requirements = new Requirement[requirementsS.length];
			
			for(int x=0; x<requirementsS.length; x++){
				String[]disjunctS = requirementsS[x].split("\\+");
				Variable[] disjuncts = new Variable[disjunctS.length];
				for(int y=0;y<disjunctS.length;y++)
					disjuncts[y]=getVariable(disjunctS[y]);
				requirements[x] = new Requirement(disjuncts);
			}
			
			//variable
			Variable result = getVariable(split[1]);
			
			Implication implication = new Implication(result, requirements);
			implication.in_effect = true;
			implications.put(string, implication);
			tryImplication(implication);
		}
	}
}

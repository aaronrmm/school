import java.util.ArrayList;
import java.util.HashMap;

public class TMS {
	ArrayList<Sentence> sentences = new ArrayList<Sentence>();
	HashMap<String,Variable> variables = new HashMap<String,Variable>();
	HashMap<String, Implication> implications = new HashMap<String, Implication>();
	
	public Variable getVariable(String name){
		Variable got = variables.get(name);
		if (got == null){
			got = new Variable(name);
			variables.put(name, got);
			DebugPrint.print_debug("adding "+got);
			sentences.add(got);
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
			in_effect(implication.result);
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
			variable.ground_truth = false;
			retractVariable(variable);
			return;
		}
		else{
			Implication implication = implications.get(string);
			if(implication!=null){
				implication.ground_truth = false;
				retractImplication(implication);
				return;
			}
			
		}
		DebugPrint.print_debug("Sentence \""+string+"\" does not exist in knowledge base.");
	}
	
	public void retractImplication(Implication implication){
		DebugPrint.print_debug("Retracting "+implication);
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
		DebugPrint.print_debug("Retracting "+variable);
		variable.in_effect = false;
		for(Variable sentence: variables.values()){
			ArrayList<Justification> toRemove = new ArrayList<Justification>();
			for(Justification justification: sentence.justifications.values()){
				if(justification.required_variables.contains(variable))
					toRemove.add(justification);
			}
			for(Justification justification: toRemove){
				sentence.justifications.remove(justification.toString());
				if(sentence.ground_truth==false && sentence.justifications.size()==0){
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
				if(implication.ground_truth==false && implication.justifications.size()==0){
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
		for(Sentence sentence: sentences){
			if (sentence.in_effect){
				sb.append("\n");
				sb.append(sentence);
			}
		}
		return sb.toString();
	}

	public void add_sentence(String string) {
		String[] split = string.split("->");
		if (split.length==1){//variable
			Variable variable = getVariable(split[0]);
			in_effect(variable);
			variable.ground_truth = true;
			retract_conflicts(variable);
			for(Implication implication: implications.values())
				if(implication.in_effect)
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
			in_effect(implication);
			implication.ground_truth = true;
			implications.put(string, implication);
			DebugPrint.print_debug("adding "+implication);
			tryImplication(implication);
		}
	}
	
	void in_effect(Sentence sentence){
		if(sentences.contains(sentence)){
			if (sentence.in_effect==false){
				if(sentences.remove(sentence))
					sentences.add(sentence);
			}
		}else {
			sentences.add(sentence);
			DebugPrint.print_debug("new addition:" +sentence);
		}
		sentence.in_effect = true;
	}
	
	void retract_conflicts(Variable variable){
		String negative = "";
		if(variable.name.startsWith("-")){
			negative = variable.name.substring(1);
		}
		else{
			negative = "-"+variable.name;
		}
		if(variables.containsKey(negative))
		retractVariable(variables.get(negative));
		
	}
}

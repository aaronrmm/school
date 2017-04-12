import java.util.ArrayList;

public class RecursivePacker {

	public ArrayList<ArrayList<Object>> pack(ArrayList<Object>[] permutations){
		ArrayList<ArrayList<Object>> bags = new ArrayList<ArrayList<Object>>();
		recursive_pack(bags, new ArrayList<Object>(), permutations, 0);
		return bags;
	}
	
	public void recursive_pack(ArrayList<ArrayList<Object>> bags, ArrayList<Object> bag, ArrayList<Object>[]permutations, int depth){
		if (depth == permutations.length){
			bags.add(bag);
			return;
		}
		ArrayList<Object> options = permutations[depth];
		
		for(int i=1;i<options.size();i++){
			ArrayList<Object> newBag = (ArrayList<Object>) bag.clone();
			newBag.add(options.get(i));
			recursive_pack(bags, newBag, permutations, depth+1);
		}
		
		bag.add(options.get(0));
		recursive_pack(bags, bag, permutations, depth+1);
	}
}
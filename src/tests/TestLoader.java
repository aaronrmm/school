package tests;

import java.util.ArrayList;

import NLP.DataLoader;

public class TestLoader extends DataLoader{

	public TestLoader(int max_lines, double testing_perportion) {
		super(max_lines, testing_perportion);
	}
	
	@Override
	public ArrayList<String>getTrainingData(){
		ArrayList<String>list = new ArrayList<String>();
		list.add("Uwha!tU mate?\t0");
		list.add("UewrhatU mate?\t0");
		list.add("UewrhaggewratUmate?\t1");
		return list;
	}
	@Override
	public ArrayList<String>getTestingData(){
		ArrayList<String>list = new ArrayList<String>();
		list.add("UwhatUmate?\t0");
		return list;
		
	}

}

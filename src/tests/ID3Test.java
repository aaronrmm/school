package tests;

import weka.classifiers.trees.Id3;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class ID3Test {
	
	public static void main(String[]args){
		Id3 id3 = new Id3();
		try {
			DataSource source = new DataSource("my.arff");
			Instances instances = source.getDataSet();
			instances.add(new DenseInstance(1, new double[]{0,0,1,0}));
			for(Instance instance : instances){
				System.out.println(instance.toStringNoWeight());
			}
			instances.setClassIndex(instances.numAttributes()-1);
			id3.buildClassifier(instances);
			System.out.println(id3.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

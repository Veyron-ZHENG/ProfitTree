import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class ProfitTree {
	private HashMap<String, Vector<String>> dataset = new HashMap<String, Vector<String>>();
	private Vector<String> attributes = new Vector<String>();
	private double CLV = 0.0;
	private double COST = 0.0;

	public void run() {

		ProfitNode root = BuildTree(dataset, attributes, null);
		printTree(root);
	}

	public void Input(double CLV, double COST, String[] attributes, String[][] dataset ) {
		
		this.CLV = CLV;
		this.COST = COST;
		for(String a : attributes){
			this.attributes.add(a);
			this.dataset.put(a, new Vector<String>());
		}
		for(int i = 0; i != dataset.length; i++){
			for(int j = 0; j != dataset[i].length; j++){
				this.dataset.get(attributes[j]).add(dataset[i][j]);
			}
		}

	}

	private String CalculateVar(HashMap<String, Vector<String>> RemainData,
			Vector<String> RemainAtt, Vector<String> attribute_values,
			Vector<Double> profits) {

		Iterator iter = RemainData.entrySet().iterator();
		int i = 0;
		int i_max = 0;
		double var_max = 0.0;
		Vector<String> results = RemainData
				.get(RemainAtt.get(RemainAtt.size() - 1));
		while (iter.hasNext()) {// 这边要判断是否是result
			if(RemainAtt.get(i).equals(RemainAtt.get(RemainAtt.size()-1))){
				break;
			}
			Vector<String> values = RemainData.get(RemainAtt.get(i));
			Vector<String> temp_attribute_values = new Vector<String>();
			for (String value : values) {
				if (!temp_attribute_values.contains(value))
					temp_attribute_values.add(value);
			}
			double[] temp_profit = new double[temp_attribute_values.size()];
			for (int j = 0; j != temp_attribute_values.size(); j++) {
				temp_profit[j] = 0.0;
			}
			for (int j = 0; j != values.size(); j++) {
				if (results.get(j).equals("是"))
					temp_profit[temp_attribute_values.indexOf(values.get(j))] += (CLV - COST);
				if (results.get(j).equals("否"))
					temp_profit[temp_attribute_values.indexOf(values.get(j))] -= COST;
			}
			double average = 0.0;
			for (int j = 0; j != temp_profit.length; j++) {
				average += (average / temp_profit.length);
			}
			double var = 0.0;
			for (int j = 0; j != temp_profit.length; j++) {
				var += (Math.pow(temp_profit[j] - average, 2) / temp_profit.length);
			}
			if (var >= var_max) {
				var_max = var;
				i_max = i;
				attribute_values.clear();
				profits.clear();
				for (int j = 0; j != temp_profit.length; j++) {
					attribute_values.add(temp_attribute_values.get(j));
					profits.add(new Double(temp_profit[j]));
				}
			}
			i++;
		}

		return RemainAtt.get(i_max);
	}

	private ProfitNode BuildTree(HashMap<String, Vector<String>> RemainData,
			Vector<String> RemainAtt, ProfitNode p) {

		if (p == null) 
			p = new ProfitNode();
		

		if (RemainAtt.size() == 1)
			return p;

		
		Vector<String> attribute_value = new Vector<String>();
		Vector<Double> profits = new Vector<Double>();
		String TargetAtt = this.CalculateVar(RemainData, RemainAtt,
				attribute_value, profits);
		p.setAttribute(TargetAtt);
		for (int i = 0; i != attribute_value.size(); i++) {
			ProfitNode child = new ProfitNode();
			child.setprofit(profits.get(i).doubleValue());
			child.setAttribute_value(attribute_value.get(i));
			p.addChild(child);
		}
		RemainAtt.remove(TargetAtt);
		for (int i = 0; i != p.getChild().size(); i++) {
			HashMap<String, Vector<String>> NewDataSet = new HashMap<String, Vector<String>>();
			for (int j = 0; j != RemainData.get(TargetAtt).size(); j++) {
				if (NewDataSet.isEmpty()) {
					for (int k = 0; k != RemainAtt.size(); k++) {
						// if(RemainAtt.get(k).equals(TargetAtt)) continue;
						NewDataSet.put(RemainAtt.get(k), new Vector<String>());
					}
				}
				if (RemainData.get(TargetAtt).get(j)
						.equals(p.getChild().get(i).getAttribute_value())) {
					for (int k = 0; k != NewDataSet.size(); k++) {
						NewDataSet.get(RemainAtt.get(k)).add(
								RemainData.get(RemainAtt.get(k)).get(j));
					}
				}
			}
			this.BuildTree(NewDataSet, RemainAtt, p.getChild().get(i));
		}

		return p;
	}
	
	private void printTree(ProfitNode p){
		System.out.println("profit:"+p.getprofit());
		System.out.println("attribute_value:"+p.getAttribute_value());
		System.out.println("attribute:"+p.getAttribute());
		System.out.println("---------------------------------------------\n");
		if(p.getChild() != null){
			for(int i = 0; i != p.getChild().size(); i ++){
				printTree(p.getChild().get(i));
			}
		}
			
	}
}

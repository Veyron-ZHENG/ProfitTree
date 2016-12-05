import java.util.Vector;

public class ProfitNode {
	private double profit = 0;
	private Vector<ProfitNode> children = null;
	private String attribute_value = null;
	private String attribute = null;

	public void setAttribute_value(String attribute_value){
		this.attribute_value = attribute_value;
	}
	
	public String getAttribute_value(){
		return this.attribute_value;
	}
	
	public double getprofit() {
		return this.profit;
	}

	public void setprofit(double profit) {
		this.profit = profit;
	}

	public void addChild(ProfitNode child) {
		if (this.children == null)
			this.children = new Vector<ProfitNode>();
		this.children.add(child);
	}

	public Vector<ProfitNode> getChild() {
		return this.children;
	}

	public String getAttribute() {
		return this.attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}

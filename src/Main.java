
public class Main {
	public static void main(String args[]){
		String[] attribute = {"A","B","C","D","R"};
		String[][] dataset = {{"a1","b1","c1","d1","��"},
				              {"a1","b2","c2","d2","��"},
				              {"a2","b1","c1","d2","��"},
				              {"a2","b2","c1","d2","��"},
				              {"a1","b1","c1","d1","��"},
				              {"a1","b1","c2","d1","��"},
				              {"a2","b2","c2","d1","��"}
							};
		ProfitTree tree = new ProfitTree();
		tree.Input(200.0,20.0,attribute,dataset);
		tree.run();
	}

}

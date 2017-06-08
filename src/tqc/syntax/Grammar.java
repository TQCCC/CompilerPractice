package tqc.syntax;

public class Grammar {
	
	private static int MAX_SIZE = 50;
	
	private Production[] productions;
	private int count = 0;
	
	public Grammar(){
		productions = new Production[MAX_SIZE];
	}
	
	
	public int count(){
		return count;
	}
	
	public Production indexOf(int i) {
		if (i>=count) {
			return null;
		}
		return productions[i];
	}
	
}

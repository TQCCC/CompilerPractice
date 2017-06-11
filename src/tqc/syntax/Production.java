package tqc.syntax;

import java.util.Vector;

public class Production {
	private Vector<Symbol> left = new Vector<>();
	private Vector<Symbol> right = new Vector<>();
	
	//Set lowercase letter as terminator, uppercase as nonternimator.
	public Production(String l, String r) {
		
		for(int i=0;i<l.length();i++){
			char ch = l.charAt(i);
			Symbol s = new Terminator(ch);
			
			if (Character.isUpperCase(ch)) {
				s = new NonTerminator(ch);
			}else if (Character.isLowerCase(ch)) {
				s = new Terminator(ch);
			}
			
			left.add(s);
		}
		
		for(int i=0;i<r.length();i++){
			char ch = r.charAt(i);
			Symbol s = new Terminator(ch);
			
			if (Character.isUpperCase(ch)) {
				s = new NonTerminator(ch);
			}else if (Character.isLowerCase(ch)) {
				s = new Terminator(ch);
			}
			
			right.add(s);
		}
		
	}
	
	public Vector<Symbol> getLeft() {
		return left;
	}
	public Vector<Symbol> getRight() {
		return right;
	}
	
	@Override
	public String toString() {
		return "[" + left.toString() + "-->" + right.toString() + "]";
	}
}

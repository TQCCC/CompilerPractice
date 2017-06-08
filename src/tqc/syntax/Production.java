package tqc.syntax;

public class Production {
	
	private char left;
	private char[] right;
	
	public Production(char left, char[] right){
		this.left = left;
		this.right = right;
	}
	
	public void setLeft(char left) {
		this.left = left;
	}
	public char getLeft() {
		return left;
	}
	public void setRight(char[] right) {
		this.right = right;
	}
	public char[] getRight() {
		return right;
	}
	
}

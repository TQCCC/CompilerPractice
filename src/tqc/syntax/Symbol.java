package tqc.syntax;

public class Symbol {
	private String value = "";
	
	public Symbol(String v) {
		this.value = v;
	}
	
	public Symbol(char v){
		this(String.valueOf(v));
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}

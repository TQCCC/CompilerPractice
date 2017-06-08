package tqc.lex;
/**
 * @author TQC
 * */
public class LexItem {
	private String keyWord;
	private Object value;
	
	public LexItem(String keyWord, Object o) {
		this.keyWord = keyWord;
		this.value = o;
	}
	
	public String getKeyWord() {
		return keyWord;
	}
	
	public Object getValue() {
		return value;
	}
	
	@Override
	public String toString() {

		return "[kw: " + keyWord + "|v: " + value+"]";
	}
}

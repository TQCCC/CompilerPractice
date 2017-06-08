package tqc.lex;

import java.util.HashMap;
import java.util.Vector;

public class StateMatrix {

	public static int MAX_SIZE = 50;
	public static String STATE = "state";
    public static String NULL = "null";
	
	private HashMap<String, Object>[] matrixArray;
	private int count = 0;
	private Vector<String> inputChars = new Vector<String>();
	
	@SuppressWarnings("unchecked")
	public StateMatrix(Vector<String> inputChars){
		matrixArray = new HashMap[MAX_SIZE];
		this.inputChars = inputChars;
	}
	
	public StateMatrix addRow(HashMap<String, Object> row){
		
		matrixArray[count] = row;
		count++;
		return this;
	}
	
	public void log(){
		
		System.out.println("state matrix-----------------");
		System.out.println("array size: " + count);
		
		System.out.print(STATE + "-----");
		for(String s : inputChars){
			System.out.print(s + "--");
		}
		System.out.println();
		
		for(int i=0; i<count; i++){
			
			System.out.print(matrixArray[i].get(STATE)+":");
			for(String input : inputChars){
				System.out.print(matrixArray[i].get(input)+"-");
			}
			System.out.println();
		}
		System.out.println("-----------------");
	}
	
	public Vector<String> getInputChars() {
		return inputChars;
	}
	
	public void setInputChars(Vector<String> inputChars) {
		this.inputChars = inputChars;
	}
	
	public int count(){
		return count;
	}
	
	public HashMap<String, Object> indexOf(int i){
		if (i>=count) {
			return null;
		}
		return matrixArray[i];
	}
	
}

package tqc.syntax.lr;

import java.util.Hashtable;
import java.util.Vector;

public class LRTable {
	
	public Vector<Hashtable<String, TableItem>> table = new Vector<>();

	public void addOneRow(Hashtable<String, TableItem> hashtable){
		table.add(hashtable);
	}
	
	public void log(Vector<String> symbols) {

		System.out.print("State   |");
		
		for(int i=0;i<symbols.size();i++) {
			String string = symbols.get(i);
			System.out.printf("%-8s|", string);
		}
		System.out.println();
		
		for(int i=0;i<table.size();i++){
			Hashtable<String, TableItem> row = table.get(i);
			System.out.printf("%-8s|", i);
			
			for (int j=0;j<symbols.size();j++) {
				TableItem tableItem = (TableItem) row.get(symbols.get(j));
				if (tableItem.getNextState()==-1) {
					System.out.printf("%-8s|","[" + tableItem.type + ",-]");
				}else{
					System.out.printf("%-8s|", tableItem);
				}
			}
			System.out.println();
		}
		
	}
	

	public static class TableItem{
		
		public static char TYPE_SHIFT = 's'; 
		public static char TYPE_REDUCE = 'r';
		public static char TYPE_ACC = 'a';
		public static char TYPE_ERR = '-';
		
		private char type = TYPE_ERR;
		private int nextState = -1;
		
		public TableItem(char t, int s){
			this.type = t;
			this.nextState = s;
		}
		
		public char getType() {
			return type;
		}
		public int getNextState() {
			return nextState;
		}
		
		@Override
		public String toString() {
			return "[" + type + "," + nextState + "]";
		}
	}

}



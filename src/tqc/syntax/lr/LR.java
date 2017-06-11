package tqc.syntax.lr;

import java.util.Hashtable;
import java.util.Vector;

import tqc.syntax.lr.LRTable.TableItem;

public class LR {
	
	static LRTable lrTable = new LRTable();

	public static void main(String[] args) {

		Hashtable<String, TableItem> r0 = new Hashtable<>();
		Hashtable<String, TableItem> r1 = new Hashtable<>();
		Hashtable<String, TableItem> r2 = new Hashtable<>();
		Hashtable<String, TableItem> r3 = new Hashtable<>();
		Hashtable<String, TableItem> r4 = new Hashtable<>();
		Hashtable<String, TableItem> r5 = new Hashtable<>();
		Hashtable<String, TableItem> r6 = new Hashtable<>();
		Hashtable<String, TableItem> r7 = new Hashtable<>();
		Hashtable<String, TableItem> r8 = new Hashtable<>();
		Hashtable<String, TableItem> r9 = new Hashtable<>();
		Hashtable<String, TableItem> r10 = new Hashtable<>();
		Hashtable<String, TableItem> r11 = new Hashtable<>();
		initOneRow(r0);
		initOneRow(r1);
		initOneRow(r2);
		initOneRow(r3);
		initOneRow(r4);
		initOneRow(r5);
		initOneRow(r6);
		initOneRow(r7);
		initOneRow(r8);
		initOneRow(r9);
		initOneRow(r10);
		initOneRow(r11);
		
		
		r0.put("i", new TableItem('s', 5));
		r0.put("(", new TableItem('s', 4));
		r0.put("E", new TableItem('-', 1));
		r0.put("T", new TableItem('-', 2));
		r0.put("F", new TableItem('-', 3));

		r1.put("+", new TableItem('s', 6));		
		r1.put("#", new TableItem('a', -1));


		r2.put("+", new TableItem('r', 2));	
		r2.put("*", new TableItem('s', 7));	
		r2.put(")", new TableItem('r', 2));	
		r2.put("#", new TableItem('r', 2));	
		
		r3.put("+", new TableItem('r', 4));	
		r3.put("*", new TableItem('r', 4));	
		r3.put(")", new TableItem('r', 4));	
		r3.put("#", new TableItem('r', 4));	


		r4.put("i", new TableItem('s', 5));	
		r4.put("(", new TableItem('s', 4));	
		r4.put("E", new TableItem('-', 8));	
		r4.put("T", new TableItem('-', 2));	
		r4.put("F", new TableItem('-', 3));	

		r5.put("+", new TableItem('r', 6));	
		r5.put("*", new TableItem('r', 6));	
		r5.put(")", new TableItem('r', 6));	
		r5.put("#", new TableItem('r', 6));	


		r6.put("i", new TableItem('s', 5));	
		r6.put("(", new TableItem('s', 4));	
		r6.put("T", new TableItem('-', 9));	
		r6.put("F", new TableItem('-', 3));	
		
		r7.put("i", new TableItem('s', 5));	
		r7.put("(", new TableItem('s', 4));	
		r7.put("F", new TableItem('-', 10));	

		r8.put("+", new TableItem('s', 6));	
		r8.put(")", new TableItem('s', 11));

		r9.put("+", new TableItem('r', 1));	
		r9.put("*", new TableItem('s', 7));	
		r9.put(")", new TableItem('r', 1));	
		r9.put("#", new TableItem('r', 1));	
		
		r10.put("+", new TableItem('r', 3));	
		r10.put("*", new TableItem('r', 3));	
		r10.put(")", new TableItem('r', 3));	
		r10.put("#", new TableItem('r', 3));	

		r11.put("+", new TableItem('r', 5));	
		r11.put("*", new TableItem('r', 5));	
		r11.put(")", new TableItem('r', 5));	
		r11.put("#", new TableItem('r', 5));	

		lrTable.log(mysymbols);
	}

	public static Vector<String> mysymbols = new Vector<>();

	static {
		mysymbols.add("i");
		mysymbols.add("+");
		mysymbols.add("*");
		mysymbols.add("(");
		mysymbols.add(")");
		mysymbols.add("#");

		mysymbols.add("E");
		mysymbols.add("T");
		mysymbols.add("F");
	}

	public static void initOneRow(Hashtable<String, TableItem> hashtable) {
		for (int i = 0; i < mysymbols.size(); i++) {
			hashtable.put(mysymbols.get(i), new TableItem('-', -1));
		}
		lrTable.addOneRow(hashtable);
	}

}

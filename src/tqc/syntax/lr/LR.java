package tqc.syntax.lr;

import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

import tqc.syntax.Grammar;
import tqc.syntax.Production;
import tqc.syntax.Symbol;
import tqc.syntax.lr.LRTable.TableItem;

/**
 * @date 6-12-2017
 * @author TQC
 * */

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
		
		Production p1 = new Production("E", "E+T");
		Production p2 = new Production("E", "T");
		Production p3 = new Production("T", "T*F");
		Production p4 = new Production("T", "F");
		Production p5 = new Production("F", "(E)");
		Production p6 = new Production("F", "i");
		Grammar grammar = new Grammar();

		grammar.addProduction(p1);
		grammar.addProduction(p2);
		grammar.addProduction(p3);
		grammar.addProduction(p4);
		grammar.addProduction(p5);
		grammar.addProduction(p6);
		
		System.out.println(grammar);
		analysis("3*(2+4)", lrTable, grammar);
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
	
	public static void analysis(String inputStr, LRTable lrTable, Grammar grammar){
		
		System.out.println("stateStack                    | charStack                    | valueStack                   | Input");
		
		Stack<Integer> stateStack = new Stack<>();
		Stack<Character> charStack = new Stack<>();
		
		Stack<Integer> valueStack = new Stack<>();	//To calculate the value.
		
		int c = 0;		//flag;
		String str = inputStr + "#";
		int value = 0;
		
		stateStack.push(0);
		charStack.push('#');
		valueStack.push(0);	//0 is placeholder
		
		//Output first row
		System.out.printf("%-30s|%-30s|%-30s|%-30s|\n",stateStack, charStack, valueStack, str.substring(c));
		
		while(c<str.length()){
			
			int s = stateStack.peek();
			char ch = str.charAt(c);
			
			if (Character.isDigit(ch)) {
				value = Integer.parseInt(String.valueOf(ch));
				ch = 'i';
			}else{
				value = 0;	//placeholder
			}
			
			Hashtable<String, TableItem> row = lrTable.table.get(s);
			TableItem tableItem = row.get(String.valueOf(ch));
			if (tableItem == null) {
				System.out.println("Error: illegal input char: " + ch);
				return;
			}
			
			if (tableItem.getType() == 's') {
				stateStack.push(tableItem.getNextState());
				charStack.push(ch);

				valueStack.push(value);
				
				c++;
				
				System.out.printf("%-30s|%-30s|%-30s|%-30s|\n",stateStack, charStack, valueStack, str.substring(c));
				
			}else if (tableItem.getType() == 'r') {
				
				int proIndex = tableItem.getNextState()-1;
				
				int rightlen = grammar.productions.get(proIndex).getRight().size();
			    Vector<Symbol> left = grammar.productions.get(proIndex).getLeft();
				
			    //Here to calculate the value.
			    if (proIndex == 0) {//+
					Integer leftv = valueStack.pop();
					valueStack.pop();
					Integer rightv = valueStack.pop();
					
					valueStack.push((leftv+rightv));
					
				}else if (proIndex == 2) {//*
					Integer leftv = valueStack.pop();
					valueStack.pop();
					Integer rightv = valueStack.pop();
					
					valueStack.push((leftv*rightv));
					
				}else if (proIndex == 4) {//()
					
					valueStack.pop();
					Integer v = valueStack.pop();
					valueStack.pop();
					valueStack.push(v);
				}
			    
			    
				for(int i=0;i<rightlen;i++){
					
					if (stateStack.isEmpty() || charStack.isEmpty()) {
						System.out.println("Error: Empty stack");
						return;
					}
					stateStack.pop();
					charStack.pop();
				}
				charStack.push(left.get(0).toString().charAt(0));
				
				int s_ = stateStack.peek();
				int ns = lrTable.table.get(s_).get(left.get(0).toString()).getNextState();
				
				stateStack.push(ns);
				
				System.out.printf("%-30s|%-30s|%-30s|%-30s|\n",stateStack, charStack, valueStack, str.substring(c));
				
			}else if (tableItem.getType() == 'a') {
				System.out.println("Success");
				System.out.println("The value is "+ valueStack.peek());
				break;
			}else {	//Error
				System.out.println("Error: " + tableItem);
				break;
			}
			
		}
	}// End of function analysis

}

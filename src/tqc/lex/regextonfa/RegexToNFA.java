package tqc.lex.regextonfa;

import java.util.*;
import tqc.graph.*;

/**
 * Convert Regular expression to non-deterministic finite automata.
 * 4/12/2017
 * @author TQC
 * */
public class RegexToNFA {
	
	public static void main(String[] args) throws Exception{
		
		System.out.println("App starts...");
		
		FAGraph graph = ToNFA("a*b");
		graph.log();
	}
	
	public static String DOT = ".";
	public static String STAR = "*";
	public static String LBRACE = "(";
	public static String RBRACE = ")";
	public static String OR = "|";
	public static String END = "#";
	
	public static String EMPTY = "empty";
	
	/**
	 * @param regex the given regular expression.
	 * @return returns the non-deterministic finite automata according to given regular expression.
	 * @author TQC
	 * */
	
	public static FAGraph ToNFA(String regex) throws Exception{
		
		FAGraph graph = new FAGraph();
		
		if (regex.isEmpty()) {
			return graph;
		}
		
		StringBuilder sb = new StringBuilder(regex);
		int n = -1;
		int f = 0;
		
		char pre = 0;
		char ch = 0;
		Stack<GraphNode> stateStack = new Stack<GraphNode>();
		Stack<String> operatorStack = new Stack<String>();
		
		operatorStack.push(END);
		sb.append(END);
		
		GraphNode node = new GraphNode(++n);graph.addNode(node);
		
		ch = sb.charAt(f++);
		
		while(f<=sb.length()){
			
			if (Character.isLetterOrDigit(ch)) {
			
				GraphNode ns = new GraphNode(++n);graph.addNode(ns);
				GraphNode ne = new GraphNode(++n);graph.addNode(ne);

				ns.addEdgeNode(new EdgeNode(ne.getIndex(), Character.toString(ch)));
				
				stateStack.push(ns);		//Push start node first.
				stateStack.push(ne);
				
				
				if (Character.isLetterOrDigit(pre)) {
					operatorStack.push(DOT);
				}				
				
				pre = ch;
				ch = sb.charAt(f++);
				
			}else{
				
				if (CmpPriority(Character.toString(ch), operatorStack.peek()) == 1) {
					operatorStack.push(Character.toString(ch));
					
					pre = ch;
					ch = sb.charAt(f++);
					
				}else if (CmpPriority(Character.toString(ch), operatorStack.peek()) == 2) {
					String op = operatorStack.pop();
					if (op.equals(DOT)) {
						
						if (stateStack.size()<4) {
							System.out.println("Regex formation error in '" + op + "'.");
							continue;
						}
						
						GraphNode e_a = stateStack.pop();
						GraphNode s_a = stateStack.pop();
						
						GraphNode e_b = stateStack.pop();
						GraphNode s_b = stateStack.pop();
						
						e_b.addEdgeNode(new EdgeNode(s_a.getIndex(),EMPTY));
						
						stateStack.push(s_b);
						stateStack.push(e_a);
						
					}else if (op.equals(OR)) {
						
						if (stateStack.size()<4) {
							System.out.println("Regex formation error in '" + op + "'.");
							continue;
						}
						
						GraphNode e_a = stateStack.pop();
						GraphNode s_a = stateStack.pop();
						
						GraphNode e_b = stateStack.pop();
						GraphNode s_b = stateStack.pop();
						
						GraphNode ns = new GraphNode(++n);graph.addNode(ns);
						GraphNode ne = new GraphNode(++n);graph.addNode(ne);
						
						ns.addEdgeNode(new EdgeNode(s_a.getIndex(),EMPTY));
						ns.addEdgeNode(new EdgeNode(s_b.getIndex(),EMPTY));
						
						e_a.addEdgeNode(new EdgeNode(ne.getIndex(),EMPTY));
						e_b.addEdgeNode(new EdgeNode(ne.getIndex(),EMPTY));
						
						stateStack.push(ns);
						stateStack.push(ne);
						
					}else if (op.equals(STAR)) {
						
						if (stateStack.size()<2) {
							System.out.println("Regex formation error in '" + op + "'.");
							continue;
						}
						
						GraphNode e_a = stateStack.pop();
						GraphNode s_a = stateStack.pop();
						
						GraphNode ns = new GraphNode(++n);graph.addNode(ns);
						GraphNode ne = new GraphNode(++n);graph.addNode(ne);
						
						ns.addEdgeNode(new EdgeNode(s_a.getIndex(),EMPTY));
						e_a.addEdgeNode(new EdgeNode(ne.getIndex(),EMPTY));
						e_a.addEdgeNode(new EdgeNode(s_a.getIndex(),EMPTY));
						ns.addEdgeNode(new EdgeNode(ne.getIndex(),EMPTY));
						
						stateStack.push(ns);
						stateStack.push(ne);
					}

					
				}else if (CmpPriority(Character.toString(ch), operatorStack.peek()) == 0) {

					if (operatorStack.peek().equals(LBRACE) && Character.toString(ch).equals(RBRACE)) {
						operatorStack.pop();
						
						pre = ch;
						ch = sb.charAt(f++);
					}else if (operatorStack.peek().equals(END) && Character.toString(ch).equals(END)) {
						
						break;
					}else{
						operatorStack.push(Character.toString(ch));		//Push back or a endless loop.
						
						pre = ch;
						ch = sb.charAt(f++);
					}
					
				}
			}// End of not digit.
			
			
		}// End of while 
		
		if (stateStack.size()<2) {
			return graph;
		}
		
		GraphNode e_a = stateStack.pop();
		GraphNode s_a = stateStack.pop();
			
		node.addEdgeNode(new EdgeNode(s_a.getIndex(),EMPTY));
		
		graph.getBeginningNodes().add(node);
		graph.getEndingNodes().add(e_a);
		
		return graph;
	}
	
	/**
	 * @return Return 1 if the priority of o1 greater than o2 or return 2<br>
	 * 			Return 0 if the priority of the two is the same.
	 * @author TQC
	 * */
	public static int CmpPriority(String o1, String o2){
		//*>.>|>#
		
		int p1 = 0;
		int p2 = 0;
		
		if (o1.equals("*")) {
			p1 = 3;
		}else if (o1.equals(".")) {
			p1 = 2;
		}else if (o1.equals("|")) {
			p1 = 1;
		}else if ( o1.equals("(") || o1.equals(")")) {
			p1 = 0;
		}else if (o1.equals("#")) {
			p1 = -1;
		}
		if (o2.equals("*")) {
			p2 = 3;
		}else if (o2.equals(".")) {
			p2 = 2;
		}else if (o2.equals("|")) {
			p2 = 1;
		}else if ( o2.equals("(") || o2.equals(")")) {
			p2 = 0;
		}else if (o2.equals("#")) {
			p2 = -1;
		}
		
		
		if (p1>p2) {
			return 1;
		}else if (p1<p2) {
			return 2;
		}else {
			return 0;
		}
		
	}
	
	public static void log(char ch, char pre, Stack<String> operatorStack, Stack<String> stateStack, int f){
		System.out.println("-----");
		System.out.println("pre: " + pre);
		System.out.println("ch: " + ch);
		System.out.println("f: " + f);
		System.out.println("sStackTop: " + stateStack.peek());
		System.out.println("oStackTop: " + operatorStack.peek());
		System.out.println("Cmp: " + CmpPriority(Character.toString(ch), operatorStack.peek()));
		
	}
	
}

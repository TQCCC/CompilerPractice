package tqc.lex.dfa;

import java.io.*;
import java.util.*;

import tqc.graph.FAGraph;
import tqc.lex.*;
import tqc.lex.minidfa.MiniDFA;
import tqc.lex.nfatodfa.NFAToDFA;
import tqc.lex.regextonfa.RegexToNFA;

/**
 * Lexical analyazer.
 * DFA to token stream.
 * @author TQC
 * */
public class DFA {

	public static void main(String[] args) throws Exception{
		
		Vector<String> inputChars = new Vector<String>();
		inputChars.add("a");
		inputChars.add("b");
		inputChars.add("c");
		
		FAGraph nfaGraph = RegexToNFA.ToNFA("a*");
		System.out.println("nfa:----------");
		nfaGraph.log();
		
		FAGraph graph = NFAToDFA.toDFA_Graph(nfaGraph, inputChars);
		
		System.out.println("dfa:----------");
		graph.log();
		
		System.out.println("mini dfa:---------");
		
		MiniDFA.toMiniDFA(graph).log();
//		Vector<LexItem> r = getByDFA(graph, "./test.txt");
//		
//		for(LexItem i : r){
//			System.out.println(i);
//		}
	}
	
	/**
	 * 
	 * @Algorithm
	 * From DFA to token stream.<br>
	 * Algorithm procedure: <br>
	 * 
	 * Initial the source file. <br>
	 * Initial the current state, set it initial state. <br>
	 * Initial the strToken.<br>
	 * 
	 * Read the first character from the source file.<br>
	 * 
	 * Do While The source file has more character:<br>
	 * begin
	 *		 
	 * 		While The Matrix[currentState][inputState] has next state
	 * 		begin
	 * 			Append the current character onto the strToken, 
	 * 			if the ch is not space, newline or tab.		Attention!	
	 * 			
	 * 			Read the next character from the source file.
	 * 			Set current state as the next state.
	 * 		end
	 *  	
	 * Judge the type of the strToken.
	 * Generate a correct LexItem.
	 *  	
	 * Set current state as the initial state.
	 * Clear the strToken. 
	 * 	
	 * end 
	 * 
	 * 
	 * @author TQC 4/11/2017
	 */
	
	/**
	 * 
	 * @author TQC
	 * */
	public static Vector<LexItem> getByDFA(StateMatrix matrix, String fileName) throws IOException{
		LexicalAnalyzer.initKeyWordVector();
		
		Vector<LexItem> result = new Vector<LexItem>();
		RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
		StringBuilder strToken = new StringBuilder();
		
		int ch = 0;
		int state = 0;
		String inputState = "";
		
		ch = raf.read();
		inputState = Character.toString((char)ch);
		
		do {
			
			while(matrix.indexOf(state).get(inputState) != null){
				
				strToken.append((char)ch);
				
				state = (int)matrix.indexOf(state).get(inputState);
				
				ch = raf.read();
				inputState = Character.toString((char)ch);
				
				if (ch == -1) {
					break;
				}
			}
			
			if (LexicalAnalyzer.Reserve(strToken.toString()) != LexicalAnalyzer.NOTKEYWORD) {
				//Key word
				result.add(new LexItem(strToken.toString(), "-"));		//key word
			}else{
				//ID
				if (strToken.length()!=0) {
					result.add(new LexItem(LexicalAnalyzer.ID, strToken.toString()));
				}
				
			}
			
			if (matrix.indexOf(state).get(inputState)==null && ch!=-1) {
				System.out.println("Error token:" + inputState);
			}
			
			state = 0;
			strToken.delete(0, strToken.length());
			
			ch = raf.read();
			inputState = Character.toString((char)ch);
			
		} while (ch!=-1);
		
		raf.close();
		return result;
	}
	
	public static Vector<LexItem> getByDFA(FAGraph graph, String fileName) throws IOException{
		
		LexicalAnalyzer.initKeyWordVector();
		
		Vector<LexItem> result = new Vector<LexItem>();
		RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
		StringBuilder strToken = new StringBuilder();
		
		int ch = 0;
		int state = 0;
		String inputState = "";
		
		ch = raf.read();
		inputState = Character.toString((char)ch);
		
		do{
			while(graph.getGraphNodesViaOneInput(state, inputState).size() != 0){
				
				strToken.append((char)ch);
				
				state = (int)graph.getGraphNodesViaOneInput(state, inputState).get(0).getIndex();
				
				ch = raf.read();
				inputState = Character.toString((char)ch);
				
				if (ch == -1) {
					break;
				}
				
			}
			
			if (LexicalAnalyzer.Reserve(strToken.toString()) != LexicalAnalyzer.NOTKEYWORD) {
				//Key word
				result.add(new LexItem(strToken.toString(), "-"));		//key word
			}else{
				//ID
				if (strToken.length()!=0) {
					result.add(new LexItem(LexicalAnalyzer.ID, strToken.toString()));
				}
				
			}
			
			if (graph.getGraphNodesViaOneInput(state, inputState).size() == 0 && ch!=-1) {
				System.out.println("Error token:" + inputState);
			}
			
			state = 0;
			strToken.delete(0, strToken.length());
			
			ch = raf.read();
			inputState = Character.toString((char)ch);
			
		}while(ch!=-1);
		
		raf.close();
		return result;
	}
	
	
}

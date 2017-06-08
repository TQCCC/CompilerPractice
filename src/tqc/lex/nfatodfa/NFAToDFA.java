package tqc.lex.nfatodfa;

import java.util.*;
import tqc.graph.*;
import tqc.lex.StateMatrix;
import tqc.lex.minidfa.MiniDFA;

/**
 * 
 * A key step of a lexcial analyzer.<br>
 * Convert non-deterministic finite automata to deterministic finite automata.
 * Mr.Liuneng didn't give me the algorithm description. 13/4/2017
 * 
 * @author TQC
 */

/**
 * 
 * @author TQC
 * @Algorithm
 * Initial the matrix array.
 * Initial the beginning vertex set, set it to the left top of the matrix array.
 * Initial setQueue, to get the new set in every cycle.
 * 
 * WHILE(TRUE){
 * 		Dequeue get the new set.
 * 		For every input Characters; For every vertex in current set:
 * 		
 * 		BEGIN
 * 		
 * 		Add all the vertex that can reach by current vertex via one current input.
 * 		
 * 		Empty handle:
 *		
 *		Add all the vertex that can reach by current vertex via one or more empty inputs. 
 * 		Add all the vertex that can reach by those has been reached via one or more empty inputs.
 * 
 * 		END
 * 
 * 		For all new state:
 * 		BEGIN
 *		
 *		Does the matrix array have this set?
 *		YES:
 *			Next new state.
 *		NO:
 *			Add this new state, enqueue this new set.
 * 		END
 * 		
 * 
 * 		IF the setQueue is empty, stop the cycle.
 * }
 * 
 * */

public class NFAToDFA {
	
	private static Vector<Integer> endings = new Vector<>();
	
	public static void main(String[] args) throws Exception{
		System.out.println("App starts...");
		
		/* Test data Example int text book p49 */
		String stra = "a";
		String strb = "b";
		FAGraph	nfaGraph = new FAGraph();
		
		int X = 0, Y = 7;
		
		GraphNode nx = new GraphNode(X);nfaGraph.addNode(nx);
		GraphNode n1 = new GraphNode(1);nfaGraph.addNode(n1);
		GraphNode n2 = new GraphNode(2);nfaGraph.addNode(n2);
		GraphNode n3 = new GraphNode(3);nfaGraph.addNode(n3);
		GraphNode n4 = new GraphNode(4);nfaGraph.addNode(n4);
		GraphNode n5 = new GraphNode(5);nfaGraph.addNode(n5);
		GraphNode n6 = new GraphNode(6);nfaGraph.addNode(n6);
		GraphNode ny = new GraphNode(Y);nfaGraph.addNode(ny);
		
		nx.addEdgeNode(new EdgeNode(n5.getIndex(), FAGraph.EMPTY));
		
		n5.addEdgeNode(new EdgeNode(n5.getIndex(), stra));
		n5.addEdgeNode(new EdgeNode(n5.getIndex(), strb));
		n5.addEdgeNode(new EdgeNode(n1.getIndex(), FAGraph.EMPTY));
		
		n1.addEdgeNode(new EdgeNode(n3.getIndex(), stra));
		n1.addEdgeNode(new EdgeNode(n4.getIndex(), strb));
		
		n3.addEdgeNode(new EdgeNode(n2.getIndex(), stra));
		n4.addEdgeNode(new EdgeNode(n2.getIndex(), strb));
		
		n2.addEdgeNode(new EdgeNode(n6.getIndex(), FAGraph.EMPTY));
		
		n6.addEdgeNode(new EdgeNode(n6.getIndex(), stra));
		n6.addEdgeNode(new EdgeNode(n6.getIndex(), strb));
		n6.addEdgeNode(new EdgeNode(ny.getIndex(), FAGraph.EMPTY));
		
		nfaGraph.getBeginningNodes().add(nx);
		nfaGraph.getEndingNodes().add(ny);
		
		System.out.println("nfa-----");
		nfaGraph.log();
		Vector<String> inputChars = new Vector<String>();
		inputChars.add(stra);
		inputChars.add(strb);
		
		System.out.println("dfa-----");
		FAGraph graph = toDFA_Graph(nfaGraph, inputChars);
		graph.log();
		
		System.out.println("to miniDFA: ");
		MiniDFA.toMiniDFA(graph).log();
		
	}
	
	public static FAGraph toDFA_Graph(FAGraph nfaGraph, Vector<String> inputChars) throws Exception{
		
		FAGraph graph = matrixToFAGraph(ToDFA_Matrix(nfaGraph, inputChars));
		
		graph.getBeginningNodes().add(graph.indexOf(0));
		for(Integer idx : endings){
			graph.getEndingNodes().add(graph.indexOf(idx));
		}
		graph.setInputChars(inputChars);
		
		return graph;
	}
	
	/**
	 * Completed: 14/4/2017
	 * @author TQC
	 * */
	public static StateMatrix ToDFA_Matrix(FAGraph nfaGraph, Vector<String> inputChars) throws Exception{
		endings.clear();
		StateMatrix matrix = new StateMatrix(inputChars);
		
		//加入左上角的初始化状态集合
		HashMap<String, Object> hm = new HashMap<String, Object>();		//行
		Vector<GraphNode> gnv = nfaGraph.getGraphNodesViaXEmptyInputs(0);	//图节点集
		Vector<Integer> stateSet = getIdxsByGraphNodes(gnv);			//当前索引集合
		
		
		LinkedList<Vector<Integer>> setQueue = new LinkedList<Vector<Integer>>();
		setQueue.add(stateSet);
		
		//序号转换表
		int count = 0;	
		HashMap<Vector<Integer>, Integer> convertMap = new HashMap<Vector<Integer>, Integer>();
		convertMap.put(stateSet, count);
		
		while(!setQueue.isEmpty()){
			
			stateSet = setQueue.removeFirst();
			
			hm = new HashMap<String, Object>();		//Really important.
			hm.put(StateMatrix.STATE, convertMap.get(stateSet));
			for(String input : inputChars){
				hm.put(input, null);
			}
			matrix.addRow(hm);
			
			
			for(String input : inputChars){
				
				Vector<Integer> newStateSet = new Vector<Integer>();
				for(int idx : stateSet){
					
					Vector<GraphNode> reachGns = nfaGraph.getGraphNodesViaOneInput(idx, input);

					if (reachGns.size()!=0) {
						//加入输入此符号所能到达所有节点
						newStateSet.addAll(getIdxsByGraphNodes(reachGns));
						//加入从 有此输入符号的节点 经过若干空弧所到达节点
						Vector<Integer> r1 = getIdxsByGraphNodes(nfaGraph.getGraphNodesViaXEmptyInputs(idx));
						if (!(r1.size() == 1 && r1.get(0).equals(idx))) {
							newStateSet.addAll(r1);
						}
						//加入输入此符号所到达的所有节点 经过若干空弧所到达节点
						for(GraphNode n : reachGns){
							Vector<Integer> r2 = getIdxsByGraphNodes(nfaGraph.getGraphNodesViaXEmptyInputs(n.getIndex()));
							if (!(r2.size() == 1 && r2.get(0).equals(n.getIndex()))) {
								newStateSet.addAll(r2);
							}
						}
					}// End of reachGns.size()!=0
				}// End of idx : stateSet
				
				
				if (newStateSet.size()!=0) {
					newStateSet = removeDuplicateValue(newStateSet);
					Collections.sort(newStateSet);
					if (!isMatrixHasThisObject(matrix, convertMap.get(newStateSet))) {
						setQueue.add(newStateSet);
						
						count++;
						convertMap.put(newStateSet, count);
					}
					hm.put(input, convertMap.get(newStateSet));
					
					if (newStateSet.contains(nfaGraph.getEndingNodes().get(0).getIndex())) {
						endings.add(count);
						endings = removeDuplicateValue(endings);
					}
				}
				
			}// End of for input : inputChars
			
		}// End of while true
		
		return matrix;	
	}
	
	public static boolean isMatrixHasThisObject(StateMatrix matrix, Object source){
		boolean flag = false;
		
		Vector<String> inputChars = matrix.getInputChars();
		
		for(int i=0; i<matrix.count(); i++){
			
			for(String input : inputChars){
				
				if (matrix.indexOf(i).get(input)!=null && matrix.indexOf(i).get(input).equals(source)) {
					return true;
				}
			}
		}
		
		return flag;
	}
	
	public static Vector<Integer> removeDuplicateValue(Vector<Integer> source) {
		Vector<Integer> newVector = new Vector<Integer>();
		int size = source.size();
		for (int i = 0; i < size; i++) {
			Integer value = source.get(i);
			if (!newVector.contains(value)) {
				newVector.add(value);
			}
		}
		return newVector;
	}
	
	public static Vector<Integer> getIdxsByGraphNodes(Vector<GraphNode> gnv){
		
		Vector<Integer> gnIdxs = new Vector<Integer>();
		for(GraphNode n : gnv){
			gnIdxs.add((int)n.getIndex());
		}
		Collections.sort(gnIdxs);
		
		return gnIdxs;
	}
	
	public static FAGraph matrixToFAGraph(StateMatrix matrix){
		
		FAGraph graph = new FAGraph();
		Vector<String> inputChars = matrix.getInputChars();
		
		for(int i = 0; i<matrix.count(); i++){
			
			Object state = matrix.indexOf(i).get(StateMatrix.STATE);
			
			GraphNode node = new GraphNode(state);
			graph.addNode(node);
			
		}
		
		for(int i = 0; i<matrix.count(); i++){
			
			for(String input : inputChars){
				Object toState = matrix.indexOf(i).get(input);
				if (toState!=null) {
					graph.indexOf(i).addEdgeNode(new EdgeNode((int)toState, input));
					
				}
			}
		}
		
		return graph.setInputChars(inputChars);
	}
	
	public static void log_vector(Vector<?> v){
		System.out.println("vector-----");
		for(Object o : v){
			System.out.print(o.toString());
		}
		System.out.println();
	}
	
	public static void log_list(List<?> l){
		System.out.println("list-----");
		for(Object o : l){
			System.out.print(o.toString());
		}
		System.out.println();
	}
	
}

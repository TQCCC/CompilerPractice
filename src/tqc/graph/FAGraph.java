package tqc.graph;

import java.util.*;

/**
 * 
 * @author TQC
 * */

public class FAGraph extends Graph{

	public static String EMPTY = "empty";
	
	private Vector<String> inputChars = new Vector<String>();
	
	private Vector<GraphNode> beginningNodes = new Vector<GraphNode>();
	private Vector<GraphNode> endingNodes = new Vector<GraphNode>();
	
	public FAGraph(){
		
	}
	
	public FAGraph(Vector<String> inputChars){
		this.inputChars = inputChars;
	}
	
	public FAGraph(Vector<String> inputChars, Vector<GraphNode> beginnings, Vector<GraphNode> endings){
		this.inputChars = inputChars;
		this.beginningNodes = beginnings;
		this.endingNodes = endings;
	}
	
	/**
	 * Get all GraphNodes through empty by depth first search.
	 * @author TQC
	 * */
	public Vector<GraphNode> getGraphNodesViaXEmptyInputs(int x0){
		
		Vector<GraphNode> result = new Vector<GraphNode>();
		for (int i = 0; i < count; i++) {
			visit[i] = false;
		}
		getGraphNodesViaXEmptyInputs_Step(x0, result);
		
		return result;
	}
	
	private void getGraphNodesViaXEmptyInputs_Step(int index, Vector<GraphNode> result){
		
		result.add(adjListArray[index]);
		visit[index] = true;
		
		Vector<EdgeNode> list = adjListArray[index].getAdjList();
		for(EdgeNode en : list){
			if (!visit[en.getAdjIndex()] && en.getWeight().toString().equals(EMPTY)) {
				getGraphNodesViaXEmptyInputs_Step(en.getAdjIndex(), result);
			}
		}
		
	}
	
	public Vector<GraphNode> getGraphNodesViaOneInput(int x0, Object input){
		Vector<GraphNode> result = new Vector<GraphNode>();
		
		Vector<EdgeNode> list = adjListArray[x0].getAdjList();
		for(EdgeNode en : list){
			if (en.getWeight().equals(input)) {
				result.add(adjListArray[en.getAdjIndex()]);
			}
		}
		
		return result;
	}
	
	public FAGraph setInputChars(Vector<String> inputChars) {
		this.inputChars = inputChars;
		return this;
	}
	
	public Vector<String> getInputChars() {
		return inputChars;
	}
	
	public Vector<GraphNode> getBeginningNodes() {
		return beginningNodes;
	}
	public void setBeginningNodes(Vector<GraphNode> beginningNodes) {
		this.beginningNodes = beginningNodes;
	}
	public Vector<GraphNode> getEndingNodes() {
		return endingNodes;
	}
	public void setEndingNodes(Vector<GraphNode> endingNodes) {
		this.endingNodes = endingNodes;
	}
	
	@Override
	public Graph log() {
		super.log();
		System.out.println("beginnings: ");System.out.print("[");
		for(GraphNode n : getBeginningNodes()){
			System.out.print(n.getIndex() + " ");
		}System.out.print("]");
		System.out.println();
		System.out.println("endings: ");System.out.print("[");
		for(GraphNode n : getEndingNodes()){
			System.out.print(n.getIndex() + " ");
		}System.out.println("]");
		System.out.println("--------------");
		return this;
	}
}

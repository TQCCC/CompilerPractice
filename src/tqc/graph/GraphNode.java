package tqc.graph;

import java.util.Vector;

/**
 * GraphNode
 * @author TQC
 * */

public class GraphNode {
	
	private Object vertex;		
	private int index = -1;
	private Vector<EdgeNode> adjList;
	
	public GraphNode(Object vertex){
		this.vertex = vertex;
		this.adjList = new Vector<EdgeNode>();
	}
	
	public GraphNode addEdgeNode(EdgeNode enode){
		this.adjList.add(enode);
		return this;
	}

	public Object getVertex() {
		return vertex;
	}
	
	public Vector<EdgeNode> getAdjList() {
		return adjList;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	@Override
	public String toString() {
		return "[idx:" + getIndex() + "|vtx:" + getVertex().toString() + "]";
	}
}
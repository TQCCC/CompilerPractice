package tqc.graph;

import java.util.LinkedList;
import java.util.Vector;

/**
 * Data structue graph.
 * 
 * @author TQC
 */

public class Graph {

	protected static int MAX_SIZE = 100;
	protected int count = 0;
	protected GraphNode[] adjListArray;
	protected boolean[] visit;

	public Graph() {
		adjListArray = new GraphNode[MAX_SIZE];
		visit = new boolean[MAX_SIZE];
	}

	public Vector<Object> DFS(){
		return DFS(true);
	}
	
	/**
	 * Depth first search from 0 vertex.
	 * 
	 * @return result vector.
	 */
	public Vector<Object> DFS(boolean recordVisit) {

		Vector<Object> result = new Vector<Object>();

		for (int i = 0; i < count; i++) {
			visit[i] = false;
		}

		for (int i = 0; i < count; i++) {

			if(recordVisit){
				if (!visit[i]) {
					DFS_Step(i, result, recordVisit);
				}
			}else {
				DFS_Step(i, result, recordVisit);
			}
		}

		return result;
	}

	/**
	 * @param i0 from index
	 * 
	 * */
	public Vector<Object> DFS(int i0, boolean recordVisit){
		Vector<Object> result = new Vector<Object>();

		for (int i = 0; i < count; i++) {
			visit[i] = false;
		}
		
		DFS_Step(i0, result, recordVisit);
		
		return result;
	}
	
	private void DFS_Step(int index, Vector<Object> r, boolean recordVisit) {

		r.add(adjListArray[index].getVertex());
		visit[index] = true;

		Vector<EdgeNode> list = adjListArray[index].getAdjList();
		for (EdgeNode node : list) {
			
			if (recordVisit) {
				if (!visit[node.getAdjIndex()]) {
					DFS_Step(node.getAdjIndex(), r, recordVisit);
				}
			}else {
				DFS_Step(node.getAdjIndex(), r, recordVisit);
			}
			
		}
	}

	/**
	 * Breadth first search.
	 * 
	 * @return result vector.
	 * @param i0
	 *            first node
	 */
	public Vector<Object> BFS(int i0) {

		Vector<Object> r = new Vector<Object>();

		LinkedList<Integer> queue = new LinkedList<Integer>();
		queue.add(i0);

		while (!queue.isEmpty()) {

			int i = queue.removeFirst();

			r.add(adjListArray[i].getVertex());

			visit[i] = true;

			Vector<EdgeNode> l = adjListArray[i].getAdjList();
			for (EdgeNode e : l) {
				if (!visit[e.getAdjIndex()]) {
					queue.add(e.getAdjIndex());
				}
			}
		}

		return r;
	}

	public Graph addNode(GraphNode node) {
		adjListArray[count] = node;
		node.setIndex(count);
		count++;
		return this;
	}

	public Graph log() {
		System.out.println("--------------");
		System.out.println("graph:");
		for (int i = 0; i < count; i++) {

			System.out.print(adjListArray[i].getIndex() + "::" + adjListArray[i].getVertex().toString() + "||");

			Vector<EdgeNode> list = adjListArray[i].getAdjList();
			for (EdgeNode e : list) {
				System.out.print(e.getWeight()+ ":" + e.getAdjIndex() + "->");
			}

			System.out.println();
		}
		
		return this;
	}

	public int count(){
		return count;
	}
	
	public GraphNode indexOf(int i){
		if (i>=count) {
			return null;
		}
		return adjListArray[i];
	}
	
}

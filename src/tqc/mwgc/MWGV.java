package tqc.mwgc;

import java.util.LinkedList;
import java.util.Vector;
import tqc.graph.*;

/**
 * Man Wolf Goat Cabbage problem.
 * @author TQC
 * */

public class MWGV {
	
	static Graph graph = new Graph();
	static LinkedList<GraphNode> queue = new LinkedList<GraphNode>();
	static SituationNode n = new SituationNode();
	static SituationNode movedn = new SituationNode();
	
	static GraphNode gn;
	
	public static void main(String[] args) {
		
		gn = new GraphNode(n);
		graph.addNode(gn);
		
		queue.add(gn);
		
		while(!queue.isEmpty()){
			gn = queue.removeFirst();
			n = (SituationNode)gn.getVertex();
			
			/* True if man is here, or false. */
			boolean f = (n.getThere().indexOf("m") == -1);
			
			movedn = n.move("m", f);
			if (!movedn.equals(n)) {	
				//can move one man
				judge();
			}
			
			String s = f?n.getHere().toString():n.getThere().toString();
			
			for(int ii=0;ii<s.length();ii++){
				char ch = s.charAt(ii);
				if (ch != 'm') {
					movedn = n.move("m" + ch, f);
					if (!movedn.equals(n)) {
						//can move man with ch
					
						judge();		
						
					}
				}
			}/* End of for s.length. */
			
		}/* End of while(!queue.isEmpty()). */
		graph.log();
		
		
		Vector<Object> v = graph.DFS(0, false);
		int count = 0;
		for(Object o : v){
			count++;
			System.out.println(count);
			((SituationNode)o).show();
		}
		
	}/* End of main function. */
	
	
	
	public static void judge(){
		boolean e = false;
		int i = 0;
		
		for(i=0;i<graph.count();i++){
			if (((SituationNode)graph.indexOf(i).getVertex()).equals(movedn)) {
				e = true;
				break;
			}
		}
		
		if (e) {	// has existed.
			GraphNode egn = graph.indexOf(i);
			
			if(queue.size()!=0 && (queue.getLast().equals(egn) == true)){
				gn.addEdgeNode(new EdgeNode(i));
			}
			
		}else{
			
			GraphNode newgn = new GraphNode(movedn);
			graph.addNode(newgn);
			gn.addEdgeNode(new EdgeNode(graph.count()-1));
			
			queue.add(newgn);
		}

	}
	
}

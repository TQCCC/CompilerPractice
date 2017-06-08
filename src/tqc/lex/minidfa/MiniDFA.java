package tqc.lex.minidfa;

import tqc.graph.*;
import tqc.lex.nfatodfa.NFAToDFA;

import java.util.*;

public class MiniDFA {
	
	public static FAGraph toMiniDFA(FAGraph source){
		
		FAGraph graph = new FAGraph();
		Vector<Vector<Integer>> mainSet = new Vector<Vector<Integer>>();
		
		mainSet.add(getIdxFromGns(source.getEndingNodes()));
		Vector<Integer> nonT = new Vector<Integer>();
		
		for(int i=0;i<source.count();i++){
			if (!mainSet.get(0).contains(source.indexOf(i).getIndex())) {
				nonT.add(source.indexOf(i).getIndex());
			}
		}
		mainSet.add(nonT);
		
		sortSet(mainSet);
		int f = 0;
		Vector<Integer> set = new Vector<Integer>();
		
		do {
			while(f<mainSet.size()){
				set = mainSet.get(f++);
				if (canBeDivided(source, mainSet, set)) {
					divide(source, mainSet, set, source.getInputChars());
				}
			}
		} while (canBeDividedAll(source, mainSet));
		
		sortSet(mainSet);
		graph = genericGraph(source, mainSet);
		
		return graph;
	}
	
	public static void sortSet(Vector<Vector<Integer>> mainSet){
		for (int i = 0; i < mainSet.size() - 1; i++) {
            for (int j = 0; j < mainSet.size() - 1 - i; j++) {
                if (mainSet.get(j).get(0) > mainSet.get(j+1).get(0)) {
                	Vector<Integer> temp = mainSet.get(j+1);
                	mainSet.set(j+1, mainSet.get(j));
                	mainSet.set(j, temp);
                }
            }
        }
	}
	
	public static boolean canBeDivided(FAGraph source, Vector<Vector<Integer>>mainSet, Vector<Integer> set){
		if (set.size()<2) {
			return false;
		}
		
		Vector<String> inputChars = source.getInputChars();
		Vector<Integer> reachIdx = new Vector<Integer>();
		
		for(String input : inputChars){
			
			for(int i : set){
				reachIdx.addAll(getIdxFromGns(source.getGraphNodesViaOneInput(i, input)));
			}// End of i : v
			
		}// End of input : inputChars
		
		reachIdx = NFAToDFA.removeDuplicateValue(reachIdx);
		Collections.sort(reachIdx);
		for(Vector<Integer> vi : mainSet){
			if (include(vi, reachIdx)) {
				return false;
			}	
		}
		return true;
	}
	
	public static boolean canBeDividedAll(FAGraph source, Vector<Vector<Integer>> mainSet){
		for(Vector<Integer> v : mainSet){
			if (canBeDivided(source, mainSet, v)) {
				return true;
			}
		}
		return false;
	}
	
	private static FAGraph genericGraph(FAGraph source, Vector<Vector<Integer>> mainSet){
		FAGraph graph = new FAGraph();
		graph.setInputChars(source.getInputChars());
		
		Vector<Integer> nodeSet = new Vector<Integer>();
		for(Vector<Integer> v : mainSet){
			nodeSet.add(v.get(0));
		}
		
		for(int idx : nodeSet){
			
			GraphNode node = new GraphNode(source.indexOf(idx).getVertex());
			graph.addNode(node);
			
			for(EdgeNode en : source.indexOf(idx).getAdjList()){
				if (nodeSet.contains(en.getAdjIndex())) {
					node.addEdgeNode(new EdgeNode(en.getAdjIndex(), en.getWeight()));
				}else{
					for(Vector<Integer> vv : mainSet){
						if (vv.contains(en.getAdjIndex())) {
							node.addEdgeNode(new EdgeNode(vv.get(0), en.getWeight()));
						}
					}
				}
			}// End of en : 
			
		}// End of idx : nodeSet
		
		//Add endings and beginnings
		for(int i = 0; i<graph.count(); i++){
			if (getIdxFromGns(source.getBeginningNodes()).contains(graph.indexOf(i).getIndex())) {
				graph.getBeginningNodes().add(graph.indexOf(i));
			}
		}
		
		for(int i = 0; i<graph.count(); i++){
			if (getIdxFromGns(source.getEndingNodes()).contains(graph.indexOf(i).getIndex())) {
				graph.getEndingNodes().add(graph.indexOf(i));
			}
		}
		
		
		return graph;
	}
	
	public static void divide(FAGraph source, Vector<Vector<Integer>> mainSet, Vector<Integer> set, Vector<String> inputChars){
		
		Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();
		
		mainSet.remove(set);

		Vector<Integer> reachGns = new Vector<Integer>();
		Vector<Integer> preReachGns = new Vector<Integer>();
		
		for(int idx : set){
			
			preReachGns.clear();
			preReachGns.addAll(reachGns);
			reachGns.clear();
			
			for(String input : inputChars){	
				reachGns.addAll(getIdxFromGns(source.getGraphNodesViaOneInput(idx, input)));
			}
			
			reachGns.add(idx);		// Add self. Really important.
			reachGns = NFAToDFA.removeDuplicateValue(reachGns);
			Collections.sort(reachGns);
			
			if (!preReachGns.equals(reachGns)) {
				Vector<Integer> ns = new Vector<Integer>();
				ns.add(idx);
				result.add(ns);
			}else{
				if (result.size() == 0) {
					result.add(new Vector<Integer>());
				}
				result.get(result.size()-1).add(idx);
			}
			
		}// End of input : inputChars
		
		for(Vector<Integer> v : result){
			mainSet.add(v);
		}
		
		
	}
	
	/**
	 * @return true if v1 includes v2;
	 * */
	public static boolean include(Vector<?> v1, Vector<?> v2){
		
		if (v2.size()>v1.size()) {
			return false;
		}
		
		for(Object o2 : v2){
			if (!v1.contains(o2)) {
				return false;
			}
		}
		
		return true;
	}
	
	public static Vector<Integer> getIdxFromGns(Vector<GraphNode> gns){
		
		Vector<Integer> r = new Vector<Integer>();
		for(GraphNode n : gns){
			r.add(n.getIndex());
		}
		return r;
	}
}

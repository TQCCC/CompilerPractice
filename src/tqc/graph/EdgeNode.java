package tqc.graph;

/**
 * The AdjList node. 
 * @author TQC
 * */
public class EdgeNode {
	
	private int adjIndex;
	private Object weight = -1;
		
	public EdgeNode(int adjIndex){
		this.adjIndex = adjIndex;
	}
	
	public EdgeNode(int adjIndex, Object weight){
		this.adjIndex = adjIndex;
		this.weight = weight;
	}
	
	public void setAdjIndex(int adjIndex) {
		this.adjIndex = adjIndex;
	}
	
	public int getAdjIndex() {
		return adjIndex;
	}
	
	public void setWeight(Object weight) {
		this.weight = weight;
	}
	
	public Object getWeight() {
		return weight;
	}
}

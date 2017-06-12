package tqc.syntax;

import java.util.Iterator;
import java.util.Vector;

public class Grammar {
	
	public Vector<Production> productions = new Vector<>();

	public Grammar(){
		
	}
	
	public Grammar(Production[] ps){
		for(int i=0;i<ps.length;i++){
			productions.add(ps[i]);
		}
	}
	
	public Grammar(Vector<Production> ps){
		Iterator<Production> i = ps.iterator();
		while (i.hasNext()) {
			Production production = (Production) i.next();
			productions.add(production);
		}
	
	}
	
	public void addProduction(Production p) {
		productions.add(p);
	}

	@Override
	public String toString() {

		String ret = "";

		Iterator<Production> i = productions.iterator();
		while (i.hasNext()) {
			ret += (i.next().toString() + "\n");
		}

		return ret;
	}
}

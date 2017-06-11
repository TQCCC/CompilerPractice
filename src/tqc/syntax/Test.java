package tqc.syntax;

import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
		Production p1 = new Production("A", "a+b");
		Production p2 = new Production("B", "a*b");
		Production p3 = new Production("C", "a-b");
		
		Grammar g = new Grammar();
		g.addProduction(p1);
		g.addProduction(p2);
		g.addProduction(p3);
		
		System.out.println(g);
		
	}
	
}
